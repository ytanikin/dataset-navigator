package com.ytanikin.xmldatasetwalker.provider

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.icons.AllIcons
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.cache.CacheManager
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.UsageSearchContext
import com.intellij.psi.xml.XmlFile
import com.intellij.util.containers.stream
import com.intellij.util.xml.DomElement
import com.intellij.util.xml.DomManager
import com.ytanikin.xmldatasetwalker.filemetadata.Dataset
import java.util.*
import kotlin.streams.toList

class XmlLineMarkerProvider : RelatedItemLineMarkerProvider() {

    override fun collectNavigationMarkers(element: PsiElement, result: MutableCollection<in RelatedItemLineMarkerInfo<*>>) {
        if (element !is XmlFile) {
            return
        }

        val dataset: Dataset? = findFileElement(element, Dataset::class.java)
        if (dataset == null) {
            return
        }
//        if (dataset.getEntities()?.isEmpty() == true) {
//            return
//        }
        val domManager = DomManager.getDomManager(element.getProject())
        val cacheManager = CacheManager.getInstance(element.getProject())
        val psiFiles = cacheManager.getFilesWithWord(
            "CUSTOMER", UsageSearchContext.ANY,
            GlobalSearchScope.projectScope(element.getProject()), true
        )
        val dataSets = psiFiles.stream()
            .filter { it is XmlFile }
            .map { domManager.getFileElement(it as XmlFile, Dataset::class.java) }
            .filter(Objects::nonNull)
//            .map(DomFileElement::getRootElement)
            .toList()
        dataSets.forEach {
            val methodIconBuilder = NavigationGutterIconBuilder.create(AllIcons.Gutter.ImplementedMethod)
                .setTarget(it?.xmlTag)
                .setTooltipText("it.()")
            result.add(methodIconBuilder.createLineMarkerInfo(it?.xmlElement!!))
        }

//        dataset.xmlElementName
//        result.
    }

    private fun <T : DomElement?> findFileElement(element: PsiElement, domClass: Class<T>?): T? {
        if (element !is XmlFile) {
            return null
        }
        val domManager = DomManager.getDomManager(element.getProject())
        val domFileElement = domManager.getFileElement(element, domClass) ?: return null
        return domFileElement.rootElement
    }

}