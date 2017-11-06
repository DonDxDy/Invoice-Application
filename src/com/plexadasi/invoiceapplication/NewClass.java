/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.invoiceapplication;

import com.google.common.base.CharMatcher;
import com.plexadasi.SiebelApplication.object.QOrderSequence;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author SAP Training
 */
public class NewClass {    
    private static final String PREFIX = "WS/AS";
    
    private static final String BIND = "-";
    
    private static final String BUS_OBJECT = "Quote";
    
    private final char start = 'A';
        
    QOrderSequence quoteSequence;
    
    private final String id;
    
    private Integer sequence;
    
    private char append = ' ';
    
    public NewClass(SiebelDataBean conn, String id) throws SiebelException
    {
        this.quoteSequence = new QOrderSequence(conn);
        this.id = id;
        this.retainer(this.id);
    }
    
    private void retainer(String Id) throws SiebelException
    {
        SiebelPropertySet properties = quoteSequence.find(BUS_OBJECT, Id);
        this.sequence = Integer.parseInt(CharMatcher.DIGIT.retainFrom(properties.getProperty(QOrderSequence.FIELD_SEQUENCE)));
        try{this.append = properties.getProperty(QOrderSequence.FIELD_TRANS).charAt(0);}catch(StringIndexOutOfBoundsException e){}catch(NullPointerException e){}
    }
    
    public void sequence()
    {        
        if(this.isMultipleOfFour(sequence))
        {
            this.sequence = 1;
            if(this.append == ' ')
                this.append = this.start;
            else
                this.append = (char)(this.append + 1);
        }else{
            this.sequence = sequence + 1;
        }
    }
    
    public String invoiceNumber()
    {
        String invoiceNumber = String.format("%04d", this.sequence);
        if(this.append != ' ')
            invoiceNumber = this.append + BIND + invoiceNumber;
        return PREFIX + BIND + invoiceNumber;
    }
    
    public void writeSequence() throws SiebelException
    {
        Map<String, String> properties = new HashMap();
        properties.put(QOrderSequence.FIELD_SEQUENCE, this.sequence.toString());
        properties.put(QOrderSequence.FIELD_TRANS, String.valueOf(this.append));
        quoteSequence.writeTo(BUS_OBJECT, this.id, properties);
    }
    
    public Boolean isMultipleOfFour(Integer n)
    {
        return function(n + 1, 4) == 0;
    }
    
    public static Double function(Integer n, Integer denominator)
    {
        return Math.log10(n) % denominator;
    }
}