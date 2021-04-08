package com.ytanikin.datasetnavigator.provider

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.icons.AllIcons
import com.intellij.ide.util.PsiElementListCellRenderer
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.cache.CacheManager
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.UsageSearchContext
import com.intellij.psi.xml.XmlFile
import com.intellij.psi.xml.XmlTag
import com.intellij.util.xml.DomManager
import javax.swing.Icon

open class XmlLineMarkerProvider : RelatedItemLineMarkerProvider() {

    override fun collectNavigationMarkers(element: PsiElement, result: MutableCollection<in RelatedItemLineMarkerInfo<*>>) {
        val tags = mutableListOf<XmlTag?>()
        if (element is XmlTag) {
            val entityId = element.getAttributeValue("ID") ?: return
            val entityName = element.name
            val entityNameWithId = "${entityName}_ID"
            val xmlFiles = CacheManager.getInstance(element.getProject()).getFilesWithWord(
                entityNameWithId, UsageSearchContext.ANY,
                GlobalSearchScope.projectScope(element.getProject()), false
            )
                .filterIsInstance<XmlFile>()
                .filter { "dataset" == it.rootTag?.name }
            for (xmlFile in xmlFiles) {
                for (subTag in xmlFile.rootTag?.subTags!!) {
                    val attribute = subTag.getAttribute(entityNameWithId)
                    if (entityId == attribute?.value) {
                        tags.add(subTag)
                    }
                }
            }
            if (tags.isEmpty()) return
            val subIcon = NavigationGutterIconBuilder.create(AllIcons.Actions.Download)
                .setTargets(tags)
                .setTooltipText("Find usages of " + element.name + " " + element.getAttribute("ID"))
                .setCellRenderer(MyListCellRenderer.INSTANCE)
            result.add(subIcon.createLineMarkerInfo(element))
        }
    }

    private class MyListCellRenderer : PsiElementListCellRenderer<XmlTag>() {
        override fun getElementText(tag: XmlTag): String {
            return tag.name + " " + (tag.getAttribute("ID")?.text ?: "")
        }

        override fun getContainerText(element: XmlTag, name: String): String? {
            return null
        }

        override fun getIcon(element: PsiElement): Icon {
            return AllIcons.Xml.Html_id
        }

        override fun getIconFlags(): Int {
            return 0
        }

        companion object {
            val INSTANCE = MyListCellRenderer()
        }
    }
}