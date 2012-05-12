application {
    title = 'TurtleCanvas'
    startupGroups = ['turtleCanvas']

    // Should Griffon exit when no Griffon created frames are showing?
    autoShutdown = true

    // If you want some non-standard application class, apply it here
    //frameClass = 'javax.swing.JFrame'
}
mvcGroups {
    // MVC Group for "preferences"
    'preferences' {
        model      = 'turtlecanvas.PreferencesModel'
        view       = 'turtlecanvas.PreferencesView'
        controller = 'turtlecanvas.DialogController'
    }

    // MVC Group for "license"
    'license' {
        model      = 'turtlecanvas.LicenseModel'
        view       = 'turtlecanvas.LicenseView'
        controller = 'turtlecanvas.DialogController'
    }

    // MVC Group for "credits"
    'credits' {
        model      = 'turtlecanvas.CreditsModel'
        view       = 'turtlecanvas.CreditsView'
        controller = 'turtlecanvas.DialogController'
    }

    // MVC Group for "about"
    'about' {
        model      = 'turtlecanvas.AboutModel'
        view       = 'turtlecanvas.AboutView'
        controller = 'turtlecanvas.DialogController'
    }

    // MVC Group for "turtleCanvas"
    'turtleCanvas' {
        model      = 'turtlecanvas.TurtleCanvasModel'
        view       = 'turtlecanvas.TurtleCanvasView'
        controller = 'turtlecanvas.TurtleCanvasController'
    }

}
