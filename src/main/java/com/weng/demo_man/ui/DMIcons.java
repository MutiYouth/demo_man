package com.weng.demo_man.ui;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.AnimatedIcon;

import javax.swing.*;

public interface DMIcons {

	// Node, Action, Filetype: 16x16
	// toolWindows: 13*13
	// Editor gutter: 12*12
	// https://bjansen.github.io/intellij-icon-generator/

	Icon CalendarIcon = IconLoader.getIcon("/tool_wnd/dm_tool_wnd/calendar-icon.png", DMIcons.class);
	Icon TimeIcon = IconLoader.getIcon("/tool_wnd/dm_tool_wnd/time-icon.png", DMIcons.class);
	Icon TimeZoneIcon = IconLoader.getIcon("/tool_wnd/dm_tool_wnd/time-zone-icon.png", DMIcons.class);
	Icon DMIcon = IconLoader.getIcon("/tool_wnd/dm_tool_wnd/dm.svg", DMIcons.class);

	// animated Icons
	AnimatedIcon icon = new AnimatedIcon(500, AllIcons.Ide.Macro.Recording_1, AllIcons.Ide.Macro.Recording_2);
}
