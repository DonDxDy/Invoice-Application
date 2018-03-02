/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.Helper;

import com.plexadasi.SiebelApplication.MyLogging;
import com.plexadasi.connect.build.ApplicationProperties;
import com.siebel.eai.SiebelBusinessServiceException;
import java.io.StringWriter;
import java.util.logging.Level;

/**
 *
 * @author Adeyemi
 */
public class HelperExcelAP
{
    private static final ApplicationProperties AP = new ApplicationProperties();
    
    private static final String ENV = "intg_property";
    
    private static final StringWriter ERRORS = new StringWriter();

    public static String getInvoiceTemplate() throws SiebelBusinessServiceException
    {   
        return initializeProperty(HelperProperties.WORKSHOP_DOCUMENT, true);
    }
    
    public static String getWaybillTemplate() throws SiebelBusinessServiceException 
    {
        return initializeProperty(HelperProperties.WAYBILL_DOCUMENT, true);
    }

    public static String getJobCardTemplate() throws SiebelBusinessServiceException 
    {
        return initializeProperty(HelperProperties.JOBCARD_DOCUMENT, true);
    }

    public static String getGeneratedPath() throws SiebelBusinessServiceException
    {
        return initializeProperty(HelperProperties.OUTPUT_KEY, true);
    }

    public static String getInvoiceTemplate2() throws SiebelBusinessServiceException {
        return initializeProperty(HelperProperties.PROFORMA_DOCUMENT, true);
    }

    public static String getLagosWarehouseId() throws SiebelBusinessServiceException {
        return initializeProperty(HelperProperties.LAGOS_WAREHOUSE_CODE, false);
    }

    public static String getAbujaWarehouseId() throws SiebelBusinessServiceException {
        return initializeProperty(HelperProperties.ABUJA_WAREHOUSE_CODE, false);
    }
        
    private static String initializeProperty(String prop, Boolean suffix) throws SiebelBusinessServiceException
    {
        String property = "";
        try{
            AP.loadProp(ENV).setProperty(prop, suffix);
            property = AP.getProperty();
            if(property == null) {
                property = "";
                com.plexadasi.connect.build.MyLogging.log(Level.SEVERE, "HelperClass:Property "+prop+" does not exist");
            }
        }catch(NullPointerException e){
            MyLogging.log(Level.INFO, "Cannot load the template file");
        }
        return property;
    }
    
    public class HelperProperties{
        
        public static final String WORKSHOP_DOCUMENT = "invoice_template";
        public static final String PROFORMA_DOCUMENT = "invoice_template_second";
        public static final String WAYBILL_DOCUMENT = "waybill_template";
        public static final String OUTPUT_KEY = "generated_path";
        public static final String JOBCARD_DOCUMENT = "jobcard_template";
        public static final String LAGOS_WAREHOUSE_CODE = "lagos_warehouse_code";
        public static final String ABUJA_WAREHOUSE_CODE = "abuja_warehouse_code";
    }
}
