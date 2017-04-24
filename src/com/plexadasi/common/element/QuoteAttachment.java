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
public class QuoteAttachment extends Attachment
{
    protected String BO       = "Quote";
    protected String BC       = "Quote Attachment";
    protected String fileName = "QuoteFileName";
    
    /**
     *
     * @param conn
     * @param id
     * @throws SiebelException
     */
    public QuoteAttachment(SiebelDataBean conn, String id) throws SiebelException
    {
        super(conn, id);
    }

    @Override
    protected void activateFields(String sAttachmentName) throws SiebelException
    {
        //create a new attachment record
        sbBC.newRecord(false); 
        sbBC.setFieldValue("QuoteFileName", sAttachmentName);
        sbBC.setFieldValue("QuoteFileSrcType", "FILE");
        sbBC.setFieldValue("Quote Id", Id);
        sbBC.setFieldValue("QuoteFileDeferFlg", "R");
        sbBC.setFieldValue("QuoteFileDockStatFlg", "E");
        sbBC.setFieldValue("QuoteFileAutoUpdFlg", YES);
        sbBC.setFieldValue("QuoteFileDockReqFlg", NO);
        sbBC.setFieldValue("PLXQuoteAttFileType", "ESTIMATE");
    }
}
