package com.plexadasi.invoiceapplication;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SAP Training
 */
public class DataConverter {
    private static Integer number;
    public static Integer toInt(String value) {
        if (value != (null) && !"".equals(value)) {
            return Integer.parseInt(value);
        } else {
            return 0;
        }
    }
    
    public static Integer nullToNumber(String value){
        // suppose str becomes null after some operation(s).
        try
        {
            number = Integer.parseInt(value);
        }
        catch (NumberFormatException e)
        {
            number = 0;
        }
        return number;
    }
}
