package com.plexadasi.SiebelApplication;


import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelBusObject;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import com.siebel.eai.SiebelBusinessServiceException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Adeyemi
 */
public class SiebelService1 {  

    /**
     *
     */
    protected static SiebelDataBean sdb;
    private StringWriter errors;
    protected static SiebelPropertySet properties, values;
    protected Integer beginCount = 1;
    
    public SiebelService1(SiebelDataBean conn)
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
    
    public List<Map<String, String>> getSField(String bO, String bC, Impl qM) throws SiebelException, SiebelBusinessServiceException
    {

        SiebelBusObject sbBO = sdb.getBusObject(bO); 
        SiebelBusComp sbBC = sbBO.getBusComp(bC);
        List<Map<String, String>> List;
        values = sdb.newPropertySet();
        sbBC.setViewMode(3);
        sbBC.clearToQuery();
        // Activate all the fields
        sbBC.activateMultipleFields(properties);
        //Get search specification
        qM.searchSpec(sbBC);
        sbBC.executeQuery2(true, true);
        List = doTrigger(sbBC);
        qM.getExtraParam(sbBC);
        sbBC.release();
        sbBC.release();

        return List;
    }
    
    protected List doTrigger(SiebelBusComp sbBC) throws SiebelException
    {
        
        List<Map<String, String>> list = new ArrayList();
        boolean isRecord = sbBC.firstRecord();
        while (isRecord)
        {
            sbBC.getMultipleFieldValues(properties, values);
            list = Service_PreInvokeMethod(properties, values);
            isRecord = sbBC.nextRecord();
        }
        return list;
    }
    
    private List<Map<String, String>> Service_PreInvokeMethod (SiebelPropertySet Inputs, SiebelPropertySet Outputs)
    {
       String propName = Inputs.getFirstProperty(), propVal;
       List list = new ArrayList();
       // stay in loop if the property name is not an empty string
        while (!"".equals(propName)) 
        {
            Map<String, String> mapProperty = new HashMap();
            propVal = Outputs.getProperty(propName);
            // if a property with the same name does not exist
            // add the name value pair to the output
            if (Inputs.propertyExists(propName)) 
            {
                mapProperty.put(Inputs.getProperty(propName), propVal);
                list.add(mapProperty);
            }
            propName = Inputs.getNextProperty();
        }
        return list;
    }
}
