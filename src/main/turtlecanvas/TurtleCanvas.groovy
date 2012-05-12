package turtlecanvas

import groovy.transform.InheritConstructors
import groovy.beans.Bindable
import griffon.transform.PropertyListener
import java.awt.image.BufferedImage
import java.awt.geom.CubicCurve2D
import java.awt.geom.Line2D
import java.awt.geom.QuadCurve2D
import java.awt.geom.Point2D
import javax.swing.JPanel
import java.awt.Paint
import java.awt.Color
import java.awt.event.ComponentEvent
import java.awt.event.ComponentAdapter
import java.awt.Component
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Dimension
import java.awt.geom.AffineTransform
import java.awt.Stroke
import java.awt.BasicStroke
import java.awt.RenderingHints

@InheritConstructors
class TurtleCanvas extends JPanel {
    Paint background = Color.WHITE
    Paint color = Color.BLACK
    Stroke stroke = new BasicStroke(1)
    protected final Point2D pos = new Point2D.Double(0d, 0d)
    protected final AffineTransform transform = new AffineTransform()
    protected BufferedImage img = recreateImage(this)
    private double angle = 0

    TurtleCanvas() {
        addComponentListener([
                componentResized: {ComponentEvent e ->
                    recreateImage(e.component)
                }
        ] as ComponentAdapter)
    }

    private recreateImage(Component c) {
        img = new BufferedImage(c.width ?: 1, c.height ?: 1, BufferedImage.TYPE_INT_ARGB)
        clear()
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, this)
    }

    void turn(double degree) {
        turnRad Math.toRadians(degree)
    }

    void turnRad(double radians) {
        angle += radians
        transform.rotate(radians, pos.x, pos.y)
    }

    void setAngle(double degree) {
        setAngleRad(Math.toRadians(degree))
    }

    void setAngleRad(double radians) {
        angle = radians
        transform.setToRotation(radians, pos.x, pos.y)
    }

    void moveTo(Point2D p) {
        pos.location = p
    }

    void lineTo(Point2D p) {
        draw { Graphics2D g ->
            g.draw(new Line2D.Double(pos, p))
        }
        pos.location = p
    }

    void curveTo(Point2D end, Point2D ctrl1, Point2D ctrl2) {
        draw { Graphics2D g ->
            g.draw(new CubicCurve2D.Double(pos.x, pos.y, ctrl1.x, ctrl1.y, ctrl2.x, ctrl2.y, end.x, end.y))
        }
        pos.location = end
    }

    void quadCurveTo(Point2D end, Point2D ctrl) {
        draw { Graphics2D g ->
            g.draw(new QuadCurve2D.Double(pos.x, pos.y, ctrl.x, ctrl.y, end.x, end.y))
        }
        pos.location = end
    }

    void horizontalLine(double x) {
        def end = new Point2D.Double(x, pos.y)
        draw { Graphics2D g ->
            g.draw(new Line2D.Double(pos, end))
        }
        pos.location = end
    }

    void verticalLine(double y) {
        def end = new Point2D.Double(pos.x, y)
        draw { Graphics2D g ->
            g.draw(new Line2D.Double(pos, end))
        }
        pos.location = end
    }

    void clear() {
        transform.setToIdentity()
        draw { Graphics2D g ->
            g.paint = background
            g.fillRect(0, 0, img.width, img.height)
        }
    }

    Point2D getPos() {
        return pos
    }

    double getAngle() {
        return Math.toDegrees(angle)
    }

    double getAngleRad() {
        return angle
    }

    protected draw(Closure closure) {
        Graphics2D g = (Graphics2D) img.getGraphics()
        g.paint = color
        g.setTransform(transform)
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY)
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY)
        g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE)
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC)
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
        closure.delegate = [width: img.width, height: img.height]
        closure.call(g)
        g.dispose()
        repaint()
    }

    Dimension getCanvasDimension() {
        new Dimension(img.getWidth(), img.getHeight())
    }
}
