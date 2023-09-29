package com.weng.demo_man.codeglance.util

import com.intellij.AbstractBundle
import com.intellij.DynamicBundle
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.PropertyKey
import java.util.*

@NonNls
const val BUNDLE: String = "config_data.inte_cgp.messages.CodeGlanceBundle"

object CodeGlanceBundle : AbstractBundle(BUNDLE) {
	private val adaptedControl = ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_PROPERTIES)

	private val adaptedBundle: AbstractBundle? by lazy {
		val dynamicLocale = DynamicBundle.getLocale()
		if (dynamicLocale.toLanguageTag() == Locale.ENGLISH.toLanguageTag()) {
			object : AbstractBundle(BUNDLE) {
				override fun findBundle(pathToBundle: String, loader: ClassLoader, control: ResourceBundle.Control): ResourceBundle {
					val dynamicBundle = ResourceBundle.getBundle(pathToBundle, dynamicLocale, loader, adaptedControl)
					return dynamicBundle ?: super.findBundle(pathToBundle, loader, control)
				}
			}
		} else null
	}

	override fun findBundle(pathToBundle: String, loader: ClassLoader, control: ResourceBundle.Control): ResourceBundle =
		DynamicBundle.getLocale().let { ResourceBundle.getBundle(pathToBundle, it, loader, control) }
			?: super.findBundle(pathToBundle, loader, control)

	fun getAdaptedMessage(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any): String {
		return adaptedBundle?.getMessage(key, *params) ?: getMessage(key, *params)
	}
}

fun message(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any): String {
	return CodeGlanceBundle.getAdaptedMessage(key, *params)
}

fun localMessage(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any): String {
	return CodeGlanceBundle.getMessage(key, *params)
}