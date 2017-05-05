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
import com.plexadasi.invoiceapplication.DataTypeCheck;
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
        
        accountName = getProperty(getQuote, ACCOUNT);
        address     = getProperty(getQuote, STREET_ADDRESS);
        city        = getProperty(getAccount, CITY);
        state       = getProperty(getAccount, STATE);
        country     = getProperty(getAccount, COUNTRY);
        phoneNumber = getProperty(getAccount, PHONE_NUMBER);
        firstName   = getProperty(getQuote, Q_CONTACT_FN);
        lastName    = getProperty(getQuote, Q_CONTACT_LN);
        vehicleNo   = getProperty(getVehicle, V_NUMBER);
        engineNo    = getProperty(getVehicle, V_ENGINE_NUM);
        model       = getProperty(getVehicle, V_MODEL);
        km          = getProperty(getVehicle, V_ODOMETER);
        
        Map<String, String> mapObj = new HashMap();
        mapObj.put("2", accountName);
        mapObj.put("9", dateFormat.format(date));
        quoteItem.add(mapObj);
        mapObj = new HashMap();
        mapObj.put("2", address);
        quoteItem.add(mapObj);
        mapObj = new HashMap();
        
        city        = !DataTypeCheck.isNull(city) ? city + ", " : BLANK;
        state       = !DataTypeCheck.isNull(state) ? state + ", " : BLANK;
        country     = !DataTypeCheck.isNull(country) ? country + "." : BLANK;
        mapObj.put("2", city + state + country);
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
        set.setProperty(ACCOUNT, "");
        set.setProperty(Q_CONTACT_FN, "");
        set.setProperty(Q_CONTACT_LN, BLANK);
        set.setProperty("Account Id", "");
        set.setProperty("PLX Vehicle VIN", BLANK);
        this.setSField(set);
        quoteSet = this.getSField("Quote", "Quote", this);
        MyLogging.log(Level.INFO, "Customer: " + quoteSet);
        return quoteSet;
    }
    
    private static String getProperty(SiebelPropertySet propertySet, String property)
    {
       return !DataTypeCheck.isNull(propertySet.getProperty(property)) ? propertySet.getProperty(property) : BLANK; 
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
