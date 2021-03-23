package com.ytanikin.xmldatasetwalker.filemetadata

//import com.intellij.psi.PsiClass
import com.intellij.util.xml.DomElement
import com.intellij.util.xml.GenericAttributeValue

interface Dataset : DomElement {

//    fun getNamespace(): GenericAttributeValue<PsiClass?>?
    fun getEntities(): List<GenericAttributeValue<Entity?>?>?

}