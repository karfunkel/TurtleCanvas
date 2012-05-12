package turtlecanvas

import java.util.prefs.Preferences
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea
import groovy.transform.Synchronized

class TurtleCanvasModel {
    private static String HELP = null

    protected Preferences node = Preferences.userRoot().node('TurtleCanvas')

    @Bindable String help
    @Bindable String status
    @Bindable String script = ''
    @Bindable File scriptFile
    @Bindable boolean dirtyScript
    @Bindable String dsl = ''
    @Bindable File dslFile
    @Bindable boolean dirtyDsl
    @Bindable String log = ''
    @Bindable RSyntaxTextArea active
    @Bindable boolean undoActive
    @Bindable boolean redoActive
    @Bindable boolean cutActive
    @Bindable boolean copyActive
    @Bindable boolean pasteActive
    @Bindable File scriptDir = new File(node.get('scriptDir', null) ?: System.properties."user.home")
    @Bindable File dslDir = new File(node.get('dslDir', null) ?: System.properties."user.home")

    void mvcGroupInit(Map<String, Object> args) {
        super.mvcGroupInit(args)
        help = fetchHelpText()
    }

    void setScriptDir(File scriptDir) {
        this.scriptDir = scriptDir
        if (scriptDir == null)
            node.remove('scriptDir')
        else
            node.put('scriptDir', scriptDir.absolutePath)
    }

    void setDslDir(File dslDir) {
        this.dslDir = dslDir
        if (dslDir == null)
            node.remove('dslDir')
        else
            node.put('dslDir', dslDir.absolutePath)
    }

    @Synchronized
    static fetchHelpText() {
        if(HELP == null) {
            try {
                HELP = CreditsModel.class.getResource('/help.txt').text
            } catch(x) {
                HELP = ''
            }
        }
        HELP
    }

}
