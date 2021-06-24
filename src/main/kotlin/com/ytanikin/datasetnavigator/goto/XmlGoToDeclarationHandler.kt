package com.ytanikin.datasetnavigator.goto

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.text.StringUtil.equalsIgnoreCase
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlElementType.XML_ATTRIBUTE_VALUE
import com.intellij.psi.xml.XmlFile
import com.intellij.psi.xml.XmlTag
import com.intellij.psi.xml.XmlToken
import com.ytanikin.datasetnavigator.*

class XmlGoToDeclarationHandler : GotoDeclarationHandler {
    override fun getGotoDeclarationTargets(sourceElement: PsiElement?, offset: Int, editor: Editor?): Array<PsiElement> {
        if (sourceElement !is XmlToken) return emptyArray()
        var xmlElement = sourceElement.parent
        val containingFile = xmlElement.containingFile
        if (containingFile is XmlFile && DATASET_ROOT_TAG != containingFile.rootTag?.name) return emptyArray()
        if (xmlElement is XmlTag) {
            val id = getId(xmlElement) ?: return emptyArray()
            return findUsageTags(xmlElement, xmlElement.name, id).toTypedArray()
        }
        if (xmlElement.elementType == XML_ATTRIBUTE_VALUE) {
            xmlElement = xmlElement.parent
        }
        if (xmlElement !is XmlAttribute) return emptyArray()

        if (equalsIgnoreCase(xmlElement.name, ID_ATTRIBUTE)) {
            val id = xmlElement.value ?: return emptyArray()
            return findUsageTags(xmlElement, xmlElement.parent.name, id).toTypedArray()
        }
        val entityId = xmlElement.value ?: return emptyArray()
        val entityName= when {
            xmlElement.name.endsWith(ID_POSTFIX) -> {
                xmlElement.name.substringBefore(ID_POSTFIX)
            }
            xmlElement.name.endsWith(ID_POSTFIX_LOWER_CASE) -> {
                xmlElement.name.substringBefore(ID_POSTFIX_LOWER_CASE)
            }
            else -> return emptyArray()
        }
        return findDeclarations(xmlElement.project, entityName, entityId).toTypedArray()
    }

    private fun getId(xmlElement: XmlTag) = xmlElement.getAttributeValue(ID_ATTRIBUTE) ?: xmlElement.getAttributeValue(ID_ATTRIBUTE_LOWER_CASE)
}