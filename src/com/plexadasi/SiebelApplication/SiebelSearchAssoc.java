package com.plexadasi.SiebelApplication;


import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelBusObject;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import java.io.IOException;
import java.io.StringWriter;
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
public class SiebelSearchAssoc {  

    /**
     *
     */
    protected static SiebelDataBean sdb;
    private StringWriter errors;
    protected static SiebelPropertySet properties, values, List;
    protected Integer beginCount = 1;
    public SiebelBusComp sbBC, priBC;
    
    public SiebelSearchAssoc(SiebelDataBean conn)
    {
        sdb = conn;
        properties = values = List = new SiebelPropertySet();
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
    
    public SiebelPropertySet getSField(String bO, String pBC, String bC, Impl qM) throws SiebelException
    {

        SiebelBusObject sbBO = sdb.getBusObject(bO); 
        priBC = sbBO.getBusComp(pBC);
        sbBC = sbBO.getBusComp(bC);
        
        values = sdb.newPropertySet();
        //priBC.setViewMode(3);
        priBC.clearToQuery();
        // Activate all the fields
        priBC.setSearchSpec("SH #", "1-3518152");
        //qM.searchSpec(priBC);
        priBC.executeQuery(true);
        Boolean isRecord = priBC.firstRecord();
        MyLogging.log(Level.INFO, "Is True " + isRecord);
        if(isRecord)
        {
            MyLogging.log(Level.INFO, "Let's know if it works.");
            sbBC.clearToQuery();
            sbBC.activateMultipleFields(properties);
            //Get search specification
            sbBC.executeQuery(true);
            List = doTrigger(sbBC);
        }
        sbBC.release();
        priBC.release();
        sbBO.release();
        
        return List;
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
