package com.weng.demo_man.achdjian.ui

import com.intellij.ui.ColoredListCellRenderer
import com.weng.demo_man.achdjian.data.RosNode
import javax.swing.JList

class RosNodeListCellRenderer : ColoredListCellRenderer<RosNode>() {
    override fun customizeCellRenderer(list: JList<out RosNode>, rosNode: RosNode?, index: Int, selected: Boolean, hasFocus: Boolean) {
        rosNode?.let { append(it.name) }
    }
}