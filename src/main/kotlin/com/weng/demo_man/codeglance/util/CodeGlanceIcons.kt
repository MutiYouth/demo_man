package com.weng.demo_man.codeglance.util

import com.intellij.ui.IconManager
import com.intellij.ui.RoundedIcon
import com.intellij.util.IconUtil
import com.intellij.util.ImageLoader
import com.intellij.util.ui.JBImageIcon
import javax.swing.Icon

object CodeGlanceIcons {
	val GlanceShow = load("/config_data/inte_cgp/icons/glanceShow.svg")

	val GlanceHide = load("/config_data/inte_cgp/icons/glanceHide.svg")

	val Widget = load("/config_data/inte_cgp/icons/widget.svg")

	private fun load(path: String): Icon {
		return IconManager.getInstance().getIcon(path, CodeGlanceIcons::class.java)  // .classLoader
	}

	@Suppress("UnstableApiUsage")
	fun loadRoundImageIcon(path: String): RoundedIcon {
		return RoundedIcon(ImageLoader.loadFromResource(path, CodeGlanceIcons::class.java)!!,50.0)
	}

	fun loadImageIcon(path: String): JBImageIcon {
		return JBImageIcon(ImageLoader.loadFromResource(path, CodeGlanceIcons::class.java)!!)
	}

	fun scaleIcon(icon: Icon, scale: Float): Icon {
		return IconUtil.scale(icon, null, scale)
	}
}