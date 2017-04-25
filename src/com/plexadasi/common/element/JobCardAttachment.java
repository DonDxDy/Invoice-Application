/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.common.element;

import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;

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
    protected void activateFields(String sAttachmentName) throws SiebelException
    {
        //create a new attachment record
        sbBC.newRecord(false);
        sbBC.setFieldValue(fieldName, sAttachmentName);
        sbBC.setFieldValue("AssetFileSrcType", "FILE");
        sbBC.setFieldValue("Order Id", Id);
        sbBC.setFieldValue("AssetFileDeferFlg", "R");
        sbBC.setFieldValue("AssetFileDockStatFlg", "E");
        sbBC.setFieldValue("AssetFileAutoUpdFlg", YES);
        sbBC.setFieldValue("AssetFileDockReqFlg", NO);
        sbBC.setFieldValue("PLXAssetAttFileType", "ESTIMATE");
    }
}
