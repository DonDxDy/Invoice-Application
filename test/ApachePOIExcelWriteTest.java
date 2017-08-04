
import com.siebel.data.SiebelPropertySet;
import com.siebel.eai.SiebelBusinessServiceException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
     * @author Adeyemi
 */
public class ApachePOIExcelWriteTest 
{
    /**
     *
     * @param args
     * @throws SiebelBusinessServiceException
     */
    public static void main(String[] args) throws SiebelBusinessServiceException 
    {
        ApachePOIExcelWrite eia = new ApachePOIExcelWrite();
        SiebelPropertySet inputs = new SiebelPropertySet();
        SiebelPropertySet outputs = new SiebelPropertySet();
        inputs.setProperty("JobId", "1-3579601");//1-1028K//1-1026S//1-1025Q//1-3247471//
        inputs.setProperty("JobNum", "Job Card");
        //eia.doInvokeMethod("JobCardGenerator", inputs, outputs);
        
        inputs.setProperty("QuoteId", "1-2HTET");
        inputs.setProperty("QuoteNum", "Quotes 1-2CUL4");
        inputs.setProperty("Type", "Proforma Invoice");
        eia.doInvokeMethod("QuoteExcelGenerator", inputs, outputs);
        
        inputs.setProperty("OrderId", "1-2C05B");
        inputs.setProperty("OrderNum", "1-3919295");
        inputs.setProperty("OrderType", "Sales Order");
        inputs.setProperty("ShipId", "1-4098412");
        //eia.doInvokeMethod("OrderExcelGenerator", inputs, outputs);
    }
}
