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
import java.util.List;
import java.util.Map;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;

/**
 *
 * @author Adeyemi
 */
public class QCustomer extends SiebelSearch implements Impl{
    
    private static SiebelPropertySet set, getQuote, getAccount, getVehicle;
    private static String Id = "";
    List<Map<String, String>> quoteItem = new ArrayList();
    private String searchSpec;
    private final QVehicle vehicleObj;
    private final QAccount accountObj;
    private final Date date;
    private final SimpleDateFormat dateFormat;
    private String accountName;
    private String address;
    private String city;
    private String state;
    private String country;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String vehicleNo;
    private String engineNo;
    private String model;
    private String km;
    
    public QCustomer(SiebelDataBean conn)
    {
        super(conn);
        vehicleObj = new QVehicle(conn);
        accountObj = new QAccount(conn);
        date = new Date();
        dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    }
    
    private Boolean isNull(String val)
    {
        boolean output = false;
        if(val == null || "".equals(val))
        {
            output = true;
        }
        return output;
    }
    @Override
    public List<Map<String, String>> getItems(String quote_id) throws SiebelException, IOException 
    {
        getQuote = getAccount = getVehicle = new SiebelPropertySet();
        getQuote = activateFields(quote_id);
        Id = getQuote.getProperty("Account Id");
        if(!"".equals(Id))
        {
            getAccount = accountObj.activateFields(Id);
        }
        Id = getQuote.getProperty("PLX Vehicle VIN");
        if(!"".equals(Id))
        {
            getVehicle = vehicleObj.activateFields(Id);
        }
        
        accountName = !isNull(getQuote.getProperty("Account")) ? getQuote.getProperty("Account") : "";
        address     = !isNull(getAccount.getProperty("Street Address")) ? getAccount.getProperty("Street Address") : "";
        city        = !isNull(getAccount.getProperty("City")) ? getAccount.getProperty("City") + "," : "";
        state       = !isNull(getAccount.getProperty("State")) ? getAccount.getProperty("State") + "," : "";
        country     = !isNull(getAccount.getProperty("Country")) ? getAccount.getProperty("Country") + "." : "";
        phoneNumber = !isNull(getAccount.getProperty("Main Phone Number")) ? getAccount.getProperty("Main Phone Number") : "";
        firstName   = !isNull(getQuote.getProperty("Contact First Name")) ? getQuote.getProperty("Contact First Name") : "";
        lastName    = !isNull(getQuote.getProperty("Contact Last Name")) ? getQuote.getProperty("Contact Last Name") : "";
        vehicleNo   = !isNull(getVehicle.getProperty(V_NUMBER)) ? getVehicle.getProperty(V_NUMBER) : "";
        engineNo    = !isNull(getVehicle.getProperty(V_ENGINE_NUM)) ? getVehicle.getProperty(V_ENGINE_NUM) : "";
        model       = !isNull(getVehicle.getProperty(V_MODEL)) ? getVehicle.getProperty(V_MODEL) : "";
        km          = !isNull(getVehicle.getProperty("Odometer UOM")) ? getVehicle.getProperty("Odometer UOM") : "";
        
        Map<String, String> mapObj = new HashMap();
        mapObj.put("2", accountName);
        mapObj.put("9", dateFormat.format(date));
        quoteItem.add(mapObj);
        mapObj = new HashMap();
        mapObj.put("2", address);
        quoteItem.add(mapObj);
        mapObj = new HashMap();
        mapObj.put("2", 
            city + 
            state +
            country
        );
        quoteItem.add(mapObj);
        mapObj = new HashMap();
        mapObj.put("2", phoneNumber);
        quoteItem.add(mapObj);
        mapObj = new HashMap();
        mapObj.put("2", firstName + " " + lastName);
        quoteItem.add(mapObj);
        mapObj = new HashMap();
        mapObj.put("6", vehicleNo);
        quoteItem.add(mapObj);
        mapObj = new HashMap();
        mapObj.put("5", engineNo);
        quoteItem.add(mapObj);
        mapObj = new HashMap();
        mapObj.put("4", model);
        mapObj.put("7", km);
        quoteItem.add(mapObj);
        MyLogging.log(Level.INFO, "Customer: " + quoteItem);
        return quoteItem;
    }
    
    public SiebelPropertySet activateFields(String quote_id) throws SiebelException
    {
        SiebelPropertySet quoteSet;
        Id = quote_id;
        searchSpec = "Id";
        set = new SiebelPropertySet();
        set.setProperty("Name", "");
        set.setProperty("Account", "");
        set.setProperty("Contact First Name", "");
        set.setProperty("Contact Last Name", BLANK);
        set.setProperty("Account Id", "");
        set.setProperty("PLX Vehicle VIN", BLANK);
        this.setSField(set);
        quoteSet = this.getSField("Quote", "Quote", this);
        MyLogging.log(Level.INFO, "Customer: " + quoteSet);
        return quoteSet;
    }

    @Override
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException 
    {
        sbBC.setSearchSpec(searchSpec, Id);  
    }
    
    @Override
    public void getExtraParam(SiebelBusComp sbBC){}

    @Override
    public void searchSpec(SiebelBusComp sbBC, String type) throws SiebelException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
