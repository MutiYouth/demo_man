package com.weng.demo_man.simple_test

import com.intellij.ide.highlighter.XmlFileType
//import com.intellij.openapi.components.service
import com.intellij.psi.xml.XmlFile
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.util.PsiErrorElementUtil
//import com.weng.demo_man.services.GitStatsService

@TestDataPath("\$CONTENT_ROOT/src/test/kotlin/com/weng/demo_man/simple_test/data")
class MyPluginTest : BasePlatformTestCase() {

    fun testXMLFile() {
        val psiFile = myFixture.configureByText(XmlFileType.INSTANCE, "<foo>bar</foo>")
        val xmlFile = assertInstanceOf(psiFile, XmlFile::class.java)

        assertFalse(PsiErrorElementUtil.hasErrors(project, xmlFile.virtualFile))

        assertNotNull(xmlFile.rootTag)

        xmlFile.rootTag?.let {
            assertEquals("foo", it.name)
            assertEquals("bar", it.value.text)
        }
    }

    fun testRename() {
        myFixture.testRename("foo.xml", "foo_after.xml", "a2")
    }

//    fun testProjectService() {
//        val projectService = project.service<GitStatsService>()
//
//        assertNotSame(projectService.getRandomNumber(), projectService.getRandomNumber())
//    }

    override fun getTestDataPath() = "src/test/kotlin/com/weng/demo_man/simple_test/data"
}
