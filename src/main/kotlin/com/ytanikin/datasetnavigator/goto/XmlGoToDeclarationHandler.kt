package com.ytanikin.datasetnavigator.goto

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlAttributeValue
import com.intellij.psi.xml.XmlTag
import com.intellij.psi.xml.XmlToken
import com.ytanikin.datasetnavigator.*

class XmlGoToDeclarationHandler : GotoDeclarationHandler {
    override fun getGotoDeclarationTargets(sourceElement: PsiElement?, offset: Int, editor: Editor?): Array<PsiElement> {
        if (sourceElement !is XmlToken) return emptyArray()
        var xmlElement = sourceElement.parent
        if (xmlElement is XmlAttributeValue) {
            xmlElement = xmlElement.parent
        }
        if (xmlElement is XmlAttribute) {
            if (xmlElement.name == ID_ATTRIBUTE) {
                val id = xmlElement.value ?: return emptyArray()
                return findUsageTags(xmlElement, xmlElement.parent.name, id).toTypedArray()
            }
            if (xmlElement.name.endsWith(ID_POSTFIX)) {
                val entityId = xmlElement.value ?: return emptyArray()
                val entityName = xmlElement.name.substringBefore(ID_POSTFIX)
                return findDeclarations(xmlElement.project, entityName, entityId).toTypedArray()
            }
        }
        if (xmlElement is XmlTag) {
            if (DATASET_ROOT_TAG != xmlElement.parentTag?.name) return emptyArray()
            val id = xmlElement.getAttributeValue(ID_ATTRIBUTE) ?: return emptyArray()
            return findUsageTags(xmlElement, xmlElement.name, id).toTypedArray()
        }
        return emptyArray()
    }
}