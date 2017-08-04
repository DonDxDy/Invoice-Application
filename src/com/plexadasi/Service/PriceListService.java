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
    private Connection ebsConn = null;
    static StringWriter errors = new StringWriter();
    
    public PriceListService(Connection ebsConn){
        this.ebsConn = ebsConn;
    }
   
    public List<String> findOnHand(String inventoryId, String orgId1, String orgId2) throws SiebelBusinessServiceException{
        List<String> list = new ArrayList();
        try {
            SqlPreparedStatement jdbcConnect = new SqlPreparedStatement(ebsConn);
            jdbcConnect.select("SUM(primary_transaction_quantity) on_hand")
            .from("mtl_onhand_quantities_detail")
            .where("inventory_item_id")
            .andWhere("(organization_id = ? OR organization_id ", " ?)")
            .groupBy(new String[]{
                "organization_id", 
                "inventory_item_id"
            }).preparedStatement();
            jdbcConnect.setString(1, inventoryId);
            jdbcConnect.setString(2, orgId1);
            jdbcConnect.setString(3, orgId2);
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
