/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.SiebelApplication.object;

import com.plexadasi.Service.PriceListService;
import com.plexadasi.SiebelApplication.MyLogging;
import com.plexadasi.SiebelApplication.SiebelService;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import com.plexadasi.connect.ebs.EbsConnect;
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
public class QParts2 extends SiebelService implements Impl{
    
    private static SiebelPropertySet set;
    private String quoteId;
    private List<Map<String, String>> quoteItem;
    private static final String BO = "Quote";
    private static final String BC = "Quote Item";
    private final Connection ebsConn;
    
    /**
     * 
     * @param conn 
     * @param ebsConn 
     */
    public QParts2(SiebelDataBean conn, Connection ebsConn)
    {
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
    public List<Map<String, String>> getItems(String quote_id) throws SiebelBusinessServiceException, SiebelException
    {
        this.quoteId = quote_id;
        set = new SiebelPropertySet();
        Map<String, String> map;
        PriceListService onHandQuantities = new PriceListService(ebsConn);
        set.setProperty("Outline Number", "Outline Number");
        set.setProperty("Part Number", "Part Number");
        set.setProperty("Quantity Requested", "Quantity Requested");
        set.setProperty("Product", "Product");
        set.setProperty("Discount Percent - Display", "Discount Percent - Display");
        set.setProperty("Item Price", "Item Price");
        set.setProperty("Extended Line Total - Display", "Extended Line Total - Display");
        set.setProperty("Organization Id", "Organization Id");
        set.setProperty("Product Inventory Item Id", "Product Inventory Item Id");
        set.setProperty("PLX Discount Group Percent", "PLX Discount Group Percent");
        this.setSField(set);
        quoteItem = this.getSField(BO, BC, this);
        MyLogging.log(Level.INFO, "Creating siebel objects Parts: " + quoteItem);
        List<Map<String, String>> temp = new ArrayList();
        for(Map<String, String> quote : quoteItem){
            map = new HashMap();map.put("0", quote.get("Outline Number"));
            map.put("1", quote.get("Part Number"));
            map.put("3", quote.get("Quantity Requested"));
            map.put("4", quote.get("Product"));
            String totalDiscount = quote.get("Discount Percent - Display").equals("") ? quote.get("PLX Discount Group Percent") : quote.get("Discount Percent - Display");
            map.put("5", totalDiscount);
            map.put("7", quote.get("Item Price"));
            map.put("8", quote.get("Extended Line Total - Display"));
            String inventoryId = quote.get("Product Inventory Item Id");
            String organizationId = quote.get("Organization Id");
            String onHandLag, onHandAbj;
            try{
                List<String> onHandQuantity = onHandQuantities.findOnHand(inventoryId, "123", "124");
                onHandLag = (onHandQuantity.size() == 1) ? onHandQuantity.get(0) : "0";
                onHandAbj = (onHandQuantity.size() == 2) ? onHandQuantity.get(1) : "0";
            }catch(SiebelBusinessServiceException e){
                throw new SiebelBusinessServiceException("SQL_EXCEPT", e.getMessage());
            }
            map.put("2", onHandLag + " Lag, " + onHandAbj + " Abj");
            temp.add(map);
        }
        quoteItem = temp;
        //MyLogging.log(Level.INFO, "Creating siebel objects Parts: " + quoteItem);
        return quoteItem;
    }

    /**
     * 
     * @param sbBC
     * @throws SiebelException 
     */
    @Override
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException 
    {
        sbBC.setSearchSpec("Quote Id", quoteId); 
        sbBC.setSearchSpec("Product Type", "Equipment");
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
}
