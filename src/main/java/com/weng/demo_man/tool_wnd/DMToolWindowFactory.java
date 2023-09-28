package com.weng.demo_man.tool_wnd;

import com.intellij.openapi.project.*;
import com.intellij.openapi.wm.*;
import com.intellij.ui.content.*;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
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

		private final JLabel currentDate = new JLabel();
		private final JLabel timeZone = new JLabel();
		private final JLabel currentTime = new JLabel();

		public DMToolWindowContent(ToolWindow toolWindow) {
			contentPanel.setLayout(new BorderLayout(0, 20)); // new FlowLayout()
			contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));

			contentPanel.add(createCalendarPanel(), BorderLayout.PAGE_START);
			contentPanel.add(createControlsPanel(toolWindow), BorderLayout.CENTER);

			contentPanel.add(createDMDialog(), BorderLayout.SOUTH);
		}

		@NotNull
		private JPanel createCalendarPanel() {
			final String CALENDAR_ICON_PATH = "/tool_wnd/dm_tool_wnd/calendar-icon.png";
			final String TIME_ZONE_ICON_PATH = "/tool_wnd/dm_tool_wnd/time-zone-icon.png";
			final String TIME_ICON_PATH = "/tool_wnd/dm_tool_wnd/time-icon.png";

			JPanel calendarPanel = new JPanel();
			setIconLabel(currentDate, CALENDAR_ICON_PATH);
			setIconLabel(timeZone, TIME_ZONE_ICON_PATH);
			setIconLabel(currentTime, TIME_ICON_PATH);
			calendarPanel.add(currentDate);
			calendarPanel.add(timeZone);
			calendarPanel.add(currentTime);
			return calendarPanel;
		}

		@NotNull
		private JPanel createControlsPanel(ToolWindow toolWindow) {
			JPanel controlsPanel = new JPanel();
			JButton refreshDateAndTimeButton = new JButton("Refresh");
			refreshDateAndTimeButton.addActionListener(e -> updateCurrentDateTime());
			controlsPanel.add(refreshDateAndTimeButton);
			JButton hideToolWindowButton = new JButton("Hide");
			hideToolWindowButton.addActionListener(e -> toolWindow.hide(null));
			controlsPanel.add(hideToolWindowButton);
			return controlsPanel;
		}

		@NotNull
		private JPanel createDMDialog() {
			JPanel testPanel = new JPanel();
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
			testPanel.add(show_dlg);
			return testPanel;
		}

		JPanel getContentPanel(){
			return contentPanel;
		}

		private void setIconLabel(JLabel label, String imagePath) {
			label.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath))));
		}

		private void updateCurrentDateTime() {
			Calendar calendar = Calendar.getInstance();
			currentDate.setText(getCurrentDate(calendar));
			timeZone.setText(getTimeZone(calendar));
			currentTime.setText(getCurrentTime(calendar));
		}

		private String getCurrentDate(Calendar calendar) {
			return calendar.get(Calendar.DAY_OF_MONTH) + "/"
					+ (calendar.get(Calendar.MONTH) + 1) + "/"
					+ calendar.get(Calendar.YEAR);
		}

		private String getTimeZone(Calendar calendar) {
			long gmtOffset = calendar.get(Calendar.ZONE_OFFSET); // offset from GMT in milliseconds
			String gmtOffsetString = String.valueOf(gmtOffset / 3600000);
			return (gmtOffset > 0) ? "GMT + " + gmtOffsetString : "GMT - " + gmtOffsetString;
		}

		private String getCurrentTime(Calendar calendar) {
			return getFormattedValue(calendar, Calendar.HOUR_OF_DAY) + ":" + getFormattedValue(calendar, Calendar.MINUTE);
		}

		private String getFormattedValue(Calendar calendar, int calendarField) {
			int value = calendar.get(calendarField);
			return StringUtils.leftPad(Integer.toString(value), 2, "0");
		}
	}

}
