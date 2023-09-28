package com.weng.demo_man.action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.ui.Messages

class HelloKotlinAction  : DumbAwareAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.getData(PlatformDataKeys.PROJECT)
        Messages.showMessageDialog(project, "Hello from Kotlin!", "Greeting", Messages.getInformationIcon())
    }
}