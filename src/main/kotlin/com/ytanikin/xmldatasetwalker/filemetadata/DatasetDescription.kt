package com.ytanikin.xmldatasetwalker.filemetadata

import com.intellij.util.xml.DomFileDescription
import com.ytanikin.xmldatasetwalker.provider.XmlLineMarkerProvider

class DatasetDescription : DomFileDescription<XmlLineMarkerProvider>(XmlLineMarkerProvider::class.java, "dataset")