package org.chartsy.main.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.LabelUI;
import org.openide.util.Exceptions;

/**
 *
 * @author Viorel
 */
public class HtmlLabelUI extends LabelUI
{

    private static final boolean antialias
		= Boolean.getBoolean("nb.cellrenderer.antialiasing")
         ||Boolean.getBoolean("swing.aatext")
         ||(isGTK() && gtkShouldAntialias())
         || isAqua();

    private static HtmlLabelUI uiInstance;
    private static int FIXED_HEIGHT;

    static
	{
        String ht = System.getProperty("nb.cellrenderer.fixedheight");
        if (ht != null)
		{
            try
			{
                FIXED_HEIGHT = Integer.parseInt(ht);
            } 
			catch (Exception e)
			{}
        }
    }

    private static Map<Object,Object> hintsMap;
    private static Color unfocusedSelBg;
    private static Color unfocusedSelFg;
    private static Boolean gtkAA;

    public static ComponentUI createUI(JComponent c)
	{
        assert c instanceof HtmlRendererImpl;
        if (uiInstance == null)
            uiInstance = new HtmlLabelUI();
        return uiInstance;
    }

    public @Override Dimension getPreferredSize(JComponent c)
	{
        return calcPreferredSize((HtmlRendererImpl) c);
    }

    private static int textWidth(String text, Graphics g, Font f, boolean html)
	{
        if (text != null)
		{
            if (html)
                return Math.round(Math.round(Math.ceil(
					HtmlRenderer.renderHTML
					(text, g, 0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE, f,
					Color.BLACK, HtmlRenderer.STYLE_CLIP, false))));
			else
                return Math.round(Math.round(Math.ceil(
					HtmlRenderer.renderPlainString
					(text, g, 0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE, f,
					Color.BLACK, HtmlRenderer.STYLE_CLIP, false))));
        } 
		else
		{
            return 0;
        }
    }

    private Dimension calcPreferredSize(HtmlRendererImpl r)
	{
        Insets ins = r.getInsets();
        Dimension prefSize = new java.awt.Dimension(ins.left + ins.right, ins.top + ins.bottom);
        String text = r.getText();

        Graphics g = r.getGraphics();
        Icon icon = r.getIcon();

        if (text != null)
		{
            FontMetrics fm = g.getFontMetrics(r.getFont());
            prefSize.height += (fm.getMaxAscent() + fm.getMaxDescent());
        }

        if (icon != null)
		{
            if (r.isCentered())
			{
                prefSize.height += (icon.getIconHeight() + r.getIconTextGap());
                prefSize.width += icon.getIconWidth();
            } 
			else
			{
                prefSize.height = Math.max(icon.getIconHeight() + ins.top + ins.bottom, prefSize.height);
                prefSize.width += (icon.getIconWidth() + r.getIconTextGap());
            }
        }

        ((Graphics2D) g).addRenderingHints(getHints());
        int textwidth = textWidth(text, g, r.getFont(), r.isHtml()) + 4;

		if (r.isCentered())
            prefSize.width = Math.max(prefSize.width, textwidth + ins.right + ins.left);
        else
            prefSize.width += (textwidth + r.getIndent());

        if (FIXED_HEIGHT > 0)
            prefSize.height = FIXED_HEIGHT;

        return prefSize;
    }

    @SuppressWarnings("unchecked")
    static final Map<?,?> getHints()
	{
        if (hintsMap == null)
		{
            hintsMap = (Map)(Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints"));
            if (hintsMap == null)
			{
                hintsMap = new HashMap<Object,Object>();
                if (antialias)
                    hintsMap.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            }
        }

        Map<?,?> ret = hintsMap;
        assert ret != null;
        return ret;
    }

    public @Override void update(Graphics g, JComponent c)
	{
        Color bg = getBackgroundFor((HtmlRendererImpl) c);
        HtmlRendererImpl h = (HtmlRendererImpl) c;

        if (bg != null)
		{
            int x = h.isSelected() ? ((h.getIcon() == null) 
				? 0
				: (h.getIcon().getIconWidth() + h.getIconTextGap())) : 0;
            x += h.getIndent();
            g.setColor(bg);
            g.fillRect(x, 0, c.getWidth() - x, c.getHeight());
        }

        if (h.isLeadSelection())
		{
            Color focus = UIManager.getColor("Tree.selectionBorderColor");

			if ((focus == null)
				|| focus.equals(bg))
                focus = Color.BLUE;

            if (!isGTK()
				&& !isAqua()
				&& !isNimbus())
			{
                int x = ((h.getIcon() == null) 
					? 0
					: (h.getIcon().getIconWidth() + h.getIconTextGap()));
                g.setColor(focus);
                g.drawRect(x, 0, c.getWidth() - (x + 1), c.getHeight() - 1);
            }
        }

        paint(g, c);
    }

    public @Override void paint(Graphics g, JComponent c)
	{
        ((Graphics2D) g).addRenderingHints(getHints());
        HtmlRendererImpl r = (HtmlRendererImpl) c;

        if (r.isCentered()) 
            paintIconAndTextCentered(g, r);
        else
            paintIconAndText(g, r);
    }

    private void paintIconAndText(Graphics g, HtmlRendererImpl r)
	{
        Font f = r.getFont();
        g.setFont(f);

        FontMetrics fm = g.getFontMetrics();

        int txtH = fm.getMaxAscent() + fm.getMaxDescent();
        Insets ins = r.getInsets();

        int rHeight = r.getHeight();
        int availH = rHeight - (ins.top + ins.bottom);

        int txtY;

        if (availH >= txtH) 
            txtY = (txtH + ins.top + ((availH / 2) - (txtH / 2))) - fm.getMaxDescent();
		else if (r.getHeight() > txtH)
            txtY = txtH + (rHeight - txtH) / 2 - fm.getMaxDescent();
		else
            txtY = fm.getMaxAscent();

        int txtX = r.getIndent();

        Icon icon = r.getIcon();

        if ((icon != null)
			&& (icon.getIconWidth() > 0)
			&& (icon.getIconHeight() > 0))
		{
            int iconY;

            if (availH > icon.getIconHeight()) 
                iconY = ins.top + ((availH / 2) - (icon.getIconHeight() / 2)); // + 2;
            else if (availH == icon.getIconHeight())
                iconY = 0;
            else
                iconY = ins.top;

            int iconX = ins.left + r.getIndent() + 1;

            try
			{
                icon.paintIcon(r, g, iconX, iconY);
            } 
			catch (NullPointerException npe)
			{
                Exceptions.attachMessage(npe, "Probably an ImageIcon with a null source image: "
					+ icon + " - " + r.getText());
                Exceptions.printStackTrace(npe);
            }

            txtX = iconX + icon.getIconWidth() + r.getIconTextGap();
        } 
		else
		{
            txtX += ins.left;
        }

        String text = r.getText();
        if (text == null)
            return;

        int txtW = (icon != null)
            ? (r.getWidth() - (ins.left + ins.right + icon.getIconWidth() + r.getIconTextGap() + r.getIndent()))
            : (r.getWidth() - (ins.left + ins.right + r.getIndent()));

        Color background = getBackgroundFor(r);
        Color foreground = ensureContrastingColor(getForegroundFor(r), background);

        if (r.isHtml())
            HtmlRenderer._renderHTML(text, 0, g, txtX, txtY, txtW, txtH, f, foreground, r.getRenderStyle(), true, background);
        else
            HtmlRenderer.renderPlainString(text, g, txtX, txtY, txtW, txtH, f, foreground, r.getRenderStyle(), true);
    }

    private void paintIconAndTextCentered(Graphics g, HtmlRendererImpl r)
	{
        Insets ins = r.getInsets();
        Icon ic = r.getIcon();
        int w = r.getWidth() - (ins.left + ins.right);
        int txtX = ins.left;
        int txtY = 0;

        if ((ic != null)
			&& (ic.getIconWidth() > 0)
			&& (ic.getIconHeight() > 0))
		{
            int iconx = (w > ic.getIconWidth()) ? ((w / 2) - (ic.getIconWidth() / 2)) : txtX;
            int icony = 0;
            ic.paintIcon(r, g, iconx, icony);
            txtY += (ic.getIconHeight() + r.getIconTextGap());
        }

        int txtW = r.getPreferredSize().width;
        txtX = (txtW < r.getWidth()) ? ((r.getWidth() / 2) - (txtW / 2)) : 0;

        int txtH = r.getHeight() - txtY;

        Font f = r.getFont();
        g.setFont(f);

        FontMetrics fm = g.getFontMetrics(f);
        txtY += fm.getMaxAscent();

        Color background = getBackgroundFor(r);
        Color foreground = ensureContrastingColor(getForegroundFor(r), background);

        if (r.isHtml())
            HtmlRenderer._renderHTML
				(r.getText(), 0, g, txtX, txtY, txtW, txtH, f,
				foreground, r.getRenderStyle(), true, background);
        else
            HtmlRenderer.renderString
				(r.getText(), g, txtX, txtY, txtW, txtH, r.getFont(),
				foreground, r.getRenderStyle(), true);
    }

    static Color ensureContrastingColor(Color fg, Color bg)
	{
        if (bg == null)
		{
            if (isNimbus())
			{
                bg = Color.WHITE;
            } 
			else
			{
                bg = UIManager.getColor("text");
                if (bg == null)
                    bg = Color.WHITE;
            }
        }

        if (fg == null)
		{
            if (isNimbus())
			{
                fg = Color.BLACK;
            } 
			else
			{
                fg = UIManager.getColor("textText");
                if (fg == null)
                    fg = Color.BLACK;
            }
        }

        if (Color.BLACK.equals(fg)
			&& Color.WHITE.equals(fg)) 
            return fg;

        boolean replace = fg.equals(bg);
        int dif = 0;

        if (!replace)
		{
            dif = difference(fg, bg);
            replace = dif < 80;
        }

        if (replace)
		{
            int lum = luminance(bg);
            boolean darker = lum >= 128;

            if (darker)
                fg = Color.BLACK;
            else
                fg = Color.WHITE;
        }

        return fg;
    }

    private static int difference(Color a, Color b)
	{
        return Math.abs(luminance(a) - luminance(b));
    }

    private static int luminance(Color c)
	{
        return (299*c.getRed() + 587*c.getGreen() + 114*c.getBlue()) / 1000;
    }

    static Color getBackgroundFor(HtmlRendererImpl r)
	{
        if (r.isOpaque())
            return r.getBackground();

        if (r.isSelected() 
			&& !r.isParentFocused()
			&& !isGTK()
			&& !isNimbus())
            return getUnfocusedSelectionBackground();

        Color result = null;

        if (r.isSelected())
		{
            switch (r.getType())
			{
				case LIST:
					result = UIManager.getColor("List.selectionBackground");
					if (result == null)
						result = UIManager.getColor("Tree.selectionBackground");
					break;
            }

            return (result == null) 
				? r.getBackground()
				: result;
        }

        return null;
    }

    static Color getForegroundFor(HtmlRendererImpl r)
	{
        if (r.isSelected()
			&& !r.isParentFocused()
			&& !isGTK()
			&& !isNimbus())
            return getUnfocusedSelectionForeground();

        if (!r.isEnabled())
            return UIManager.getColor("textInactiveText");

        Color result = null;

        if (r.isSelected())
		{
            switch (r.getType())
			{
				case LIST:
					result = UIManager.getColor("List.selectionForeground");
					break;
            }
        }

        return (result == null) 
			? r.getForeground()
			: result;
    }

    public static boolean isAqua()
	{
        return "Aqua".equals(UIManager.getLookAndFeel().getID());
    }

    public static boolean isGTK()
	{
        return "GTK".equals(UIManager.getLookAndFeel().getID());
    }

    public static boolean isNimbus()
	{
        return "Nimbus".equals(UIManager.getLookAndFeel().getID());
    }

    private static Color getUnfocusedSelectionBackground()
	{
        if (unfocusedSelBg == null)
		{
            unfocusedSelBg = UIManager.getColor("nb.explorer.unfocusedSelBg");
            if (unfocusedSelBg == null)
			{
                unfocusedSelBg = UIManager.getColor("controlShadow");
                if (unfocusedSelBg == null) 
                    unfocusedSelBg = Color.lightGray;

                if (!Color.WHITE.equals(unfocusedSelBg.brighter()))
                    unfocusedSelBg = unfocusedSelBg.brighter();
            }
        }

        return unfocusedSelBg;
    }

    private static Color getUnfocusedSelectionForeground()
	{
        if (unfocusedSelFg == null)
		{
            unfocusedSelFg = UIManager.getColor("nb.explorer.unfocusedSelFg");
            if (unfocusedSelFg == null)
			{
                unfocusedSelFg = UIManager.getColor("textText");
                if (unfocusedSelFg == null) 
                    unfocusedSelFg = Color.BLACK;
            }
        }

        return unfocusedSelFg;
    }

    public static final boolean gtkShouldAntialias()
	{
        if (gtkAA == null)
		{
            Object o = Toolkit.getDefaultToolkit().getDesktopProperty("gnome.Xft/Antialias");
            gtkAA = new Integer(1).equals(o) 
				? Boolean.TRUE
				: Boolean.FALSE;
        }

        return gtkAA.booleanValue();
    }
}
