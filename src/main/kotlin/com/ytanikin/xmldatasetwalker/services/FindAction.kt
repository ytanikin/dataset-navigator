package com.ytanikin.xmldatasetwalker.services

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.xml.XmlFile
import com.intellij.psi.xml.XmlTag
import com.intellij.util.xml.DomElement

class FindAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project : Project? = e.project
        val editor : Editor? = PlatformDataKeys.EDITOR.getData(e.dataContext)
        val offset = editor?.caretModel?.offset
        val primaryCaret: Caret? = editor?.caretModel?.primaryCaret
        val logicalPosition = primaryCaret?.logicalPosition
        val column = logicalPosition?.column
        val editorDocument = editor?.document
        val text = editorDocument?.text
//        val editor: Editor = FileEditorManager.getInstance(project).getSelectedTextEditor()
//        val file1: @Nullable VirtualFile? = FileDocumentManager.getInstance().getFile(editor.document)
//        val offset = editor.caretModel.offset

        val file: VirtualFile? = e.getData(PlatformDataKeys.VIRTUAL_FILE)
        val element: PsiElement? = file?.let { PsiManager.getInstance(project!!).findFile(it) }!!.findElementAt(offset!!)
/*
        element?.node.
        PsiTreeUtil.findFirstParent(element, object : Condition<PsiElement?> {
            override fun value(psiElement: PsiElement?): Boolean {
                return true;
            }
        })
*/

        val xmlFile : XmlFile = PsiManager.getInstance(project!!).findFile(file!!) as XmlFile
        val document = xmlFile.document;
        if (document != null) {
            val rootTag: XmlTag = document.rootTag ?: return
            if (rootTag.name == "dataset") {
                for (attribute in rootTag.attributes) {
                    val nameElement = attribute.nameElement

                }
            }
        }
//        WriteCommandAction.runWriteCommandAction(project) { editorDocument?.replaceString(0, 1, "editor_basics") }
/*
        val manager: DomManager = DomManager.getDomManager(project)
        val root = manager.getFileElement(xmlFile, Root::class.java)?.rootElement
        val entities: List<Entity?> = root?.dataset?.entities ?: return
        if (entities.isEmpty()) {
            return
        }
        for (entity in entities) {
            entity.getvalue
        }
*/




        //        val xmlFile = (XmlFile) PsiManager.getInstance(project!!).findFile(file)

//        file.getDocument().getRootTag().findFirstSubTag("foo")

        // If an element is selected in the editor, add info about it.
//        val nav: @Nullable com.intellij.pom.Navigatable? = e.getData(CommonDataKeys.NAVIGATABLE)
        Messages.showMessageDialog(project, "no usages", "usages not found", Messages.getInformationIcon());
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = performAction(e)
    }

    private fun performAction(e: AnActionEvent): Boolean {
        val project: Project? = e.project
        val file: VirtualFile? = e.getData(PlatformDataKeys.VIRTUAL_FILE)
        val xmlFile: XmlFile = file?.let { project?.let { it1 -> PsiManager.getInstance(it1).findFile(it) } } as XmlFile
        val rootTag = xmlFile.document?.rootTag?.name
        return rootTag == "dataset"
    }
}