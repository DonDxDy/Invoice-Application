/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.SiebelApplication.object;

import com.plexadasi.SiebelApplication.MyLogging;
import com.plexadasi.SiebelApplication.SiebelSearch;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import java.util.logging.Level;

/**
 *
 * @author Adeyemi
 */
public class QAccount extends SiebelSearch implements Impl
{
    
    private static SiebelPropertySet set;
    private static String Id = "";
    private String searchSpec;
    private String searchKey = "";
    List<Map<String, String>> quoteItem = new ArrayList();
    private static final String BO = "Account";
    private static final String BC = "Account";
    
    public QAccount(SiebelDataBean conn)
    {
        super(conn);
    }
    
    /**
     * 
     * @param job_id
     * @return
     * @throws SiebelException 
     */
    @Override
    public List<Map<String, String>> getItems(String job_id) throws SiebelException
    {
        SiebelPropertySet setProp;
        setProp = this.activateFields(job_id);
        Map<String, String> map = new HashMap();
        quoteItem.add(map);
        return quoteItem;
    }
    
    public SiebelPropertySet activateFields(String account_id) throws SiebelException
    {
        Id = account_id;
        set = new SiebelPropertySet();
        set.setProperty("Main Phone Number", "2");
        set.setProperty("Country", "2");
        set.setProperty("State", "2");
        set.setProperty("City", "2");
        set.setProperty("Street Address", "2");
        searchKey = "Id";
        this.setSField(set);
        SiebelPropertySet prop = this.getSField(BO, BC, this);
        MyLogging.log(Level.INFO, "Creating objects for QAccount: "  + prop);
        return prop;
    }
//Asset Number
    @Override
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException 
    {
        sbBC.setSearchSpec(searchKey, Id);  
    }
    
    @Override
    public void getExtraParam(SiebelBusComp sbBC){}

    @Override
    public void searchSpec(SiebelBusComp sbBC, String type) throws SiebelException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
