package org.chartsy.tema;

import java.awt.Color;
import java.awt.Stroke;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.chartsy.main.dataset.Dataset;
import org.chartsy.main.utils.RandomColor;
import org.chartsy.main.utils.StrokeGenerator;

/**
 *
 * @author viorel.gheba
 */
public class OverlayProperties implements Serializable {

    private static final long serialVersionUID = 101L;

    public static final int PERIOD = 20;
    public static final String PRICE = Dataset.CLOSE;
    public static final String LABEL = "TEMA";
    public static final boolean MARKER = true;
    public static Color COLOR;
    public static final int STROKE_INDEX = 0;

    private int period = PERIOD;
    private String price = PRICE;
    private String label = LABEL;
    private boolean marker = MARKER;
    private Color color;
    private int strokeIndex = STROKE_INDEX;

    private List listeners = Collections.synchronizedList(new LinkedList());

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        listeners.add(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        listeners.remove(pcl);
    }

    private void fire(String propertyName, Object old, Object nue) {
        PropertyChangeListener[] pcls = (PropertyChangeListener[]) listeners.toArray(new PropertyChangeListener[0]);
        for (int i = 0; i < pcls.length; i++) {
            pcls[i].propertyChange(new PropertyChangeEvent(this, propertyName, old, nue));
        }
    }

    public OverlayProperties() { COLOR = RandomColor.getRandomColor(); color = COLOR; }

    public int getPeriod() { return period; }
    public void setPeriod(int i) { period = i; }

    public String getPrice() { return price; }
    public void setPrice(String s) { price = s; }

    public String getLabel() { return label; }
    public void setLabel(String s) { label = s; }

    public boolean getMarker() { return marker; }
    public void setMarker(boolean b) { marker = b; }

    public Color getColor() { return color; }
    public void setColor(Color c) { color = c; }

    public int getStrokeIndex() { return strokeIndex; }
    public void setStrokeIndex(int i) { strokeIndex = i; }
    public Stroke getStroke() { return StrokeGenerator.getStroke(strokeIndex); }
    public void setStroke(Stroke s) { strokeIndex = StrokeGenerator.getStrokeIndex(s); }

}
