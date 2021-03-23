package com.ytanikin.xmldatasetwalker.services

import com.intellij.ide.highlighter.XmlFileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.xml.XmlFile
import com.intellij.util.indexing.FileBasedIndex

class PostAction : StartupActivity {
    override fun runActivity(project: Project) {
//        var forLanguage = LanguageParserDefinitions.INSTANCE.forLanguage(XMLLanguage.INSTANCE)
//        var findViewProvider = PsiManager.getInstance(project).findFile()
//        PsiManager.getInstance(project).findViewProvider()
//        fileViewProvider.getPsi(XMLLanguage.INSTANCE)
//        FilenameIndex.getFilesByName(project, , GlobalSearchScope.allScope(project))
        val containingFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, XmlFileType.INSTANCE, GlobalSearchScope.allScope(project))
        for (containingFile in containingFiles) {
            val xmlFile : XmlFile = PsiManager.getInstance(project).findFile(containingFile) as XmlFile

        }

        FilenameIndex.getAllFilenames(project)
    }

}