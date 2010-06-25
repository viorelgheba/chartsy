package org.chartsy.stockscanpro;

import org.chartsy.stockscanpro.ui.Content;
import java.awt.BorderLayout;
import java.util.logging.Logger;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

/**
 *
 * @author Viorel
 */
public class StockScreenerComponent extends TopComponent
{

    private static final String PREFERRED_ID = "StockScreenerComponent";
    private static final Logger LOG = Logger.getLogger(StockScreenerComponent.class.getPackage().getName());

    public StockScreenerComponent()
    {
        setName(NbBundle.getMessage(StockScreenerComponent.class, "CTL_StockScreenerComponent"));
        setToolTipText(NbBundle.getMessage(StockScreenerComponent.class, "HINT_StockScreenerComponent"));
        
        putClientProperty(TopComponent.PROP_DRAGGING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_MAXIMIZATION_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_UNDOCKING_DISABLED, Boolean.TRUE);

        setLayout(new BorderLayout());
        add(new Content(), BorderLayout.CENTER);
    }

    public @Override int getPersistenceType()
    {
        return TopComponent.PERSISTENCE_NEVER;
    }

    protected @Override String preferredID()
    {
        return PREFERRED_ID;
    }

}
