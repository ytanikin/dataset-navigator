package com.ytanikin.xmldatasetwalker.filemetadata

import com.intellij.util.xml.DomElement
import com.intellij.util.xml.GenericAttributeValue

interface Entity : DomElement {
    fun getId(): GenericAttributeValue<Int?>?
}