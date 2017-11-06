/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.common.element;

import com.plexadasi.SiebelApplication.MyLogging;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.eai.SiebelBusinessServiceException;
import java.io.PrintWriter;
import java.util.logging.Level;

/**
 *
 * @author Adeyemi
 */
public class JobCardAttachment extends Attachment
{
    /**
     * 
     */
    //protected String BO       = "Auto Vehicle";
    //protected String BC       = "Asset Mgmt - Asset Attachment";
    
    /**
     *
     * @param conn
     * @param id
     * @throws SiebelException
     */
    public JobCardAttachment(SiebelDataBean conn, String id) throws SiebelException
    {
        super(conn, "Auto Vehicle", "Asset Mgmt - Asset Attachment", id);
        fieldName = "AssetFileName";
    }

    @Override
    protected void activateFields(String sAttachmentName) throws SiebelBusinessServiceException
    {
        try
        {
            //create a new attachment record
            sbBC.newRecord(false);
            sbBC.setFieldValue(fieldName, sAttachmentName);
            sbBC.setFieldValue("AssetFileSrcType", "FILE");
            sbBC.setFieldValue("Asset Id", Id);
            sbBC.setFieldValue("AssetFileDeferFlg", "R");
            sbBC.setFieldValue("AssetFileDockStatFlg", "E");
            sbBC.setFieldValue("AssetFileAutoUpdFlg", YES);
            sbBC.setFieldValue("AssetFileDockReqFlg", NO);
            sbBC.setFieldValue("PLXAssetAttFileType", "ESTIMATE");
        } catch (SiebelException ex) {
            ex.printStackTrace(new PrintWriter(ERROR));
            MyLogging.log(Level.SEVERE, "Caught IOException: " + ERROR.toString());
            throw new SiebelBusinessServiceException("SiebelException", ex.getMessage());
        }
    }
}
