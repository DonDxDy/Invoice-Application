/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.plexadasi.SiebelApplication.MyLogging;
import com.plexadasi.common.JobCardGenerator;
import com.siebel.data.SiebelPropertySet;
import com.siebel.eai.SiebelBusinessService;
import com.siebel.eai.SiebelBusinessServiceException;
import com.plexadasi.common.OrderExcelGenerator;
import com.plexadasi.common.QuoteExcelGenerator;
import com.plexadasi.common.QuoteExcelGenerator2;
import java.io.Writer;
import java.util.logging.Level;

/**
 *
 * @author gbege
 */
public class ApachePOIExcelWrite  extends SiebelBusinessService{

    private Writer error_txt;
    
    @Override
            public void doInvokeMethod(String MethodName, SiebelPropertySet inputs, SiebelPropertySet outputs) throws SiebelBusinessServiceException 
    {
        if (inputs == null)
        {
            MyLogging.log(Level.SEVERE, "Caught Exception: Missing param");
            outputs.setProperty("status", "failed");
            outputs.setProperty("error_message", "Missing param");
            throw new SiebelBusinessServiceException("NO_PAR", "Missing param"); 
        }
        if(MethodName.equalsIgnoreCase("QuoteExcelGenerator"))
        {
            if("Workshop Template".equalsIgnoreCase(inputs.getProperty("Type"))){
                Context.callMethod(new QuoteExcelGenerator(), inputs, outputs);
            }else if("Proforma Invoice".equalsIgnoreCase(inputs.getProperty("Type"))){
                Context.callMethod(new QuoteExcelGenerator2(), inputs, outputs);
            }else{
                throw new SiebelBusinessServiceException("NO_TYPE", "Type not found.");
            }
        }
        else if(MethodName.equalsIgnoreCase("OrderExcelGenerator"))
        {
            Context.callMethod(new OrderExcelGenerator(), inputs, outputs);
        }
        else if(MethodName.equalsIgnoreCase("JobCardGenerator"))
        {
            Context.callMethod(new JobCardGenerator(), inputs, outputs);
        }
        else
        {
            MyLogging.log(Level.SEVERE, "Caught Exception: No such method");
            outputs.setProperty("status", "failed");
            outputs.setProperty("error_message", "No such method");
            throw new SiebelBusinessServiceException("NO_SUCH_METHOD", "No such method"); 
        }
    }
}