package com.weng.demo_man.pkg.xml.condition;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.weng.demo_man.ROSIcons;
import com.weng.demo_man.pkg.xml.condition.lang.ROSConditionLanguage;

import javax.swing.*;

/**
 * formal definition of the ROS condition file. It gives ROSCondition file fragments their own icon
 * @author Noam Dori
 */
public class ROSConditionFileType extends LanguageFileType {
    public static final ROSConditionFileType INSTANCE = new ROSConditionFileType();

    /**
     * construct an instance of this definition
     */
    public ROSConditionFileType() {
        super(ROSConditionLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "ROSCondition";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "ROS Condition type";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "condition";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return ROSIcons.CONDITION;
    }
}
