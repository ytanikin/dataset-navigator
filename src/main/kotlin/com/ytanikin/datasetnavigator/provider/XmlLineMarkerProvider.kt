package com.ytanikin.datasetnavigator.provider

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.icons.AllIcons
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.cache.CacheManager
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.UsageSearchContext
import com.intellij.psi.xml.XmlFile
import com.intellij.psi.xml.XmlTag
import com.intellij.util.xml.DomElement
import com.intellij.util.xml.DomManager

open class XmlLineMarkerProvider : RelatedItemLineMarkerProvider() {

    override fun collectNavigationMarkers(element: PsiElement, result: MutableCollection<in RelatedItemLineMarkerInfo<*>>) {
//        if (element !is XmlFile) {
//            return
//        }

//        val dataset: Dataset? = findFileElement(element, Dataset::class.java)
//        if (dataset == null) {
//            return
//        }
//        if (dataset.getEntities()?.isEmpty() == true) {
//            return
//        }
//        val domManager = DomManager.getDomManager(element.getProject())
//        if (element is XmlAttribute) {
//            if (element.name == "sss") {
//                println("ss")
//            }
//        }
//        if (element is XmlAttribute) {
//            val domElement = domManager.getDomElement(element)
//            val xmlTag = domElement?.xmlTag
//            var name = xmlTag?.name

//            for (child in element.children) {
        val tags = mutableListOf<PsiElement?>()
        if (element is XmlTag) {
            val entityId = element.getAttributeValue("ID") ?: return
            val entityName = element.name
            val entityNameWithId = "${entityName}_ID"
            val xmlFiles = CacheManager.getInstance(element.getProject()).getFilesWithWord(
                entityNameWithId, UsageSearchContext.ANY,
                GlobalSearchScope.projectScope(element.getProject()), false
            ).filterIsInstance<XmlFile>().filter { "dataset" == it.rootTag?.name && it.rootTag!!.subTags != null }
            for (xmlFile in xmlFiles) {
                for (subTag in xmlFile.rootTag?.subTags!!) {
                    val attribute = subTag.getAttribute(entityNameWithId)
                    if (entityId == attribute?.value) {
                        tags.add(subTag.navigationElement)
                    }
                }
            }
        }
        if (tags.isEmpty()) return

        val subIcon = NavigationGutterIconBuilder.create(AllIcons.Gutter.ImplementingMethod)
            .setTargets(tags)
            .setNamer { it.text + "sf" }
//            .setTooltipText("subTag.name")
        result.add(subIcon.createLineMarkerInfo(element))

//                            .setTooltipText("fs d sd sdftf sdfe toasdf asd data ${subTag.name}")
//                            .setTooltipTitle("title")
//                            .setNamer(function)
//                            .setPopupTitle("ABCDEFGHJKLMNOPQRST")
//                            .createGutterIcon()
//                        RelatedItemLineMarkerInfo(
//                            element,
//                            TextRange(0, 10),
//                            AllIcons.Gutter.ImplementingMethod,
//                            function,
//                            DefaultGutterIconNavigationHandler(listOf(subTag), "mytitle")
//                        )

        /*  if (element is XmlAttributeImpl) {
              if (element.name != null) {
                  if (element.name.endsWith("CUSTOMER_ID")) { //todo child.name == ID child.value = 1
                      val cacheManager = CacheManager.getInstance(element.getProject())
                      val psiFiles = cacheManager.getFilesWithWord(
                          "CUSTOMER", UsageSearchContext.ANY,
                          GlobalSearchScope.projectScope(element.getProject()), false
                      )
  //                            cacheManager.getVirtualFilesWithWord()
  //                            val xmlFile : XmlFile
  //                            xmlFile.document.children
  //                            xmlFile.rootTag
                      val xmlFiles = psiFiles.stream()
                          .filter { it is XmlFile } //todo add xmlFile.rootTag what dataset
  //                                .map { domManager.getFileElement(it as XmlFile, Dataset::class.java) }
  //                                .filter(Objects::nonNull)
  //                              .map(DomFileElement::getRootElement)
                          .toList()
                      for (xmlFile in xmlFiles) {
                          for (child7 in xmlFile.children[0].children[1].children) {
                              if (child7 is XmlTag) {
                                  for (child8 in child7.children) {
                                      if (child8 is XmlToken) {
                                          if (child8.text == "CUSTOMER") {
                                              if (child7.children[3].text == "ID=\"1\"") {
                                                  val subIconBuilder = NavigationGutterIconBuilder.create(AllIcons.Gutter.ImplementedMethod)
                                                      .setTarget(child7)
                                                      .setTooltipText("navigate to data")

                                                  result.add(subIconBuilder.createLineMarkerInfo(element))

                                              }
                                          }
                                      }
                                  }
                              }
                          }*/
/*                        for (child5 in xmlFile.children[0].children[1].children) {
//                                    child5.children ==
//                                    xmlFile.children[0].children[1].children[4].children[5].text == INVOICE_NUMBER="00001"
                            //xmlFile.children[0].children[1].children[4].children[1].text == customer
                            if (child5.text.contains("CUSTOMER")) {
                                if (child5.text == "ID") {
                                    if (child5 is XmlAttribute) {
                                        if (child5.value == element.value) {
                                            val subIconBuilder = NavigationGutterIconBuilder.create(AllIcons.Gutter.ImplementingMethod)
                                                .setTarget(child5)
                                                .setTooltipText(child5.name)

                                            result.add(subIconBuilder.createLineMarkerInfo(element.getFirstChild()))
                                        }
                                    }

                                }
                            }
                        }
                        for (child1 in xmlFile.children) {
                            for (child3 in child1.children) {
                                for (child2 in child3.children) {
                                    if (child2.textMatches("CUSTOMER")) {
                                        if (child2.text == "ID") {
                                            if (child2 is XmlAttribute) {
                                                if (child2.value == element.value) {
                                                    val subIconBuilder = NavigationGutterIconBuilder.create(AllIcons.Gutter.ImplementingMethod)
                                                        .setTarget(child2)
                                                        .setTooltipText(child2.name)

                                                    result.add(subIconBuilder.createLineMarkerInfo(element.getFirstChild()))
                                                }
                                            }

                                        }
                                    }
                                }

                            }
                        }*/
    }


//            xmlTag.getAttributeValue()
//            (element.children[3] as XmlAttributeImpl).value
//            (element.children[3] as XmlAttributeImpl).name


//        if (element is XmlToken) {
//            element.text
//        }

/*        val dataSets = psiFiles.stream()
            .filter { it is XmlFile }
            .map { domManager.getFileElement(it as XmlFile, Dataset::class.java) }
            .filter(Objects::nonNull)
//            .map(DomFileElement::getRootElement)
            .toList()
        dataSets.forEach {
            val methodIconBuilder = NavigationGutterIconBuilder.create(AllIcons.Gutter.ImplementedMethod)
                .setTarget(it?.xmlTag)
                .setTooltipText("it.()")
            result.add(methodIconBuilder.createLineMarkerInfo(it?.xmlElement!!))
        }*/

//        dataset.xmlElementName
//        result.
    }

    private fun <T : DomElement?> findFileElement(element: PsiElement, domClass: Class<T>?): T? {
        if (element !is XmlFile) {
            return null
        }
        val domManager = DomManager.getDomManager(element.getProject())
        val domFileElement = domManager.getFileElement(element, domClass) ?: return null
        return domFileElement.rootElement
    }