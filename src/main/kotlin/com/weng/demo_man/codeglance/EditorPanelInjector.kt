package com.weng.demo_man.codeglance

import com.intellij.diff.DiffContext
import com.intellij.diff.DiffExtension
import com.intellij.diff.FrameDiffTool
import com.intellij.diff.requests.DiffRequest
import com.intellij.diff.tools.fragmented.UnifiedDiffViewer
import com.intellij.diff.tools.util.side.OnesideTextDiffViewer
import com.intellij.diff.tools.util.side.ThreesideTextDiffViewer
import com.intellij.diff.tools.util.side.TwosideTextDiffViewer
import com.intellij.ide.ui.LafManager
import com.intellij.ide.ui.LafManagerListener
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.EditorKind
import com.intellij.openapi.editor.event.EditorFactoryEvent
import com.intellij.openapi.editor.event.EditorFactoryListener
import com.intellij.openapi.editor.impl.EditorImpl
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.Key
import com.intellij.ui.components.JBPanel
import com.weng.demo_man.codeglance.config.CodeGlanceConfigService
import com.weng.demo_man.codeglance.config.SettingsChangeListener
import com.weng.demo_man.codeglance.panel.GlancePanel
import com.weng.demo_man.codeglance.panel.vcs.MyVcsPanel
import java.awt.BorderLayout
import java.awt.Color
import javax.swing.JPanel

val CURRENT_EDITOR_DIFF_VIEW = Key<FrameDiffTool.DiffViewer>("CURRENT_EDITOR_DIFF_VIEW")

class EditorPanelInjector : EditorFactoryListener {
    override fun editorCreated(event: EditorFactoryEvent) {
        if(event.editor.editorKind == EditorKind.DIFF) return
        val editorImpl = event.editor as? EditorImpl ?: return
        com.weng.demo_man.codeglance.firstRunEditor(
            com.weng.demo_man.codeglance.EditorInfo(
                editorImpl, if (CodeGlanceConfigService.getConfig().isRightAligned)
                    BorderLayout.LINE_END else BorderLayout.LINE_START
            ), null
        )
    }

    override fun editorReleased(event: EditorFactoryEvent) {
        event.editor.putUserData(com.weng.demo_man.codeglance.CURRENT_EDITOR_DIFF_VIEW, null)
    }
}

class DiffEditorPanelInjector : DiffExtension(){
    override fun onViewerCreated(viewer: FrameDiffTool.DiffViewer, context: DiffContext, request: DiffRequest) = viewer.diffEditorInjector()
}

class GlobalSettingsChangeListener : SettingsChangeListener{
    override fun onGlobalChanged() {
        com.weng.demo_man.codeglance.processAllGlanceEditor { oldGlance, info ->
            oldGlance?.apply { Disposer.dispose(this) }
            if (info.editor.isDisableExtensionFile() || !CodeGlanceConfigService.getConfig().editorKinds.contains(info.editor.editorKind)) {
                oldGlance?.changeOriginScrollBarWidth(false)
            } else {
                if (info.editor.editorKind == EditorKind.DIFF) {
                    info.editor.getUserData(com.weng.demo_man.codeglance.CURRENT_EDITOR_DIFF_VIEW)
                        ?.apply { diffEditorInjector() }
                } else {
                    com.weng.demo_man.codeglance.setMyPanel(info).apply {
                        oldGlance?.let { originalScrollbarWidth = it.originalScrollbarWidth }
                        changeOriginScrollBarWidth()
                    }
                }
            }
        }
    }
}

class GlobalLafManagerListener : LafManagerListener {
    private var isFirstSetup = true

    override fun lookAndFeelChanged(source: LafManager) = if(isFirstSetup) isFirstSetup = false else {
        com.weng.demo_man.codeglance.processAllGlanceEditor { oldGlance, _ -> oldGlance?.apply { refreshDataAndImage() } }
    }
}

private fun firstRunEditor(info: com.weng.demo_man.codeglance.EditorInfo, diffView: FrameDiffTool.DiffViewer?) {
    if(diffView != null) info.editor.putUserData(com.weng.demo_man.codeglance.CURRENT_EDITOR_DIFF_VIEW, diffView)
    if(info.editor.isDisableExtensionFile() || !CodeGlanceConfigService.getConfig().editorKinds.contains(info.editor.editorKind)) {
        return
    }
    val layout = (info.editor.component as? JPanel)?.layout
    if (layout is BorderLayout && layout.getLayoutComponent(info.place) == null) {
        com.weng.demo_man.codeglance.setMyPanel(info).apply { changeOriginScrollBarWidth() }
    }
}

private fun FrameDiffTool.DiffViewer.diffEditorInjector() {
    val config = CodeGlanceConfigService.getConfig()
    val where = if (config.isRightAligned) BorderLayout.LINE_END else BorderLayout.LINE_START
    when (this) {
        is UnifiedDiffViewer -> if(editor is EditorImpl) {
            com.weng.demo_man.codeglance.firstRunEditor(
                com.weng.demo_man.codeglance.EditorInfo(
                    editor as EditorImpl,
                    where
                ), this
            )
        }
        is OnesideTextDiffViewer -> if(editor is EditorImpl) {
            com.weng.demo_man.codeglance.firstRunEditor(
                com.weng.demo_man.codeglance.EditorInfo(
                    editor as EditorImpl,
                    where
                ), this
            )
        }
        is TwosideTextDiffViewer -> if(config.diffTwoSide) {
            editors.filterIsInstance<EditorImpl>().forEachIndexed { index, editor ->
                com.weng.demo_man.codeglance.firstRunEditor(
                    com.weng.demo_man.codeglance.EditorInfo(
                        editor,
                        if (index == 0) BorderLayout.LINE_START else BorderLayout.LINE_END
                    ), this
                )
            }
        }
        is ThreesideTextDiffViewer -> if(config.diffThreeSide) {
            editors.filterIsInstance<EditorImpl>().forEachIndexed { index, editor ->
                if (index != 1 || config.diffThreeSideMiddle) {
                    com.weng.demo_man.codeglance.firstRunEditor(
                        com.weng.demo_man.codeglance.EditorInfo(
                            editor,
                            if (index == 0) BorderLayout.LINE_START else BorderLayout.LINE_END
                        ), this
                    )
                }
            }
        }
    }
}

private fun processAllGlanceEditor(action: (oldGlance:GlancePanel?, com.weng.demo_man.codeglance.EditorInfo)->Unit){
    try {
        val where = if (CodeGlanceConfigService.getConfig().isRightAligned) BorderLayout.LINE_END else BorderLayout.LINE_START
        for (editor in EditorFactory.getInstance().allEditors.filterIsInstance<EditorImpl>()) {
            val info = com.weng.demo_man.codeglance.EditorInfo(editor, where)
            val layout = (info.editor.component as? JPanel)?.layout
            if (layout is BorderLayout) {
                action(((layout.getLayoutComponent(BorderLayout.LINE_END) ?:
                layout.getLayoutComponent(BorderLayout.LINE_START)) as? com.weng.demo_man.codeglance.MyPanel)?.panel, info)
            }
        }
    }catch (e:Exception){
        e.printStackTrace()
    }
}

private fun setMyPanel(info: com.weng.demo_man.codeglance.EditorInfo): GlancePanel {
    val glancePanel = GlancePanel(info)
    info.editor.apply{ component.add(com.weng.demo_man.codeglance.MyPanel(glancePanel), info.place) }
    glancePanel.hideScrollBarListener.addHideScrollBarListener()
    return glancePanel
}

private fun EditorImpl.isDisableExtensionFile(): Boolean{
    val extension = (virtualFile ?: FileDocumentManager.getInstance().getFile(document))?.run { fileType.defaultExtension } ?: ""
    return extension.isNotBlank() && CodeGlanceConfigService.getConfig().disableLanguageSuffix.split(",").toSet().contains(extension)
}

internal class MyPanel(val panel: GlancePanel?): JBPanel<com.weng.demo_man.codeglance.MyPanel>(BorderLayout()){
    init{
        add(panel!!)
        if (CodeGlanceConfigService.getConfig().hideOriginalScrollBar){
            panel.myVcsPanel = MyVcsPanel(panel)
            add(panel.myVcsPanel!!, if (panel.getPlaceIndex() == GlancePanel.PlaceIndex.Left) BorderLayout.EAST else BorderLayout.WEST)
        }
    }

    override fun getBackground(): Color? = panel?.run { editor.contentComponent.background } ?: super.getBackground()
}

data class EditorInfo(val editor: EditorImpl, val place: String)