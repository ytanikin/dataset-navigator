package com.ytanikin.datasetnavigator

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.text.StringUtil.equalsIgnoreCase
import com.intellij.psi.impl.cache.CacheManager
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.UsageSearchContext
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlElement
import com.intellij.psi.xml.XmlFile
import com.intellij.psi.xml.XmlTag

const val DATASET_ROOT_TAG = "dataset"
const val ID_POSTFIX = "_ID"
const val ID_POSTFIX_LOWER_CASE = "_id"
const val ID_ATTRIBUTE = "ID"
const val ID_ATTRIBUTE_LOWER_CASE = "id"

fun findUsageTags(element: XmlElement, entityName: String, id: String): List<XmlTag> {
    val usages = mutableListOf<XmlTag>()
    val upperCase = entityName.first().isUpperCase()
    val entityAndId = getEntityName(upperCase, entityName)
    for (xmlFile in getXmlFilesWithWord(entityAndId, element.project)) {
        for (tag in xmlFile.rootTag?.subTags!!) {
            val attribute = getAttribute(tag, entityAndId, upperCase)
            if (attribute != null && attribute.value == id) {
                usages.add(tag)
            }
        }
    }
    return usages
}

fun findUsageAttributes(element: XmlElement, entityName: String, id: String): List<XmlAttribute> {
    val usages = mutableListOf<XmlAttribute>()
    val upperCase = entityName.first().isUpperCase()
    val entityAndId = getEntityName(upperCase, entityName)
    for (xmlFile in getXmlFilesWithWord(entityAndId, element.project)) {
        for (tag in xmlFile.rootTag?.subTags!!) {
            val attribute = getAttribute(tag, entityAndId, upperCase)
            if (attribute != null && attribute.value == id) {
                usages.add(attribute)
            }
        }
    }
    return usages
}

private fun getEntityName(upperCase: Boolean, entityName: String) =
    if (upperCase) "${entityName}$ID_POSTFIX" else "${entityName}$ID_POSTFIX_LOWER_CASE"

fun findDeclarations(project: Project, entityName: String, entityId: String): List<XmlTag> {
    val declarations = mutableListOf<XmlTag>()
    for (xmlFile in getXmlFilesWithWord(entityName, project)) {
        for (tag in xmlFile.rootTag?.subTags!!) {
            if (equalsIgnoreCase(tag.name, entityName) && getAttributeValue(tag) == entityId) {
                declarations.add(tag)
            }
        }
    }
    return declarations
}

private fun getAttributeValue(tag: XmlTag) = tag.getAttributeValue(ID_ATTRIBUTE) ?: tag.getAttributeValue(ID_ATTRIBUTE_LOWER_CASE)

private fun getAttribute(tag: XmlTag, entityAndId: String, upperCase: Boolean): XmlAttribute? {
    return if (upperCase) tag.getAttribute(entityAndId) else tag.getAttribute(entityAndId.toUpperCase())
}

private fun getXmlFilesWithWord(word: String, project: Project): List<XmlFile> {
    return CacheManager.getInstance(project).getFilesWithWord(
        word, UsageSearchContext.IN_PLAIN_TEXT,
        GlobalSearchScope.projectScope(project), false
    ).filterIsInstance<XmlFile>().filter { it.rootTag != null && it.rootTag!!.name == DATASET_ROOT_TAG }
}