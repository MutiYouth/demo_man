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
		Messages.showMessageDialog(project, "<br>Say hello<br/> world ~", "Info", Messages.getInformationIcon());


		DMNotifier.notifyError(null, "Hi Balloons Notifications.<br/> 23.9.24");


		PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
		if(psiFile == null)
			return;
		String classPath = psiFile.getVirtualFile().getPath();
		Messages.showMessageDialog(project, "爷说你的路径: " + classPath, "Hi IDEA Plugin", Messages.getInformationIcon());

	}
}
