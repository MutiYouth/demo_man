// This is a generated file. Not intended for manual editing.
package com.weng.demo_man.pkt.psi.impl;

import com.weng.demo_man.pkt.psi.ROSPktTypeFrag;
import com.weng.demo_man.pkt.psi.ROSPktVisitor;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.weng.demo_man.pkt.psi.*;
import com.weng.demo_man.pkt.psi.impl.ROSPktTypeBaseImpl;

public class ROSPktTypeFragImpl extends ROSPktTypeBaseImpl implements ROSPktTypeFrag {

  public ROSPktTypeFragImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ROSPktVisitor visitor) {
    visitor.visitTypeFrag(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ROSPktVisitor) accept((ROSPktVisitor)visitor);
    else super.accept(visitor);
  }

}
