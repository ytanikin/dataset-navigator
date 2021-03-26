/*
package com.ytanikin.xmldatasetwalker.provider;

import static com.intellij.psi.PsiReferenceRegistrar.HIGHER_PRIORITY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import com.intellij.patterns.XmlNamedElementPattern;
import com.intellij.patterns.XmlPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.impl.cache.CacheManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.UsageSearchContext;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ProcessingContext;

public class XmlPsiReferenceContributorJava extends PsiReferenceContributor {
    private static final XmlNamedElementPattern.XmlAttributePattern PATTERN = XmlPatterns.xmlAttribute().withName("ID")
            .withParent(
                    XmlPatterns.xmlTag().withName("CUSTOMER")
                            .withParent(XmlPatterns.xmlTag().withName("dataset"))
            );

    private static final XmlPsiReferenceProvider REFERENCE_PROVIDER = new XmlPsiReferenceProvider();

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(PATTERN, REFERENCE_PROVIDER, HIGHER_PRIORITY);
    }

    public static class XmlPsiReferenceProvider extends PsiReferenceProvider {

        @NotNull
        @Override
        public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext processingContext) {
            List<XmlAttribute> xmlAttributes = new ArrayList<>();
            if (element instanceof XmlAttribute) {
                XmlAttribute xmlAttribute = (XmlAttribute) element;
                String entityName = xmlAttribute.getParent().getText();
                String attributeValue = xmlAttribute.getValue();
                String entityWithId = entityName + "_ID";
                CacheManager instance = CacheManager.getInstance(element.getProject());
                PsiFile[] filesWithWord = instance.getFilesWithWord(
                        entityWithId,
                        UsageSearchContext.ANY,
                        GlobalSearchScope.projectScope(element.getProject()),
                        true);
                List<XmlFile> xmlFiles = Arrays.stream(filesWithWord).filter(f -> f instanceof XmlFile).map(f -> (XmlFile) f).collect(Collectors.toList());
                for (XmlFile xmlFile : xmlFiles) {
                    for (XmlTag subTag : xmlFile.getRootTag().getSubTags()) {
                        XmlAttribute attribute = subTag.getAttribute(entityWithId);
                        if (attribute != null && attributeValue.equals(attribute.getValue())) {
                            xmlAttributes.add(attribute);
                            return new PsiReference[]{ new XmlReferenceJava<PsiElement>(element, xmlAttributes) };
                        }
                    }
                }

            }
            return new PsiReference[0];
        }
    }
}
*/
