package com.weng.demo_man.ros

import com.intellij.ide.IconProvider
import com.intellij.psi.*
import com.weng.demo_man.filesystem.Icons

class WorkspaceIconProvider : IconProvider() {
  override fun getIcon(e: PsiElement, f: Int) = if (isCatkinFolder(e)) Icons.workspace else null

  private fun isCatkinFolder(element: PsiElement) = element is PsiDirectory && element.name == "catkin_ws"
}