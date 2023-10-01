package com.weng.demo_man.roslaunch.runconfig

import com.intellij.execution.configurations.ConfigurationType
import com.weng.demo_man.filesystem.Icons

object RosLaunchRunConfigType : ConfigurationType {
    override fun getIcon() = Icons.ros_launch

    override fun getConfigurationTypeDescription() = "ros_launch_runconfig_description"

    override fun getId() = "ros_launch_run_config_type_id"

    override fun getDisplayName() = "ROS Launch"

    override fun getConfigurationFactories() = arrayOf(RosLaunchRunConfigFactory)
}