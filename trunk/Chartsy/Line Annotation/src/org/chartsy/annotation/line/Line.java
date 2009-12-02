package org.chartsy.annotation.line;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import org.chartsy.main.chartsy.ChartFrame;
import org.chartsy.main.chartsy.chart.Annotation;
import org.openide.nodes.AbstractNode;

/**
 *
 * @author Viorel
 */
public class Line extends Annotation implements Serializable {

    private static final long serialVersionUID = 101L;

    private AnnotationProperties properties = new AnnotationProperties();

    public Line(ChartFrame cf) {
        super(cf);
        inflectionSet.set(TOP_LEFT);
        inflectionSet.set(BOTTOM_RIGHT);
    }

    public boolean pointIntersects(double x, double y) { return (getInflectionPoint(x, y) != NONE) || lineContains(getP1(), getP2(), new Point2D.Double(x, y), 4); }

    public void paint(Graphics2D g) {
        Stroke old = g.getStroke();
        g.setPaint(properties.getColor());
        g.setStroke(properties.getStroke());
        Point2D.Double p1 = getP1();
        Point2D.Double p2 = getP2();
        g.draw(new Line2D.Double(p1, p2));
        g.setStroke(old);
        if (isSelected()) paintInflectionPoints(g);
    }

    protected void updateRectangles(long oldT1, long newT1, long oldT2, long newT2, double oldV1, double newV1, double oldV2, double newV2) {
        Rectangle2D.Double r;
        r = getRectangle(oldT1, oldV1, oldT2, oldV2);
        Rectangle r1 = new Rectangle(); r1.setFrameFromDiagonal(r.getMinX(), r.getMinY(), r.getMaxX(), r.getMaxY());
        r1.grow(3, 3);
        addUpdateRectangle(r1, false);
        r = getRectangle(newT1, newV1, newT2, newV2);
        Rectangle r2 = new Rectangle(); r2.setFrameFromDiagonal(r.getMinX(), r.getMinY(), r.getMaxX(), r.getMaxY());
        r2.grow(3, 3);
        addUpdateRectangle(r2, false);
    }

    public AbstractNode getNode() { return new AnnotationNode(properties); }

}
