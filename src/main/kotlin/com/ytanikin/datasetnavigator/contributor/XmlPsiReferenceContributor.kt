package com.ytanikin.datasetnavigator.contributor

import com.intellij.openapi.project.Project
import com.intellij.patterns.PatternCondition
import com.intellij.patterns.XmlPatterns
import com.intellij.psi.*
import com.intellij.psi.PsiReferenceRegistrar.HIGHER_PRIORITY
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlElement
import com.intellij.psi.xml.XmlTag
import com.intellij.util.ProcessingContext
import com.ytanikin.datasetnavigator.XmlHelper
import com.ytanikin.datasetnavigator.XmlHelper.DATASET_ROOT_TAG
import com.ytanikin.datasetnavigator.XmlHelper.ID_ATTRIBUTE
import com.ytanikin.datasetnavigator.XmlHelper.ID_POSTFIX
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable


open class XmlPsiReferenceContributor : PsiReferenceContributor() {

    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(ID_PATTERN, ID_REFERENCE_CONTRIBUTOR,  HIGHER_PRIORITY)
        registrar.registerReferenceProvider(ENTITY_PATTERN, ENTITY_REFERENCE_CONTRIBUTOR,  HIGHER_PRIORITY)
        registrar.registerReferenceProvider(FOREIGN_KEY_PATTERN, FOREIGN_KEY_REFERENCE_CONTRIBUTOR,  HIGHER_PRIORITY)
    }

    private class IdPsiReferenceProvider : PsiReferenceProvider() {

        override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
            if (element is XmlAttribute) {
                return arrayOf(XmlReference(element, findUsages(element, element.value, element.parent.name)))
            }
            return PsiReference.EMPTY_ARRAY
        }
    }

    private class EntityAttributePsiReferenceProvider : PsiReferenceProvider() {

        override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
            if (element is XmlTag) {
                val id = element.getAttributeValue(ID_ATTRIBUTE) ?: return PsiReference.EMPTY_ARRAY
                return arrayOf(XmlReference(element, findUsages(element, id, element.name)))
            }
            return PsiReference.EMPTY_ARRAY
        }
    }

    internal class ForeignKeyPsiReferenceProvider : PsiReferenceProvider() {

        override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
            if (element is XmlAttribute) {
                val entityName = element.name.substringBefore(ID_POSTFIX)
                return arrayOf(XmlReference(element, findDeclarations(entityName, element.value, element.project)))
            }
            return PsiReference.EMPTY_ARRAY
        }

        private fun findDeclarations(entityName: String, id: String?, project: Project): List<XmlTag> {
            val targets = mutableListOf<XmlTag>()
            for (xmlFile in XmlHelper.getXmlFilesWithWord(entityName, project)) {
                for (tag in xmlFile.rootTag?.subTags!!) {
                    if (tag.name == entityName && tag.getAttributeValue(ID_ATTRIBUTE) == id) {
                        targets.add(tag)
                    }
                }
            }
            return targets
        }
    }

    companion object {
        private const val DEBUG_METHOD_NAME = "withName"
        private val DATASET_CONDITION = XmlPatterns.xmlTag().with(object : PatternCondition<XmlTag?>(DEBUG_METHOD_NAME) {
            override fun accepts(xmlTag: XmlTag, context: ProcessingContext): Boolean {
                return true
            }
        }).withParent(XmlPatterns.xmlTag().withName(DATASET_ROOT_TAG))

        private val ID_PATTERN = XmlPatterns.xmlAttribute().withName(ID_ATTRIBUTE).withParent(DATASET_CONDITION)
        private val ENTITY_PATTERN = XmlPatterns.xmlTag().withAnyAttribute(ID_ATTRIBUTE).withParent(XmlPatterns.xmlTag().withName(DATASET_ROOT_TAG))
        private val FOREIGN_KEY_PATTERN = XmlPatterns.xmlAttribute().with(object : PatternCondition<XmlAttribute?>(DEBUG_METHOD_NAME) {
            override fun accepts(xmlAttribute: XmlAttribute, context: ProcessingContext): Boolean {
                return xmlAttribute.name.endsWith(ID_POSTFIX)
            }
        }).withParent(DATASET_CONDITION)
        private val ID_REFERENCE_CONTRIBUTOR = IdPsiReferenceProvider()
        private val ENTITY_REFERENCE_CONTRIBUTOR = EntityAttributePsiReferenceProvider()
        private val FOREIGN_KEY_REFERENCE_CONTRIBUTOR = ForeignKeyPsiReferenceProvider()

        private fun findUsages(element: XmlElement, id: @Nullable String?, entityName: @NotNull String): List<XmlTag> {
            val usages = mutableListOf<XmlTag>()
            val entityAndId = "${entityName}$ID_POSTFIX"
            for (xmlFile in XmlHelper.getXmlFilesWithWord(entityAndId, element.project)) {
                for (tag in xmlFile.rootTag?.subTags!!) {
                    val attribute = tag.getAttribute(entityAndId)
                    if (attribute != null && attribute.value == id) {
                        usages.add(tag)
                    }
                }
            }
            return usages
        }

    }
    //<!DOCTYPE  dataset [<!ELEMENT dataset (ANY)>]>
}
