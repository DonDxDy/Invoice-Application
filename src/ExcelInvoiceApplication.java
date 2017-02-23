
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import com.siebel.eai.SiebelBusinessService;
import com.siebel.eai.SiebelBusinessServiceException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Level;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

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
            String excelTemplate = ApplicationProperties.INVOICE_TEMPLATE;
            MyLogging.log(Level.INFO, "Excel Template is "+excelTemplate);
            try {
                String quote_id = inputs.getProperty("QuoteId");
                MyLogging.log(Level.INFO, "Quote Id is "+inputs.getProperty("QuoteId"));
               //List<Map> customerDetails = ss.getCustomerDetails(inputs.getProperty("QuoteId"));
                List<Customer> thecustomerDetails = ss.getTheCustomerDetails(quote_id);
                List<Labour> labourDetails = ss.getQuoteLabourItems(quote_id);
               // eg.writeCustomerDetailsInInvoice(excelTemplate, customerDetails);
                String currTemplate = eg.writeTheCustomerDetailsInInvoice(excelTemplate, thecustomerDetails);
                eg.writeLabourDetailsInInvoice(excelTemplate, labourDetails);
            } catch (SiebelException ex) {
                ex.printStackTrace(new PrintWriter(errors));
                MyLogging.log(Level.SEVERE, "Error:GenerateInvoice: "+errors.toString());
            } catch (IOException ex) {
                ex.printStackTrace(new PrintWriter(errors));
                MyLogging.log(Level.SEVERE, "Error:GenerateInvoice||Template File Not Found: "+errors.toString());
            }catch (InvalidFormatException ex) {
                ex.printStackTrace(new PrintWriter(errors));
                MyLogging.log(Level.SEVERE, "Error:GenerateInvoice: "+errors.toString());
            }
            
        }                                        
    }
    
    
    public static void main(String[] args) throws SiebelBusinessServiceException {
        ExcelInvoiceApplication eia = new ExcelInvoiceApplication();
        SiebelPropertySet inputs = new SiebelPropertySet();
        SiebelPropertySet outputs = new SiebelPropertySet();
        inputs.setProperty("QuoteId", "1-1025Q");
        eia.doInvokeMethod("GenerateInvoice", inputs, outputs);
    }
    
    
}
