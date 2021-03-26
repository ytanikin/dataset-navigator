package com.ytanikin.xmldatasetwalker.provider

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.xml.XmlAttribute
import org.apache.commons.collections.CollectionUtils
import java.util.function.Function
import kotlin.streams.toList

class XmlReference(element: PsiElement, private val targets: List<XmlAttribute?>) :
    PsiReferenceBase<PsiElement?>(element, TextRange(0, element.text.length - 1)), PsiPolyVariantReference {

    private val variants: List<XmlAttribute?> = targets

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        if (CollectionUtils.isEmpty(targets)) {
            return emptyArray()
        }
        val toList = targets.stream()
            .map { element -> PsiElementResolveResult(element!!) }
            .map { it as ResolveResult }.toList()

        return toList.toTypedArray()
    }

    override fun resolve(): PsiElement? {
        val resolveResults = multiResolve(false)
        return if (resolveResults.isNotEmpty()) resolveResults[0].element else null
    }

    override fun getVariants(): Array<Any> {
        return variants.stream()
            .map { psiElement: XmlAttribute? ->
                LookupElementBuilder
                    .createWithSmartPointer(Function { obj: XmlAttribute? -> obj!!.text }.apply(psiElement), psiElement!!)
                    .withIcon(AllIcons.FileTypes.Xml)
            }
            .toArray()
    }

}