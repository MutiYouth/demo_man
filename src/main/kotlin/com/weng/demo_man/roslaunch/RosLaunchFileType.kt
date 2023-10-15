package com.weng.demo_man.roslaunch

import com.intellij.lang.xml.XMLLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import com.weng.demo_man.filesystem.Icons

/*
 *  http://wiki.ros.org/Manifest
 */

object RosLaunchFileType : LanguageFileType(XMLLanguage.INSTANCE) {
    override fun getName() = "roslaunch"
    override fun getDescription() = "ros launch file"
    override fun getDisplayName() = "roslaunch"
    override fun getDefaultExtension() = "launch"

    override fun getIcon() = Icons.ros_launch
}
