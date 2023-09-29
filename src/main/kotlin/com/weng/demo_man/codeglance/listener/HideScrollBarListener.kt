package com.weng.demo_man.codeglance.listener

import com.intellij.openapi.Disposable
import com.intellij.util.Alarm
import com.intellij.util.animation.JBAnimator
import com.intellij.util.animation.animation
import com.weng.demo_man.codeglance.panel.GlancePanel
import java.awt.Dimension
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

@Suppress("UnstableApiUsage")
class HideScrollBarListener(private val glancePanel: GlancePanel) : MouseAdapter(), Disposable {
	private var animationId = -1L
	private val animator by lazy {
		JBAnimator(glancePanel).apply {
			name = "Minimap Width Animator"
			period = 5
			ignorePowerSaveMode()
		}
	}
	private val alarm by lazy { Alarm(glancePanel) }
	private val checkHide
		get()= glancePanel.config.hoveringToShowScrollBar && glancePanel.width > 0
				&& !glancePanel.myPopHandler.isVisible && glancePanel.scrollbar.isNotHoverScrollBar()

	override fun mouseEntered(e: MouseEvent) {
		if(glancePanel.width == 0) {
			val delay = glancePanel.config.delayHoveringToShowScrollBar
			val action = { start(glancePanel.getConfigSize().width) }
			if(delay > 0) {
				alarm.cancelAllRequests()
				alarm.addRequest(action, delay)
			} else action.invoke()
		}
	}

	override fun mouseExited(e: MouseEvent) {
		if (glancePanel.config.delayHoveringToShowScrollBar > 0 &&
			animator.isRunning(animationId).not() && glancePanel.width == 0){
			alarm.cancelAllRequests()
		}
	}

	fun hideGlanceRequest(delay : Int = 500) {
		if (checkHide) {
			alarm.cancelAllRequests()
			alarm.addRequest({if (checkHide) start(0) },delay)
		}
	}

	fun isNotRunning() = !(glancePanel.config.hoveringToShowScrollBar && animator.isRunning(animationId))

	private fun start(to: Int) {
		if (animator.isRunning(animationId).not()){
			animationId = animator.animate(
				animation(glancePanel.preferredSize, Dimension(to, 0),glancePanel::setPreferredSize).apply {
					duration = 300
					runWhenUpdated {
						glancePanel.revalidate()
						glancePanel.repaint()
					}
					runWhenScheduled {
						showHideOriginScrollBar(to == 0)
					}
					runWhenExpiredOrCancelled {
						if(to != 0) {
							hideGlanceRequest(1000)
						}
					}
				}
			)
		}
	}

	private fun showHideOriginScrollBar(show : Boolean){
		if(!glancePanel.config.hideOriginalScrollBar){
			if(show) glancePanel.editor.scrollPane.verticalScrollBar.apply {
				preferredSize = Dimension(glancePanel.originalScrollbarWidth, preferredSize.height)
			} else glancePanel.editor.scrollPane.verticalScrollBar.apply {
				preferredSize = Dimension(0, preferredSize.height)
			}
		}
	}

	fun addHideScrollBarListener() {
		glancePanel.apply {
			if (config.hoveringToShowScrollBar && !isDefaultDisable) {
				if (!config.hideOriginalScrollBar) editor.scrollPane.verticalScrollBar.addMouseListener(hideScrollBarListener)
				else myVcsPanel?.addMouseListener(hideScrollBarListener)
				if(checkVisible()) start(0)
			}
		}
	}

	fun removeHideScrollBarListener() {
		dispose()
		alarm.cancelAllRequests()
		animator.stop()
		glancePanel.apply {
			showHideOriginScrollBar(true)
			if(checkVisible()) refresh()
		}
	}

	override fun dispose() {
		if (!glancePanel.config.hideOriginalScrollBar) {
			glancePanel.editor.scrollPane.verticalScrollBar.removeMouseListener(this@HideScrollBarListener)
		}else {
			glancePanel.myVcsPanel?.removeMouseListener(this@HideScrollBarListener)
		}
	}
}