/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.SiebelApplication.object;

import com.plexadasi.SiebelApplication.MyLogging;
import com.plexadasi.SiebelApplication.SiebelService;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import com.siebel.eai.SiebelBusinessServiceException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author Adeyemi
 */
public class OParts extends SiebelService implements Impl
{
    private static SiebelPropertySet set;
    private String orderId;
    private List<Map<String, String>> orderItem;
    private static final String BO = "Order Entry";
    private static final String BC = "Order Entry - Line Items";
    
    /**
     * 
     * @param conn 
     */
    public OParts(SiebelDataBean conn)
    {
        super(conn);
    }
    
    /**
     *
     * @param order_id
     * @return
     * @throws SiebelException
     * @throws com.siebel.eai.SiebelBusinessServiceException
     */
    @Override
    public List<Map<String, String>> getItems(String order_id) throws SiebelException, SiebelBusinessServiceException
    {
        this.orderId = order_id;
        set = new SiebelPropertySet();
        set.setProperty("Outline Number", "0");
        set.setProperty("Part Number", "1");
        set.setProperty("Product Description", "2");
        //set.setProperty("Product", "4");
        set.setProperty("Quantity Requested", "4");
        set.setProperty("Account", "5");
        set.setProperty("Order Number", "6");
        set.setProperty("PLX Delivery Note", "7");
        this.setSField(set);
        orderItem = this.getSField(BO, BC, this);
        MyLogging.log(Level.INFO, "Creating siebel objects Parts: " + orderItem);
        return orderItem;
    }

    /**
     * 
     * @param sbBC
     * @throws SiebelException 
     */
    @Override
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException 
    {
        sbBC.setSearchSpec("Order Number", orderId); 
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
