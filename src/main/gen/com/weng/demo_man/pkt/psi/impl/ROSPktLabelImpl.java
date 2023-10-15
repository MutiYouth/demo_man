// This is a generated file. Not intended for manual editing.
package com.weng.demo_man.pkt.psi.impl;

import com.weng.demo_man.pkt.psi.ROSPktLabel;
import com.weng.demo_man.pkt.psi.ROSPktVisitor;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.weng.demo_man.pkt.psi.*;
import com.weng.demo_man.pkt.psi.impl.ROSPktIdentifierImpl;
import com.weng.demo_man.pkt.psi.impl.ROSPktPsiImplUtil;

public class ROSPktLabelImpl extends ROSPktIdentifierImpl implements ROSPktLabel {

  public ROSPktLabelImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ROSPktVisitor visitor) {
    visitor.visitLabel(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ROSPktVisitor) accept((ROSPktVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  public PsiElement set(String newName) {
    return ROSPktPsiImplUtil.set(this, newName);
  }

  @Override
  public String getName() {
    return ROSPktPsiImplUtil.getName(this);
  }

}
