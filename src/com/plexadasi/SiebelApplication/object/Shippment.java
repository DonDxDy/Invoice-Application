/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.SiebelApplication.object;

import com.plexadasi.SiebelApplication.Models.Shipment;
import com.plexadasi.SiebelApplication.enu.Sales;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.logging.Level;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import com.plexadasi.SiebelApplication.SiebelServiceClone;
import com.siebel.eai.SiebelBusinessServiceException;
import java.util.Enumeration;
import java.util.HashMap;

/**
 *
 * @author Adeyemi
 */
public class Shippment implements Impl
{
    private SiebelPropertySet properties, values;
    private final SiebelDataBean conn;
    private String Id;
    private final String searchKey = "Shipment Number";
    private final String busObj = "Order Entry";
    private final String busComp = "FS Shipment";
    List<Map<String, String>> shipments;
    
    public Shippment(SiebelDataBean conn)
    {
        this.shipments = new ArrayList();
        this.conn = conn;
    }
    
    @Override
    public List<Map<String, String>> getItems(String Id) throws SiebelBusinessServiceException, SiebelException
    {
        this.Id = Id;
        this.properties = new SiebelPropertySet(); 
        this.values = new SiebelPropertySet();
        this.properties.setProperty(Sales.DELIVERY_DATE, Sales.DELIVERY_DATE);
        this.properties.setProperty(Sales.WAYBILL_NUMBER, Sales.WAYBILL_NUMBER);
        this.properties.setProperty(Sales.CARRIER, Sales.CARRIER);
        this.properties.setProperty(Sales.CONTACT_FULLNAME, Sales.CONTACT_FULLNAME);
        this.properties.setProperty(Sales.PHONE_NUMBER, Sales.PHONE_NUMBER);
        this.properties.setProperty(Sales.ADDRESS, Sales.ADDRESS);
        this.properties.setProperty(Sales.CITY, Sales.CITY);
        SiebelServiceClone siebelService = new SiebelServiceClone(this.conn);
        siebelService.setSField(this.properties);
        SiebelBusComp sbBC = siebelService.fields(this.busObj, this.busComp, this).getBusComp();
        Boolean isRecord = sbBC.firstRecord();
        while(isRecord){
            sbBC.getMultipleFieldValues(this.properties, this.values);
            Map<String, String> items = new HashMap();
            final Enumeration<String> e = this.properties.getPropertyNames();
            while (e.hasMoreElements()){
                String value = e.nextElement();
                items.put(this.properties.getProperty(value), this.values.getProperty(value));
            }
            this.shipments.add(items);
            isRecord = sbBC.nextRecord();
        }
        com.plexadasi.ebs.SiebelApplication.MyLogging.log(Level.INFO, getClass().getSimpleName()+":"+this.shipments.toString());
        return this.shipments;
    }
    
    @Override
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException 
    {
        sbBC.setSearchSpec(searchKey, Id);  
    }
    
    @Override
    public void getExtraParam(SiebelBusComp sbBC){}
    
    @Override
    public void searchSpec(SiebelBusComp sbBC, String type) throws SiebelException {}
}
