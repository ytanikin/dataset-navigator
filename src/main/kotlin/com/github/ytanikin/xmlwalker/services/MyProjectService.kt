package com.github.ytanikin.xmlwalker.services

import com.github.ytanikin.xmlwalker.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
