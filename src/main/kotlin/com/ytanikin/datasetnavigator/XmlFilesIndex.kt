package com.ytanikin.datasetnavigator

import com.intellij.ide.highlighter.XmlFileType
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.xml.XmlFile
import com.intellij.util.indexing.*
import com.intellij.util.io.EnumeratorStringDescriptor
import com.intellij.util.io.KeyDescriptor

class XmlFilesIndex : ScalarIndexExtension<String>() {

    override fun getIndexer(): DataIndexer<String, Void, FileContent> {
        return DataIndexer<String, Void, FileContent> {
            val namespace = getNamespace(it)
            if (namespace != null) {
                mapOf(Pair(namespace, null))
            } else {
                emptyMap<String, Void>()
            }
        }
    }

    override fun getName() = NAME

    override fun getKeyDescriptor(): KeyDescriptor<String> = EnumeratorStringDescriptor.INSTANCE

    override fun getVersion() = 0

    override fun getInputFilter(): FileBasedIndex.InputFilter = DefaultFileTypeSpecificInputFilter(XmlFileType.INSTANCE)

    override fun dependsOnFileContent() = true

    private fun getNamespace(fileContent: FileContent): String? {
        val hasDatasetRootTag = PsiTreeUtil.findChildrenOfType(fileContent.psiFile, XmlFile::class.java).any { xmlFile ->
            xmlFile.rootTag != null && xmlFile.rootTag!!.equals(DATASET_ROOT_TAG)
        }
        return if (hasDatasetRootTag) DATASET_ROOT_TAG else null
    }

    companion object {
        val NAME: ID<String, Void> = ID.create("com.ytanikin.XmlFilesIndex")
    }
}