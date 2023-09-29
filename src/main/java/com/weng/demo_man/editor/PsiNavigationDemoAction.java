// Copyright 2000-2023 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package com.weng.demo_man.editor;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

public class PsiNavigationDemoAction extends AnAction {

	@Override
	public @NotNull ActionUpdateThread getActionUpdateThread() {
		return ActionUpdateThread.BGT;
	}

	@Override
	public void actionPerformed(AnActionEvent anActionEvent) {
		Editor editor = anActionEvent.getData(CommonDataKeys.EDITOR);
		PsiFile psiFile = anActionEvent.getData(CommonDataKeys.PSI_FILE);
		if (editor == null || psiFile == null) {
			return;
		}
		int offset = editor.getCaretModel().getOffset();
		final StringBuilder infoBuilder = new StringBuilder();
		PsiElement element = psiFile.findElementAt(offset);
		infoBuilder.append("Element at caret: ").append(element).append("\n");


		if (element != null) {
			// Containing method
			PsiElement containingMethod = PsiTreeUtil.getParentOfType(element, PsiElement.class);
			infoBuilder.append("Containing method: ")
					.append(containingMethod != null ? containingMethod.getText() : "none")
					.append("\n");

			// Containing class
			if (containingMethod != null) {
				PsiFile containingFile = containingMethod.getContainingFile();
				infoBuilder.append("Containing class: ")
						.append(containingFile != null ? containingFile.getName() : "none")
						.append("\n");

				infoBuilder.append("Local variables:\n");
				containingMethod.accept(new PsiElementVisitor() {
					@Override
					public void visitElement(@NotNull PsiElement variable) {
						super.visitElement(variable);
						infoBuilder.append(variable.getClass().getName()).append("\n");
					}
				});
			}
		}
		Messages.showMessageDialog(anActionEvent.getProject(), infoBuilder.toString(), "PSI Info", null);
	}

	@Override
	public void update(AnActionEvent e) {
		Editor editor = e.getData(CommonDataKeys.EDITOR);
		PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
		e.getPresentation().setEnabled(editor != null && psiFile != null);
	}

}
