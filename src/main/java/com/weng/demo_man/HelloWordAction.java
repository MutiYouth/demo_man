package com.weng.demo_man;

import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class HelloWordAction extends AnAction {
	@Override
	public void update(@NotNull final AnActionEvent event) {
		Project project = event.getProject();
		final Editor editor = event.getData(CommonDataKeys.EDITOR);
		boolean visibility = project != null && editor != null;
		event.getPresentation().setEnabledAndVisible(visibility);
	}

	@Override
	public void actionPerformed(AnActionEvent e) {
		Project project1 = e.getData(PlatformDataKeys.PROJECT);
		Messages.showMessageDialog(project1, "<br>Say hello<br/> world ~", "Info", Messages.getInformationIcon());


		DMNotifier.notifyError(null, "Hi Balloons Notifications.<br/> 23.9.24");


		// 得到当前文件在项目中的信息
		Project project = e.getProject();
		final Editor editor = e.getData(CommonDataKeys.EDITOR);
		if (project == null || editor == null) {
			return;
		}
		Document document = editor.getDocument();
		FileDocumentManager fileDocumentManager = FileDocumentManager.getInstance();
		VirtualFile virtualFile = fileDocumentManager.getFile(document);
		ProjectFileIndex projectFileIndex = ProjectRootManager.getInstance(project).getFileIndex();
		if (virtualFile != null) {
			Module module = projectFileIndex.getModuleForFile(virtualFile);
			String moduleName;
			moduleName = module != null ? module.getName() : "No module defined for file";

			VirtualFile moduleContentRoot = projectFileIndex.getContentRootForFile(virtualFile);
			boolean isLibraryFile = projectFileIndex.isLibraryClassFile(virtualFile);
			boolean isInLibraryClasses = projectFileIndex.isInLibraryClasses(virtualFile);
			boolean isInLibrarySource = projectFileIndex.isInLibrarySource(virtualFile);
			Messages.showInfoMessage("Module: " + moduleName + "\n" +
							"Module content root: " + moduleContentRoot + "\n" +
							"Is library file: " + isLibraryFile + "\n" +
							"Is in library classes: " + isInLibraryClasses +
							", Is in library source: " + isInLibrarySource,
					"Main File Info for" + virtualFile.getName());
		}
	}
}
