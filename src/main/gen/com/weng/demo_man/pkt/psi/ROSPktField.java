// This is a generated file. Not intended for manual editing.
package com.weng.demo_man.pkt.psi;

import org.jetbrains.annotations.*;
import com.weng.demo_man.pkt.psi.ROSPktFieldBase;

public interface ROSPktField extends ROSPktFieldBase {

  @Nullable
  ROSPktConst getConst();

  @NotNull
  ROSPktLabel getLabel();

  @NotNull
  ROSPktType getType();

  @NotNull
  ROSPktType getTypeBase();

  boolean isComplete();

}
