/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Efosa Ehigie
 * Plexada System Integrators
 */
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import com.siebel.eai.SiebelBusinessService;
import com.siebel.eai.SiebelBusinessServiceException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


public class InvoiceApplication  extends SiebelBusinessService {
    private String templateFileName = ApplicationProperties.getInvoiceTempate();
    private String destFileName = ApplicationProperties.getInvoiceTempate();
    private StringWriter errors = new StringWriter();
    
    @Override
    public void doInvokeMethod(String MethodName, SiebelPropertySet inputs, SiebelPropertySet outputs) throws SiebelBusinessServiceException{
        
        
        if (MethodName.equalsIgnoreCase("GenerateInvoice")){
            MyLogging.log(Level.INFO, "======= In GenerateInvoice ========");
            templateFileName = templateFileName+".xls";
            MyLogging.log(Level.INFO, "Excel Template is "+templateFileName);
            String quoteId = inputs.getProperty("QuoteId");
            MyLogging.log(Level.INFO, "quoteId "+quoteId);
            try{                
                SiebelService ss = new SiebelService();                
                Customer customer = new Customer();                
                customer = ss.getCustomer(quoteId);                
                QuoteInvoice quoteinvoice = new QuoteInvoice(customer);
                quoteinvoice = ss.getQuoteLabour(quoteId, quoteinvoice);
                quoteinvoice = ss.getQuoteParts(quoteId, quoteinvoice);
                quoteinvoice = ss.getQuoteLubricants(quoteId, quoteinvoice);
                quoteinvoice = ss.getQuoteExpense(quoteId, quoteinvoice);
                Map beans = new HashMap();
                beans.put("quoteinvoice", quoteinvoice);
                XLSTransformer transformer = new XLSTransformer();        
                transformer.groupCollection("quoteinvoice.labour");
                transformer.groupCollection("quoteinvoice.part");
                transformer.groupCollection("quoteinvoice.lubricant");
                transformer.groupCollection("quoteinvoice.expense");
                Date date = new Date();
                SimpleDateFormat extn = new SimpleDateFormat("yyMMddHHmmss");
                destFileName = destFileName+customer.getAccountId()+extn.format(date)+".xls";                
                transformer.transformXLS(templateFileName, beans, destFileName);
                MyLogging.log(Level.INFO, "destFileName"+destFileName);
            }catch(SiebelException e){
                e.printStackTrace(new PrintWriter(errors));
                outputs.setProperty("RETURN", "Error");
                outputs.setProperty("ERR_MSG", errors.toString());
                MyLogging.log(Level.SEVERE, "ERROR:SiebelException:"+ errors.toString());
            } catch (ParsePropertyException e) {
                e.printStackTrace(new PrintWriter(errors));
                outputs.setProperty("RETURN", "Error");
                outputs.setProperty("ERR_MSG", errors.toString());
                MyLogging.log(Level.SEVERE, "ERROR:ParsePropertyException:"+ errors.toString());
            } catch (IOException e) {
                e.printStackTrace(new PrintWriter(errors));
                outputs.setProperty("RETURN", "Error");
                outputs.setProperty("ERR_MSG", errors.toString());
                MyLogging.log(Level.SEVERE, "ERROR:IOException:"+ errors.toString());
            } catch (InvalidFormatException ex) {
                ex.printStackTrace(new PrintWriter(errors));
                outputs.setProperty("RETURN", "Error");
                outputs.setProperty("ERR_MSG", errors.toString());
                MyLogging.log(Level.SEVERE, "ERROR:InvalidFormatException:"+ errors.toString());
            }
        }
        
        outputs.setProperty("RETURN", "Success");
        outputs.setProperty("ERR_MSG", "");
        
    }
    
    public static void main(String[] args) throws SiebelBusinessServiceException {
        InvoiceApplication eia = new InvoiceApplication();
        SiebelPropertySet inputs = new SiebelPropertySet();
        SiebelPropertySet outputs = new SiebelPropertySet();
        inputs.setProperty("QuoteId", "1-1028K");
        eia.doInvokeMethod("GenerateInvoice", inputs, outputs);
    }
}
