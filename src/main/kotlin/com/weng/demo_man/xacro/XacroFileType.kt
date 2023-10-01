package com.weng.demo_man.xacro

import com.intellij.lang.xml.XMLLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import com.weng.demo_man.filesystem.Icons

/*
 * http://wiki.ros.org/xacro
 */

object XacroFileType : LanguageFileType(XMLLanguage.INSTANCE) {
    override fun getName() = "xacro_file_name"
    override fun getDisplayName() = "xacro_file_name"
    override fun getDescription() = "xacro_file_description"

    override fun getDefaultExtension() = "xacro"

    override fun getIcon() = Icons.ros_file
}
