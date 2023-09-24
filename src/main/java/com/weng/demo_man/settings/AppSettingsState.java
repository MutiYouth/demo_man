package com.weng.demo_man.settings;


import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@State(
		name = "com.weng.demo_man.settings.AppSettingsState",
		storages = @Storage("DM_SdkSettingsPlugin.xml")
)
// 如本机中，保存在: D:\Pros\IDE\Cache\.CLion-2020.1\config\options\DM_SdkSettingsPlugin.xml
public class AppSettingsState implements PersistentStateComponent<AppSettingsState> {

	public String userId = "Murphe Blue Public";
	public boolean ideaStatus = false;

	public static AppSettingsState getInstance() {
		return ApplicationManager.getApplication().getService(AppSettingsState.class);
	}

	@Nullable
	@Override
	public AppSettingsState getState() {
		return this;
	}

	@Override
	public void loadState(@NotNull AppSettingsState state) {
		XmlSerializerUtil.copyBean(state, this);
	}

}
