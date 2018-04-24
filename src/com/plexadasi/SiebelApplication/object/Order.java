/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.SiebelApplication.object;

import com.plexadasi.SiebelApplication.Models.Shipment;
import com.plexadasi.SiebelApplication.SiebelSearch;
import com.plexadasi.SiebelApplication.SiebelServiceClone;
import com.plexadasi.SiebelApplication.enu.Sales;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import com.siebel.eai.SiebelBusinessServiceException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

/**
 *
 * @author Adeyemi
 */
public class Order extends SiebelSearch implements Impl{
    
    private SiebelPropertySet set;
    private String orderId;
    private List<Map<String, String>> orderItem = null;
    private static final String BO = "Order Entry";
    private static final String BC = "Order Entry - Orders";
    
    /**
     * 
     * @param conn 
     */
    public Order(SiebelDataBean conn)
    {
        super(conn);
        this.orderItem = new ArrayList();
    }
    
    /**
     *
     * @param orderId
     * @param properties
     * @return
     * @throws SiebelException
     * @throws com.siebel.eai.SiebelBusinessServiceException
     */
    public SiebelPropertySet find(String orderId, SiebelPropertySet properties) throws SiebelException, SiebelBusinessServiceException
    {
        this.orderId = orderId;
        this.setSField(properties);
        return this.getSField(BO, BC, this);
    }
    
    public List<Map<String, String>> getOrderShipment(String orderId, String shipmentId) throws SiebelException, SiebelBusinessServiceException{
        this.orderId = orderId;
        String columnNumber = "4";
        properties = new SiebelPropertySet();
        values = new SiebelPropertySet();
        Shippment shipment = new Shippment(sdb);
        SiebelServiceClone siebelService = new SiebelServiceClone(sdb);
        properties.setProperty(Sales.REFERENCE_NUMBER, Sales.REFERENCE_NUMBER);
        siebelService.setSField(properties);
        this.sbBC = siebelService.fields(BO, BC, this).getBusComp();
        Boolean isRecord = this.sbBC.firstRecord();
        while(isRecord){
            sbBC.getMultipleFieldValues(properties, values);
            Map<String, String> items = new HashMap();
            List<Map<String, String>> product = shipment.getItems(shipmentId);
            for(Map<String, String> products : product){
                items = new HashMap();
                items.put(columnNumber, products.get(Sales.DELIVERY_DATE));
                this.orderItem.add(items);
                items = new HashMap();
                items.put(columnNumber, products.get(Sales.WAYBILL_NUMBER));
                this.orderItem.add(items);
                items = new HashMap();
                items.put(columnNumber, products.get(Sales.CARRIER));
                this.orderItem.add(items);
                items = new HashMap();
                items.put(columnNumber, values.getProperty(Sales.REFERENCE_NUMBER));
                this.orderItem.add(items);
                items = new HashMap();
                items.put(columnNumber, products.get(Sales.CONTACT_FULLNAME));
                this.orderItem.add(items);
                Shipment address = new Shipment();
                address.setAddress(products.get(Sales.ADDRESS));
                address.setCity(products.get(Sales.CITY));
                address.setPhoneNumber(products.get(Sales.PHONE_NUMBER));
                items = new HashMap();
                items.put(columnNumber, address(address));
                this.orderItem.add(items);
            }
            isRecord = sbBC.nextRecord();
        }
        com.plexadasi.ebs.SiebelApplication.MyLogging.log(Level.INFO, getClass().getSimpleName()+":"+this.orderItem.toString());
        return this.orderItem; 
    }
    
    private String address(Shipment shipment){
        String address = "";
        if(shipment.getAddress() != null)
            address += shipment.getAddress();
        if(shipment.getCity() != null)
            address += "," + shipment.getCity();
        if(shipment.getState() != null)
            address += "," + shipment.getState();
        if(shipment.getCountry() != null)
            address += "," + shipment.getCountry();
        if(shipment.getAddress() != null)
            address += ".";
        if(shipment.getPhoneNumber() != null)
            address += shipment.getPhoneNumber();
        return address;
    }

    /**
     * 
     * @param sbBC
     * @throws SiebelException 
     */
    @Override
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException 
    {
        sbBC.setSearchSpec("Id", this.orderId); 
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

    @Override
    public List<Map<String, String>> getItems(String quote_id) throws SiebelException, SiebelBusinessServiceException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
