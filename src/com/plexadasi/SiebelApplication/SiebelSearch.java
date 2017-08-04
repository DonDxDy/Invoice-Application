package com.plexadasi.SiebelApplication;


import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelBusObject;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Adeyemi
 */
public class SiebelSearch {  

    /**
     *
     */
    protected static SiebelDataBean sdb;
    private StringWriter errors;
    protected static SiebelPropertySet properties, values;
    protected Integer beginCount = 1;
    public SiebelBusComp sbBC;
    
    public SiebelSearch(SiebelDataBean conn)
    {
        sdb = conn;
    }
    
    public static SiebelDataBean getService() throws IOException
    {
        return sdb;
    }
    
    public void serviceLogOff() throws SiebelException
    {
        sdb.logoff();
    }
    
    public void setSField(SiebelPropertySet property)
    {
        properties = property;
    }
    
    public SiebelPropertySet getSField(String bO, String bC, Impl qM) throws SiebelException
    {

        SiebelBusObject sbBO = sdb.getBusObject(bO); 
        sbBC = sbBO.getBusComp(bC);
        SiebelPropertySet List;
        values = sdb.newPropertySet();
        sbBC.setViewMode(3);
        sbBC.clearToQuery();
        // Activate all the fields
        sbBC.activateMultipleFields(properties);
        //Get search specification
        qM.searchSpec(sbBC);
        sbBC.executeQuery2(true, true);
        List = doTrigger(sbBC);
        sbBC.release();
        sbBO.release();
        return List;
    }
    
    public void writeRecord(String bO, String bC, Impl qM, Map<String, String> setField) throws SiebelException{
        SiebelBusObject sbBO = sdb.getBusObject(bO); 
        sbBC = sbBO.getBusComp(bC);
        sbBC.setViewMode(3);
        sbBC.clearToQuery();
        // Activate all the fields
        sbBC.activateMultipleFields(properties);
        qM.searchSpec(sbBC);
        sbBC.executeQuery2(true, true);
        properties = doTrigger(sbBC);
        if(properties.getPropertyCount() == 0){
            sbBC.newRecord(true);
        }
        for(Map.Entry<String, String> entry : setField.entrySet()){
            sbBC.setFieldValue(entry.getKey(), entry.getValue());
        }
        sbBC.writeRecord();
        sbBC.release();
        sbBO.release();
    }
    
    protected SiebelPropertySet doTrigger(SiebelBusComp sbBC) throws SiebelException
    {
        boolean isRecord = sbBC.firstRecord();
        while (isRecord)
        {
            sbBC.getMultipleFieldValues(properties, values);
            isRecord = sbBC.nextRecord();
        }
        return values;
    }
    
    public String getFieldValue(String string) throws SiebelException
    {
        return sbBC.getFieldValue(string);
    }
}
