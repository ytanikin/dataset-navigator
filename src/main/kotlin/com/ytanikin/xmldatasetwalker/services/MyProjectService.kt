package com.ytanikin.xmldatasetwalker.services

import com.ytanikin.xmldatasetwalker.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
