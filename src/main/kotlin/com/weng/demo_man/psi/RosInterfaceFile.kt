package com.weng.demo_man.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.psi.FileViewProvider
import com.weng.demo_man.filesystem.Icons
import com.weng.demo_man.rosinterface.*

/*
 * https://github.com/ros2/ros2/wiki/About-ROS-Interfaces
 */

class RosInterfaceFile : PsiFileBase {
  constructor(viewProvider: FileViewProvider) : super(viewProvider, RosInterfaceLanguage)

  override fun getFileType() = RosInterfaceFileType

  override fun toString() = "ROS Interface File"

  override fun getIcon(flags: Int) = Icons.ros_msg
}