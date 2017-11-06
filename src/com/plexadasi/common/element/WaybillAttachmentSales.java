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
public class WaybillAttachmentSales extends Attachment
{
    //protected String BO       = "Order Entry";
    //protected String BC       = "Order Entry Attachment";
    
    /**
     *
     * @param conn
     * @param id
     * @throws SiebelException
     */
    public WaybillAttachmentSales(SiebelDataBean conn, String id) throws SiebelException
    {
        super(conn, "Order Entry (Sales)", "Order Entry Attachment", id);
        fieldName = "OrderFileName";
    }

    @Override
    protected void activateFields(String sAttachmentName) throws SiebelBusinessServiceException
    {
        try
        {
            //create a new attachment record
            sbBC.newRecord(false);
            sbBC.setFieldValue(fieldName, sAttachmentName);
            sbBC.setFieldValue("OrderFileSrcType", "FILE");
            sbBC.setFieldValue("Order Id", Id);
            sbBC.setFieldValue("OrderFileDeferFlg", "R");
            sbBC.setFieldValue("OrderFileDockStatFlg", "E");
            sbBC.setFieldValue("OrderFileAutoUpdFlg", YES);
            sbBC.setFieldValue("OrderFileDockReqFlg", NO);
            sbBC.setFieldValue("PLXOrderAttFileType", "ESTIMATE");
        } catch (SiebelException ex) {
            ex.printStackTrace(new PrintWriter(ERROR));
            MyLogging.log(Level.SEVERE, "Caught IOException: " + ERROR.toString());
            throw new SiebelBusinessServiceException("SiebelException", ex.getMessage());
        }
    }
}
