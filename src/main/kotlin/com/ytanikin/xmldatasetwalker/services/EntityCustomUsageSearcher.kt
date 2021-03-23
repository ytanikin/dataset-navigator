package com.ytanikin.xmldatasetwalker.services

import com.intellij.find.findUsages.CustomUsageSearcher
import com.intellij.find.findUsages.FindUsagesOptions
import com.intellij.psi.PsiElement
import com.intellij.usages.Usage
import com.intellij.util.Processor

class EntityCustomUsageSearcher : CustomUsageSearcher() {
    override fun processElementUsages(element: PsiElement, processor: Processor<in Usage>, options: FindUsagesOptions) {
        println("processElementUsages $element")
    }
}