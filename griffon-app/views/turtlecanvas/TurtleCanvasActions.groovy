package turtlecanvas

import javax.swing.KeyStroke

actions {
    action(newAction,
            accelerator: KeyStroke.getKeyStroke(app.getMessage('application.action.New.shortcut', 'ctrl N')))
    action(openAction,
            accelerator: KeyStroke.getKeyStroke(app.getMessage('application.action.Open.shortcut', 'ctrl O')))
    action(saveAction,
            accelerator: KeyStroke.getKeyStroke(app.getMessage('application.action.Save.shortcut', 'ctrl S')),
            enabled: bind {model.dirtyScript})
    action(saveAsAction,
            enabled: bind {model.dirtyScript})
    action(newDSLAction,
            accelerator: KeyStroke.getKeyStroke(app.getMessage('application.action.NewDSL.shortcut', 'ctrl shift N')))
    action(openDSLAction,
            accelerator: KeyStroke.getKeyStroke(app.getMessage('application.action.OpenDSL.shortcut', 'ctrl shift O')))
    action(saveDSLAction,
            accelerator: KeyStroke.getKeyStroke(app.getMessage('application.action.SaveDSL.shortcut', 'ctrl shift S')),
            enabled: bind {model.dirtyDsl})
    action(saveDSLAsAction,
            enabled: bind {model.dirtyDsl})
    action(quitAction,
            accelerator: KeyStroke.getKeyStroke(app.getMessage('application.action.Quit.shortcut', 'ctrl Q')))
    action(undoAction,
            accelerator: KeyStroke.getKeyStroke(app.getMessage('application.action.Undo.shortcut', 'ctrl Z')),
            enabled: bind { model.undoActive })
    action(redoAction,
            accelerator: KeyStroke.getKeyStroke(app.getMessage('application.action.Redo.shortcut', 'ctrl Y')),
            enabled: bind { model.redoActive })
    action(cutAction,
            accelerator: KeyStroke.getKeyStroke(app.getMessage('application.action.Cut.shortcut', 'ctrl X')))
    action(copyAction,
            accelerator: KeyStroke.getKeyStroke(app.getMessage('application.action.Copy.shortcut', 'ctrl C')))
    action(pasteAction,
            accelerator: KeyStroke.getKeyStroke(app.getMessage('application.action.Paste.shortcut', 'ctrl V')))
    action(runAction,
            accelerator: KeyStroke.getKeyStroke(app.getMessage('application.action.Run.shortcut', 'ctrl R')))
    action(clearAction,
            accelerator: KeyStroke.getKeyStroke(app.getMessage('application.action.Clear.shortcut', 'ctrl W')))
    action(aboutAction)
    action(preferencesAction)
}