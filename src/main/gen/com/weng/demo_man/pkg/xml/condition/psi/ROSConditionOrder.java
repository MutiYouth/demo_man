// This is a generated file. Not intended for manual editing.
package com.weng.demo_man.pkg.xml.condition.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.weng.demo_man.pkg.xml.condition.psi.ROSConditionExpr;

public interface ROSConditionOrder extends ROSConditionExpr {

  @NotNull
  List<ROSConditionItem> getItemList();

  @NotNull
  List<ROSConditionLogic> getLogicList();

  @NotNull
  List<ROSConditionOrder> getOrderList();

}
