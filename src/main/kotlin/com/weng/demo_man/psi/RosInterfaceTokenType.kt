package com.weng.demo_man.psi

import com.intellij.psi.tree.IElementType
import com.weng.demo_man.rosinterface.RosInterfaceLanguage
import org.jetbrains.annotations.NonNls

class RosInterfaceTokenType : IElementType {
    constructor(@NonNls debugName: String) : super(debugName, RosInterfaceLanguage)

    override fun toString() = "RosInterfaceTokenType." + super.toString()
}