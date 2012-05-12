package turtlecanvas

import javax.swing.JFileChooser
import groovy.ui.SystemOutputInterceptor
import javax.swing.event.UndoableEditEvent
import javax.swing.event.DocumentEvent
import java.awt.event.FocusEvent

class TurtleCanvasController {
    TurtleCanvasModel model
    def view
    def builder
    def SystemOutputInterceptor outInterceptor = new SystemOutputInterceptor(this.&log, true)
    def SystemOutputInterceptor errInterceptor = new SystemOutputInterceptor(this.&log, false)

    def newAction = {
        model.script = ''
        model.scriptFile = null
        view.scriptEditor.requestFocus()
    }

    def openAction = {
        view.scriptChooser.dialogTitle = app.getMessage('application.dialog.Open.title')
        if (view.scriptChooser.showOpenDialog() != JFileChooser.APPROVE_OPTION)
            return
        model.scriptFile = view.scriptChooser.selectedFile
        model.scriptDir = model.scriptFile.parentFile
        model.script = model.scriptFile.text
        view.scriptEditor.requestFocus()
    }

    def saveAction = {
        if (model.scriptFile != null) {
            model.scriptFile.text = model.script
            model.dirtyScript = false
            view.scriptEditor.requestFocus()
        } else
            saveAsAction()
    }

    def saveAsAction = {
        view.scriptChooser.dialogTitle = app.getMessage('application.dialog.Save.title')
        if (view.scriptChooser.showSaveDialog() != JFileChooser.APPROVE_OPTION)
            return
        model.scriptFile = view.scriptChooser.selectedFile
        if(!model.scriptFile.name.endsWith('.turtle'))
            model.scriptFile = new File(model.scriptFile.parentFile, "${model.scriptFile.name}.turtle")
        model.scriptDir = model.scriptFile.parentFile
        model.scriptFile.text = model.script
        model.dirtyScript = false
        view.scriptEditor.requestFocus()
    }

    def newDSLAction = {
        model.dsl = ''
        model.dslFile = null
        view.dslEditor.requestFocus()
    }

    def openDSLAction = {
        view.dslChooser.dialogTitle = app.getMessage('application.dialog.OpenDSL.title')
        if (view.dslChooser.showOpenDialog() != JFileChooser.APPROVE_OPTION)
            return
        model.dslFile = view.dslChooser.selectedFile
        model.dslDir = model.dslFile.parentFile
        model.dsl = model.dslFile.text
        view.dslEditor.requestFocus()
    }

    def saveDSLAction = {
        if (model.dslFile != null) {
            model.dslFile.text = model.dsl
            model.dirtyDsl = false
            view.dslEditor.requestFocus()
        } else
            saveDSLAsAction()
    }

    def saveDSLAsAction = {
        view.dslChooser.dialogTitle = app.getMessage('application.dialog.SaveDSL.title')
        if (view.dslChooser.showSaveDialog() != JFileChooser.APPROVE_OPTION)
            return
        model.dslFile = view.dslChooser.selectedFile
        if(!model.dslFile.name.endsWith('.groovy'))
            model.dslFile = new File(model.dslFile.parentFile, "${model.dslFile.name}.groovy")
        model.dslDir = model.dslFile.parentFile
        model.dslFile.text = model.dsl
        model.dirtyDsl = false
        view.dslEditor.requestFocus()
    }

    def aboutAction = {
        withMVCGroup('about') { m, v, c ->
            c.show()
        }
    }

    def preferencesAction = {
        withMVCGroup('preferences') { m, v, c ->
            c.show()
        }
    }

    def quitAction = {
        app.shutdown()
    }

    def undoAction = {
        model.active.undoLastAction()
    }

    def redoAction = {
        model.active.redoLastAction()
    }

    def cutAction = {
        model.active.cut()
    }

    def copyAction = {
        model.active.copy()
    }

    def pasteAction = {
        model.active.paste()
    }

    def runAction = {
        view.canvas.clear()
        def dimension = view.canvas.canvasDimension
        Binding binding = new Binding([
                script: model.script,
                canvas: view.canvas,
                width: dimension.width,
                height: dimension.height,
        ])
        GroovyShell shell = new GroovyShell(binding)
        try {
            outInterceptor.start()
            errInterceptor.start()
            shell.evaluate(model.dsl)
        } catch (Throwable e) {
            e.printStackTrace()
        } finally {
            outInterceptor.stop()
            errInterceptor.stop()
        }
    }

    def clearAction = {
        view.canvas.clear()
        model.log = ''
    }

    def handleUndo(def evt = null) {
        model.undoActive = model.active?.canUndo()
        model.redoActive = model.active?.canRedo()
    }

    def handleFocus(FocusEvent evt) {
        model.active = evt.source
        handleUndo()
    }

    def handleDslChanges(DocumentEvent evt = null) {
        model.dirtyDsl = true
    }

    def handleScriptChanges(DocumentEvent evt = null) {
        model.dirtyScript = true
    }

    protected log(String msg) {
        model.log += msg
        return true
    }

    def onOSXAbout = { app ->
        withMVCGroup('about') { m, v, c ->
            c.show()
        }
    }

    def onOSXQuit = { app ->
        quitAction()
    }

    def onOSXPrefs = { app ->
        withMVCGroup('preferences') { m, v, c ->
            c.show()
        }
    }
}
