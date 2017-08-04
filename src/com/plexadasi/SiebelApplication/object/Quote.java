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
import com.siebel.eai.SiebelBusinessServiceException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 *
 * @author Adeyemi
 */
public class Quote extends SiebelSearch implements Impl{
    
    private static SiebelPropertySet set;
    private String quoteId;
    private List<Map<String, String>> quoteItem = null;
    private static final String BO = "Quote";
    private static final String BC = "Quote";
    private QOrderSequence seq = null;
    private String search = "";
    
    /**
     * 
     * @param conn 
     */
    public Quote(SiebelDataBean conn)
    {
        super(conn);
        this.quoteItem = new ArrayList();
        this.seq = new QOrderSequence(conn);
    }
    
    /**
     *
     * @param set
     * @param quote_id
     * @return
     * @throws SiebelException
     */
    public SiebelPropertySet find(String quote_id, SiebelPropertySet set) throws SiebelException
    {
        this.quoteId = quote_id;
        this.search = "Id";
        this.setSField(set);
        return this.getSField(BO, BC, this);
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

    @Override
    public List<Map<String, String>> getItems(String quote_id) throws SiebelException, SiebelBusinessServiceException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
