package com.ytanikin.xmldatasetwalker.provider;

import java.util.List;
import java.util.function.Function;

import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.xml.XmlAttribute;

public class XmlReferenceJava extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {

    private final List<XmlAttribute> targets;

    private final List<XmlAttribute> variants;

    private final Function<XmlAttribute, String> getLookupString;

    public XmlReferenceJava(PsiElement element, List<XmlAttribute> targets) {
        super(element, new TextRange(0, element.getText().length() - 1));
        this.targets = targets;
        this.getLookupString = PsiElement::getText;
        this.variants = targets;
    }

    public XmlReferenceJava(@NotNull PsiElement element, List<XmlAttribute> targets, List<XmlAttribute> variants) {
        super(element, new TextRange(0, element.getText().length() - 1));
        this.targets = targets;
        this.getLookupString = PsiElement::getText;
        this.variants = variants;
    }

    public XmlReferenceJava(@NotNull PsiElement element, List<XmlAttribute> targets, List<XmlAttribute> variants, Function<XmlAttribute, String> getLookupString) {
        super(element, new TextRange(0, element.getText().length() - 1));
        this.targets = targets;
        this.variants = variants;
        this.getLookupString = getLookupString;
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        if (CollectionUtils.isEmpty(targets)) {
            return new ResolveResult[0];
        }

        ResolveResult[] resolveResults = targets.stream()
                .map(PsiElementResolveResult::new)
                .toArray(ResolveResult[]::new);
        return resolveResults;
    }

    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length >= 1 ? resolveResults[0].getElement() : null;
    }

    @Override
    public Object @NotNull [] getVariants() {
        return variants.stream()
                .map(psiElement -> LookupElementBuilder
                        .createWithSmartPointer(getLookupString.apply(psiElement), psiElement)
                        .withIcon(AllIcons.FileTypes.Xml))
                .toArray();
    }

}
