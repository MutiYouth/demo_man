// This is a generated file. Not intended for manual editing.
package com.weng.demo_man.pkg.xml.condition.psi.impl;

import java.util.List;

import com.weng.demo_man.pkg.xml.condition.psi.ROSConditionItem;
import com.weng.demo_man.pkg.xml.condition.psi.ROSConditionLogic;
import com.weng.demo_man.pkg.xml.condition.psi.ROSConditionOrder;
import com.weng.demo_man.pkg.xml.condition.psi.ROSConditionVisitor;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.weng.demo_man.pkg.xml.condition.psi.*;
import com.weng.demo_man.pkg.xml.condition.psi.impl.ROSConditionExprImpl;

public class ROSConditionOrderImpl extends ROSConditionExprImpl implements ROSConditionOrder {

  public ROSConditionOrderImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ROSConditionVisitor visitor) {
    visitor.visitOrder(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ROSConditionVisitor) accept((ROSConditionVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<ROSConditionItem> getItemList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ROSConditionItem.class);
  }

  @Override
  @NotNull
  public List<ROSConditionLogic> getLogicList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ROSConditionLogic.class);
  }

  @Override
  @NotNull
  public List<ROSConditionOrder> getOrderList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ROSConditionOrder.class);
  }

}
