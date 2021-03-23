package com.ytanikin.xmldatasetwalker.services

import com.intellij.find.findUsages.AbstractFindUsagesDialog
import com.intellij.find.findUsages.FindUsagesHandler
import com.intellij.find.findUsages.FindUsagesOptions
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.SearchScope
import com.intellij.psi.search.searches.ReferencesSearch
import com.intellij.usageView.UsageInfo
import com.intellij.util.Processor
import org.jetbrains.annotations.NotNull

class FindEntityHandler(psiElement: @NotNull PsiElement) : FindUsagesHandler(psiElement) {
    override fun getPrimaryElements(): Array<PsiElement> {
        return super.getPrimaryElements()
    }

    override fun getSecondaryElements(): Array<PsiElement> {
        return super.getSecondaryElements()
    }

    override fun getFindUsagesOptions(): FindUsagesOptions {
        return super.getFindUsagesOptions()
    }

    override fun getFindUsagesOptions(dataContext: DataContext?): FindUsagesOptions {
        return super.getFindUsagesOptions(dataContext)
    }

    override fun processElementUsages(element: PsiElement, processor: Processor<in UsageInfo>, options: FindUsagesOptions): Boolean {
        return super.processElementUsages(element, processor, options)
    }

    override fun processUsagesInText(element: PsiElement, processor: Processor<in UsageInfo>, searchScope: GlobalSearchScope): Boolean {
        return super.processUsagesInText(element, processor, searchScope)
    }

    override fun isSearchForTextOccurrencesAvailable(psiElement: PsiElement, isSingleFile: Boolean): Boolean {
        return super.isSearchForTextOccurrencesAvailable(psiElement, isSingleFile)
    }


//    override fun findReferencesToHighlight(target: PsiElement, searchScope: SearchScope): MutableCollection<PsiReference> {
//        return super.findReferencesToHighlight(target, searchScope)
//    }

    override fun createSearchParameters(
        target: PsiElement,
        searchScope: SearchScope,
        findUsagesOptions: FindUsagesOptions?
    ): ReferencesSearch.SearchParameters {
        return super.createSearchParameters(target, searchScope, findUsagesOptions)
    }

    override fun getFindUsagesDialog(isSingleFile: Boolean, toShowInNewTab: Boolean, mustOpenInNewTab: Boolean): AbstractFindUsagesDialog {
        return super.getFindUsagesDialog(isSingleFile, toShowInNewTab, mustOpenInNewTab)
    }

    override fun getHelpId(): String? {
        return super.getHelpId()
    }
}