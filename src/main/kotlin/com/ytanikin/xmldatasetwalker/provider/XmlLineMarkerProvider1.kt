package com.ytanikin.xmldatasetwalker.provider

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.icons.AllIcons
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.cache.CacheManager
import com.intellij.psi.impl.source.xml.XmlAttributeImpl
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.UsageSearchContext
import com.intellij.psi.xml.XmlFile
import com.intellij.psi.xml.XmlTag
import com.intellij.psi.xml.XmlToken
import com.intellij.util.containers.stream
import kotlin.streams.toList

class XmlLineMarkerProvider1 : RelatedItemLineMarkerProvider() {

    override fun collectNavigationMarkers(element: PsiElement, result: MutableCollection<in RelatedItemLineMarkerInfo<*>>) {
        if (element is XmlTag) {
            for (child in element.children) {
                if (child is XmlAttributeImpl) {
                    if (child.name != null) {
                        if (child.name.endsWith("ID")) { //todo child.name == ID
                            val cacheManager = CacheManager.getInstance(element.getProject())
                            val psiFiles = cacheManager.getFilesWithWord(
                                "CUSTOMER", UsageSearchContext.ANY,
                                GlobalSearchScope.projectScope(element.getProject()), false
                            )
                            val xmlFiles = psiFiles.stream()
                                .filter { it is XmlFile } //todo check with xmlFile.rootTag
                                .toList()
                            for (xmlFile in xmlFiles) {
                                for (child7 in xmlFile.children[0].children[1].children) {
                                    if (child7 is XmlTag) {
                                        for (child8 in child7.children) {
                                            if (child8 is XmlToken && child8.text == "CUSTOMER" && child7.children[3].text == "ID=\"1\"") { // todo check with child text
                                                val subIconBuilder = NavigationGutterIconBuilder.create(AllIcons.Gutter.ImplementingMethod)
                                                    .setTarget(child7)
                                                    .setTooltipText(child7.name)

                                                result.add(subIconBuilder.createLineMarkerInfo(element))

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}