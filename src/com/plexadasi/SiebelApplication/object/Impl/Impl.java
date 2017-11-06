/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.SiebelApplication.object.Impl;

import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelException;
import com.siebel.eai.SiebelBusinessServiceException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Adeyemi
 */
public interface Impl {
    // Constant for vehicle
    public static final String V_ASSET_ID           = "Asset Id";
    public static final String V_NUMBER             = "Asset Number";
    public static final String V_ID                 = "Vehicle Id";
    public static final String V_ENGINE_NO          = "Engine"; 
    public static final String V_ENGINE_NUM         = "Engine Num";
    public static final String V_MAKE               = "Make (Denorm)";
    public static final String V_LICENSE_NO         = "Vehicle License Number";
    public static final String V_REG_DATE           = "Registered Date";
    public static final String V_MODEL              = "Model (Denorm)";
    public static final String V_FIRST_NAME         = "Primary Contact First Name";
    public static final String V_LAST_NAME          = "Primary Contact Last Name";
    public static final String V_ACCOUNT_NAME       = "Account Name";
    public static final String V_ODOMETER           = "Odometer UOM";
    // Constant for Account
    public static final String ACCOUNT              = "Account";
    public static final String STREET_ADDRESS       = "Street Address";
    public static final String CITY                 = "City";
    public static final String STATE                = "State";
    public static final String COUNTRY              = "Country";
    public static final String PHONE_NUMBER         = "Main Phone Number";
    public static final String EMAIL                = "Email Address";
    public static final String ACCOUNT_TYPE         = "Account Type";
    public static final String FIELD_ID             = "Id";
    // Constant for jobcard
    public static final String J_SH_ID              = "SH #"; 
    public static final String J_NUMBER             = "Repair Order #";
    public static final String J_KM                 = "Current Mileage";
    public static final String J_RECIEVED_DATE      = "PLX Create Date";
    public static final String J_BROUGHT_BY         = "Last Name";
    public static final String J_DEADLINE           = "Work Order DeadLine";
    public static final String J_ACC_INFO           = "Account Info";
    public static final String J_REF_NUMBER         = "PLX WS Number";
    public static final String BLANK                = "";
    // Constant for Quote
    //public static final
    public static final String Q_CONTACT_FN         = "Contact First Name";
    public static final String Q_CONTACT_LN         = "Contact Last Name";
    public static final String V_OWNER_PHONE_NUMBER = "Primary Contact Main Number";
    public static final String V_OWNER_ADDRESS      = "Owner Street Address";
    public static final String V_OWNER_EMAIL        = "Primary Contact Email Address";
    public static final String V_OWNER_NAME         = "Primary Contact Name";
    
    public List<Map<String, String>> getItems(String quote_id) throws SiebelException, SiebelBusinessServiceException, IOException;
    
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException;
    
    public void searchSpec(SiebelBusComp sbBC, String type) throws SiebelException; 

    public void getExtraParam(SiebelBusComp sbBC);
}
