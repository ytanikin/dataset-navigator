package com.ytanikin.xmldatasetwalker.services

import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.xml.XMLLanguage
import com.intellij.lang.xml.XmlFindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.xml.XmlAttlistDeclImpl
import com.intellij.psi.impl.source.xml.XmlAttributeDeclImpl
import com.intellij.psi.impl.source.xml.XmlDocumentImpl


class FindEntityUsages : XmlFindUsagesProvider() {

    private val set = mutableSetOf("a")

    override fun getType(element: PsiElement) : String {
        println(element)
        if (element is XmlAttributeDeclImpl) {
            element.nameElement
        }
        return element.text
    }

    override fun canFindUsagesFor(element: PsiElement): Boolean {
        return when {
            element.language != XMLLanguage.INSTANCE -> false
            element is XmlAttributeDeclImpl && isDataSet(element) -> true
            element is XmlAttlistDeclImpl && isDataSet(element) -> true
            element is XmlDocumentImpl && isDataSet(element) -> true
            else -> false
        }
    }

    private fun isDataSet(element: XmlAttlistDeclImpl): Boolean {
        return element.parent.parent.textMatches("dataset")
    }

    private fun isDataSet(element: XmlDocumentImpl): Boolean {
        return element.parent.textMatches("dataset")
    }

    private fun isDataSet(element: XmlAttributeDeclImpl): Boolean {
        return element.parent.parent.textMatches("dataset")
    }

    override fun getWordsScanner() : WordsScanner {
        return DefaultWordsScanner(null, null, null, null)
//        return WordsScanner { charSequence: @NotNull CharSequence, processor: @NotNull Processor<in WordOccurrence> ->
//            set.add(charSequence.toString())
//        }
    }

    override fun getHelpId(element: PsiElement): String? {
        return element.text
    }

    override fun getDescriptiveName(element: PsiElement): String {
        return element.text
    }

    override fun getNodeText(element: PsiElement, useFullName: Boolean): String {
        println(element)
        println(useFullName)
        TODO("Not yet implemented")
    }
}