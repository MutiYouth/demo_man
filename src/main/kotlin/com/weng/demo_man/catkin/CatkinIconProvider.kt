package com.weng.demo_man.catkin

import com.intellij.ide.IconProvider
import com.intellij.psi.*
import com.weng.demo_man.filesystem.Icons

class CatkinIconProvider : IconProvider() {
  override fun getIcon(element: PsiElement, flags: Int) =
    if (element is PsiDirectory && element.hasPackageXml()) Icons.catkin_file else null

  private fun PsiDirectory.hasPackageXml() = files.any { it.name == "package.xml" }
}