package com.ytanikin.datasetnavigator.provider

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.icons.AllIcons
import com.intellij.ide.util.PsiElementListCellRenderer
import com.intellij.psi.PsiElement
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlTag
import com.ytanikin.datasetnavigator.Utils.ID_ATTRIBUTE
import com.ytanikin.datasetnavigator.Utils.findUsageAttributes
import javax.swing.Icon

open class XmlLineMarkerProvider : RelatedItemLineMarkerProvider() {

    override fun collectNavigationMarkers(element: PsiElement, result: MutableCollection<in RelatedItemLineMarkerInfo<*>>) {
        if (element !is XmlTag) return
        val entityId = element.getAttributeValue(ID_ATTRIBUTE) ?: return
        val usages = findUsageAttributes(element, element.name, entityId)
        if (usages.isEmpty()) return
        val subIcon = NavigationGutterIconBuilder.create(AllIcons.Actions.FindForward)
            .setTargets(usages)
            .setTooltipText("Find usages of ${element.name} ${element.getAttribute(ID_ATTRIBUTE)?.text ?: ""}")
            .setCellRenderer(XmlCellRenderer.INSTANCE)
        result.add(subIcon.createLineMarkerInfo(element.navigationElement))
    }

    private class XmlCellRenderer : PsiElementListCellRenderer<XmlAttribute>() {
        override fun getElementText(tag: XmlAttribute): String {
            return tag.parent.name + " " + (tag.parent.getAttribute(ID_ATTRIBUTE)?.text ?: "")
        }

        override fun getContainerText(tag: XmlAttribute, name: String): String {
            val attribute = tag.parent.getAttribute(ID_ATTRIBUTE)
            var hint = ""
            for (attr in tag.parent.attributes) {
                if (attr == attribute) continue
                hint += " ${attr.text}"
                if (hint.length > 70) break
            }
            return hint
        }

        override fun getIcon(element: PsiElement): Icon {
            return AllIcons.Xml.Html_id
        }

        override fun getIconFlags(): Int {
            return 1
        }

        companion object {
            val INSTANCE = XmlCellRenderer()
        }
    }
}