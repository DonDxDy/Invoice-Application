/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.invoiceapplication;

/**
 *
 * @author Adeyemi
 */
public class QuoteKey implements IKey{
    @Override
    public Integer productKeyToInt(String value){
        int index = 0;
        value = value.toLowerCase();
        if(StringInt.isStringInt(value)){
            index = Integer.valueOf(value);
        }else{
            index = 8;
        }
        return index;
    }
}
