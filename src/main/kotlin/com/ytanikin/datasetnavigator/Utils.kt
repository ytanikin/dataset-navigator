package com.ytanikin.datasetnavigator

import com.intellij.openapi.project.Project
import com.intellij.psi.impl.cache.CacheManager
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.UsageSearchContext
import com.intellij.psi.xml.XmlElement
import com.intellij.psi.xml.XmlFile
import com.intellij.psi.xml.XmlTag

object Utils {
    const val DATASET_ROOT_TAG = "dataset"
    const val ID_POSTFIX = "_ID"
    const val ID_ATTRIBUTE = "ID"

    fun findUsages(element: XmlElement, entityName: String, id: String): List<XmlTag> {
        val usages = mutableListOf<XmlTag>()
        val entityAndId = "${entityName}$ID_POSTFIX"
        for (xmlFile in getXmlFilesWithWord(entityAndId, element.project)) {
            for (tag in xmlFile.rootTag?.subTags!!) {
                val attribute = tag.getAttribute(entityAndId)
                if (attribute != null && attribute.value == id) {
                    usages.add(tag)
                }
            }
        }
        return usages
    }

    fun findDeclarations(entityName: String, entityId: String, project: Project): List<XmlTag> {
        val declarations = mutableListOf<XmlTag>()
        for (xmlFile in getXmlFilesWithWord(entityName, project)) {
            for (tag in xmlFile.rootTag?.subTags!!) {
                if (tag.name == entityName && tag.getAttributeValue(ID_ATTRIBUTE) == entityId) {
                    declarations.add(tag)
                }
            }
        }
        return declarations
    }

    private fun getXmlFilesWithWord(word: String, project: Project): List<XmlFile> {
        val filesWithWord = CacheManager.getInstance(project).getFilesWithWord(
            word, UsageSearchContext.ANY,
            GlobalSearchScope.projectScope(project), true
        )
        return filesWithWord.filterIsInstance<XmlFile>().filter { it.rootTag != null && it.rootTag!!.name == DATASET_ROOT_TAG }
    }

}