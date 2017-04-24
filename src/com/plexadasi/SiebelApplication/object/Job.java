/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.SiebelApplication.object;

import com.plexadasi.SiebelApplication.MyLogging;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author Adeyemi
 */
public class Job implements Impl
{
    private static SiebelPropertySet set;
    private String job_id;
    private List<Map<String, String>> quoteItem = new ArrayList<Map<String, String>>();
    private String searchSpec;
    private final String searchKey = "Parent SH Id";
    private static final String BO = "Auto Vehicle";
    private static final String BC = "PLX Auto Job Card";
    private static JComplaints complaints;
    private static JDiagnosis diagnosis;
    private static JRemedy remedy;
    /**
     * 
     * @param conn 
     */
    public Job(SiebelDataBean conn)
    {
        complaints = new JComplaints(conn);
        diagnosis = new JDiagnosis(conn);
        remedy = new JRemedy(conn);
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
        //Map<String, String> map = new HashMap();
        //map.put("1", "CUSTOMER'S COMPLAINT");
        //quoteItem.add(map);
        quoteItem.addAll(complaints.getItems(id));
        quoteItem.addAll(diagnosis.getItems(id));
        quoteItem.addAll(remedy.getItems(id));
        MyLogging.log(Level.INFO, "Creating siebel objects Job: " + quoteItem);
        return quoteItem;
    }

    /**
     * 
     * @param sbBC
     * @throws SiebelException 
     */
    @Override
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException {}
    
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
