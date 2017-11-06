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
public class StringInt {
    public static boolean isStringInt(String s)
    {
        try
        {
            Integer.parseInt(s);
            return true;
        } 
        catch (NumberFormatException ex)
        {
            return false;
        } 
        catch(NullPointerException ex)
        {
            return false;
        }
    }
    
    public static boolean isStringFloat(String s)
    {
        try
        {
            Float.parseFloat(s);
            return true;
        } 
        catch(NumberFormatException ex)
        {
            return false;
        } 
        catch(NullPointerException ex)
        {
            return false;
        }
    }
}
