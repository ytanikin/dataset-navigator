package com.ytanikin.xmldatasetwalker.filemetadata

import com.intellij.util.xml.DomElement
import com.intellij.util.xml.GenericAttributeValue

interface Result : DomElement {

    fun getEntities(): GenericAttributeValue<Entity>
}