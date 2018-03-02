/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.invoiceapplication;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 *
 * @author Adeyemi
 */
public class ProductKey implements IKey{
    @Override
    public Integer productKeyToInt(String value) throws InvalidFormatException{
        int index = 0;
        value = value.toLowerCase();
        if(StringInt.isStringInt(value)){
            index = Integer.valueOf(value);
        }else{
            if("sn".equals(value))
                index = 0;
            else if("Part Number".equalsIgnoreCase(value))
                index = 1;
            else if("Stock Location".equalsIgnoreCase(value))
                index = 2;
            else if("Quantity".equalsIgnoreCase(value))
                index = 3;
            else if("Product".equalsIgnoreCase(value))
                index = 4;
            else if("Discount".equalsIgnoreCase(value))
                index = 5;
            else if("Gross Price".equalsIgnoreCase(value))
                index = 6;
            else if("Unit Price".equalsIgnoreCase(value))
                index = 7;
            else if("Total".equalsIgnoreCase(value))
                index = 8;
            else
                throw new InvalidFormatException("Invalid index value.");
        }
        return index;
    }
}
