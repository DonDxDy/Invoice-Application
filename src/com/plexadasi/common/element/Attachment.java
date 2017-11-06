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
import com.siebel.eai.SiebelBusinessServiceException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author Adeyemi
 */
abstract public class Attachment 
{
    protected SiebelBusObject sbBO = new SiebelBusObject();
    protected SiebelBusComp sbBC = new SiebelBusComp();
    public final static String YES = "Y";
    public final static String NO = "N";
    private String sGetFileReturn = "";
    private final Map<String, String> properties = new HashMap();
    protected static String Id;
    protected String fieldName;
    protected File file;
    protected StringWriter ERROR = new StringWriter();
    
    /**
     *
     * @param conn
     * @param BO
     * @param BC
     * @param id
     * @throws com.siebel.data.SiebelException
     */
    public Attachment(SiebelDataBean conn, String BO, String BC, String id) throws SiebelException
    {
        this.sbBO = conn.getBusObject(BO);
        this.sbBC = this.sbBO.getBusComp(BC);
        Id = id;
    }
    
    abstract protected void activateFields(String sAttachmentName) throws SiebelBusinessServiceException;

    public void Attach(String sAbsoluteFileName, String sAttachmentName, Boolean cond) throws SiebelBusinessServiceException, IOException
    {
        try {
            activateFields(sAbsoluteFileName);
            MyLogging.log(Level.INFO, "sAbsoluteFileName: " + sAbsoluteFileName);
            MyLogging.log(Level.INFO, "sAttachmentName: " + sAttachmentName);
            MyLogging.log(Level.INFO, "storeAsLink(cond): " + storeAsLink(cond));
            String[] args = new String[3];
            args[0] = sAbsoluteFileName;
            args[1] = this.fieldName;
            args[2] = storeAsLink(cond);
            //call CreateFile method to attach a file on the server to the Siebel
            //file system
            this.sGetFileReturn = sbBC.invokeMethod("CreateFile", args);
            this.properties.put("aGetFileReturn", this.sGetFileReturn);
            this.sbBC.writeRecord();
            if (!"Success".equals(this.sGetFileReturn)) 
            {
                deleteAttachmentFromFs(sAbsoluteFileName);
                throw new IOException("Error attaching file!");
            }
        } 
        catch (SiebelException ex) 
        {
            deleteAttachmentFromFs(sAbsoluteFileName);
            ex.printStackTrace(new PrintWriter(ERROR));
            MyLogging.log(Level.SEVERE, "Caught IOException: " + ERROR.toString());
            throw new SiebelBusinessServiceException("SiebelException", ex.getMessage());
        }
    }
    
    /**
     *
     * @param K
     * @return
     */
    public String getProperty(String K)
    {
        return this.properties.get(K);
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
    
    private void deleteAttachmentFromFs(String sAbsoluteFileName) throws IOException
    {
        
        this.file = new File(sAbsoluteFileName);
        if(this.file.exists())
        {
            boolean delete = file.delete();
            MyLogging.log(Level.SEVERE, "Deleted file " +sAbsoluteFileName+ " from directory.");
            if(!delete)
            {
                throw new IOException("Error Attaching file. \nError deleting file " + sAbsoluteFileName + " from file system.");
            }
        }
    }
}
