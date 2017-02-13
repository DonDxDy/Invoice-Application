
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import com.siebel.eai.SiebelBusinessService;
import com.siebel.eai.SiebelBusinessServiceException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SAP Training
 */
public class ExcelInvoiceApplication extends SiebelBusinessService {
    private StringWriter errors = new StringWriter();
    
    
    @Override
    public void doInvokeMethod(String MethodName, SiebelPropertySet inputs, SiebelPropertySet outputs) throws SiebelBusinessServiceException {
        
        if (MethodName.equalsIgnoreCase("GenerateInvoice")){
            MyLogging.log(Level.INFO, "======= In GenerateInvoice ========");
            SiebelService ss = new SiebelService();
            ExcelGenerator eg = new ExcelGenerator();
            String excelTemplate = ApplicationProperties.getInvoiceTempate();
            MyLogging.log(Level.INFO, "Excel Template is "+excelTemplate);
            try {
                MyLogging.log(Level.INFO, "Quote Id is "+inputs.getProperty("QuoteId"));
                List<Map> customerDetails = ss.getCustomerDetails(inputs.getProperty("QuoteId"));
                eg.writeCustomerDetailsInInvoice(excelTemplate, customerDetails);
            } catch (SiebelException ex) {
                ex.printStackTrace(new PrintWriter(errors));
                MyLogging.log(Level.SEVERE, "Error in GenerateInvoice: "+errors.toString());
            } catch (IOException ex) {
                ex.printStackTrace(new PrintWriter(errors));
                MyLogging.log(Level.SEVERE, "Error:Template File Not Found: "+errors.toString());
            }
            
        }                                        
    }
    
    
    public static void main(String[] args) throws SiebelBusinessServiceException {
        ExcelInvoiceApplication eia = new ExcelInvoiceApplication();
        SiebelPropertySet inputs = new SiebelPropertySet();
        SiebelPropertySet outputs = new SiebelPropertySet();
        inputs.setProperty("QuoteId", "1-1KMI6");
        eia.doInvokeMethod("GenerateInvoice", inputs, outputs);
    }
    
    
}
