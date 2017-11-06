/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.SiebelApplication.object;

import com.plexadasi.SiebelApplication.MyLogging;
import com.plexadasi.SiebelApplication.SiebelSearch;
import com.plexadasi.SiebelApplication.SiebelServiceClone;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import com.plexadasi.invoiceapplication.NewClass;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author Adeyemi
 */
public class QOrderBillToAccount extends SiebelServiceClone implements Impl{
    
    private SiebelPropertySet set;
    private String quoteId;
    private List<Map<String, String>> quoteItem = null;
    private static final String BO = "Quote";
    private static final String BC = "Quote";
    private final String search = "Id";
    private final NewClass sequence;
    private String invoiceNumber;
    
    /**
     * 
     * @param conn 
     * @param sequence 
     */
    public QOrderBillToAccount(SiebelDataBean conn, NewClass sequence)
    {
        super(conn);
        this.quoteItem = new ArrayList();
        this.sequence = sequence;
    }
    
    /**
     *
     * @param quote_id
     * @return
     * @throws SiebelException
     */
    @Override
    public List<Map<String, String>> getItems(String quote_id) throws SiebelException
    {
        this.quoteId = quote_id;
        set = new SiebelPropertySet();
        set.setProperty("Created", "Created");
        set.setProperty("Account", "Account");
        set.setProperty("Billing Account", "Billing Account");
        set.setProperty("PLX Proforma Invoice Number", "PLX Proforma Invoice Number");
        set.setProperty("Contact Home Phone #", "Contact Home Phone #");
        set.setProperty("Creator EBS Id", "Creator EBS Id");
        set.setProperty("Account Bill To Contact First Name", "Account Bill To Contact First Name");
        set.setProperty("Account Bill To Contact Last Name", "Account Bill To Contact Last Name");
        set.setProperty("Account Bill To Street Address", "Account Bill To Street Address");
        set.setProperty("Bill To Contact Name", "Bill To Contact Name");
        set.setProperty("Bill To - Full Address", "Bill To - Full Address");
        set.setProperty("Account Id", "Account Id");
        set.setProperty("Contact First Last Name", "Contact First Last Name");
        set.setProperty("Contact Work Phone #", "Contact Work Phone #");
        set.setProperty("PLX Ref Number", "PLX Ref Number");
        set.setProperty("PLX Warehouse Id", "PLX Warehouse Id");
        set.setProperty("PLX Proforma Invoice Number", "PLX Proforma Invoice Number");
        this.setSField(set);
        SiebelBusComp sdBC = this.fields(BO, BC, this).getBusComp();
        Boolean isRecord = sdBC.firstRecord();
        Map<String, String> map = new HashMap();
        while(isRecord)
        {
            invoiceNumber = sdBC.getFieldValue("PLX Proforma Invoice Number");
            if("".equals(invoiceNumber))
            {
                sdBC.setFieldValue("PLX Proforma Invoice Number", this.sequence.invoiceNumber());
                map.put("8", this.sequence.invoiceNumber());
            }else{
                map.put("8", invoiceNumber);
            }
            quoteItem.add(map);
            map = new HashMap();
            map.put("8", sdBC.getFieldValue("Created"));
            quoteItem.add(map);
            map = new HashMap();
            quoteItem.add(map);
            map = new HashMap();
            quoteItem.add(map);
            map = new HashMap();
            quoteItem.add(map);
            map = new HashMap();
            map.put("8", sdBC.getFieldValue("Billing Account"));
            quoteItem.add(map);
            map = new HashMap();
            map.put("8", sdBC.getFieldValue("Bill To - Full Address"));
            quoteItem.add(map);
            map = new HashMap();
            map.put("8", sdBC.getFieldValue("Contact First Last Name"));
            quoteItem.add(map);//Creator EBS Id
            map = new HashMap();
            map.put("8", sdBC.getFieldValue("Contact Work Phone #"));
            quoteItem.add(map);
            map = new HashMap();
            quoteItem.add(map);
            map = new HashMap();
            map.put("8", sdBC.getFieldValue("PLX Ref Number"));
            quoteItem.add(map);
            isRecord = sdBC.nextRecord();
        }
        MyLogging.log(Level.INFO, "Creating siebel objects Parts: " + quoteItem + set);
        set.reset();
        this.release();
        return quoteItem;
    }
    
    public String getInvoiceNumber()
    {
        return this.invoiceNumber;
    }
    
    public SiebelPropertySet getExtra()
    {
        return set;
    }

    /**
     * 
     * @param sbBC
     * @throws SiebelException 
     */
    @Override
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException 
    {
        sbBC.setSearchSpec(this.search, quoteId); 
    }
    
    /**
     *
     * @param sbBC
     */
    @Override
    public void getExtraParam(SiebelBusComp sbBC){};

    @Override
    public void searchSpec(SiebelBusComp sbBC, String type) throws SiebelException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
