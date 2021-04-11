package com.ytanikin.datasetnavigator.contributor

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.model.SymbolResolveResult
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlFile
import com.intellij.psi.xml.XmlTag
import com.ytanikin.datasetnavigator.ID_POSTFIX

class XmlReference(element: PsiElement, private val targets: List<PsiElement?>) :
    PsiReferenceBase<PsiElement?>(element, TextRange(0, element.text.length + 3)), PsiPolyVariantReference {

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        return targets.map { PsiElementResolveResult(it!!) }.toList().toTypedArray() }

    override fun resolve(): PsiElement? {
        return null
//        val resolveResults = multiResolve(false)
//        return if (resolveResults.isNotEmpty()) resolveResults[0].element else null
    }

    override fun getVariants(): Array<Any> {
        return targets.stream()
            .map { LookupElementBuilder.createWithSmartPointer(it!!.parent.text, it).withIcon(AllIcons.FileTypes.Java) }
            .toArray()
    }

    override fun getCanonicalText(): String {
        return "tesxt"
    }

    override fun resolveReference(): MutableCollection<out SymbolResolveResult> {

        return super<PsiPolyVariantReference>.resolveReference()
    }

    override fun isSoft(): Boolean {
        return true
    }

    override fun getValue(): String {
//        val text = myElement!!.text
//        val range = rangeInElement
        return "asdfasdf"
    }


}
class MyReference(element: PsiElement) : PsiReferenceBase<PsiElement>(element) {

    override fun resolve(): PsiElement? {
        val xmlAttribute = (PsiTreeUtil.findFirstParent(element) { it is XmlAttribute && it.name.endsWith(ID_POSTFIX) }
            ?: return null) as XmlAttribute
        val name = xmlAttribute.name.substringBefore(ID_POSTFIX)

        if (element.containingFile !is XmlFile) return null
        val xmlFile = element.containingFile as XmlFile

        val xmlTag = PsiTreeUtil.findChildrenOfType(xmlFile, XmlTag::class.java)
            .findLast { it.name == name }

        return xmlTag?.navigationElement
    }

    override fun isSoft(): Boolean {
        return true
    }
}