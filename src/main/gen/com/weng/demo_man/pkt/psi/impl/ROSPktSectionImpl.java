// This is a generated file. Not intended for manual editing.
package com.weng.demo_man.pkt.psi.impl;

import java.util.List;

import com.weng.demo_man.pkt.psi.*;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.weng.demo_man.pkt.psi.*;
import com.weng.demo_man.pkt.psi.impl.ROSPktPsiImplUtil;

public class ROSPktSectionImpl extends ASTWrapperPsiElement implements ROSPktSection {

  public ROSPktSectionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ROSPktVisitor visitor) {
    visitor.visitSection(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ROSPktVisitor) accept((ROSPktVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<ROSPktComment> getCommentList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ROSPktComment.class);
  }

  @Override
  @NotNull
  public List<ROSPktField> getFieldList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ROSPktField.class);
  }

  @Override
  @NotNull
  public List<ROSPktFieldFrag> getFieldFragList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ROSPktFieldFrag.class);
  }

  @Override
  @NotNull
  public <T extends ROSPktFieldBase> List<T> getFields(Class<T> queryClass, boolean includeConstants) {
    return ROSPktPsiImplUtil.getFields(this, queryClass, includeConstants);
  }

}
