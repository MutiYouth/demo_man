package com.weng.demo_man.codeglance.extensions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.actionSystem.ex.ActionManagerEx
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorKind
import com.intellij.openapi.editor.markup.InspectionWidgetActionProvider
import com.weng.demo_man.codeglance.panel.GlancePanel.Companion.CURRENT_GLANCE

private class GlanceVisibleActionProvider : InspectionWidgetActionProvider {
	override fun createAction(editor: Editor): AnAction {
		return object : DefaultActionGroup(ActionManagerEx.getInstanceEx().getAction("CodeGlancePro.toggle")) {
			override fun update(e: AnActionEvent) {
				e.presentation.isEnabledAndVisible = editor.getUserData(CURRENT_GLANCE)?.run {
					editor.editorKind == EditorKind.MAIN_EDITOR && !config.hoveringToShowScrollBar && config.singleFileVisibleButton()
				} ?: false
			}

			override fun getActionUpdateThread(): ActionUpdateThread {
				return ActionUpdateThread.BGT
			}
		}
	}
}