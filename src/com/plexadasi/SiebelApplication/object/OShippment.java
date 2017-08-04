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
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.logging.Level;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import java.util.HashMap;

/**
 *
 * @author Adeyemi
 */
public class OShippment extends SiebelSearch implements Impl
{
    
    private static SiebelPropertySet set;
    private static String Id;
    private String searchSpec;
    private String searchKey;
    private String value = "";
    List<Map<String, String>> quoteItem;
    
    public OShippment(SiebelDataBean conn)
    {
        super(conn);
    }
    
    @Override
    public List<Map<String, String>> getItems(String id) throws SiebelException
    {
        List<Map<String, String>> listFinal;
        //findOrderItem(id);
        MyLogging.log(Level.INFO, "Search param" + id);
        listFinal = orderItem(id);
        MyLogging.log(Level.INFO,"Creating siebel objects Customer: " + listFinal);
        return listFinal;
    }
    
    private void findOrderItem(String quote_id) throws SiebelException
    {
        Id = quote_id;
        
        set = new SiebelPropertySet();
        set.setProperty("Account", "2");
        this.value = "Order Number";
        searchKey = "Id";
        this.setSField(set);
        set = this.getSField("Order Entry", "Order Entry - Line Items", this);
          System.out.println(quoteItem);  
        //return quoteItem;
    }
    
    private List<Map<String, String>> orderItem(String order_id) throws SiebelException
    {
        Id = order_id;
        set = new SiebelPropertySet();
        set.setProperty("Scheduled Delivery Date", "5");
        set.setProperty("Carrier", "5");
        set.setProperty("Waybill Number", "5");
        set.setProperty("Waybill Seq Num", "5");
        this.value = "";
        searchKey = "Shipment Number";
        this.setSField(set);
        SiebelPropertySet sets = this.getSField("Order Entry", "FS Shipment", this);
        int waybillNum = Integer.parseInt(sets.getProperty("Waybill Seq Num"));
        String waybill = "WB-WS/" + String.format("%04d", waybillNum);
        quoteItem = new ArrayList();
        Map<String, String> map = new HashMap();
        map.put("5", sets.getProperty("Scheduled Delivery Date"));
        quoteItem.add(map);
        map = new HashMap();
        map.put("5", waybill);
        quoteItem.add(map);
        map = new HashMap();
        map.put("5", sets.getProperty("Carrier"));
        quoteItem.add(map);
        MyLogging.log(Level.INFO, String.valueOf(quoteItem));
        Map<String, String> m = new HashMap();
        m.put("Waybill Number", waybill);
        if(sets.getProperty("Waybill Number").equals("")){
            this.writeRecord("Order Entry", "FS Shipment", this, m);
        }
        return quoteItem;
    }
    
    @Override
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException 
    {
        sbBC.setSearchSpec(searchKey, Id);  
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
