/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.Service;

import com.plexadasi.SiebelApplication.MyLogging;
import com.plexadasi.SiebelApplication.SqlPreparedStatement;
import com.siebel.eai.SiebelBusinessServiceException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author SAP Training
 */
public class PriceListService {
    private String inventoryId;
    
    private String firstLocationId;
    
    private String secondLocationId;
    
    private Connection ebsConn = null;
    
    private final String[] groupClause = new String[]{
        "organization_id", 
        "inventory_item_id"
    };
    
    static StringWriter errors = new StringWriter();
    
    public PriceListService(Connection ebsConn){
        this.ebsConn = ebsConn;
    }
    
    public void setInventoryId(String inventoryId)
    {
        this.inventoryId = inventoryId;
    }
    
    public void setFirstLocationId(String locationId)
    {
        this.firstLocationId = locationId;
    }
    
    public void setSecondLocationId(String locationId)
    {
        this.secondLocationId = locationId;
    }
   
    public List<String> findOnHand() throws SiebelBusinessServiceException{
        List<String> list = new ArrayList();
        try 
        {
            SqlPreparedStatement jdbcConnect = new SqlPreparedStatement(ebsConn);
            
            jdbcConnect.select("SUM(primary_transaction_quantity) on_hand")
            .from("mtl_onhand_quantities_detail")
            .where("inventory_item_id")
            .andWhere("(organization_id = ? OR organization_id ", " ?)")
            .groupBy(this.groupClause).preparedStatement();
            jdbcConnect.setString(1, this.inventoryId);
            jdbcConnect.setString(2, this.firstLocationId);
            jdbcConnect.setString(3, this.secondLocationId);
            ResultSet rs = jdbcConnect.get();
            while (rs.next()) {
                list.add(rs.getString("on_hand"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace(new PrintWriter(errors));
            MyLogging.log(Level.SEVERE, "Caught Sql Exception:" + errors.toString());
            throw new SiebelBusinessServiceException("SQL_EXCEPT", ex.getMessage());
        }
        return list;
    }
}
