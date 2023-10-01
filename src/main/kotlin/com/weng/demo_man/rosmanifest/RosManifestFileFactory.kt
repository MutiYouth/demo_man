package com.weng.demo_man.rosmanifest

import com.intellij.openapi.fileTypes.*

class RosManifestFileFactory : FileTypeFactory() {
    private object PackageFileNameMatcher : ExactFileNameMatcher(RosManifestFileType.filename)

    override fun createFileTypes(consumer: FileTypeConsumer) =
        consumer.consume(RosManifestFileType, PackageFileNameMatcher)
}