/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.SiebelApplication.object;

import com.plexadasi.Helper.HelperAP;
import com.plexadasi.Service.PriceListService;
import com.plexadasi.SiebelApplication.MyLogging;
import com.plexadasi.SiebelApplication.SiebelService;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import com.siebel.eai.SiebelBusinessServiceException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

/**
 *
 * @author Adeyemi
 */
public class QParts21 extends SiebelService implements Impl {

    private static SiebelPropertySet set;
    private String quoteId;
    private List<Map<String, String>> quoteItems;
    private static final String BO = "Quote";
    private static final String BC = "Quote Item";
    private final Connection ebsConn;
    private String firstLocation;
    private String secondLocation;
    private Integer length = 0;

    /**
     *
     * @param conn
     * @param ebsConn
     */
    public QParts21(SiebelDataBean conn, Connection ebsConn) {
        super(conn);
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
        set = new SiebelPropertySet();
        Map<String, String> map;
        set.setProperty("Outline Number", "sn");
        set.setProperty("Part Number", "Part Number");
        set.setProperty("Quantity Requested", "Quantity");
        set.setProperty("Product", "Product");
        set.setProperty("Base Price - Display", "Gross Price");
        set.setProperty("Extended Line Total - Display", "Total");
        set.setProperty("Net Discount Percent - Display", "Discount");
        set.setProperty("Net Price", "Unit Price");
        set.setProperty("Product Inventory Item Id", "Product Inventory Item Id");
        this.setSField(set);
        quoteItems = this.getSField(BO, BC, this);
        //MyLogging.log(Level.INFO, "Creating siebel objects Parts: " + quoteItems);
        List<Map<String, String>> temp = new ArrayList();
        this.length = quoteItems.size();
        for (int i = 0; i < this.length; i++) {
            Map<String, String> products = quoteItems.get(i);
            Map<String, String> tempMap = new HashMap();
            for(Map.Entry<String, String> product : products.entrySet())
            {
                String key = product.getKey();
                String value = product.getValue();
                String item;
                if("Product Inventory Item Id".equals(key))
                {
                    //tempMap.put(key, value);
                //}else{
                    String inventoryId = value;
                    PriceListService onHandQuantities = new PriceListService(ebsConn);
                    onHandQuantities.setInventoryId(inventoryId);
                    onHandQuantities.setFirstLocationId(HelperAP.getLagosWarehouseId());
                    onHandQuantities.setSecondLocationId(HelperAP.getAbujaWarehouseId()); 
                    this.onHandQuantities(onHandQuantities);
                    item = this.firstLocation + " Lag, " + this.secondLocation + " Abj";
                    tempMap.put("Stock Location", item);
                }
                    System.out.print(key + " " + value);
                System.out.print(" ");
            }
            temp.add(i, tempMap);
        }
        quoteItems = temp;
        MyLogging.log(Level.INFO, "Creating siebel objects Parts: " + temp);
        return quoteItems;
    }
    
    private void onHandQuantities(PriceListService priceList) throws SiebelBusinessServiceException
    {
        List<String> onHandQuantity = priceList.findOnHand();
        this.length = onHandQuantity.size();
        switch(this.length)
        {
            case 1:
                this.firstLocation = onHandQuantity.get(this.length -1);
            break;
            case 2:
                this.secondLocation = onHandQuantity.get(this.length -1);
            break;
        }
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
