package com.weng.demo_man.achdjian.ui

import com.intellij.openapi.util.IconLoader
import com.intellij.ui.*
import com.weng.demo_man.achdjian.data.RosVersionImpl
import com.weng.demo_man.filesystem.Icons
import javax.swing.JList

class RosVersionListCellRenderer : ColoredListCellRenderer<RosVersionImpl>() {
    override fun customizeCellRenderer(list: JList<out RosVersionImpl>, value: RosVersionImpl?, index: Int, selected: Boolean, hasFocus: Boolean) {
        value?.let {
            icon = Icons.ros
            append(it.name)
            append(it.path, SimpleTextAttributes.GRAYED_SMALL_ATTRIBUTES)
        }
    }
}