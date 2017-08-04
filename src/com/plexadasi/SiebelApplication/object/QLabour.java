/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.SiebelApplication.object;

import com.plexadasi.SiebelApplication.MyLogging;
import com.plexadasi.SiebelApplication.SiebelService;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import com.siebel.eai.SiebelBusinessServiceException;

/**
 *
 * @author Adeyemi
 */
public class QLabour extends SiebelService implements Impl{
    
    private static SiebelPropertySet set;
    private String quoteId;
    private List<Map<String, String>> quoteItem;
    private static final String BO = "Quote";
    private static final String BC = "Quote Item";
    
    /**
     *
     * @param conn
     */
    public QLabour(SiebelDataBean conn)
    {
        super(conn);
    }
    
    /**
     * 
     * @param quote_id
     * @return
     * @throws SiebelException 
     * @throws com.siebel.eai.SiebelBusinessServiceException 
     */
    @Override
    public List<Map<String, String>> getItems(String quote_id) throws SiebelException, SiebelBusinessServiceException
    {
        this.quoteId = quote_id;
        set = new SiebelPropertySet();
        set.setProperty("Outline Number", "0");
        set.setProperty("Part Number", "1");
        set.setProperty("Product", "2");
        set.setProperty("Quantity Requested", "7");
        set.setProperty("Adjusted List Price - Display", "8");
        set.setProperty("Item Price", "9");
        this.setSField(set);
        quoteItem = this.getSField(BO, BC, this);
        MyLogging.log(Level.INFO, "Creating siebel objects Labour Quote: " + quoteItem);
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
        sbBC.setSearchSpec("Product Type Code", "Service");  
    }
    
    /**
     *
     * @param sbBC
     */
    @Override
    public void getExtraParam(SiebelBusComp sbBC){};

    @Override
    public void searchSpec(SiebelBusComp sbBC, String type) throws SiebelException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
