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
public class QuoteAttachment extends Attachment
{

    //protected String BO       = "Quote";
    //protected String BC       = "Quote Attachment";
    
    /**
     *
     * @param conn
     * @param id
     * @throws com.siebel.data.SiebelException
     */
    public QuoteAttachment(SiebelDataBean conn, String id) throws SiebelException
    {
        super(conn, "Quote", "Quote Attachment", id);
        fieldName = "QuoteFileName";
    }

    @Override
    protected void activateFields(String sAttachmentName) throws SiebelBusinessServiceException
    {
        try 
        {
            //create a new attachment record
            sbBC.newRecord(false);
            sbBC.setFieldValue(fieldName, sAttachmentName);
            sbBC.setFieldValue("QuoteFileSrcType", "FILE");
            sbBC.setFieldValue("Quote Id", Id);
            sbBC.setFieldValue("QuoteFileDeferFlg", "R");
            sbBC.setFieldValue("QuoteFileDockStatFlg", "E");
            sbBC.setFieldValue("QuoteFileAutoUpdFlg", YES);
            sbBC.setFieldValue("QuoteFileDockReqFlg", NO);
            sbBC.setFieldValue("PLXQuoteAttFileType", "ESTIMATE");
        } catch (SiebelException ex) {
            ex.printStackTrace(new PrintWriter(ERROR));
            MyLogging.log(Level.SEVERE, "Caught IOException: " + ERROR.toString());
            throw new SiebelBusinessServiceException("SiebelException", ex.getMessage());
        }
    }
}
