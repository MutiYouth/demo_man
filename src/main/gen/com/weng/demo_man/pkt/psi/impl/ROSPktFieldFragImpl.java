// This is a generated file. Not intended for manual editing.
package com.weng.demo_man.pkt.psi.impl;

import com.weng.demo_man.pkt.psi.*;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.weng.demo_man.pkt.psi.*;
import com.weng.demo_man.pkt.psi.impl.ROSPktFieldBaseImpl;
import com.weng.demo_man.pkt.psi.impl.ROSPktPsiImplUtil;

public class ROSPktFieldFragImpl extends ROSPktFieldBaseImpl implements ROSPktFieldFrag {

  public ROSPktFieldFragImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ROSPktVisitor visitor) {
    visitor.visitFieldFrag(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ROSPktVisitor) accept((ROSPktVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public ROSPktConst getConst() {
    return findChildByClass(ROSPktConst.class);
  }

  @Override
  @Nullable
  public ROSPktLabel getLabel() {
    return findChildByClass(ROSPktLabel.class);
  }

  @Override
  @Nullable
  public ROSPktType getType() {
    return findChildByClass(ROSPktType.class);
  }

  @Override
  @Nullable
  public ROSPktTypeFrag getTypeFrag() {
    return findChildByClass(ROSPktTypeFrag.class);
  }

  @Override
  @NotNull
  public ROSPktTypeBase getTypeBase() {
    return ROSPktPsiImplUtil.getTypeBase(this);
  }

}
