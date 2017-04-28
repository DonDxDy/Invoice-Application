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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import java.util.HashMap;

/**
 *
 * @author Adeyemi
 * Job Card Object
 */
public class JOrganizationAccount extends SiebelSearch implements Impl
{
    
    private static SiebelPropertySet set, getVehicle;
    private static String Id;
    private String searchSpec;
    private String searchKey;
    List<Map<String, String>> quoteItem = new ArrayList();
    Map<String, String> map = new HashMap();
    private static final String BO = "Account";
    private static final String BC = "Account";
    private static SiebelDataBean CONN;
    private final JVehicle vehicle;
    private final JCard card;
    
    public JOrganizationAccount(SiebelDataBean conn)
    {
        super(conn);
        CONN = conn;
        vehicle = new JVehicle(conn);
        card = new JCard(conn);
        set = getVehicle = new SiebelPropertySet();
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
        getVehicle = vehicle.activateFields(card.findJobProperty(id, V_ID));
        id = getVehicle.getProperty(V_ACCOUNT_NAME);
        listFinal = accountItem(id);
        MyLogging.log(Level.INFO,"Creating siebel objects Customer: " + listFinal);
        return listFinal;
    }
    
    private List<Map<String, String>> accountItem(String account_id) throws SiebelException
    {
        Id = account_id;
        set = new SiebelPropertySet();
        set.setProperty("First Name", "1");
        set.setProperty("Last Name", "");
        set.setProperty("Street Address", "1");
        set.setProperty("Email Address", "1");
        set.setProperty("Main Phone Number", "1");
        searchKey = "Name";
        this.setSField(set);
        SiebelPropertySet getProp = this.getSField(BO, BC, this);
        getProp.addChild(getVehicle);
        map.put("1", getProp.getChild(0).getProperty(V_FIRST_NAME) + " " + getProp.getChild(0).getProperty(V_LAST_NAME));
        quoteItem.add(map);
        map = new HashMap();
        map.put("1", getProp.getProperty("Street Address"));
        quoteItem.add(map);
        map = new HashMap();
        map.put("1", getProp.getProperty("Email Address"));
        quoteItem.add(map);
        map = new HashMap();
        map.put("1", getProp.getProperty("Main Phone Number"));
        quoteItem.add(map);
        return quoteItem;
    }
    
    @Override
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException 
    {
        sbBC.setSearchSpec(searchKey, Id);  
    }

    @Override
    public void getExtraParam(SiebelBusComp sbBC) {}

    @Override
    public void searchSpec(SiebelBusComp sbBC, String type) throws SiebelException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
