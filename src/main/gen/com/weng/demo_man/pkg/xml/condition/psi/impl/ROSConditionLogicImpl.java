// This is a generated file. Not intended for manual editing.
package com.weng.demo_man.pkg.xml.condition.psi.impl;

import com.weng.demo_man.pkg.xml.condition.psi.ROSConditionLogic;
import com.weng.demo_man.pkg.xml.condition.psi.ROSConditionVisitor;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.weng.demo_man.pkg.xml.condition.psi.*;

public class ROSConditionLogicImpl extends ASTWrapperPsiElement implements ROSConditionLogic {

  public ROSConditionLogicImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ROSConditionVisitor visitor) {
    visitor.visitLogic(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ROSConditionVisitor) accept((ROSConditionVisitor)visitor);
    else super.accept(visitor);
  }

}
