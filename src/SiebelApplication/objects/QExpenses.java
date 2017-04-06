/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SiebelApplication.objects;

import SiebelApplication.MyLogging;
import SiebelApplication.SiebelService;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import SiebelApplication.objects.Impl.Impl;

/**
 *
 * @author Adeyemi
 */
public class QExpenses extends SiebelService implements Impl{
    
    private static SiebelPropertySet set;
    private String quoteId;
    private List<Map<String, String>> quoteItem;
    private static final String BO = "Quote";
    private static final String BC = "Quote Item";
    
    /**
     * 
     * @param conn 
     */
    public QExpenses(SiebelDataBean conn)
    {
        super(conn);
    }
    
    /**
     * 
     * @param quote_id
     * @return
     * @throws SiebelException 
     */
    @Override
    public List<Map<String, String>> getItems(String quote_id) throws SiebelException
    {
        this.quoteId = quote_id;
        set = new SiebelPropertySet();
        set.setProperty("Product", "2");
        set.setProperty("Quantity Requested", "7");
        set.setProperty("Unit Price - Display", "8");
        set.setProperty("Item Price", "9");
        this.setSField(set);
        quoteItem = this.getSField(BO, BC, this);
        MyLogging.log(Level.INFO, "Creating siebel objects Expenses: " + quoteItem);
        return quoteItem;
    }
    
    /**
     * 
     * @param sbBC
     * @throws SiebelException 
     */
    @Override
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException 
    {
        sbBC.setSearchSpec("Quote Id", quoteId); 
        sbBC.setSearchSpec("Product Type", "Travel Expense");
    }
    
    /**
     *
     * @param sbBC
     */
    @Override
    public void getExtraParam(SiebelBusComp sbBC){};
}
