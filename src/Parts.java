/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SAP Training
 */
public class Parts {
    public String sn;
    public String partnumber;
    public String description;
    public String qty;
    public String unitprice;
    public String amount;

    public Parts(String sn, String partnumber, String description, String qty, String unitprice, String amount) {
        this.sn = sn;
        this.partnumber = partnumber;
        this.description = description;
        this.qty = qty;
        this.unitprice = unitprice;
        this.amount = amount;
    }

    public Parts() {
    }
    
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getPartnumber() {
        return partnumber;
    }

    public void setPartnumber(String partnumber) {
        this.partnumber = partnumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(String unitprice) {
        this.unitprice = unitprice;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    
    
    
            
}
