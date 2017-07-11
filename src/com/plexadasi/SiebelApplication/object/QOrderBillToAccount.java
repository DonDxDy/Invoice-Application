/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.SiebelApplication.object;

import com.plexadasi.SiebelApplication.MyLogging;
import com.plexadasi.SiebelApplication.SiebelSearch;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author Adeyemi
 */
public class QOrderBillToAccount extends SiebelSearch implements Impl{
    
    private static SiebelPropertySet set;
    private String quoteId;
    private List<Map<String, String>> quoteItem;
    private static final String BO = "Quote";
    private static final String BC = "Quote";
    
    /**
     * 
     * @param conn 
     */
    public QOrderBillToAccount(SiebelDataBean conn)
    {
        super(conn);
        quoteItem = new ArrayList();
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
        set.setProperty("Created", "Created");
        set.setProperty("Account", "Account");
        set.setProperty("Billing Account", "Billing Account");
        set.setProperty("Sales Team", "Sales Team");
        this.setSField(set);
        set = this.getSField(BO, BC, this);
        Map<String, String> map =  new HashMap();
        map.put("8", set.getProperty("Created"));
        quoteItem.add(map);
        map = new HashMap();
        quoteItem.add(map);
        map = new HashMap();
        quoteItem.add(map);
        map = new HashMap();
        quoteItem.add(map);
        map = new HashMap();
        map.put("8", set.getProperty("Account"));
        quoteItem.add(map);
        map = new HashMap();
        map.put("8", set.getProperty("Billing Account"));
        quoteItem.add(map);
        map = new HashMap();
        map.put("8", set.getProperty("Sales Team"));
        quoteItem.add(map);
        MyLogging.log(Level.INFO, "Creating siebel objects Parts: " + quoteItem);
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
        sbBC.setSearchSpec("Id", quoteId); 
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
