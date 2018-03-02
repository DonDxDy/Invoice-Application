/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.SiebelApplication.object;

import com.plexadasi.Helper.HelperExcelAP;
import com.plexadasi.SiebelApplication.MyLogging;
import com.plexadasi.SiebelApplication.SiebelServiceClone;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import com.plexadasi.ebs.model.Order;
import com.plexadasi.ebs.services.SalesOrderService;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import com.siebel.eai.SiebelBusinessServiceException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.logging.Level;

/**
 *
 * @author Adeyemi
 */
public class QuotePartItem implements Impl {

    private static SiebelPropertySet set;
    private String quoteId;
    private List<Map<String, String>> quoteItem;
    private static final String BO = "Quote";
    private static final String BC = "Quote Item";
    private final Connection ebsConn;
    private final SiebelDataBean siebelConn;
    private Integer location1;
    private Integer location2;

    /**
     *
     * @param siebelConn
     * @param ebsConn
     */
    public QuotePartItem(SiebelDataBean siebelConn, Connection ebsConn) {
        this.siebelConn = siebelConn;
        this.ebsConn = ebsConn;
    }

    /**
     *
     * @param quote_id
     * @return
     * @throws com.siebel.eai.SiebelBusinessServiceException
     * @throws com.siebel.data.SiebelException
     */
    @Override
    public List<Map<String, String>> getItems(String quote_id) throws SiebelBusinessServiceException, SiebelException {
        this.quoteId = quote_id;
        SiebelServiceClone s = new SiebelServiceClone(this.siebelConn);
        this.quoteItem = new ArrayList();
        set = new SiebelPropertySet();
        SiebelPropertySet values = new SiebelPropertySet();
        SalesOrderService sos = new SalesOrderService(this.ebsConn);
        set.setProperty("Outline Number", "sn");
        set.setProperty("Part Number", "Part Number");
        set.setProperty("Quantity Requested", "Quantity");
        set.setProperty("Product", "Product");
        set.setProperty("Base Price - Display", "Gross Price");
        set.setProperty("Extended Line Total - Display", "Total");
        set.setProperty("Net Discount Percent - Display", "Discount");
        set.setProperty("Net Price", "Unit Price");
        s.setSField(set);
        SiebelBusComp sbBC = s.fields(BO, BC, this).getBusComp();
        Boolean isRecord = sbBC.firstRecord();
        while(isRecord){
            this.location1 = 0;
            this.location2 = 0;
            sbBC.getMultipleFieldValues(set, values);
            String partNumber = values.getProperty("Part Number");
            Map<String, String> quoteItems = new HashMap();
            
            final Enumeration<String> e = set.getPropertyNames();
            while (e.hasMoreElements()){
                String value = e.nextElement();
                quoteItems.put(set.getProperty(value), values.getProperty(value));
            }
            quoteItems.put("Stock Location", this.stockQuantity(sos, partNumber));
            this.quoteItem.add(quoteItems);
            isRecord = sbBC.nextRecord();
        }
        MyLogging.log(Level.INFO, "Creating siebel objects Parts: " + quoteItem);
        return quoteItem;
    }
    
    private String stockQuantity(SalesOrderService sos, String partNumber) throws SiebelBusinessServiceException{
        try { 
            //List<com.plexadasi.ebs.model.Order> sales ;
            String warehouse1 = HelperExcelAP.getLagosWarehouseId();
            String warehouse2 = HelperExcelAP.getAbujaWarehouseId();
            
            /*
             This method sos.findOnHandQuantity(partNumber, warehouse1, warehouse2) exists in EBSintegration package.
             It helps to check the total amount of an item that exists in a warehouse location in EBS.
            */
            for(Order salesItem : sos.findOnHandQuantity(partNumber, warehouse1, warehouse2)){
                // Check if the item exists in the warehouse and get the total amount available
                // Get lagos item onhand quantity
                if(salesItem.getWarehouseId() == Integer.parseInt(warehouse1))
                    this.location1 = salesItem.getQuantity();
                // Get abuja item onhand quantity
                else if(salesItem.getWarehouseId() == Integer.parseInt(warehouse2))
                    this.location2 = salesItem.getQuantity();
            }
        } catch (SQLException e) {
            MyLogging.log(Level.INFO, e.getMessage());
        }
        return "Lagos:" + this.location1 + ",Abuja: "+ this.location2;
    }

    /**
     *
     * @param sbBC
     * @throws SiebelException
     */
    @Override
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException {
        sbBC.setSearchSpec("Quote Id", quoteId);
        sbBC.setSearchSpec("Product Type", "Equipment");
        //sbBC.setSearchSpec("PLX Lot#", "");
    }

    /**
     *
     * @param sbBC
     */
    @Override
    public void getExtraParam(SiebelBusComp sbBC) {
    }

    ;

    @Override
    public void searchSpec(SiebelBusComp sbBC, String type) throws SiebelException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
