package org.chartsy.annotation.verticalline;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import org.chartsy.main.chartsy.ChartFrame;
import org.chartsy.main.chartsy.chart.Annotation;
import org.chartsy.main.utils.StrokeGenerator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author viorel.gheba
 */
public class VerticalLine extends Annotation implements Serializable {

    private static final long serialVersionUID = 101L;

    public VerticalLine(ChartFrame chartFrame) {
        super(chartFrame);
        inflectionSet.set(TOP);
        inflectionSet.set(BOTTOM);
    }

    public boolean pointIntersects(double x, double y) { double X = getXCoord(getT1()); return (getInflectionPoint(x, y) != NONE) || lineContains(X, getBounds().getMinY(), X, getBounds().getMaxY(), x, y, 4); }

    public void paint(Graphics2D g) {
        double x = getXCoord(getT1());
        Stroke old = g.getStroke();
        g.setPaint(color);
        g.setStroke(StrokeGenerator.getStroke(strokeIndex));
        g.draw(new Line2D.Double(x, getBounds().getMinY(), x, getBounds().getMaxY()));
        g.setStroke(old);
        if (isSelected()) paintInflectionPoints(g);
    }

    protected void paintInflectionPoints(Graphics2D g) {
        double x = getXCoord(getT1());
        g.setPaint(Color.BLACK);
        if (inflectionSet.get(TOP)) {
            g.draw(new Rectangle2D.Double(x - 2, getBounds().getMinY() + 5 - 2, 4, 4));
        }
        if (inflectionSet.get(BOTTOM)) {
            g.draw(new Rectangle2D.Double(x - 2, getBounds().getMaxY() - 5 - 2, 4, 4));
        }
    }

    public void readXMLDocument(Element parent) { readFromXMLDocument(parent); }
    public void writeXMLDocument(Document document, Element parent) { writeToXMLDocument(document, parent, "Vertical Line"); }

}