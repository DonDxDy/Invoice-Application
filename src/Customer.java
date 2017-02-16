/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SAP Training
 */
public class Customer {
    private String Name;
    private String CustomerSiebelId;
    private String Address;
      private String Address2;
    private String MainPhoneNumber;
    private String Account;
    private String AccountId;

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String AccountId) {
        this.AccountId = AccountId;
    }
    
    public String getAccount() {
        return Account;
    }

    public void setAccount(String Account) {
        this.Account = Account;
    }
  
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getCustomerSiebelId() {
        return CustomerSiebelId;
    }

    public void setCustomerSiebelId(String CustomerSiebelId) {
        this.CustomerSiebelId = CustomerSiebelId;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String Address2) {
        this.Address2 = Address2;
    }

    public String getMainPhoneNumber() {
        return MainPhoneNumber;
    }

    public void setMainPhoneNumber(String MainPhoneNumber) {
        this.MainPhoneNumber = MainPhoneNumber;
    }
    
}
