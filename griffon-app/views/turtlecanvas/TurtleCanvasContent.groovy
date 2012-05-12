package turtlecanvas

import org.fife.ui.rsyntaxtextarea.SyntaxConstants

noparent {
    fileChooser(
            id: "scriptChooser",
            fileSelectionMode: JFileChooser.FILES_ONLY,
            fileFilter: [getDescription: {-> "*.turtle"}, accept: {file -> file ==~ /.*?\.turtle/ || file.isDirectory() }] as javax.swing.filechooser.FileFilter,
            multiSelectionEnabled: false,
            currentDirectory: model.scriptDir
    )
    fileChooser(
            id: "dslChooser",
            fileSelectionMode: JFileChooser.FILES_ONLY,
            fileFilter: [getDescription: {-> "*.groovy"}, accept: {file -> file ==~ /.*?\.groovy/ || file.isDirectory() }] as javax.swing.filechooser.FileFilter,
            multiSelectionEnabled: false,
            currentDirectory: model.dslDir
    )
}

panel {
    borderLayout()
    splitPane(orientation: JSplitPane.VERTICAL_SPLIT, dividerLocation: 350, oneTouchExpandable: true) {
        splitPane(orientation: JSplitPane.HORIZONTAL_SPLIT, dividerLocation: 500, oneTouchExpandable: true) {
            panel {
                borderLayout()
                label(app.getMessage('application.label.Script', 'Script'), constraints: NORTH)
                rtextScrollPane {
                    rsyntaxTextArea(id: 'scriptEditor', text: bind(source: model, sourceProperty: 'script', mutual: true), syntaxEditingStyle: SyntaxConstants.SYNTAX_STYLE_GROOVY)
                    scriptEditor.document.addUndoableEditListener(controller.&handleUndo as UndoableEditListener)
                    scriptEditor.addFocusListener(controller.&handleFocus as FocusListener)
                    scriptEditor.document.addDocumentListener([insertUpdate: controller.&handleScriptChanges, removeUpdate: controller.&handleScriptChanges, changedUpdate: controller.&handleScriptChanges] as DocumentListener)
                }
            }
            panel {
                borderLayout()
                label(app.getMessage('application.label.DSL', 'DSL'), constraints: NORTH)
                rtextScrollPane {
                    rsyntaxTextArea(id: 'dslEditor', text: bind(source: model, sourceProperty: 'dsl', mutual: true), syntaxEditingStyle: SyntaxConstants.SYNTAX_STYLE_GROOVY)
                    dslEditor.document.addUndoableEditListener(controller.&handleUndo as UndoableEditListener)
                    dslEditor.addFocusListener(controller.&handleFocus as FocusListener)
                    dslEditor.document.addDocumentListener([insertUpdate: controller.&handleDslChanges, removeUpdate: controller.&handleDslChanges, changedUpdate: controller.&handleDslChanges] as DocumentListener)
                }
            }

        }
        splitPane(orientation: JSplitPane.VERTICAL_SPLIT, dividerLocation: 250, oneTouchExpandable: true) {
            splitPane(orientation: JSplitPane.HORIZONTAL_SPLIT, dividerLocation: 300, oneTouchExpandable: true) {
                panel {
                    borderLayout()
                    label(app.getMessage('application.label.Result', 'Result'), constraints: NORTH)
                    widget(new TurtleCanvas(), id: 'canvas')
                }
                panel {
                    borderLayout()
                    label(app.getMessage('application.label.Help', 'Help'), constraints: NORTH)
                    scrollPane {
                        textArea(id: 'help', editable: false, text: bind { model.help })
                    }
                }
            }
            panel {
                borderLayout()
                label(app.getMessage('application.label.Console', 'Console'), constraints: NORTH)
                scrollPane {
                    textArea(id: 'log', editable: false, text: bind { model.log })
                }
            }
        }
    }
}
