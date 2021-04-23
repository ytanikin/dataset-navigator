package com.ytanikin.datasetnavigator

import com.intellij.util.indexing.*
import com.intellij.util.io.KeyDescriptor

class XmlFilesIndex : ScalarIndexExtension<String>() {

    override fun getIndexer(): DataIndexer<String, Void, FileContent> {
//        return DataIndexer<String, Void, FileContent> {
//            mapOf(Pair(namespace, null))
//        }
        TODO()
    }

    override fun getName(): ID<String, Void> {
        return NAME
    }

    override fun getKeyDescriptor(): KeyDescriptor<String> {
        TODO("Not yet implemented")
    }

    override fun getVersion(): Int {
        TODO("Not yet implemented")
    }

    override fun getInputFilter(): FileBasedIndex.InputFilter {
        TODO("Not yet implemented")
    }

    override fun dependsOnFileContent(): Boolean {
        TODO("Not yet implemented")
    }
    companion object {
        val NAME: ID<String, Void> = ID.create("com.ytanikin.XmlFilesIndex")
    }


}