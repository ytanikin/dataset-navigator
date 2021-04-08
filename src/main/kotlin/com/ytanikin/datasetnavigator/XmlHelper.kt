package com.ytanikin.datasetnavigator

import com.intellij.openapi.project.Project
import com.intellij.psi.impl.cache.CacheManager
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.UsageSearchContext
import com.intellij.psi.xml.XmlFile

object XmlHelper {
    fun getXmlFilesWithWord(entityName: String, project: Project): List<XmlFile> {
        val filesWithWord = CacheManager.getInstance(project).getFilesWithWord(
            entityName, UsageSearchContext.ANY,
            GlobalSearchScope.projectScope(project), true
        )
        return filesWithWord.filterIsInstance<XmlFile>().filter { it.rootTag != null && it.rootTag!!.name == "dataset" }
    }
}