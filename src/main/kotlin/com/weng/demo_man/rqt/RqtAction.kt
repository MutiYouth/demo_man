package com.weng.demo_man.rqt

import com.intellij.openapi.actionSystem.*
import com.weng.demo_man.settings.RosConfig

class RqtAction : AnAction() {
  override fun actionPerformed(e: AnActionEvent) {
    e.actionManager.getId(this)?.let {
      RosConfig.rosSettings.localRos.runInBackground(it)
    }
  }
}