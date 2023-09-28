package com.weng.demo_man.tool_wnd;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;


public class DMDialog extends DialogWrapper {
	@Getter
	private String var1;
	@Getter
	private String var2;

	protected DMDialog(@Nullable Project project, String title) {
		super(project);
		init();
		setTitle(title);
		setSize(350, 400);
	}
	
	private JPanel center = new JPanel();
	private JPanel south = new JPanel();

	{
		setSize(600, 360); // dlg size
	}

	private final JLabel var1Label = new JLabel("Drop Down Combo");
	private final JComboBox<Object> var1Box = new JComboBox<>();
	private final JLabel var2Label = new JLabel("文本输入");
	private final JTextField var2Text = new JTextField();

	@Override
	protected @Nullable
	JComponent createCenterPanel() {    // 面板布局
		GridLayout gridLayout = new GridLayout(4, 1);
		center.setLayout(gridLayout);
		var1Box.addItem("下拉选项");    // 面板内容
		center.add(var1Label);
		center.add(var1Box);
		center.add(var2Label);
		center.add(var2Text);
		return center;
	}

	@Override
	protected JComponent createSouthPanel() {
		JButton button = new JButton("提交");
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setVerticalAlignment(SwingConstants.CENTER);
		south.add(button);    // 事件绑定
		button.addActionListener(e -> {
			this.var1 = var1Box.getSelectedItem().toString();
			this.var2 = var2Text.getText();
			dispose();
		});
		return south;
	}

}
