// This is a generated file. Not intended for manual editing.
package com.weng.demo_man.pkg.xml.condition.psi.impl;

import com.weng.demo_man.pkg.xml.condition.psi.ROSConditionItem;
import com.weng.demo_man.pkg.xml.condition.psi.ROSConditionVisitor;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.weng.demo_man.pkg.xml.condition.psi.*;
import com.weng.demo_man.pkg.xml.condition.psi.impl.ROSConditionExprImpl;
import com.weng.demo_man.pkg.xml.condition.psi.impl.ROSConditionImplUtil;

public class ROSConditionItemImpl extends ROSConditionExprImpl implements ROSConditionItem {

  public ROSConditionItemImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ROSConditionVisitor visitor) {
    visitor.visitItem(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ROSConditionVisitor) accept((ROSConditionVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  public boolean checkValid() {
    return ROSConditionImplUtil.checkValid(this);
  }

  @NotNull
  @Override
  public String evaluate() {
    return ROSConditionImplUtil.evaluate(this);
  }

}
