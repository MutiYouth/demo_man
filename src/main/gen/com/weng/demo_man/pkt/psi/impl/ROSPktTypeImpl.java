// This is a generated file. Not intended for manual editing.
package com.weng.demo_man.pkt.psi.impl;

import com.weng.demo_man.pkt.psi.ROSPktType;
import com.weng.demo_man.pkt.psi.ROSPktVisitor;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.weng.demo_man.pkt.psi.*;
import com.weng.demo_man.pkt.psi.impl.ROSPktPsiImplUtil;
import com.weng.demo_man.pkt.psi.impl.ROSPktTypeBaseImpl;

public class ROSPktTypeImpl extends ROSPktTypeBaseImpl implements ROSPktType {

  public ROSPktTypeImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ROSPktVisitor visitor) {
    visitor.visitType(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ROSPktVisitor) accept((ROSPktVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  public boolean isComplete() {
    return ROSPktPsiImplUtil.isComplete(this);
  }

}
