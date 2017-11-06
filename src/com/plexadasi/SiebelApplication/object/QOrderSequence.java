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
    public static final String FIELD_SEQUENCE = "Proforma";
    public static final String FIELD_TRANS = "Trans 1";
    public static final String FIELD_NAME = "Name";
    private static SiebelPropertySet set;
    private String searchSpec;
    private String id;
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
        this.searchSpec = "Id";
        this.id = quote_id;
        set = new SiebelPropertySet();
        set.setProperty(FIELD_SEQUENCE, FIELD_SEQUENCE);
        set.setProperty(FIELD_TRANS, FIELD_TRANS);
        set.setProperty(FIELD_NAME, FIELD_NAME);
        this.setSField(set);
        return this.getSField(BO, BC, this);
    }
    
    public void writeTo(String BO, String quote_id, Map<String, String> setField) throws SiebelException{
        this.searchSpec = "Id";
        this.id = quote_id;
        set = new SiebelPropertySet();
        set.setProperty("Id", quote_id);
        set.setProperty(FIELD_SEQUENCE, FIELD_SEQUENCE);
        set.setProperty(FIELD_TRANS, FIELD_TRANS);
        set.setProperty(FIELD_NAME, FIELD_NAME);
        this.setSField(set);
        this.writeRecord(BO, BC, this, setField);
    }
    
    /**
     *
     * @param id
     * @return
     * @throws SiebelException
     */
    @Override
    public List<Map<String, String>> getItems(String id) throws SiebelException
    {
        this.searchSpec = "Id";
        this.id = id;
        set = new SiebelPropertySet();
        set.setProperty(this.searchSpec, this.id);
        set.setProperty(FIELD_SEQUENCE, FIELD_SEQUENCE);
        set.setProperty(FIELD_TRANS, FIELD_TRANS);
        set.setProperty(FIELD_NAME, FIELD_NAME);
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
        sbBC.setSearchSpec(this.searchSpec, this.id); 
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