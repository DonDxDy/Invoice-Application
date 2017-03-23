/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SiebelApplication.bin;

import SiebelApplication.MyLogging;
import SiebelApplication.SiebelService;
import com.siebel.data.SiebelBusComp;
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
public class QLabour implements IQuote{
    
    private static SiebelPropertySet set;
    private String quoteId;
    
    @Override
    public List<Map<String, String>> getQuoteItems(String quote_id) throws SiebelException
    {
        this.quoteId = quote_id;
        List<Map<String, String>> quoteItem = new ArrayList();
        SiebelService ss = new SiebelService();
        set = new SiebelPropertySet();
        set.setProperty("Outline Number", "0");
        set.setProperty("Part Number", "1");
        set.setProperty("Product", "2");
        set.setProperty("Product Inventory Item Id", "7");
        set.setProperty("Quantity Requested", "8");
        set.setProperty("Item Price", "9");
        ss.setSField(set);
        quoteItem = ss.getSField(SiebelService.BO, SiebelService.BC, this);
        MyLogging.log(Level.INFO, "Creating siebel objects Labour Quote: " + quoteItem);
        return quoteItem;
    }
    
    @Override
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException 
    {
        sbBC.setSearchSpec("Quote Id", quoteId); 
        sbBC.setSearchSpec("Product Type Code", "Service");  
    }
    
    /**
     *
     * @param sbBC
     */
    @Override
    public void getExtraParam(SiebelBusComp sbBC){};
}
