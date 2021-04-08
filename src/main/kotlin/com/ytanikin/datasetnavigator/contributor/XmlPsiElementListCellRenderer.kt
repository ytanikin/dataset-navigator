package com.ytanikin.datasetnavigator.contributor

import com.intellij.ide.util.PsiElementListCellRenderer
import com.intellij.navigation.ItemPresentation
import com.intellij.navigation.NavigationItem
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import org.jetbrains.annotations.Nullable

class XmlPsiElementListCellRenderer : PsiElementListCellRenderer<PsiElement>() {
    override fun getElementText(element: PsiElement?): String {
        if (element is PsiNamedElement) {
            val name: String? = element.name
            return name ?: ""
        }
        return element!!.text
    }

    override fun getContainerText(element: PsiElement?, name: String?): String? {
        if (element is NavigationItem) {
            val presentation: @Nullable ItemPresentation? = (element as NavigationItem).presentation
            if (presentation != null) {
                return presentation.locationString
            }
        }
        return null
    }

    override fun getIconFlags(): Int {
        return 0;
    }
}