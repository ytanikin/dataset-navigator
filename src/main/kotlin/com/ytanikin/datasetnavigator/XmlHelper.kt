package com.ytanikin.datasetnavigator

import com.intellij.openapi.project.Project
import com.intellij.psi.impl.cache.CacheManager
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.UsageSearchContext
import com.intellij.psi.xml.XmlElement
import com.intellij.psi.xml.XmlFile
import com.intellij.psi.xml.XmlTag
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

object XmlHelper {
    const val DATASET_ROOT_TAG = "dataset"
    const val ID_POSTFIX = "_ID"
    const val ID_ATTRIBUTE = "ID"

    fun getXmlFilesWithWord(word: String, project: Project): List<XmlFile> {
        val filesWithWord = CacheManager.getInstance(project).getFilesWithWord(
            word, UsageSearchContext.ANY,
            GlobalSearchScope.projectScope(project), true
        )
        return filesWithWord.filterIsInstance<XmlFile>().filter { it.rootTag != null && it.rootTag!!.name == DATASET_ROOT_TAG }
    }

    fun findUsages(element: XmlElement, entityName: @NotNull String, id: @Nullable String?): List<XmlTag> {
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
}