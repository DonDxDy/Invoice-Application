/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SiebelApplication.bin;

import SiebelApplication.MyLogging;
import SiebelApplication.SiebelServiceExtended;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author Adeyemi
 */
public class QCustomer extends SiebelServiceExtended implements IQuote{
    
    private static SiebelPropertySet set;
    private static String Id;
    private String searchSpec;
    private String value = "";
    List<Map<String, String>> quoteItem;
    
    public QCustomer(SiebelDataBean conn)
    {
        super(conn);
    }
    
    @Override
    public List<Map<String, String>> getQuoteItems(String id) throws SiebelException
    {
        List<Map<String, String>> listFinal = new ArrayList();
        listFinal.addAll(quoteItem(id));
        listFinal.addAll(accountItem(searchSpec));
        MyLogging.log(Level.INFO,"Creating siebel objects Customer: " + listFinal);
        return listFinal;
    }
    
    private List<Map<String, String>> quoteItem(String quote_id) throws SiebelException
    {
        Id = quote_id;
        
        set = new SiebelPropertySet();
        set.setProperty("Account", "2");
        this.value = "Account Id";
        this.setSField(set);
        quoteItem = this.getSField("Quote", "Quote", this);
            
        return quoteItem;
    }
    
    private List<Map<String, String>> accountItem(String account_id) throws SiebelException
    {
        Id = account_id;
        
        set = new SiebelPropertySet();
        set.setProperty("Main Phone Number", "2");
        set.setProperty("Street Address", "2");
        this.value = "";
        this.setSField(set);
        quoteItem = this.getSField("Account", "Account", this);
        return quoteItem;
    }
    

    @Override
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException 
    {
        sbBC.setSearchSpec("Id", Id);  
    }
    
    @Override
    public void getExtraParam(SiebelBusComp sbBC)
    {
        try 
        {
            if(!"".equals(this.value))
            {
                this.searchSpec = sbBC.getFieldValue(this.value);
            }
        } 
        catch (SiebelException ex) {
            MyLogging.log(Level.SEVERE, "Caught Exception: " + ex.getMessage());
        }
    }
}
