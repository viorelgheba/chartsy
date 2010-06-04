package org.chartsy.fractaldimension;

import java.awt.Color;
import java.awt.Stroke;
import java.beans.PropertyEditorSupport;
import java.util.logging.Level;
import org.chartsy.main.chart.AbstractPropertiesNode;
import org.chartsy.main.utils.AlphaPropertyEditor;
import org.chartsy.main.utils.SerialVersion;
import org.chartsy.main.utils.StrokeGenerator;
import org.chartsy.main.utils.StrokePropertyEditor;
import org.openide.nodes.Sheet;

/**
 *
 * @author viorel.gheba
 */
public class IndicatorNode 
        extends AbstractPropertiesNode
{

    private static final long serialVersionUID = SerialVersion.APPVERSION;

    public IndicatorNode()
    {
        super("Fractal Dimension Properties");
    }

    public IndicatorNode(IndicatorProperties indicatorProperties)
    {
        super("Fractal Dimension Properties", indicatorProperties);
    }

    @SuppressWarnings("unchecked")
    protected @Override Sheet createSheet()
    {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = getPropertiesSet();
        sheet.put(set);

        try
        {
            // Period
            set.put(getProperty(
                    "Period", // property name
                    "Sets the period value", // property description
                    IndicatorProperties.class, // properties class
                    int.class, // property class
                    null, // property editor class (null if none)
                    "getPeriod", // get method name
                    "setPeriod", // set method name
                    IndicatorProperties.PERIOD // default property value
                    ));
            // Label
            set.put(getProperty(
                    "Label", // property name
                    "Sets the label", // property description
                    IndicatorProperties.class, // properties class
                    String.class, // property class
                    PropertyEditorSupport.class, // property editor class (null if none)
                    "getLabel", // get method name
                    "setLabel", // set method name
                    IndicatorProperties.LABEL // default property value
                    ));
            // Marker Visibility
            set.put(getProperty(
                    "Marker Visibility", // property name
                    "Sets the marker visibility", // property description
                    IndicatorProperties.class, // properties class
                    boolean.class, // property class
                    null, // property editor class (null if none)
                    "getMarker", // get method name
                    "setMarker", // set method name
                    IndicatorProperties.MARKER // default property value
                    ));
            // Line Color
            set.put(getProperty(
                    "Line Color", // property name
                    "Sets the line color", // property description
                    IndicatorProperties.class, // properties class
                    Color.class, // property class
                    null, // property editor class (null if none)
                    "getColor", // get method name
                    "setColor", // set method name
                    IndicatorProperties.COLOR // default property value
                    ));
            // Line Style
            set.put(getProperty(
                    "Line Style", // property name
                    "Sets the line style", // property description
                    IndicatorProperties.class, // properties class
                    Stroke.class, // property class
                    StrokePropertyEditor.class, // property editor class (null if none)
                    "getStroke", // get method name
                    "setStroke", // set method name
                    StrokeGenerator.getStroke(IndicatorProperties.STROKE_INDEX) // default property value
                    ));
            // Delimiters Line Color
            set.put(getProperty(
                    "Delimiters Line Color", // property name
                    "Sets the delimiters line color", // property description
                    IndicatorProperties.class, // properties class
                    Color.class, // property class
                    null, // property editor class (null if none)
                    "getDelimiterColor", // get method name
                    "setDelimiterColor", // set method name
                    IndicatorProperties.DELIMITER_COLOR // default property value
                    ));
            // Delimiters Line Style
            set.put(getProperty(
                    "Delimiters Line Style", // property name
                    "Sets the delimiters line style", // property description
                    IndicatorProperties.class, // properties class
                    Stroke.class, // property class
                    StrokePropertyEditor.class, // property editor class (null if none)
                    "getDelimiterStroke", // get method name
                    "setDelimiterStroke", // set method name
                    StrokeGenerator.getStroke(IndicatorProperties.DELIMITER_STROKE_INDEX) // default property value
                    ));
            // Inside Color
            set.put(getProperty(
                    "Inside Color", // property name
                    "Sets the inside color", // property description
                    IndicatorProperties.class, // properties class
                    Color.class, // property class
                    null, // property editor class (null if none)
                    "getInsideColor", // get method name
                    "setInsideColor", // set method name
                    IndicatorProperties.INSIDE_COLOR // default property value
                    ));
            // Inside Alpha
            set.put(getProperty(
                    "Inside Alpha", // property name
                    "Sets the inside alpha value", // property description
                    IndicatorProperties.class, // properties class
                    int.class, // property class
                    AlphaPropertyEditor.class, // property editor class (null if none)
                    "getInsideAlpha", // get method name
                    "setInsideAlpha", // set method name
                    IndicatorProperties.INSIDE_ALPHA // default property value
                    ));
            // Inside Visibility
            set.put(getProperty(
                    "Inside Visibility", // property name
                    "Sets the inside visibility flag", // property description
                    IndicatorProperties.class, // properties class
                    boolean.class, // property class
                    null, // property editor class (null if none)
                    "getInsideVisibility", // get method name
                    "setInsideVisibility", // set method name
                    IndicatorProperties.INSIDE_VISIBILITY // default property value
                    ));
        }
        catch (NoSuchMethodException ex)
        {
            LOG.log(Level.SEVERE, "[FractalDimensionNode] : Method does not exist.", ex);
        }

        return sheet;
    }

}
