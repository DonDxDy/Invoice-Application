/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SAP Training
 */
public class Labour {
    public String sn;
    public String description;
    public String labourrate;
    public String hourlyrate;
    public String amount;
    
    public Labour(){
        
    }
    
    public Labour(String SN, String Description, String LabourRate, String HourlyRate, String Amount) {
        this.sn = SN;
        this.description = Description;
        this.labourrate = LabourRate;
        this.hourlyrate = HourlyRate;
        this.amount = Amount;
    }

    public String getSN() {
        return sn;
    }

    public void setSN(String SN) {
        this.sn = SN;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        this.description = Description;
    }

    public String getLabourRate() {
        return labourrate;
    }

    public void setLabourRate(String LabourRate) {
        this.labourrate = LabourRate;
    }

    public String getHourlyRate() {
        return hourlyrate;
    }

    public void setHourlyRate(String HourlyRate) {
        this.hourlyrate = HourlyRate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String Amount) {
        this.amount = Amount;
    }
    
    
}
