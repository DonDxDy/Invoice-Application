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
import java.util.List;
import java.util.Map;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

/**
 *
 * @author Adeyemi
 */
public class QCustomer extends SiebelSearch implements Impl{
    
    private static SiebelPropertySet set;
    private static String Id = "";
    List<Map<String, String>> quoteItem = new ArrayList();
    private String searchSpec;
    
    public QCustomer(SiebelDataBean conn)
    {
        super(conn);
    }
    
    @Override
    public List<Map<String, String>> getItems(String quote_id) throws SiebelException, IOException 
    {
        SiebelPropertySet prop, prop2, prop3;
        Id = quote_id;
        searchSpec = "Id";
        set = new SiebelPropertySet();
        set.setProperty("Name", "");
        set.setProperty("Account", "");
        set.setProperty("Account Id", "");
        this.setSField(set);
        prop = this.getSField("Quote", "Quote", this);
        System.out.println(prop.toString());
        Id = prop.getProperty("Account Id");
        searchSpec = "Id";
        set = new SiebelPropertySet();
        set.setProperty("Name", "");
        set.setProperty("Main Phone Number", "2");
        set.setProperty("Country", "2");
        set.setProperty("State", "2");
        set.setProperty("City", "2");
        set.setProperty("Street Address", "2");
        this.setSField(set);
        prop2 = this.getSField("Account", "Account", this);
        
        Id = quote_id;
        searchSpec = "Id";
        set = new SiebelPropertySet();
        set.setProperty("Serial Number", "");
        set.setProperty("Engine Num", "");
        set.setProperty("Model", "");
        set.setProperty("Odometer UOM", "");
        prop3 = this.getSField("Auto Vehicle", "Auto Vehicle", this);
        
        Map<String, String> mapObj = new HashMap();
        mapObj.put("2", prop.getProperty("Account"));
        quoteItem.add(mapObj);
        mapObj = new HashMap();
        mapObj.put("2", prop2.getProperty("Street Address"));
        quoteItem.add(mapObj);
        mapObj = new HashMap();
        mapObj.put("2", 
            prop2.getProperty("City") + ", " + 
            prop2.getProperty("State") + ", " +
            prop2.getProperty("Country") + "."
        );
        quoteItem.add(mapObj);
        mapObj = new HashMap();
        mapObj.put("2", prop2.getProperty("Main Phone Number"));
        quoteItem.add(mapObj);
        mapObj = new HashMap();
        mapObj.put("2", prop2.getProperty("Name"));
        quoteItem.add(mapObj);
        mapObj = new HashMap();
        mapObj.put("6", prop3.getProperty("Serial Number"));
        quoteItem.add(mapObj);
        mapObj = new HashMap();
        mapObj.put("5", prop3.getProperty("Engine Num"));
        quoteItem.add(mapObj);
        mapObj = new HashMap();
        mapObj.put("4", prop3.getProperty("Model"));
        mapObj.put("7", prop3.getProperty("Odometer UOM"));
        quoteItem.add(mapObj);
        MyLogging.log(Level.INFO, "Customer: " + quoteItem);
        return quoteItem;
    }
    
    

    @Override
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException 
    {
        sbBC.setSearchSpec(searchSpec, Id);  
    }
    
    @Override
    public void getExtraParam(SiebelBusComp sbBC){}
}