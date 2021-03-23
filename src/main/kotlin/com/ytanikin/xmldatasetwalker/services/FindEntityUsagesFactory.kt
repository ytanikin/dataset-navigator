package com.ytanikin.xmldatasetwalker.services

import com.intellij.find.findUsages.FindUsagesHandler
import com.intellij.find.findUsages.FindUsagesHandlerFactory
import com.intellij.psi.PsiElement

class FindEntityUsagesFactory : FindUsagesHandlerFactory() {
    override fun canFindUsages(element: PsiElement): Boolean {
        return FindEntityUsages().canFindUsagesFor(element)
    }

    override fun createFindUsagesHandler(element: PsiElement, forHighlightUsages: Boolean): FindUsagesHandler {
        return FindEntityHandler(element)
    }

    override fun createFindUsagesHandler(element: PsiElement, operationMode: OperationMode): FindUsagesHandler {
        return FindEntityHandler(element)
//        return super.createFindUsagesHandler(element, operationMode)
    }

}