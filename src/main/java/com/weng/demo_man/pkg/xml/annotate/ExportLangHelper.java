package com.weng.demo_man.pkg.xml.annotate;

import com.intellij.openapi.extensions.ExtensionPointName;
import com.weng.demo_man.pkg.psi.ROSPackage;

/**
 * a plugin interface that helps check if the supported tags (message_generator and architecture_independent)
 * in export are valid
 * @author Noam Dori
 */
public interface ExportLangHelper {
    ExtensionPointName<ExportLangHelper> EP_NAME = ExtensionPointName.create("com.weng.demo_man.exportLangHelper");

    /**
     * @param lang the language the package is supposed to generate messages for.
     * @param aPackage the package that is checked for a message generator.
     * @return true if aPackage generates message code for language lang, false otherwise.
     */
    boolean messageGeneratorFor(String lang, ROSPackage aPackage);

    /**
     * @param aPackage the package that is checked for dependencies on computer architecture in compilation.
     * @return true if package depends on computer architecture during compilation (for example, C/C++ code),
     *         false if the extension cannot determine the above.
     */
    boolean dependsOnArchitecture(ROSPackage aPackage);
}
