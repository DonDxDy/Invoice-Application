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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import java.util.logging.Level;

/**
 *
 * @author Adeyemi
 */
public class JCard extends SiebelSearch implements Impl
{
    
    private static SiebelPropertySet set;
    private static String Id;
    private String searchSpec;
    private String searchKey;
    List<Map<String, String>> quoteItem = new ArrayList();
    private static final String BO = "Auto Vehicle";
    private static final String BC = "eAuto Service History";
    private static JVehicle vehicle;
    
    public JCard(SiebelDataBean conn)
    {
        super(conn);
        vehicle = new JVehicle(conn);
    }
    
    /**
     * 
     * @param job_id
     * @return
     * @throws SiebelException 
     */
    @Override
    public List<Map<String, String>> getItems(String job_id) throws SiebelException
    {
        SiebelPropertySet getProp, getPropV;
        getProp = this.activateFields(job_id);
        String vId = getProp.getProperty(V_ID);
        MyLogging.log(Level.INFO, "Setting properties: "  + getProp);
        MyLogging.log(Level.INFO, vId);
        getPropV = vehicle.activateFields(vId);
        
        Map<String, String> map = new HashMap();
        map.put("11", getProp.getProperty("SH #"));
        quoteItem.add(map);
        map = new HashMap();
        map.put("5", getPropV.getProperty(V_MAKE));
        quoteItem.add(map);
        map = new HashMap();
        quoteItem.add(map);
        map = new HashMap();
        map.put("1", getPropV.getProperty(V_LICENSE_NO));
        map.put("4", getPropV.getProperty(V_REG_DATE));
        map.put("5", getPropV.getProperty(V_MODEL));
        map.put("6", getPropV.getProperty(V_NUMBER));
        map.put("10", getPropV.getProperty(J_KM));
        quoteItem.add(map);
        map = new HashMap();
        quoteItem.add(map);
        map = new HashMap();
        map.put("1", getProp.getProperty(J_RECIEVED_DATE));
        map.put("5", getProp.getProperty(J_BROUGHT_BY));
        map.put("6", getPropV.getProperty(V_ENGINE_NO));
        map.put("10", getProp.getProperty(J_DEADLINE));
        quoteItem.add(map);
        map = new HashMap();
        quoteItem.add(map);
        map = new HashMap();
        map.put("10", getProp.getProperty(J_ACC_INFO));
        quoteItem.add(map);
        MyLogging.log(Level.INFO, "Creating property: "  + quoteItem);
        return quoteItem;
    }
    
    private SiebelPropertySet activateFields(String id) throws SiebelException
    {
        Id = id;
        MyLogging.log(Level.INFO, "Check Id found: " + Id);
        set = new SiebelPropertySet();
        set.setProperty("SH #", BLANK);
        set.setProperty(J_KM, BLANK);
        set.setProperty(J_RECIEVED_DATE, BLANK);
        set.setProperty(J_BROUGHT_BY, BLANK);
        set.setProperty(V_ID, BLANK);
        set.setProperty(J_DEADLINE, BLANK);
        set.setProperty(J_ACC_INFO, BLANK);
        searchKey = "SH #";
        this.setSField(set);
        return this.getSField(BO, BC, this);
    }
    
    public String findJobProperty(String job_id, String property) throws SiebelException
    {
        Id = job_id;
        SiebelPropertySet setProp;
        set = new SiebelPropertySet();
        set.setProperty(property, BLANK);
        searchKey = "SH #";
        this.setSField(set);
        setProp = this.getSField(BO, BC, this);
        return setProp.getProperty(property);
    }

    @Override
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException 
    {
        sbBC.setSearchSpec(searchKey, Id);  
    }
    
    @Override
    public void getExtraParam(SiebelBusComp sbBC){}

    @Override
    public void searchSpec(SiebelBusComp sbBC, String type) throws SiebelException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
