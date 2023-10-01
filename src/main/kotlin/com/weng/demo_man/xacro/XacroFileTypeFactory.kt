package com.weng.demo_man.xacro

import com.intellij.openapi.fileTypes.*

class XacroFileTypeFactory : FileTypeFactory() {
    override fun createFileTypes(consumer: FileTypeConsumer) = consumer.consume(XacroFileType, "xacro")
}