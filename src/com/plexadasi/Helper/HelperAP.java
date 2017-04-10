/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.Helper;

import com.plexadasi.SiebelApplication.ApplicationProperties;
import com.plexadasi.SiebelApplication.IProperties;
import java.io.IOException;

/**
 *
 * @author Adeyemi
 */
public class HelperAP
{
    private static final ApplicationProperties AP = new ApplicationProperties();

    public static String getInvoiceTemplate() throws IOException
    {   
        AP.setProperties(IProperties.NIX_INVOICE_INPUT_KEY, IProperties.WIN_INVOICE_INPUT_KEY);
        return AP.getProperty();
    }
    
    public static String getWaybillTemplate() throws IOException 
    {
        AP.setProperties(IProperties.NIX_WAYBILL_INPUT_KEY, IProperties.WIN_WAYBILL_INPUT_KEY);
        return AP.getProperty();
    }

    public static String getGeneratedPath() throws IOException
    {
        AP.setProperties(IProperties.NIX_OUTPUT_KEY, IProperties.WIN_OUTPUT_KEY);
        return AP.getProperty();
    }
}
