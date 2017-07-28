/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.SiebelApplication.object;

import com.plexadasi.SiebelApplication.SiebelSearch;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 *
 * @author Adeyemi
 */
public class QOrderSequence extends SiebelSearch implements Impl{
    
    private static SiebelPropertySet set;
    private String quoteId;
    private List<Map<String, String>> quoteItem;
    private static final String BO = "Quote";
    private static final String BC = "PLX Doc Sequence Cache";
    
    /**
     * 
     * @param conn 
     */
    public QOrderSequence(SiebelDataBean conn)
    {
        super(conn);
        quoteItem = new ArrayList();
    }
    
    public SiebelPropertySet find(String BO, String quote_id) throws SiebelException{
        this.quoteId = quote_id;
        set = new SiebelPropertySet();
        set.setProperty("Proforma", "Proforma");
        this.setSField(set);
        return this.getSField(BO, BC, this);
    }
    
    public void writeTo(String BO, String quote_id, Map<String, String> setField) throws SiebelException{
        this.quoteId = quote_id;
        set = new SiebelPropertySet();
        set.setProperty("Proforma", "Proforma");
        this.setSField(set);
        this.writeRecord(BO, BC, this, setField);
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
        set.setProperty("Proforma", "Proforma");
        this.setSField(set);
        set = this.getSField(BO, BC, this);
        return quoteItem;
    }

    /**
     * 
     * @param sbBC
     * @throws SiebelException 
     */
    @Override
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException 
    {
        sbBC.setSearchSpec("Id", quoteId); 
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
