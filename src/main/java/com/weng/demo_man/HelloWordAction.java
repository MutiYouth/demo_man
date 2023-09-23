package com.weng.demo_man;

import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiFile;

public class HelloWordAction extends AnAction {

	@Override
	public void actionPerformed(AnActionEvent e) {
		Project project = e.getData(PlatformDataKeys.PROJECT);
		Messages.showMessageDialog(project, "Say hello world ~", "Info", Messages.getInformationIcon());


		/*
		NotificationGroup notificationGroup = new NotificationGroup("testid", NotificationDisplayType.BALLOON, false);
		 *//**
		 * content :  通知内容
		 * type  ：通知的类型，warning,info,error
		 *//*
		Notification notification = notificationGroup.createNotification("测试通知", MessageType.INFO);
		Notifications.Bus.notify(notification);
		*/


		PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
		if(psiFile == null)
			return;
		String classPath = psiFile.getVirtualFile().getPath();
		Messages.showMessageDialog(project, "爷说你的路径是: " + classPath, "Hi IDEA Plugin", Messages.getInformationIcon());

	}
}
