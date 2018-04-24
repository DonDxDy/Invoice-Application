
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import com.siebel.eai.SiebelBusinessServiceException;
import java.util.Enumeration;

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
    private SiebelPropertySet set;
    public static void main(String[] args) throws SiebelBusinessServiceException, SiebelException 
    {
        ApachePOIExcelWrite eia = new ApachePOIExcelWrite();
        ApachePOIExcelWriteTest d = new ApachePOIExcelWriteTest();
        SiebelPropertySet inputs = new SiebelPropertySet();
        SiebelPropertySet outputs = new SiebelPropertySet();
        inputs.setProperty("JobId", "1-677881");//1-1028K//1-1026S//1-1025Q//1-3247471//
        inputs.setProperty("JobNum", "Job Card");
        //eia.doInvokeMethod("JobCardGenerator", inputs, outputs);
        
        //Proforma document test
        inputs.setProperty("QuoteId", "1-QCHID");
        inputs.setProperty("QuoteNum", "Quotes 1-2CUL4");
        inputs.setProperty("Type", "Proforma Invoice");
        //eia.doInvokeMethod("QuoteExcelGenerator", inputs, outputs);
        
        //Workshop document test
        inputs.setProperty("QuoteId", "1-QCHID");
        inputs.setProperty("QuoteNum", "Quotes 1-2CUL4");
        inputs.setProperty("Type", "Workshop Template");
        //eia.doInvokeMethod("QuoteExcelGenerator", inputs, outputs);
        
        inputs.setProperty("OrderId", "1-4ASNE");
        inputs.setProperty("OrderNum", "1-7222154");
        inputs.setProperty("OrderType", "Sales Order");
        inputs.setProperty("ShipId", "1-7222196");
        eia.doInvokeMethod("OrderExcelGenerator", inputs, outputs);
        
        SiebelPropertySet vset = new SiebelPropertySet();
        SiebelPropertySet set = new SiebelPropertySet();
        set.setType("test");
        set.setValue("test value");
        /*
        set.setProperty("PLX Quote Total", "PLX Quote Total");        
        set.setProperty("PLX Vat", "PLX Vat");
        set.setProperty("Freight Total", "1-4098412");
        set.setProperty("PLX Local Delivery Charges", "PLX Local Delivery Charges");
        set.setProperty("Freight", "Freight");
        set.setProperty("Current Quote Total Net Price", "Current Quote Total Net Price");
        set.setProperty("PC Total Savings", "PC Total Savings"); 
        set.setProperty("Grand Total", "Grand Total");*/
        
        set.setProperty("Scheduled Delivery Date", "5");
        set.setProperty("Waybill Number", "5");
        set.setProperty("Carrier", "5");
        vset.addChild(set);
        //set = new SiebelPropertySet(set);
	final Enumeration<String> e = set.getPropertyNames();
        while (e.hasMoreElements()){
            String value = e.nextElement();
            System.out.println(value);
            //map.put(value, properties.getProperty(value));
        }
        System.out.println(vset);
        //System.out.println((char)('A' + (Math.log10(999999999)%4)));
        //System.out.println(Math.log10(100000000)%4==0);
        //String theDigits = CharMatcher.DIGIT.retainFrom("WS-A_A-3000");
        //System.out.println(theDigits);
        /*
        try{
        System.out.println(CharMatcher.JAVA_LETTER.retainFrom("").charAt(0));
        }catch(StringIndexOutOfBoundsException e){}
        NewClass c = new NewClass();
        c.setSequence(9999 + 1);*/
        //for(int i =0; i < 1000; i++)
            //System.out.println(i + ") " +(char)('A' + i));
    }
    
    public void setProp(SiebelPropertySet set)
    {
        this.set = set;
    }
    
    public SiebelPropertySet getProp()
    {
        return this.set;
    }
}
