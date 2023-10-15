package com.weng.demo_man.pkg.xml.ref;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;
import com.weng.demo_man.pkg.psi.ROSPackage;
import com.weng.demo_man.pkg.ref.ROSPackageReferenceBase;
import com.weng.demo_man.pkg.xml.ROSPackageXml;

/**
 * a class defining the references of {@link com.weng.demo_man.pkg.xml.ROSPackageXml} to {@link ROSPackage} and its affiliated roots.
 * @author Noam Dori
 */
public class NameXmlToPackageReference extends ROSPackageReferenceBase<XmlTag> {
    // note: myElement is the referencing element, and the result of resolve() is the original element (the file).

    /**
     * construct a new reference
     * @param element the referencing name tag in a package.xml file.
     * @param pkgXml the package.xml file this tag belongs to.
     */
    public NameXmlToPackageReference(@NotNull XmlTag element, @NotNull ROSPackageXml pkgXml) {
        super(element, getTextRange(element));
        pkgName = pkgXml.getPackage().getName();
    }

    @NotNull
    private static TextRange getTextRange(@NotNull XmlTag element) {
        return element.getValue().getTextRange().shiftLeft(element.getTextOffset());
    }
}
