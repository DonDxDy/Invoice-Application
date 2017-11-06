/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.SiebelApplication.object;

import com.plexadasi.SiebelApplication.MyLogging;
import com.plexadasi.SiebelApplication.SiebelSearch;
import com.plexadasi.SiebelApplication.SiebelServiceExtended;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import com.siebel.eai.SiebelBusinessServiceException;

/**
 *
 * @author Adeyemi
 */
public class OAddress extends SiebelSearch implements Impl
{
    
    private static SiebelPropertySet set, setChild;
    private String Id;
    private String searchSpec;
    private String quoteId;
    private String searchKey;
    private String value = "";
    List<Map<String, String>> quoteItem = new ArrayList();
    private final SiebelSearch ss;
    
    public OAddress(SiebelDataBean conn, String quoteId)
    {
        super(conn);
        this.quoteId = quoteId;
        ss = new SiebelSearch(conn);
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
        //findOrderItem(id);
        listFinal = orderItem(id);
        MyLogging.log(Level.INFO,"Creating siebel objects Customer: " + listFinal);
        return listFinal;
    }
    
    public SiebelPropertySet findOrderItem(String quote_id, SiebelPropertySet set) throws SiebelException
    {
        Id = quote_id;
        
        this.value = "Order Number";
        this.searchKey = "Order Number";
        this.setSField(set);
        return this.getSField("Order Entry", "Order Entry - Line Items", this);
    }
    
    private List<Map<String, String>> orderItem(String account_id) throws SiebelException
    {
        Map<String, String> map = new HashMap();
        String text;
        Id = account_id;
        set = new SiebelPropertySet();
        //setChild = new SiebelPropertySet();
        SiebelPropertySet setProp;
        set.setProperty("Ship To Account", "5");
        set.setProperty("Ship To", "5");
        set.setProperty("Ship To City State", "5");
        set.setProperty("Primary Order Id", "5");
        set.setProperty("Ship To Contact FullName", "5");
        //set.setProperty("Ship To First Name", "5");
        //set.setProperty("Ship To Last Name", "5");
        set.setProperty("Ship To Contact Cellular Phone", "5");
        this.searchKey = "Shipment Number";
        ss.setSField(set);
        setProp = ss.getSField("Order Entry", "FS Shipment", this);
        //MyLogging.log(Level.INFO, String.valueOf(setProp));
        //setProp.addChild(this.findOrderItem(this.quoteId, setChild));
        //MyLogging.log(Level.INFO, String.valueOf(setProp));
        map.put("5", setProp.getProperty("Ship To Contact FullName"));
        quoteItem.add(map);
        map = new HashMap();
        map.put("5", setProp.getProperty("Ship To Contact Cellular Phone"));
        quoteItem.add(map);
        map = new HashMap();
        String city = setProp.getProperty("Ship To City State");
        city = (!"".equals(city) ? city + "." : "");
        text =  setProp.getProperty("Ship To") + " " + city;
        map.put("5", text);
        quoteItem.add(map);
        return quoteItem;
    }
    

    @Override
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException 
    {
        sbBC.setSearchSpec(this.searchKey, this.Id);  
        MyLogging.log(Level.INFO, "Search spec: " + this.Id);
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

    @Override
    public void searchSpec(SiebelBusComp sbBC, String type) throws SiebelException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
