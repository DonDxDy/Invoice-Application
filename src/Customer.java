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
    public String name;
    public String address;
    public String address2;
    public String mainphonenumber;
    public String Account;
    public String AccountId;

    public String getAccount() {
        return Account;
    }

    public void setAccount(String Account) {
        this.Account = Account;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String AccountId) {
        this.AccountId = AccountId;
    }
    

    public Customer() {
    }

    public Customer(String name, String address, String address2, String mainphonenumber) {
        this.name = name;
        this.address = address;
        this.address2 = address2;
        this.mainphonenumber = mainphonenumber;
    }

    public Customer(String name, String address, String address2, String mainphonenumber, String Account, String AccountId) {
        this.name = name;
        this.address = address;
        this.address2 = address2;
        this.mainphonenumber = mainphonenumber;
        this.Account = Account;
        this.AccountId = AccountId;
    }
    
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getMainphonenumber() {
        return mainphonenumber;
    }

    public void setMainphonenumber(String mainphonenumber) {
        this.mainphonenumber = mainphonenumber;
    }
         
}
