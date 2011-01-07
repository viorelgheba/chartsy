package org.chartsy.chatsy.chat.component;

import org.chartsy.chatsy.chat.plugin.ContextMenuListener;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TreeFolder implements Serializable
{

    private Set<TreeFolder> subFolders = new HashSet<TreeFolder>();
    private Set<TreeItem> paletteItems = new HashSet<TreeItem>();
    private String displayName;
    private String description;
    private String icon;
    private ContextMenuListener listener;

    public TreeFolder()
	{
    }

    public TreeFolder(String displayName, String description, String icon)
	{
        this.displayName = displayName;
        this.description = description;
        this.icon = icon;
    }

    public void addSubFolder(TreeFolder folder)
	{
        subFolders.add(folder);
    }

    public void removeSubFolder(TreeFolder folder)
	{
        subFolders.remove(folder);
    }

    public Iterator getSubFolders()
	{
        return subFolders.iterator();
    }

    public void addPaletteItem(TreeItem item)
	{
        paletteItems.add(item);
    }

    public void removePaletteItem(TreeItem item)
	{
        paletteItems.remove(item);
    }

    public Iterator getPaletteItems()
	{
        return paletteItems.iterator();
    }

    public void setDisplayName(String displayName)
	{
        this.displayName = displayName;
    }

    public String getDisplayName()
	{
        return displayName;
    }

    public void setDescription(String description)
	{
        this.description = description;
    }

    public String getDescription()
	{
        return description;
    }

    public void setIcon(String icon)
	{
        this.icon = icon;
    }

    public String getIcon()
	{
        return icon;
    }

    public void setListener(ContextMenuListener listener)
	{
        this.listener = listener;
    }

    public ContextMenuListener getListener()
	{
        return listener;
    }
	
}
