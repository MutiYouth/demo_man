package com.weng.demo_man.codeglance.util

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.weng.demo_man.codeglance.config.CodeGlanceConfigService

object Util {
	val MARK_COMMENT_ATTRIBUTES = TextAttributesKey.createTextAttributesKey("MARK_COMMENT_ATTRIBUTES")
	const val PLUGIN_NAME = "DM: CodeGlance Pro"  // 在设置页面上的名称
	var MARK_REGEX = CodeGlanceConfigService.getConfig().markRegex.run {
		if(isNotBlank()) Regex(this) else null
	}
}