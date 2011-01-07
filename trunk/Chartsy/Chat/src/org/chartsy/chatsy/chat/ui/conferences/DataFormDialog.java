package org.chartsy.chatsy.chat.ui.conferences;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.chartsy.chatsy.chat.component.CheckBoxList;
import org.chartsy.chatsy.chat.util.GraphicUtils;
import org.chartsy.chatsy.chat.util.ModelUtil;
import org.chartsy.chatsy.chat.util.log.Log;

public class DataFormDialog extends JPanel
{
    
    private final Map<String,JComponent> valueMap = new HashMap<String,JComponent>();
    private int row = 0;
    JDialog dialog = null;

    public DataFormDialog(JFrame parent, final MultiUserChat chat, final Form submitForm)
	{
        dialog = new JDialog(parent, true);
        dialog.setTitle("Configure Chat Room");

        this.setLayout(new GridBagLayout());
        Form form = null;
        try
		{
            form = chat.getConfigurationForm();
        }
        catch (XMPPException e)
		{
            Log.error(e);
        }

        try
		{
            Iterator<FormField> fields = form.getFields();
            while (fields.hasNext())
			{
                FormField field = fields.next();
                submitForm.addField(field);
                String variable = field.getVariable();
                String label = field.getLabel();
                String type = field.getType();

                Iterator iter = field.getValues();
                List<Object> valueList = new ArrayList<Object>();
                while (iter.hasNext())
                    valueList.add(iter.next());
				
                if (type.equals(FormField.TYPE_BOOLEAN))
				{
                    String o = (String)valueList.get(0);
                    boolean isSelected = o.equals("1");
                    JCheckBox box = new JCheckBox(label);
                    box.setSelected(isSelected);
                    addField(label, box, variable);
                }
                else if (type.equals(FormField.TYPE_TEXT_SINGLE) 
					|| type.equals(FormField.TYPE_JID_SINGLE))
				{
                    String value = (String)valueList.get(0);
                    addField(label, new JTextField(value), variable);
                }
                else if (type.equals(FormField.TYPE_TEXT_MULTI) 
					|| type.equals(FormField.TYPE_JID_MULTI))
				{
                    StringBuilder buf = new StringBuilder();
                    iter = field.getValues();
                    while (iter.hasNext()) {
                        buf.append((String)iter.next());
                        if (iter.hasNext()) 
                            buf.append(",");
                    }
                    addField(label, new JTextArea(buf.toString()), variable);
                }
                else if (type.equals(FormField.TYPE_TEXT_PRIVATE))
				{
                    addField(label, new JPasswordField(), variable);
                }
                else if (type.equals(FormField.TYPE_LIST_SINGLE))
				{
                    JComboBox box = new JComboBox();
                    iter = field.getOptions();
                    while (iter.hasNext())
					{
                        FormField.Option option = (FormField.Option)iter.next();
                        String value = option.getValue();
                        box.addItem(value);
                    }
                    if (valueList.size() > 0)
					{
                        String defaultValue = (String)valueList.get(0);
                        box.setSelectedItem(defaultValue);
                    }
                    addField(label, box, variable);
                }
                else if (type.equals(FormField.TYPE_LIST_MULTI))
				{
                    CheckBoxList checkBoxList = new CheckBoxList();
                    Iterator i = field.getValues();
                    while (i.hasNext())
					{
                        String value = (String)i.next();
                        checkBoxList.addCheckBox(new JCheckBox(value), value);
                    }
                    addField(label, checkBoxList, variable);
                }
            }
        }
        catch (NullPointerException e)
		{
            Log.error(e);
        }

        JButton button = new JButton("Update");
        button.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
			{
                dialog.dispose();
                updateRoomConfiguration(submitForm, chat);
            }
        });

        final JScrollPane pane = new JScrollPane(this);
        dialog.getContentPane().setLayout(new BorderLayout());
        dialog.getContentPane().add(pane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(button);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent actionEvent)
			{
                dialog.dispose();
            }
        });
        bottomPanel.add(cancelButton);
        dialog.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setSize(600, 400);
        GraphicUtils.centerWindowOnScreen(dialog);
        dialog.setVisible(true);
    }

    private void updateRoomConfiguration(Form submitForm, MultiUserChat chat)
	{
        for (Object o1 : valueMap.keySet())
		{
            String answer = (String) o1;
            Object o = valueMap.get(answer);
            if (o instanceof JCheckBox)
			{
                boolean isSelected = ((JCheckBox) o).isSelected();
                submitForm.setAnswer(answer, isSelected);
            } 
			else if (o instanceof JTextArea)
			{
                List<String> list = new ArrayList<String>();
                String value = ((JTextArea) o).getText();
                StringTokenizer tokenizer = new StringTokenizer(value, ", ", false);
                while (tokenizer.hasMoreTokens())
                    list.add(tokenizer.nextToken());
                if (list.size() > 0)
                    submitForm.setAnswer(answer, list);
            } 
			else if (o instanceof JTextField)
			{
                String value = ((JTextField) o).getText();
                if (ModelUtil.hasLength(value))
                    submitForm.setAnswer(answer, value);
            } 
			else if (o instanceof JComboBox)
			{
                String value = (String) ((JComboBox) o).getSelectedItem();
                List<String> list = new ArrayList<String>();
                list.add(value);
                if (list.size() > 0)
                    submitForm.setAnswer(answer, list);
            } 
			else if (o instanceof CheckBoxList)
			{
                List list = ((CheckBoxList) o).getSelectedValues();
                if (list.size() > 0)
                    submitForm.setAnswer(answer, list);
            }
        }

        try
		{
            chat.sendConfigurationForm(submitForm);
        }
        catch (XMPPException e)
		{
            Log.error(e);
        }
    }

    private void addField(String label, JComponent comp, String variable)
	{
        if (!(comp instanceof JCheckBox))
		{
            JLabel formLabel = new JLabel(label);
            formLabel.setFont(new Font("Dialog", Font.BOLD, 10));
            this.add(formLabel, new GridBagConstraints(0, row, 1, 1, 1.0, 0.0, 
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
        }
        if (comp instanceof JTextArea)
		{
            JScrollPane pane = new JScrollPane(comp);
            pane.setBorder(BorderFactory.createTitledBorder("Comma delimited"));
            this.add(pane, new GridBagConstraints(1, row, 1, 1, 1.0, 0.0, 
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 75, 50));
        }
        else if (comp instanceof JCheckBox)
		{
            this.add(comp, new GridBagConstraints(0, row, 1, 1, 0.0, 0.0, 
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
        }
        else if (comp instanceof CheckBoxList)
		{
            this.add(comp, new GridBagConstraints(1, row, 1, 1, 0.0, 0.0, 
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 75, 50));
        }
        else
		{
            this.add(comp, new GridBagConstraints(1, row, 1, 1, 1.0, 0.0, 
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 75, 0));
        }
        valueMap.put(variable, comp);
        row++;
    }

    public String getValue(String label)
	{
        Component comp = valueMap.get(label);
        if (comp instanceof JCheckBox)
            return "" + ((JCheckBox)comp).isSelected();
        if (comp instanceof JTextField)
            return ((JTextField)comp).getText();
        return null;
	}
	
}
