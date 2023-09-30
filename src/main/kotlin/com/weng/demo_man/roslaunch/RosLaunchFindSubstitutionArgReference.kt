package com.weng.demo_man.roslaunch

import com.intellij.psi.*
import com.intellij.psi.xml.XmlAttributeValue
import com.weng.demo_man.util.findFilesByRelativePath

class RosLaunchFindSubstitutionArgReference : PsiReferenceBase<XmlAttributeValue> {
  constructor(element: XmlAttributeValue) : super(element, false)

  // TODO: Make this smarter
  override fun resolve(): PsiElement? {
    val attributeVal = element.value
    val relativePath = attributeVal.substringAfter("$(find ").replace(")", "")
    return findFilesByRelativePath(element.project, relativePath).firstOrNull()
  }

  override fun getVariants() = arrayOf<String>()
}