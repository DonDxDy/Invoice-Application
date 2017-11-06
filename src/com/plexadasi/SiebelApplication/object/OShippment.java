/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.SiebelApplication.object;

import com.plexadasi.SiebelApplication.MyLogging;
import com.plexadasi.SiebelApplication.SiebelServiceExtended;
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
public class OShippment extends SiebelServiceExtended implements Impl
{
    
    private static SiebelPropertySet properties, values;
    private static String Id;
    //private String searchSpec;
    private final String searchKey = "Shipment Number";
    //private String value = "";
    List<Map<String, String>> quoteItem;
    
    public OShippment(SiebelDataBean conn)
    {
        super(conn);
    }
    
    /*
    @Override
    public List<Map<String, String>> getItems(String id) throws SiebelException, SiebelBusinessServiceException
    {
        List<Map<String, String>> listFinal;
        //findOrderItem(id);
        MyLogging.log(Level.INFO, "Search param" + id);
        listFinal = orderItem(id);
        MyLogging.log(Level.INFO,"Creating siebel objects Customer: " + listFinal);
        return listFinal;
    }
    
    public SiebelPropertySet findOrderItem(SiebelPropertySet set, String order_id) throws SiebelException
    {
        Id = order_id;
        
        this.value = "";
        searchKey = "Shipment Number";
        this.setSField(set);
        //SiebelPropertySet property = this.getSField("Order Entry", "FS Shipment", this);
        return property;
    }*/
    
    @Override
    public List<Map<String, String>> getItems(String order_id) throws SiebelBusinessServiceException, SiebelException
    {
        Id = order_id;
        properties = new SiebelPropertySet();
        properties.setProperty("Scheduled Delivery Date", "5");
        properties.setProperty("Carrier", "5");
        properties.setProperty("Waybill Number", "5");
        this.setSField(properties);
        quoteItem = this.getSField("Order Entry", "FS Shipment", this);
        MyLogging.log(Level.INFO, String.valueOf(quoteItem));
        return quoteItem;
    }
    
    @Override
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException 
    {
        sbBC.setSearchSpec(searchKey, Id);  
    }
    
    @Override
    public void getExtraParam(SiebelBusComp sbBC){}
    /*{
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
    */
    @Override
    public void searchSpec(SiebelBusComp sbBC, String type) throws SiebelException {}
}
