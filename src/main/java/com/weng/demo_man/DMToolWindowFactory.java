package com.weng.demo_man;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.*;
import com.intellij.openapi.wm.*;
import com.intellij.psi.PsiDirectory;
import com.intellij.ui.content.*;
import lombok.Data;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class DMToolWindowFactory implements ToolWindowFactory, DumbAware {

	@Override
	public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
		DMToolWindowContent toolWindowContent = new DMToolWindowContent(toolWindow);
		Content content = ContentFactory.getInstance().createContent(toolWindowContent.getContentPanel(), "", false);
		toolWindow.getContentManager().addContent(content);
	}

	@Data
	private static class DMToolWindowContent {

		private final JPanel contentPanel = new JPanel();

		public DMToolWindowContent(ToolWindow toolWindow) {
			contentPanel.setLayout(new FlowLayout());
			contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			JLabel currentDate = new JLabel();
			final String CALENDAR_ICON_PATH = "/tool_wnd/dm_tool_wnd/calendar-icon.png";
			currentDate.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(CALENDAR_ICON_PATH))));
			contentPanel.add(currentDate);
			contentPanel.add(createControlsPanel(toolWindow));
			contentPanel.add(createDMDialog());
		}

		@NotNull
		private JButton createControlsPanel(ToolWindow toolWindow) {
			JButton hideToolWindowButton = new JButton("Hide");
			hideToolWindowButton.setSize(120, 50);
			hideToolWindowButton.addActionListener(e -> toolWindow.hide(null));
			return hideToolWindowButton;
		}

		@NotNull
		private JButton createDMDialog() {
			JButton show_dlg = new JButton("Show Dialog");
			show_dlg.setSize(120, 50);
			show_dlg.addActionListener(actionEvent -> {
				DMDialog dialog = new DMDialog(null, "dialog title");
				if (dialog.showAndGet()) {
					// user pressed OK
					// System.out.println(dialog.getVar1());
					System.out.println("cccc");
				}
			});
			return show_dlg;
		}

		JPanel getContentPanel(){
			return contentPanel;
		}
	}

}
