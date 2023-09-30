package com.weng.demo_man.achdjian.ui

import com.intellij.openapi.util.*
import com.weng.demo_man.filesystem.Icons
import javax.swing.Icon

object ICON_NODE : NotNullLazyValue<Icon>() {
    override fun compute() = Icons.ros
}

object ICON_LAUNCH : NotNullLazyValue<Icon>() {
    override fun compute() = Icons.ros
}