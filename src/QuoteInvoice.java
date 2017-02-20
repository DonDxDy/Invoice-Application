/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Efosa Ehigie
 * Plexada System Integrator
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author gbege
 */
public class QuoteInvoice {
    public Customer customerDetails;
    public List labour = new ArrayList();
    public List part = new ArrayList();
    public List lubricant = new ArrayList();
    public List expense = new ArrayList();
    
    public QuoteInvoice(Customer customer) {
        customerDetails = customer;
    }
            
    public void addCustomer(Customer customer){
        customerDetails = customer;
    }
    
    public Customer getCustomer(){
        return customerDetails;
    }
    
    public void addLabour(Labour labourDetails) {
        labour.add(labourDetails);
    }
    
    public void addPart(Parts partDetails) {
        part.add(partDetails);
    }
    
    public void addLubricant(Lubricants lubricantDetails) {
        lubricant.add(lubricantDetails);
    }
    
    public void addExpense(Expenses expenseDetails) {
        expense.add(expenseDetails);
    }
    
    public List getPart(){
        return part;
    }
    
    public List getLubricant(){
        return lubricant;
    }
    
    public List getExpense(){
        return expense;
    }
    
    public List getLabourDetails(){
        return labour;
    }
    
    
}
