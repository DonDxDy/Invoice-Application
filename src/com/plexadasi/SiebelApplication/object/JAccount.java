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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import com.plexadasi.SiebelApplication.object.Impl.Impl;

/**
 *
 * @author Adeyemi
 * Job Card Object
 */
public class JAccount extends SiebelService implements Impl
{
    
    private static SiebelPropertySet set;
    private static String Id;
    private String searchSpec;
    private String searchKey;
    List<Map<String, String>> quoteItem = new ArrayList();
    private static final String BO = "Account";
    private static final String BC = "Account";
    private static SiebelDataBean CONN;
    
    public JAccount(SiebelDataBean conn)
    {
        super(conn);
        CONN = conn;
    }
    
    /**
     * 
     * @param id
     * @return
     * @throws SiebelException 
     */
    @Override
    public List<Map<String, String>> getItems(String id) throws SiebelException
    {
        List<Map<String, String>> listFinal;
        JCard v = new JCard(CONN);
        listFinal = accountItem(id);
        MyLogging.log(Level.INFO,"Creating siebel objects Customer: " + listFinal);
        return listFinal;
    }
    
    private List<Map<String, String>> accountItem(String account_id) throws SiebelException
    {
        Id = account_id;
        set = new SiebelPropertySet();
        set.setProperty("Name", "1");
        set.setProperty("Street Address", "1");
        set.setProperty("Email Address", "1");
        set.setProperty("Main Phone Number", "1");
        searchKey = "Id";
        this.setSField(set);
        quoteItem = this.getSField(BO, BC, this);
        return quoteItem;
    }
    

    @Override
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException 
    {
        sbBC.setSearchSpec(searchKey, Id);  
    }

    @Override
    public void getExtraParam(SiebelBusComp sbBC) {}

    @Override
    public void searchSpec(SiebelBusComp sbBC, String type) throws SiebelException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
