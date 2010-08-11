package org.chartsy.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;
import org.chartsy.main.utils.MainActions;
import org.chartsy.main.utils.SerialVersion;

/**
 *
 * @author Administrator
 */
public class ChartToolbar extends JToolBar implements Serializable
{

    private static final long serialVersionUID = SerialVersion.APPVERSION;

    private ChartFrame chartFrame;
    private SymbolChanger symbolChanger;

    public ChartToolbar(ChartFrame frame)
    {
        super("ChartToolbar", JToolBar.HORIZONTAL);
        chartFrame = frame;
        initComponents();
        setFloatable(false);
        setBorder(new BottomBorder());
        addMouseListener(new ToolbarOptions(this));
    }

    private void initComponents()
    {
        // SymbolChanger Toolbar
        symbolChanger = new SymbolChanger(chartFrame);
        add(symbolChanger);

		// ChartToolbar buttons
        add(zoomInBtn
			= ToolbarButton.getButton(MainActions.zoomIn(chartFrame)));
        add(zoomOutBtn
			= ToolbarButton.getButton(MainActions.zoomOut(chartFrame)));
        add(intervalsBtn
			= ToolbarButton.getButton(MainActions.intervalPopup(chartFrame)));
        add(chartBtn
			= ToolbarButton.getButton(MainActions.chartPopup(chartFrame)));
        add(indicatorsBtn
			= ToolbarButton.getButton(MainActions.openIndicators(chartFrame)));
        add(overlaysBtn
			= ToolbarButton.getButton(MainActions.openOverlays(chartFrame)));
        add(annotationsBtn
			= ToolbarButton.getButton(MainActions.annotationPopup(chartFrame)));
        add(markerBtn
			= ToolbarToggleButton.getButton(MainActions.toggleMarker(chartFrame)));
        add(exportBtn
			= ToolbarButton.getButton(MainActions.exportImage(chartFrame)));
        add(printBtn
			= ToolbarButton.getButton(MainActions.printChart(chartFrame)));
        add(propertiesBtn
			= ToolbarButton.getButton(MainActions.chartProperties(chartFrame)));

		markerBtn.setSelected(true);
    }

    public void updateToolbar()
    {
		symbolChanger.updateToolbar();
    }

	public void toggleLabels()
	{
		boolean show = chartFrame.getChartProperties().getToolbarShowLabels();
		zoomInBtn.toggleLabel(show);
		zoomOutBtn.toggleLabel(show);
		intervalsBtn.toggleLabel(show);
		chartBtn.toggleLabel(show);
		indicatorsBtn.toggleLabel(show);
		overlaysBtn.toggleLabel(show);
		annotationsBtn.toggleLabel(show);
		markerBtn.toggleLabel(show);
		exportBtn.toggleLabel(show);
		printBtn.toggleLabel(show);
		propertiesBtn.toggleLabel(show);
	}

	public void toggleIcons()
	{
		boolean small = chartFrame.getChartProperties().getToolbarSmallIcons();
		zoomInBtn.toggleIcon(small);
		zoomOutBtn.toggleIcon(small);
		intervalsBtn.toggleIcon(small);
		chartBtn.toggleIcon(small);
		indicatorsBtn.toggleIcon(small);
		overlaysBtn.toggleIcon(small);
		annotationsBtn.toggleIcon(small);
		markerBtn.toggleIcon(small);
		exportBtn.toggleIcon(small);
		printBtn.toggleIcon(small);
		propertiesBtn.toggleIcon(small);
	}

    public JPopupMenu getToolbarMenu()
    {
        JPopupMenu popup = new JPopupMenu();
        JCheckBoxMenuItem item;
        
        popup.add(item = new JCheckBoxMenuItem(
			MainActions.toggleToolbarSmallIcons(chartFrame, this)));
		item.setMargin(new Insets(0,0,0,0));
		item.setState(chartFrame.getChartProperties().getToolbarSmallIcons());

        
        popup.add(item = new JCheckBoxMenuItem(
			MainActions.toggleToolbarShowLabels(chartFrame, this)));
		item.setMargin(new Insets(0,0,0,0));
		item.setState(!chartFrame.getChartProperties().getToolbarShowLabels());

        return popup;
    }

    public static class ToolbarOptions extends MouseAdapter
    {

        private ChartToolbar toolbar;

        public ToolbarOptions(ChartToolbar bar)
        {
            toolbar = bar;
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            if (e.getButton() == MouseEvent.BUTTON3)
            {
                toolbar.getToolbarMenu().show(toolbar, e.getX(), e.getY());
            }
        }

    }

    class BottomBorder extends AbstractBorder implements Serializable
    {

        private static final long serialVersionUID = SerialVersion.APPVERSION;

        protected Color color = new Color(0x898c95);
        protected int thickness = 1;
        protected int gap = 1;

        public BottomBorder()
        {}

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
        {
            Color old = g.getColor();
            g.setColor(color);
            for (int i = 0; i < thickness; i++)
                g.drawLine(x, y + height - i - 1, x + width, y + height - i - 1);
            g.setColor(old);
        }

        @Override
        public Insets getBorderInsets(Component c) 
        { return new Insets(0, 0, gap, 0); }

        @Override
        public Insets getBorderInsets(Component c, Insets insets)
        {
            insets.left = 0;
            insets.top = 0;
            insets.right = 0;
            insets.bottom = gap;
            return insets;
        }

        @Override
        public boolean isBorderOpaque() 
        { return false; }

    }

	public static class ToolbarButton extends JButton
	{

		public static ToolbarButton getButton(Action action)
		{
			return new ToolbarButton(action);
		}

		public ToolbarButton(Action action)
		{
			super(action);

			setVerticalAlignment(SwingConstants.TOP);
			setVerticalTextPosition(SwingConstants.BOTTOM);
			setHorizontalTextPosition(SwingConstants.CENTER);

			setMargin(new Insets(6, 6, 6, 6));
			setBorderPainted(false);
		}

		public void toggleLabel(boolean show)
		{
			if (show) showText();
			else hideText();
		}

		public void hideText() { setText(""); }
		public void showText() { setText((String) getAction().getValue(Action.NAME)); }

		public void toggleIcon(boolean small)
		{
			if (small) showSmallIcon();
			else showBigIcon();
		}

		public void showSmallIcon() { setIcon((ImageIcon) getAction().getValue(Action.SMALL_ICON)); }
		public void showBigIcon() { setIcon((ImageIcon) getAction().getValue(Action.LARGE_ICON_KEY)); }

	}

	public static class ToolbarToggleButton extends JToggleButton
	{

		public static ToolbarToggleButton getButton(Action action)
		{
			return new ToolbarToggleButton(action);
		}

		public ToolbarToggleButton(Action action)
		{
			super(action);

			setVerticalAlignment(SwingConstants.TOP);
			setVerticalTextPosition(SwingConstants.BOTTOM);
			setHorizontalTextPosition(SwingConstants.CENTER);

			setMargin(new Insets(6, 6, 6, 6));
			setBorderPainted(false);
		}

		public void toggleLabel(boolean show)
		{
			if (show) showText();
			else hideText();
		}

		public void hideText() { this.setText(""); }
		public void showText() { setText((String) getAction().getValue(Action.NAME)); }

		public void toggleIcon(boolean small)
		{
			if (small) showSmallIcon();
			else showBigIcon();
		}

		public void showSmallIcon() { setIcon((ImageIcon) getAction().getValue(Action.SMALL_ICON)); }
		public void showBigIcon() 
		{ setIcon((ImageIcon) getAction().getValue(Action.LARGE_ICON_KEY)); }

	}

	private ToolbarButton zoomInBtn;
	private ToolbarButton zoomOutBtn;
	private ToolbarButton intervalsBtn;
	private ToolbarButton chartBtn;
	private ToolbarButton indicatorsBtn;
	private ToolbarButton overlaysBtn;
	private ToolbarButton annotationsBtn;
	private ToolbarToggleButton markerBtn;
	private ToolbarButton exportBtn;
	private ToolbarButton printBtn;
	private ToolbarButton propertiesBtn;

}
