package com.ytanikin.datasetnavigator.provider

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.icons.AllIcons
import com.intellij.ide.util.PsiElementListCellRenderer
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlTag
import com.intellij.psi.xml.XmlToken
import com.intellij.psi.xml.XmlTokenType
import com.ytanikin.datasetnavigator.ID_ATTRIBUTE
import com.ytanikin.datasetnavigator.findUsageAttributes
import javax.swing.Icon

open class XmlLineMarkerProvider : RelatedItemLineMarkerProvider() {

    override fun collectNavigationMarkers(identifier: PsiElement, result: MutableCollection<in RelatedItemLineMarkerInfo<*>>) {
        if (identifier !is XmlToken || identifier.elementType != XmlTokenType.XML_NAME) return
        val element = identifier.parent
        if (element !is XmlTag) return
        val entityId = element.getAttributeValue(ID_ATTRIBUTE) ?: return
        val usages = findUsageAttributes(element, element.name, entityId)
        if (usages.isEmpty()) return
        result.add(
            NavigationGutterIconBuilder.create(AllIcons.Xml.Html_id)
                .setTargets(usages)
                .setTooltipText("Find usages of ${element.name} with ${element.getAttribute(ID_ATTRIBUTE)?.text ?: ""}")
                .setCellRenderer(XmlCellRenderer.INSTANCE)
                .createLineMarkerInfo(identifier)
        )
    }

    private class XmlCellRenderer : PsiElementListCellRenderer<XmlAttribute>() {
        override fun getElementText(tag: XmlAttribute): String {
            return tag.parent.name
        }

        override fun getContainerText(tag: XmlAttribute, name: String): String {
            val attribute = tag.parent.getAttribute(ID_ATTRIBUTE)
            var hint =  (attribute?.text ?: "")
            for (attr in tag.parent.attributes) {
                if (attr == attribute) continue
                hint += " ${attr.text}"
                if (hint.length > 90) break
            }
            return hint
        }

        override fun getIcon(element: PsiElement): Icon {
            return AllIcons.Xml.Html_id
        }

        override fun getIconFlags(): Int {
            return 0
        }

        companion object {
            val INSTANCE = XmlCellRenderer()
        }
    }
}