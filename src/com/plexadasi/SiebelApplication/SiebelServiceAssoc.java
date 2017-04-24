package com.plexadasi.SiebelApplication;


import static com.plexadasi.SiebelApplication.SiebelSearchAssoc.properties;
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
public class SiebelServiceAssoc {  

    /**
     *
     */
    protected static SiebelDataBean sdb;
    private StringWriter errors;
    protected static SiebelPropertySet properties, values;
    public SiebelBusComp sbBC, priBC;
    protected Integer beginCount = 1;
    private List<Map<String, String>> List = new ArrayList<Map<String, String>>();
    
    public SiebelServiceAssoc(SiebelDataBean conn)
    {
        sdb = conn;
        properties = values = new SiebelPropertySet();
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
    
    public List<Map<String, String>> getSField(String bO, String pBC, String bC, Impl qM) throws SiebelException
    {
        SiebelBusObject sbBO = sdb.getBusObject(bO); 
        priBC = sbBO.getBusComp(pBC);
        SiebelBusObject sbBO2 = sdb.getBusObject(bO); 
        sbBC = sbBO2.getBusComp(bC);
        values = sdb.newPropertySet();
        priBC.setViewMode(3);
        priBC.clearToQuery();
        
        //Get search specification
        qM.searchSpec(priBC, pBC);
        priBC.executeQuery2(true, true);
        if(priBC.firstRecord())
        {
            sbBC.setViewMode(3);
            sbBC.clearToQuery();
            sbBC.activateMultipleFields(properties);
            //Get search specification
            qM.searchSpec(sbBC, bC);
            sbBC.executeQuery2(true, true);
            List = doTrigger(sbBC);
        }
        sbBC.release();
        priBC.release();
        sbBO.release();
        
        return List;
    }
    
    protected List<Map<String, String>> doTrigger(SiebelBusComp sbBC) throws SiebelException
    {
        
        List<Map<String, String>> list = new ArrayList();
        boolean isRecord = sbBC.firstRecord();
        while (isRecord)
        {
            MyLogging.log(Level.INFO, "Will it work? ." + isRecord);
            sbBC.getMultipleFieldValues(properties, values);
            list.add(Service_PreInvokeMethod(properties, values));
            isRecord = sbBC.nextRecord();
        }
        return list;
    }
    
    private Map<String, String> Service_PreInvokeMethod (SiebelPropertySet Inputs, SiebelPropertySet Outputs)
    {
       String propName = Inputs.getFirstProperty(), propVal;
       Map<String, String> mapProperty = new HashMap();
       // stay in loop if the property name is not an empty string
       while (!"".equals(propName)) 
       {
          propVal = Outputs.getProperty(propName);
          // if a property with the same name does not exist
          // add the name value pair to the output
          if (Inputs.propertyExists(propName)) 
          {
             if("Outline Number".equals(propName) || "Operation Line No".equals(propName))
             {
                 propVal = String.valueOf(beginCount++);
             }
             mapProperty.put(Inputs.getProperty(propName), propVal);
          }
          propName = Inputs.getNextProperty();

       }
       return mapProperty;
    }
}
