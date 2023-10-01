package com.weng.demo_man.ros

import com.intellij.ide.IconProvider
import com.intellij.psi.*
import com.weng.demo_man.filesystem.Icons

class LaunchDirIconProvider : IconProvider() {
    override fun getIcon(e: PsiElement, f: Int) = if (isLaunchDir(e) && hasLaunchFiles(e)) Icons.launch_dir else null

    private fun isLaunchDir(element: PsiElement) = element is PsiDirectory && element.name == "launch"

    private fun hasLaunchFiles(element: PsiElement) =
        (element as PsiDirectory).files.any { it.name.endsWith(".launch") }
}