package com.weng.demo_man;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * a map containing information for a bunch of usable icons in the ROS plugin
 * A useful list of icons: https://jetbrains.design/intellij/resources/config_data/inte_ros_integrate/icons_list/
 * @author Noam Dori
 */
public interface ROSIcons {
    /** 16x16 */ Icon MSG_FILE = IconLoader.getIcon("/config_data/inte_ros_integrate/icons/msgFile.png", ROSIcons.class.getClassLoader());
    /** 16x16 */ Icon SRV_FILE = IconLoader.getIcon("/config_data/inte_ros_integrate/icons/srvFile.png", ROSIcons.class.getClassLoader());
    /** 16x16 */ Icon ACT_FILE = IconLoader.getIcon("/config_data/inte_ros_integrate/icons/actionFile.png", ROSIcons.class.getClassLoader());
    /** 16x16 */ Icon SRC_PKG = IconLoader.getIcon("/config_data/inte_ros_integrate/icons/srcPackage.svg", ROSIcons.class.getClassLoader());
    /** 16x16 */ Icon LIB_PKG = IconLoader.getIcon("/config_data/inte_ros_integrate/icons/libPackage.svg", ROSIcons.class.getClassLoader());
    /** 16x16 */ Icon DEP_KEY = IconLoader.getIcon("/config_data/inte_ros_integrate/icons/rosdepKey.svg", ROSIcons.class.getClassLoader());
    /** 16x16 */ Icon CONDITION = IconLoader.getIcon("/config_data/inte_ros_integrate/icons/condition.svg", ROSIcons.class.getClassLoader());
    /** 13x13 */ Icon CATKIN = IconLoader.getIcon("/config_data/inte_ros_integrate/icons/catkin.svg", ROSIcons.class.getClassLoader());
    /** 13x13 */ Icon CATKIN_MAKE = IconLoader.getIcon("/config_data/inte_ros_integrate/icons/catkin_make.svg", ROSIcons.class.getClassLoader());
    /** 13x12 */ Icon COLCON = IconLoader.getIcon("/config_data/inte_ros_integrate/icons/colcon.svg", ROSIcons.class.getClassLoader());
    /** 16x16 */ Icon GROUP = AllIcons.Nodes.Tag;
    /** 16x16 */ Icon LIST_FILES = IconLoader.getIcon("/config_data/inte_ros_integrate/icons/listFiles.svg", ROSIcons.class.getClassLoader());
    /** 16x16 */ Icon LIST_FILES_HOVER = IconLoader.getIcon("/config_data/inte_ros_integrate/icons/listFilesHover.svg", ROSIcons.class.getClassLoader());
}
