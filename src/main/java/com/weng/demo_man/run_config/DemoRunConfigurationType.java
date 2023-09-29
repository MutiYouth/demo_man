// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.weng.demo_man.run_config;

import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.NotNullLazyValue;
import com.weng.demo_man.ui.DMIcons;

public class DemoRunConfigurationType extends ConfigurationTypeBase {

	static final String ID = "DemoRunConfiguration";

	protected DemoRunConfigurationType() {
		super(ID, "DM Demo", "Demo run configuration type", DMIcons.DMIcon);  // NotNullLazyValue.createValue(() -> AllIcons.Nodes.Console)
		addFactory(new DemoConfigurationFactory(this));
	}

}
