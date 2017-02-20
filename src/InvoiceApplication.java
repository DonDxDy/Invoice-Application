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
import java.util.List;
import java.util.logging.Level;


public class InvoiceApplication  extends SiebelBusinessService {
    
    @Override
    public void doInvokeMethod(String MethodName, SiebelPropertySet inputs, SiebelPropertySet outputs) throws SiebelBusinessServiceException{
        if (MethodName.equalsIgnoreCase("GenerateInvoice")){
            MyLogging.log(Level.INFO, "======= In GenerateInvoice ========");
            
        }
        
    }
    
    public static void main(String[] args) throws SiebelBusinessServiceException {
        InvoiceApplication eia = new InvoiceApplication();
        SiebelPropertySet inputs = new SiebelPropertySet();
        SiebelPropertySet outputs = new SiebelPropertySet();
        inputs.setProperty("QuoteId", "1-1025Q");
        eia.doInvokeMethod("GenerateInvoice", inputs, outputs);
    }
}
