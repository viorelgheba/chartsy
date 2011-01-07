package org.chartsy.chatsy.chat.component;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.geom.AffineTransform;

public class ImageTitlePanel extends JPanel
{
	
    private Image backgroundImage;
    private final JLabel titleLabel = new JLabel();
    private final JLabel iconLabel = new JLabel();
    private final GridBagLayout gridBagLayout = new GridBagLayout();
    private final WrappedLabel descriptionLabel = new WrappedLabel();

    public ImageTitlePanel(String title)
	{
        backgroundImage = null;
        init();
        titleLabel.setText(title);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 11));
    }

    public ImageTitlePanel()
	{
        backgroundImage = null;
        init();
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 11));
    }

    public void paintComponent(Graphics g)
	{
		if (backgroundImage != null)
		{
			double scaleX = getWidth() / (double)backgroundImage.getWidth(null);
			double scaleY = getHeight() / (double)backgroundImage.getHeight(null);
			AffineTransform xform = AffineTransform.getScaleInstance(scaleX, scaleY);
			((Graphics2D)g).drawImage(backgroundImage, xform, this);
		}
    }

    private void init()
	{
        setLayout(gridBagLayout);
        add(titleLabel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, 
			GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
			new Insets(5, 5, 5, 5), 0, 0));
    }

    public void setDescription(String description)
	{
        descriptionLabel.setText(description);
        add(descriptionLabel, new GridBagConstraints(0, 1, 3, 1, 1.0, 0.0, 
			GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
			new Insets(5, 5, 5, 5), 0, 0));
    }

    public void setDescriptionFont(Font font)
	{
        descriptionLabel.setFont(font);
    }

    public JTextArea getDescriptionLabel()
	{
        return descriptionLabel;
    }

    public void setTitle(String title)
	{
        titleLabel.setText(title);
    }

    public JLabel getTitleLabel()
	{
        return titleLabel;
    }

    public void setTitleFont(Font font)
	{
        titleLabel.setFont(font);
    }

    public void setComponent(JComponent component)
	{
        add(new JLabel(), new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, 
			GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
			new Insets(5, 5, 5, 5), 0, 0));
        add(component, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
			new Insets(5, 5, 5, 5), 0, 0));
    }

    public void setIcon(ImageIcon icon)
	{
        add(new JLabel(), new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
			new Insets(5, 5, 5, 5), 0, 0));
        iconLabel.setIcon(icon);
        add(iconLabel, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0,
			GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
			new Insets(5, 5, 5, 5), 0, 0));
    }
	
}