package com.weng.demo_man.pkt.inspection;

import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import com.weng.demo_man.pkt.intention.UpdateKeytypeQuickFix;
import com.weng.demo_man.pkt.psi.ROSPktFieldBase;
import com.weng.demo_man.pkt.psi.ROSPktTypeBase;

import java.util.List;

/**
 * <p>Checks for deprecated types. There are only 2 deprecated types in ROS messages:</p>
 * <ul>
 *     <li><code>char</code>, an old alias for <code>uint8</code></li>
 *     <li><code>byte</code>, an old alias for <code>int8</code></li>
 * </ul>
 * <p>this inspection offers one fix:</p>
 * <ol>
 *     <li>convert char to uint8, and convert byte to int8</li>
 * </ol>
 * @author Noam Dori
 */
public class DeprecatedTypeInspection extends ROSPktInspectionBase {
    @Override
    protected void checkField(@NotNull ROSPktFieldBase field, @NotNull InspectionManager manager, boolean isOnTheFly, @NotNull List<ProblemDescriptor> descriptors) {
        ROSPktTypeBase type = field.getTypeBase();
        PsiElement raw = type.raw();
        if (raw.getText().equals("char") || raw.getText().equals("byte")) {
            String message = "Deprecated alias of " + (raw.getText().equals("char") ? "u" : "") + "int8";
            ProblemDescriptor descriptor = manager.createProblemDescriptor(type, type, message,
                    ProblemHighlightType.LIKE_DEPRECATED, isOnTheFly,
                    new UpdateKeytypeQuickFix());
            descriptors.add(descriptor);
        }
    }
}
