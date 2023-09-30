package com.weng.demo_man.achdjian.settings

import java.io.File

data class InitWorkspaceCmd(val executableFile: File, val args: String?) {
    override fun toString(): String {
        val cmd = executableFile.toString() + " " + args
        return cmd.trim()
    }
}


