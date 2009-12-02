package org.chartsy.lineonclose;

import java.awt.Graphics2D;
import java.io.Serializable;
import org.chartsy.main.chartsy.ChartFrame;
import org.chartsy.main.chartsy.DefaultPainter;
import org.chartsy.main.chartsy.chart.AbstractChart;

/**
 *
 * @author viorel.gheba
 */
public class LineOnClose extends AbstractChart implements Serializable {

    private static final long serialVersionUID = 101L;

    public LineOnClose() { super("Line On Close", "Description"); }

    public void paint(Graphics2D g, ChartFrame cf) {
        DefaultPainter.line(g, cf, cf.getChartRenderer().getVisibleDataset(), cf.getChartProperties().getBarUpColor(), null);
    }

}