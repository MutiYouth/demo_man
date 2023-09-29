package com.weng.demo_man.codeglance.panel

import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.runReadAction
import com.intellij.openapi.editor.EditorKind
import com.intellij.openapi.editor.colors.CodeInsightColors
import com.intellij.openapi.editor.colors.EditorColors
import com.intellij.openapi.editor.ex.RangeHighlighterEx
import com.intellij.openapi.editor.impl.EditorImpl
import com.intellij.openapi.editor.markup.HighlighterLayer
import com.intellij.openapi.editor.markup.HighlighterTargetArea
import com.intellij.openapi.fileEditor.impl.EditorsSplitters
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.Key
import com.intellij.psi.PsiDocumentManager
import com.intellij.util.Range
import com.weng.demo_man.codeglance.EditorInfo
import com.weng.demo_man.codeglance.config.CodeGlanceConfig.Companion.getWidth
import com.weng.demo_man.codeglance.config.CodeGlanceConfigService
import com.weng.demo_man.codeglance.listener.GlanceListener
import com.weng.demo_man.codeglance.listener.HideScrollBarListener
import com.weng.demo_man.codeglance.panel.scroll.CustomScrollBarPopup
import com.weng.demo_man.codeglance.panel.scroll.ScrollBar
import com.weng.demo_man.codeglance.panel.vcs.MyVcsPanel
import com.weng.demo_man.codeglance.render.BaseMinimap.Companion.getMinimap
import com.weng.demo_man.codeglance.render.MarkCommentState
import com.weng.demo_man.codeglance.render.ScrollState
import java.awt.*
import javax.swing.JPanel
import javax.swing.SwingUtilities
import kotlin.math.max
import kotlin.math.min

class GlancePanel(info: com.weng.demo_man.codeglance.EditorInfo) : JPanel(), Disposable {
	val editor = info.editor
	val project: Project
		get() = editor.project ?: ProjectManager.getInstance().defaultProject
	var originalScrollbarWidth = editor.scrollPane.verticalScrollBar.preferredSize.width
	val psiDocumentManager: PsiDocumentManager = PsiDocumentManager.getInstance(project)
	val config = CodeGlanceConfigService.getConfig()
	val scrollState = ScrollState()
	val isDefaultDisable
		get() = config.disabled || editor.document.lineCount > config.maxLinesCount
	val myPopHandler = CustomScrollBarPopup(this)
	val hideScrollBarListener = HideScrollBarListener(this)
	val scrollbar = ScrollBar(this)
	var myVcsPanel: MyVcsPanel? = null
	val markCommentState = MarkCommentState(this)
	private val minimap = editor.editorKind.getMinimap(this)
	private var isReleased = false
	init {
		Disposer.register(editor.disposable, this)
		Disposer.register(this, GlanceListener(this))
		isOpaque = false
		isVisible = !isDefaultDisable
		markCommentState.refreshMarkCommentHighlight(editor)
		editor.putUserData(CURRENT_GLANCE, this)
		editor.putUserData(CURRENT_GLANCE_PLACE_INDEX, if (info.place == BorderLayout.LINE_START) PlaceIndex.Left else PlaceIndex.Right)
		updateScrollState()
		refreshDataAndImage()
	}

	fun refresh() {
		preferredSize = if(!config.hoveringToShowScrollBar) getConfigSize() else Dimension(0,0)
		revalidate()
		repaint()
	}

	fun refreshDataAndImage() {
		preferredSize = if(!config.hoveringToShowScrollBar) getConfigSize() else Dimension(0,0)
		revalidate()
		minimap.rebuildDataAndImage()
	}

	fun updateScrollState() = scrollState.run {
		computeDimensions()
		recomputeVisible(editor.scrollingModel.visibleArea)
	}

	fun checkVisible() = !isReleased && !editor.isDisposed && (config.hoveringToShowScrollBar || isVisible)

	fun getPlaceIndex() = editor.getUserData(CURRENT_GLANCE_PLACE_INDEX) ?: PlaceIndex.Right

	fun isInSplitter() = if(editor.editorKind == EditorKind.MAIN_EDITOR){
		(SwingUtilities.getAncestorOfClass(EditorsSplitters::class.java, editor.component) as? EditorsSplitters)?.
		currentWindow?.run { inSplitter() }?: false
	}else false

	fun changeOriginScrollBarWidth(control: Boolean = true) {
		if (config.hideOriginalScrollBar && control && (!isDefaultDisable || isVisible)) {
			myVcsPanel?.apply { isVisible = true }
			editor.scrollPane.verticalScrollBar.apply { preferredSize = Dimension(0, preferredSize.height) }
		} else {
			myVcsPanel?.apply { isVisible = false }
			editor.scrollPane.verticalScrollBar.apply { preferredSize = Dimension(originalScrollbarWidth, preferredSize.height) }
		}
	}

	fun getMyRenderVisualLine(y: Int) = minimap.getMyRenderVisualLine(y)

	fun getVisibleRangeOffset(): Range<Int> {
		var startOffset = 0
		var endOffset = editor.document.textLength
		if (scrollState.visibleStart > 0) {
			val offset = editor.visualLineStartOffset(fitLineToEditor(editor, getMyRenderVisualLine(scrollState.visibleStart) - 1))
			startOffset = if (offset > 0) offset else 0
		}
		if (scrollState.visibleEnd > 0) {
			val offset = editor.visualLineStartOffset(fitLineToEditor(editor, getMyRenderVisualLine(scrollState.visibleEnd) + 1))
			endOffset = if (offset < endOffset) offset else endOffset
		}
		return Range(startOffset, endOffset)
	}

	fun RangeHighlighterEx.getMarkupColor() = getErrorStripeMarkColor(editor.colorsScheme) ?:
	(if (editor.editorKind == EditorKind.CONSOLE && textAttributesKey != CodeInsightColors.HYPERLINK_ATTRIBUTES) {
		val attributes = getTextAttributes(editor.colorsScheme)
		attributes?.foregroundColor ?: attributes?.backgroundColor
	} else null)

	fun Graphics2D.paintVcs(rangeOffset: Range<Int>,width:Int) {
		if(config.showVcsHighlight.not()) return
		composite = if (config.hideOriginalScrollBar) srcOver else srcOver0_4
		editor.filteredDocumentMarkupModel.processRangeHighlightersOverlappingWith(rangeOffset.from, rangeOffset.to) {
			if (it.isThinErrorStripeMark) it.getErrorStripeMarkColor(editor.colorsScheme)?.apply {
				val start = editor.offsetToVisualLine(it.startOffset)
				val end = editor.offsetToVisualLine(it.endOffset)
				val documentLine = minimap.getMyRenderLine(start, end)
				val sY = start * config.pixelsPerLine + documentLine.first - scrollState.visibleStart
				val eY = end * config.pixelsPerLine + documentLine.second - scrollState.visibleStart
				if (sY >= 0 || eY >= 0) {
					color = this
					if (sY == eY) {
						fillRect(0, sY, width, config.pixelsPerLine)
					} else {
						fillRect(0, sY, width, config.pixelsPerLine)
						if (eY + config.pixelsPerLine != sY) fillRect(0, sY + config.pixelsPerLine, width, eY - sY - config.pixelsPerLine)
						fillRect(0, eY, width, config.pixelsPerLine)
					}
				}
			}
			return@processRangeHighlightersOverlappingWith true
		}
	}

	private fun Graphics2D.paintSelection(existLine: MutableSet<Int>) {
		for ((index, startByte) in editor.selectionModel.blockSelectionStarts.withIndex()) {
			val endByte = editor.selectionModel.blockSelectionEnds[index]
			val start = editor.offsetToVisualPosition(startByte)
			val end = editor.offsetToVisualPosition(endByte,false,true)
			val documentLine = minimap.getMyRenderLine(start.line, end.line)

			val sX = start.column
			val sY = start.line * config.pixelsPerLine + documentLine.first - scrollState.visibleStart
			val eX = end.column + 1
			val eY = end.line * config.pixelsPerLine + documentLine.second - scrollState.visibleStart
			if (sY >= 0 || eY >= 0) {
				setGraphics2DInfo(srcOver, editor.colorsScheme.getColor(EditorColors.SELECTION_BACKGROUND_COLOR))
				// Single line is real easy
				if (start.line == end.line) {
					fillRect(sX, sY, eX - sX, config.pixelsPerLine)
				} else {
					// Draw the line leading in
					fillRect(sX, sY, width - sX, config.pixelsPerLine)
					// Then the line at the end
					fillRect(0, eY, eX, config.pixelsPerLine)
					if (eY + config.pixelsPerLine != sY) {
						// And if there is anything in between, fill it in
						fillRect(0, sY + config.pixelsPerLine, width, eY - sY - config.pixelsPerLine)
					}
				}
				existLine.addAll(start.line..end.line)
			}
		}
	}

	private fun Graphics2D.paintCaretPosition() {
		setGraphics2DInfo(srcOver, editor.colorsScheme.getColor(EditorColors.SELECTION_BACKGROUND_COLOR))
		editor.caretModel.allCarets.forEach {
			val line = it.visualPosition.line
			val documentLine = minimap.getMyRenderLine(line, line)
			val start = line * config.pixelsPerLine + documentLine.second - scrollState.visibleStart
			if (start >= 0) fillRect(0, start, width, config.pixelsPerLine)
		}
	}

	private fun Graphics2D.paintEditorFilterMarkupModel(rangeOffset: Range<Int>,existLine: MutableSet<Int>) {
		editor.filteredDocumentMarkupModel.processRangeHighlightersOverlappingWith(rangeOffset.from, rangeOffset.to) {
			if (!it.isThinErrorStripeMark && it.layer >= HighlighterLayer.CARET_ROW) {
				it.getErrorStripeMarkColor(editor.colorsScheme)?.apply {
					val highlightColor = RangeHighlightColor(it, this, config.showErrorStripesFullLineHighlight &&
							(config.hideOriginalScrollBar || HighlightInfo.fromRangeHighlighter(it) == null), existLine)
					drawMarkupLine(highlightColor)
				}
			}
			return@processRangeHighlightersOverlappingWith true
		}
	}

	private fun Graphics2D.paintEditorMarkupModel(rangeOffset: Range<Int>,existLine: MutableSet<Int>) {
		val map by lazy((LazyThreadSafetyMode.NONE)){ hashMapOf<String, Int>() }
		editor.markupModel.processRangeHighlightersOverlappingWith(rangeOffset.from, rangeOffset.to) {
			it.getMarkupColor()?.apply {
				val highlightColor = RangeHighlightColor(it, this, config.showOtherFullLineHighlight, existLine)
				map.compute("${highlightColor.startOffset}-${highlightColor.endOffset}") { _, layer ->
					if (layer == null || layer < it.layer) {
						drawMarkupLine(highlightColor)
						return@compute it.layer
					}
					return@compute layer
				}
			}
			return@processRangeHighlightersOverlappingWith true
		}
	}

	private fun Graphics2D.drawMarkupLine(it: RangeHighlightColor) {
		val start = it.startVis
		val end = it.endVis
		val documentLine = minimap.getMyRenderLine(start.line, end.line)
		var sX = if (start.column > width - MIN_GAP) width - MIN_GAP else start.column
		val sY = start.line * config.pixelsPerLine + documentLine.first - scrollState.visibleStart
		var eX = if (end.column > width - MIN_GAP) width else end.column
		val eY = end.line * config.pixelsPerLine + documentLine.second - scrollState.visibleStart
		if (sY >= 0 || eY >= 0) {
			setGraphics2DInfo(if (it.fullLine && it.fullLineWithActualHighlight) srcOver0_6 else srcOver, it.color)
			val collapsed = editor.foldingModel.getCollapsedRegionAtOffset(it.startOffset)
			if (sY == eY && collapsed == null) {
				if (it.fullLineWithActualHighlight && eX - sX < MIN_GAP) {
					if(eX == width) sX = width - MIN_GAP
					else eX += MIN_GAP - (eX - sX)
				}
				drawMarkOneLine(it, sY, sX, eX)
			} else if (collapsed != null) {
				val startVis = editor.offsetToVisualPosition(collapsed.startOffset)
				val endVis = editor.offsetToVisualPosition(collapsed.endOffset,false,true)
				drawMarkOneLine(it, sY, startVis.column, endVis.column)
			} else {
				fillRect(if (it.fullLine) 0 else sX, sY, if (it.fullLine) width else width - sX, config.pixelsPerLine)
				if (eY + config.pixelsPerLine != sY) fillRect(0, sY + config.pixelsPerLine, width, eY - sY - config.pixelsPerLine)
				fillRect(0, eY, if (it.fullLine) width else eX, config.pixelsPerLine)
			}
		}
	}

	private fun Graphics2D.drawMarkOneLine(it: RangeHighlightColor, sY: Int, sX: Int, eX: Int) {
		if (it.fullLine && it.fullLineWithActualHighlight) {
			fillRect(0, sY, width, config.pixelsPerLine)
			setGraphics2DInfo(srcOver, it.color.brighter())
			fillRect(sX, sY, eX - sX, config.pixelsPerLine)
		} else if (it.fullLine) {
			fillRect(0, sY, width, config.pixelsPerLine)
		} else {
			fillRect(sX, sY, eX - sX, config.pixelsPerLine)
		}
	}

	private fun Graphics2D.setGraphics2DInfo(al: AlphaComposite, col: Color?) {
		composite = al
		color = col
	}

	fun getConfigSize(): Dimension{
		val curWidth = editor.editorKind.getWidth()
		return Dimension(if (config.autoCalWidthInSplitterMode && isInSplitter()) {
			val calWidth = editor.component.width / 12
			if (calWidth < curWidth) {
				if (calWidth < 15) 15 else calWidth
			} else curWidth
		} else curWidth, 0)
	}

	override fun paintComponent(gfx: Graphics) {
		super.paintComponent(gfx)
		if(isReleased) return
		with(gfx as Graphics2D){
			if(hideScrollBarListener.isNotRunning()) runReadAction { paintSomething() }
			minimap.getImageOrUpdate()?.let {
				composite = srcOver0_8
				drawImage(it, 0, 0, width, scrollState.drawHeight,
					0, scrollState.visibleStart, width, scrollState.visibleEnd, null)
			}
			scrollbar.paint(this)
		}
	}

	private fun Graphics2D.paintSomething() {
		val rangeOffset = getVisibleRangeOffset()
		if (!config.hideOriginalScrollBar) paintVcs(rangeOffset,width)
		val existLine by lazy((LazyThreadSafetyMode.NONE)) { mutableSetOf<Int>() }
		if (editor.selectionModel.hasSelection()) paintSelection(existLine)
		else paintCaretPosition()
		if(config.showFilterMarkupHighlight) paintEditorFilterMarkupModel(rangeOffset,existLine)
		if(config.showMarkupHighlight) paintEditorMarkupModel(rangeOffset,existLine)
	}

	override fun dispose() {
		if(isReleased) return
		isReleased = true
		editor.putUserData(CURRENT_GLANCE, null)
		editor.putUserData(CURRENT_GLANCE_PLACE_INDEX, null)
		editor.component.remove(this.parent)
		hideScrollBarListener.dispose()
		scrollbar.clear()
		markCommentState.clear()
	}

	private inner class RangeHighlightColor(val startOffset: Int, val endOffset: Int, val color: Color, var fullLine: Boolean, val fullLineWithActualHighlight: Boolean) {
		constructor(it: RangeHighlighterEx, color: Color, fullLine: Boolean,existLine: MutableSet<Int>) :
				this(it.startOffset, it.endOffset, color, fullLine, it.targetArea == HighlighterTargetArea.EXACT_RANGE) {
			if (fullLine) {
				val elements = startVis.line..startVis.line
				if (elements.any { existLine.contains(it) }) this@RangeHighlightColor.fullLine = false
				else existLine.addAll(elements)
			}
		}

		val startVis by lazy(LazyThreadSafetyMode.NONE) { editor.offsetToVisualPosition(startOffset) }
		val endVis by lazy(LazyThreadSafetyMode.NONE) { editor.offsetToVisualPosition(endOffset,false,true) }
	}

	companion object {
		const val MIN_GAP = 15
		const val MIN_WIDTH = 30
		const val MAX_WIDTH = 250
		val CLEAR: AlphaComposite = AlphaComposite.getInstance(AlphaComposite.CLEAR)
		val srcOver0_4: AlphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.40f)
		val srcOver0_6: AlphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.60f)
		val srcOver0_8: AlphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.80f)
		val srcOver: AlphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER)
		val CURRENT_GLANCE = Key<GlancePanel>("CURRENT_GLANCE")
		val CURRENT_GLANCE_PLACE_INDEX = Key<PlaceIndex>("CURRENT_GLANCE_PLACE_INDEX")

		fun fitLineToEditor(editor: EditorImpl, visualLine: Int): Int {
			val lineCount = editor.visibleLineCount
			var shift = 0
			if (visualLine >= lineCount - 1) {
				val sequence = editor.document.charsSequence
				shift = if (sequence.isEmpty()) 0 else if (sequence[sequence.length - 1] == '\n') 1 else 0
			}
			return max(0, min(lineCount - shift, visualLine))
		}
	}

	enum class PlaceIndex{
		Left,Right
	}
}