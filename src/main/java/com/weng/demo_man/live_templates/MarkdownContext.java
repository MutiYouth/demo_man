package com.weng.demo_man.live_templates;

import com.intellij.codeInsight.template.TemplateActionContext;
import com.intellij.codeInsight.template.TemplateContextType;
import org.jetbrains.annotations.NotNull;

public class MarkdownContext extends TemplateContextType {

	protected MarkdownContext() {
		super("_Markdown",".md");
	}

	@Override
	public boolean isInContext(@NotNull TemplateActionContext templateActionContext) {
		return templateActionContext.getFile().getName().endsWith(".md");
	}

}