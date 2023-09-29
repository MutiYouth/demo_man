package com.weng.demo_man.codeglance.config

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.SimplePersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.weng.demo_man.codeglance.util.Util

@Service(Service.Level.APP)
@State(name = Util.PLUGIN_NAME, storages = [Storage("CodeGlancePro.xml")])
class CodeGlanceConfigService : SimplePersistentStateComponent<CodeGlanceConfig>(CodeGlanceConfig()) {
	companion object {
		private val ConfigInstance = ApplicationManager.getApplication().getService(CodeGlanceConfigService::class.java)

		fun getConfig(): CodeGlanceConfig = ConfigInstance.state
	}
}