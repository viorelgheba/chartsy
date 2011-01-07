package org.chartsy.chatsy.chat.component.renderer;


import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import java.awt.Component;

public class ListIconRenderer extends JLabel implements ListCellRenderer
{

    public ListIconRenderer()
	{
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

    public Component getListCellRendererComponent(JList list,
		Object value, int index, boolean isSelected, boolean cellHasFocus)
	{
        if (isSelected)
		{
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }
        else
		{
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        setHorizontalAlignment(SwingConstants.LEFT);
        ImageIcon icon = (ImageIcon)value;
        setText(icon.getDescription());
        setIcon(icon);
        return this;
    }
	
}   

