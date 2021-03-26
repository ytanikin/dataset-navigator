/*
package com.ytanikin.xmldatasetwalker.provider


import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

class SimpleReference(@NotNull element: PsiElement, textRange: TextRange) : PsiReferenceBase<PsiElement?>(element, textRange), PsiPolyVariantReference {

    private val key: String

    @NotNull
    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val project: Project = myElement.getProject()
        val properties: List<SimpleProperty> = SimpleUtil.findProperties(project, key)
        val results: MutableList<ResolveResult> = ArrayList()
        for (property in properties) {
            results.add(PsiElementResolveResult(property))
        }
        return results.toTypedArray()
    }

    @Nullable
    override fun resolve(): PsiElement? {
        val resolveResults: Array<ResolveResult> = multiResolve(false)
        return if (resolveResults.size == 1) resolveResults[0].getElement() else null
    }

    @get:NotNull
    val variants: Array<Any>
        get() {
            val project: Project = myElement.getProject()
            val properties: List<SimpleProperty> = SimpleUtil.findProperties(project)
            val variants: MutableList<LookupElement> = ArrayList()
            for (property in properties) {
                if (property.getKey() != null && property.getKey().length() > 0) {
                    variants.add(
                        LookupElementBuilder
                            .create(property).withIcon(SimpleIcons.FILE)
                            .withTypeText(property.getContainingFile().getName())
                    )
                }
            }
            return variants.toTypedArray()
        }

    init {
        key = element.getText().substring(textRange.getStartOffset(), textRange.getEndOffset())
    }
}*/
