package com.weng.demo_man;

import com.intellij.notification.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import org.jetbrains.annotations.Nullable;

public class DMNotifier {
	public static void notifyError(@Nullable Project project, String content) {
		// content: 通知内容
		// type: 通知的类型，warning, info, error
		NotificationGroupManager groupManager = NotificationGroupManager.getInstance();
		Notification notification = groupManager.
				getNotificationGroup("DM Notifications").createNotification(content, MessageType.INFO);
		Notifications.Bus.notify(notification);
	}
}
