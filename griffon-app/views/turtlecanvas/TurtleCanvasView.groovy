package turtlecanvas

build(TurtleCanvasActions)

application(title: GriffonNameUtils.capitalize(app.getMessage('application.title', app.config.application.title)),
    size: [1000,750],
    pack: false,
    locationByPlatform:true,
    iconImage: imageIcon('/turtle-48x48.png').image,
    iconImages: [imageIcon('/turtle-48x48.png').image,
               imageIcon('/turtle-32x32.png').image,
               imageIcon('/turtle-16x16.png').image]) {
   widget(build(TurtleCanvasMenuBar))
   migLayout(layoutConstraints: 'fill')
   widget(build(TurtleCanvasContent), constraints: 'center, grow')
   widget(build(TurtleCanvasStatusBar), constraints: 'south, grow')
}
