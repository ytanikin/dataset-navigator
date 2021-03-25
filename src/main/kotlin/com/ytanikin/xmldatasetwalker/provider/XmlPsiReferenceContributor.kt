package com.ytanikin.xmldatasetwalker.provider

import com.intellij.patterns.XmlNamedElementPattern
import com.intellij.patterns.XmlPatterns
import com.intellij.psi.*
import com.intellij.psi.impl.cache.CacheManager
import com.intellij.psi.impl.source.xml.XmlAttributeImpl
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.UsageSearchContext
import com.intellij.psi.xml.XmlFile
import com.intellij.psi.xml.XmlTag
import com.intellij.psi.xml.XmlToken
import com.intellij.util.ProcessingContext
import com.intellij.util.containers.stream
import kotlin.streams.toList


open class XmlPsiReferenceContributor : PsiReferenceContributor() {
    val SQL_PATTERN: XmlNamedElementPattern.XmlAttributePattern = XmlPatterns.xmlAttribute().withName("INVOICE")
//    private val SQL_PATTERN: XmlAttributeValuePattern = XmlPatterns.xmlAttributeValue()
//        .withParent(XmlPatterns.xmlAttribute().withName("INVOICE"))
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
    val withName = XmlPatterns.xmlAttribute().withName("ID")

    registrar.registerReferenceProvider(withName, XmlPsiReferenceProvider())
    }

    private class XmlPsiReferenceProvider : PsiReferenceProvider() {
        override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
            if (element is XmlAttributeImpl) {
                if (element.parent.name == "CUSTOMER") {
                    val cacheManager = CacheManager.getInstance(element.getProject())
                    val psiFiles = cacheManager.getFilesWithWord(
                        "CUSTOMER_ID", UsageSearchContext.ANY,
                        GlobalSearchScope.projectScope(element.getProject()), false
                    ).filterIsInstance<XmlFile>().toList()
                    for (xmlFile in psiFiles) {
                        for (child in xmlFile.children[0].children[1].children) {
                            if (child is XmlTag) {
                                for (child1 in child.children) {
                                    if (child1 is XmlAttributeImpl) {
                                        if (child1.text == "CUSTOMER_ID=\"1\"") {
                                            return arrayOf(XmlReferenceJava(element, listOf(child1)))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


                if (element.name != null) {
                    if (element.name.endsWith("CUSTOMER_ID")) { //todo child.name == ID child.value = 1
                        val cacheManager = CacheManager.getInstance(element.getProject())
                        val psiFiles = cacheManager.getFilesWithWord(
                            "CUSTOMER", UsageSearchContext.ANY,
                            GlobalSearchScope.projectScope(element.getProject()), false
                        )
                        val xmlFiles = psiFiles.stream()
                            .filter { it is XmlFile } //todo add xmlFile.rootTag what dataset
                            .toList()
                        for (xmlFile in xmlFiles) {
                            for (child7 in xmlFile.children[0].children[1].children) {
                                if (child7 is XmlTag) {
                                    for (child8 in child7.children) {
                                        if (child8 is XmlToken) {
                                            if (child8.text == "CUSTOMER") {
                                                if (child7.children[3].text == "ID=\"1\"") {
//                                                    val subIconBuilder = NavigationGutterIconBuilder.create(AllIcons.Gutter.ImplementedMethod)
//                                                        .setTarget(child7)
//                                                        .setTooltipText("navigate to data")
                                                    return arrayOf(
                                                        XmlReferenceJava(
                                                            element,
                                                            listOf(child7)
                                                        )
                                                    )
//                                                    val targets: Unit = includeTags.stream()
//                                                        .map { includeTag -> includeTag.getAttribute(StringConstants.REFID) }
//                                                        .filter { xmlAttribute -> xmlAttribute != null && StringUtils.equals(sqlId, xmlAttribute.getValue()) }
//                                                        .collect(Collectors.toList())
//                                                    return arrayOf(XmlEnumeratedValueReference(element, null))
//                                                    return arrayOf(XmlReference(element, targets))
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
            return arrayOf()
        }
    }
}
