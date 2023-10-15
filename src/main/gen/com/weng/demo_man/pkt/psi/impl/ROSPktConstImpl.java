// This is a generated file. Not intended for manual editing.
package com.weng.demo_man.pkt.psi.impl;

import com.weng.demo_man.pkt.psi.ROSPktConst;
import com.weng.demo_man.pkt.psi.ROSPktType;
import com.weng.demo_man.pkt.psi.ROSPktVisitor;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.weng.demo_man.pkt.psi.*;
import com.weng.demo_man.pkt.psi.impl.ROSPktPsiImplUtil;

public class ROSPktConstImpl extends ASTWrapperPsiElement implements ROSPktConst {

  public ROSPktConstImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ROSPktVisitor visitor) {
    visitor.visitConst(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ROSPktVisitor) accept((ROSPktVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public ROSPktType getBestFit() {
    return ROSPktPsiImplUtil.getBestFit(this);
  }

}
