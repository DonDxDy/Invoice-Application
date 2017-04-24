/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.SiebelApplication.object.Impl;

import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Adeyemi
 */
public interface Impl {
    // Constant for vehicle
    public static final String V_MAKE = "Make";
    public static final String V_LICENSE_NO = "Vehicle License Number";
    public static final String V_REG_DATE = "Registered Date";
    public static final String V_MODEL = "Model";
    public static final String V_NUMBER = "Asset Number";
    public static final String V_ENGINE_NO = "Engine No";
    public static final String V_ID = "Vehicle Id";
    // Constant for jobcard
    public static final String J_NUMBER = "Repair Order #";
    public static final String J_KM = "Current Mileage";
    public static final String J_RECIEVED_DATE = "Created Date";
    public static final String J_BROUGHT_BY = "Last Name";
    public static final String J_DEADLINE = "Work Order DeadLine";
    public static final String J_ACC_INFO = "Account Info";
    public static final String BLANK = "";
    
    public List<Map<String, String>> getItems(String quote_id) throws SiebelException, IOException;
    
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException;
    
    public void searchSpec(SiebelBusComp sbBC, String type) throws SiebelException; 

    public void getExtraParam(SiebelBusComp sbBC);
}
