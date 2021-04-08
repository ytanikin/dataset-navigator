package com.ytanikin.datasetnavigator.provider

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.icons.AllIcons
import com.intellij.ide.util.PsiElementListCellRenderer
import com.intellij.psi.PsiElement
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlTag
import com.ytanikin.datasetnavigator.XmlHelper
import com.ytanikin.datasetnavigator.XmlHelper.ID_ATTRIBUTE
import org.jetbrains.annotations.Nullable
import javax.swing.Icon

open class XmlLineMarkerProvider : RelatedItemLineMarkerProvider() {

    override fun collectNavigationMarkers(element: PsiElement, result: MutableCollection<in RelatedItemLineMarkerInfo<*>>) {
        if (element is XmlTag) {
            val entityId = element.getAttributeValue(ID_ATTRIBUTE) ?: return
            val usages =  findUsages(element, entityId)
            if (usages.isEmpty()) return
            val subIcon = NavigationGutterIconBuilder.create(AllIcons.Actions.Download)
                .setTargets(usages)
                .setTooltipText("Find usages of " + element.name + " " + (element.getAttribute(ID_ATTRIBUTE)?.text ?: ""))
                .setCellRenderer(XmlCellRenderer.INSTANCE)
            result.add(subIcon.createLineMarkerInfo(element))
        }
    }

    private fun findUsages(element: XmlTag, entityId: @Nullable String): List<XmlAttribute> {
        val usages = mutableListOf<XmlAttribute>()
        val entityNameWithId = "${element.name}${XmlHelper.ID_POSTFIX}"
        for (xmlFile in XmlHelper.getXmlFilesWithWord(entityNameWithId, element.project)) {
            for (subTag in xmlFile.rootTag?.subTags!!) {
                val attribute = subTag.getAttribute(entityNameWithId)
                if (entityId == attribute?.value) {
                    usages.add(attribute)
                }
            }
        }
        return usages
    }

    private class XmlCellRenderer : PsiElementListCellRenderer<XmlAttribute>() {
        override fun getElementText(tag: XmlAttribute): String {
            return tag.parent.name + " " + (tag.parent.getAttribute(ID_ATTRIBUTE)?.text ?: "")
        }

        override fun getContainerText(element: XmlAttribute, name: String): String? {
            return null
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