package com.ytanikin.datasetnavigator.contributor

import com.intellij.patterns.PatternCondition
import com.intellij.patterns.XmlPatterns
import com.intellij.psi.*
import com.intellij.psi.PsiReferenceRegistrar.HIGHER_PRIORITY
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlTag
import com.intellij.util.ProcessingContext
import com.ytanikin.datasetnavigator.XmlHelper


open class XmlPsiReferenceContributor : PsiReferenceContributor() {

    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(ID_PATTERN, ID_REFERENCE_CONTRIBUTOR,  HIGHER_PRIORITY)
        registrar.registerReferenceProvider(ENTITY_PATTERN, ENTITY_REFERENCE_CONTRIBUTOR,  HIGHER_PRIORITY)
        registrar.registerReferenceProvider(FOREIGN_KEY_PATTERN, FOREIGN_KEY_REFERENCE_CONTRIBUTOR,  HIGHER_PRIORITY)
    }

    private class IdPsiReferenceProvider : PsiReferenceProvider() {

        override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
            val usages = mutableListOf<XmlTag>()
            if (element is XmlAttribute) {
                val entityName = element.parent.name
                val id = element.value
                val entityWithId = "${entityName}_ID"
                for (xmlFile in XmlHelper.getXmlFilesWithWord(entityWithId, element.project)) {
                    for (tag in xmlFile.rootTag?.subTags!!) {
                        val attribute = tag.getAttribute(entityWithId)
                        if (attribute != null && attribute.value == id) {
                            usages.add(tag)
                        }
                    }
                }
            }
            return arrayOf(XmlReference(element, usages))
        }
    }

    private class EntityAttributePsiReferenceProvider : PsiReferenceProvider() {

        override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
            val usages = mutableListOf<XmlTag>()
            if (element is XmlTag) {
                val id = element.getAttributeValue("ID") ?: return PsiReference.EMPTY_ARRAY
                val entityWithId = "${element.name}_ID"

                for (xmlFile in XmlHelper.getXmlFilesWithWord(entityWithId, element.project)) {
                    for (tag in xmlFile.rootTag?.subTags!!) {
                        val attribute = tag.getAttribute(entityWithId)
                        if (attribute != null && attribute.value == id) {
                            usages.add(tag)
                        }
                    }
                }
            }
            return arrayOf(XmlReference(element, usages))
        }
    }

    internal class ForeignKeyPsiReferenceProvider : PsiReferenceProvider() {

        override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
            val targets = mutableListOf<XmlTag>()
            if (element is XmlAttribute) {
                val foreignKeyName = element.name
                val id = element.value
                val entityName = foreignKeyName.substringBefore("_ID")
                for (xmlFile in XmlHelper.getXmlFilesWithWord(entityName, element.getProject())) {
                    for (tag in xmlFile.rootTag?.subTags!!) {
                        if (tag.name == entityName && tag.getAttributeValue("ID") == id) {
                            targets.add(tag)
                        }
                    }
                }
            }
            return arrayOf(XmlReference(element, targets))
        }
    }

    companion object {
        private const val DEBUG_METHOD_NAME = "withName"
        private val DATASET_CONDITION = XmlPatterns.xmlTag().with(object : PatternCondition<XmlTag?>(DEBUG_METHOD_NAME) {
            override fun accepts(xmlTag: XmlTag, context: ProcessingContext): Boolean {
                return true
            }
        }).withParent(XmlPatterns.xmlTag().withName("dataset"))

        private val ID_PATTERN = XmlPatterns.xmlAttribute().withName("ID").withParent(DATASET_CONDITION)
        private val ENTITY_PATTERN = XmlPatterns.xmlTag().withAnyAttribute("ID").withParent(XmlPatterns.xmlTag().withName("dataset"))
        private val FOREIGN_KEY_PATTERN = XmlPatterns.xmlAttribute().with(object : PatternCondition<XmlAttribute?>(DEBUG_METHOD_NAME) {
            override fun accepts(xmlAttribute: XmlAttribute, context: ProcessingContext): Boolean {
                return xmlAttribute.name.endsWith("_ID")
            }
        }).withParent(DATASET_CONDITION)
        private val ID_REFERENCE_CONTRIBUTOR = IdPsiReferenceProvider()
        private val ENTITY_REFERENCE_CONTRIBUTOR = EntityAttributePsiReferenceProvider()
        private val FOREIGN_KEY_REFERENCE_CONTRIBUTOR = ForeignKeyPsiReferenceProvider()


    }
    //<!DOCTYPE  dataset [<!ELEMENT dataset (ANY)>]>
}
