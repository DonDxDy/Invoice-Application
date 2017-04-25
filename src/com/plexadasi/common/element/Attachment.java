/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.common.element;

import com.plexadasi.SiebelApplication.MyLogging;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelBusObject;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author Adeyemi
 */
abstract public class Attachment {
    protected final SiebelBusObject sbBO;
    protected final SiebelBusComp sbBC;
    public final static String YES = "Y";
    public final static String NO = "N";
    private String sGetFileReturn = "";
    private final Map<String, String> properties = new HashMap();
    protected static String Id;
    protected String fieldName;
    
    /**
     *
     * @param conn
     * @param BO
     * @param BC
     * @param id
     * @throws SiebelException
     */
    public Attachment(SiebelDataBean conn, String BO, String BC, String id) throws SiebelException
    {
        sbBO = conn.getBusObject(BO);
        sbBC = sbBO.getBusComp(BC);
        Id = id;
    }
    
    abstract protected void activateFields(String sAttachmentName) throws SiebelException;

    public void Attach(String sAbsoluteFileName, String sAttachmentName, Boolean cond) throws SiebelException, IOException
    {
        activateFields(sAbsoluteFileName);
        MyLogging.log(Level.INFO, "sAbsoluteFileName: "+sAbsoluteFileName);
        MyLogging.log(Level.INFO, "sAttachmentName: "+sAttachmentName);
        MyLogging.log(Level.INFO, "storeAsLink(cond): "+storeAsLink(cond));
        String[] args = new String[3];
        args[0] = sAbsoluteFileName;
        args[1] = fieldName;
        args[2] = storeAsLink(cond);
        //call CreateFile method to attach a file on the server to the Siebel
        //file system
        sGetFileReturn = sbBC.invokeMethod("CreateFile", args);
        properties.put("aGetFileReturn", sGetFileReturn);
        sbBC.writeRecord();
        if (!"Success".equals(sGetFileReturn))
        {
            throw new IOException("Error attaching file!");
        }
    }
    
    /**
     *
     * @param K
     * @return
     */
    public String getProperty(String K)
    {
        return properties.get(K);
    }
    
    /**
     * 
     * @param cond
     * @return 
     */
    private String storeAsLink(Boolean cond)
    {
        String output = NO;
        if(cond)
        {
            output = YES;
        }
        return output;
    }
}
