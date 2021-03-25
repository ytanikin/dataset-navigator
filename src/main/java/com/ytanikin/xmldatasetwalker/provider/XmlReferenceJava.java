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

public class XmlReferenceJava<T extends PsiElement> extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {

    private final List<T> targets;

    private final List<T> variants;

    private final Function<T, String> getLookupString;

    public XmlReferenceJava(PsiElement element, List<T> targets) {
        super(element, new TextRange(0, element.getText().length() - 1));
        this.targets = targets;
        this.getLookupString = PsiElement::getText;
        this.variants = targets;
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        return CollectionUtils.isEmpty(targets) ? new ResolveResult[0] : targets
                .stream()
                .map(PsiElementResolveResult::new)
                .toArray(ResolveResult[]::new);
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
