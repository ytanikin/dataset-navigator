/*
package com.ytanikin.xmldatasetwalker.provider

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.xml.XmlAttribute
import org.apache.commons.collections.CollectionUtils
import java.util.function.Function

class XmlReference(element: PsiElement, targets: List<XmlAttribute>) :
    PsiReferenceBase<XmlAttribute>(element, TextRange(0, element.text.length - 1)), PsiPolyVariantReference {

    private val targets: List<XmlAttribute>
    private val variants: List<XmlAttribute>
    private val getLookupString: Function<XmlAttribute, String>

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        if (CollectionUtils.isEmpty(targets)) {
            return arrayOf()
        }
        return targets.stream()
            .map { e -> PsiElementResolveResult(ResolveResult) }
            .toArray() as Array<ResolveResult>
//        return if (CollectionUtils.isEmpty(targets)) arrayOfNulls(0) else targets
//            .stream()
//            .map(Function { element: T -> PsiElementResolveResult(element) })
//            .toArray(IntFunction<Array<ResolveResult>> { _Dummy_.__Array__() })
    }

    override fun resolve(): PsiElement? {
        val resolveResults = multiResolve(false)
        return if (resolveResults.size >= 1) resolveResults[0].element else null
    }

    override fun getVariants(): Array<Any> {
        return variants.stream()
            .map { psiElement: T ->
                LookupElementBuilder
                    .createWithSmartPointer(getLookupString.apply(psiElement), psiElement)
                    .withIcon(AllIcons.FileTypes.Xml)
            }
            .toArray()
    }

    init {
        this.targets = targets
        getLookupString = Function { obj: XmlAttribute -> obj!!.text }
        variants = targets
    }
}
*/
