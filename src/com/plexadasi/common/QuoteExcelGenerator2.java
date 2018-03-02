package com.plexadasi.common;


import com.plexadasi.Helper.HelperExcelAP;
import com.plexadasi.SiebelApplication.MyLogging;
import com.plexadasi.SiebelApplication.object.QOrderBillToAccount;
import com.plexadasi.SiebelApplication.object.Quote;
import com.plexadasi.SiebelApplication.object.QuotePartItem;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelPropertySet;
import com.plexadasi.common.element.InvoiceExcel;
import com.plexadasi.common.element.QuoteAttachment;
import com.plexadasi.common.element.XGenerator;
import com.plexadasi.common.impl.Generator;
import com.plexadasi.connect.ebs.EbsConnect;
import com.plexadasi.invoiceapplication.ContactKey;
import com.plexadasi.invoiceapplication.NewClass;
import com.plexadasi.invoiceapplication.ProductKey;
import com.plexadasi.invoiceapplication.QuoteKey;
import com.siebel.eai.SiebelBusinessServiceException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SAP Training
 */
public class QuoteExcelGenerator2 implements Generator
{
    private String inputFile;
    
    private String quote_id;
    
    private String quote_number;
    
    private String type;
            
    private final SiebelDataBean conn;
    
    private final StringWriter error_txt = new StringWriter();
    
    private FileInputStream input_document;
    
    private Connection ebsConn = null;
    
    private List list = new ArrayList();
    
    private Map<String, String> map = new HashMap();

    public QuoteExcelGenerator2(SiebelDataBean conn) {
        this.conn = conn;
        this.quote_number = null;
        this.input_document = null;
        this.type = null;
    }
    
    /**
     *
     * @param inputs the value of inputs
     * @param outputs the value of outputs
     */
    @Override
    public void generateExcelDoc(SiebelPropertySet inputs, SiebelPropertySet outputs) throws SiebelBusinessServiceException
    {
        try{
            ebsConn = EbsConnect.connectToEBSDatabase();
        }catch(NullPointerException e){
            MyLogging.log(Level.SEVERE, "Caught Exception: Failed to connect to EBS. Please try again or ask your administrator.");
            outputs.setProperty("status", "failed");
            outputs.setProperty("error_message", "Failed to connect to EBS. Please try again or ask your administrator.");
            throw new SiebelBusinessServiceException("CONN_EXCEPT", "Failed to connect to EBS. Please try again or ask your administrator."); 
        }
        try {
            //
            //Get excel path
            inputFile = HelperExcelAP.getInvoiceTemplate2();
            //Read Excel document first
            input_document = new FileInputStream(new File(inputFile));
            // Convert it into a POI object
            Workbook my_xlsx_workbook = WorkbookFactory.create(input_document);
            // Read excel sheet that needs to be updated
            Sheet my_worksheet = my_xlsx_workbook.getSheet("Sheet1");
            // Declare a Cell object
            this.quote_id = inputs.getProperty("QuoteId");
            this.quote_number = inputs.getProperty("QuoteNum");
            this.type = inputs.getProperty("Type");
            InvoiceExcel contact, parts;
            contact = new InvoiceExcel(my_xlsx_workbook, my_worksheet);
            NewClass sequence = new NewClass(conn, "1-2G23X"); 
            
            int startRowAt = 3;
            contact.setJobId(this.quote_id);
            contact.setStartRow(startRowAt);
            QOrderBillToAccount billToAccount = new QOrderBillToAccount(conn, sequence);
            contact.createCellFromList(billToAccount, new ContactKey());
            
            //
            startRowAt = 16;
            parts = new InvoiceExcel(my_xlsx_workbook, my_worksheet);
            parts.setJobId(this.quote_id);
            parts.setStartRow(startRowAt);
            parts.createCellFromList(new QuotePartItem(conn, ebsConn), new ProductKey());
            
            SiebelPropertySet set = this.conn.newPropertySet();
            set.setProperty("PLX Quote Total", "PLX Quote Total");
            set.setProperty("Current Quote Total Base Price", "Current Quote Total Base Price");
            set.setProperty("PLX Vat", "PLX Vat");
            set.setProperty("PLX Local Delivery Charges", "PLX Local Delivery Charges");
            set.setProperty("Freight", "Freight");
            set.setProperty("Current Quote Total Net Price", "Current Quote Total Net Price");
            set.setProperty("Current Quote Total Net Price NRC", "Current Quote Total Net Price NRC");
            set.setProperty("Product Total", "Product Total");
            set.setProperty("PC Total Savings", "PC Total Savings");            
            set.setProperty("Grand Total", "Grand Total");         
            set.setProperty("Quote Total", "Quote Total");
            set.setProperty("Sales Team", "Sales Team");
            set.setProperty("PLX Quote Sub Total", "PLX Quote Sub Total");
            set.setProperty("PLX Calculate VAT", "PLX Calculate VAT");
            //set.setProperty("PLX Quote Total NRC", "PLX Quote Total NRC");
            
            parts.setStartRow(parts.next(0));
            Quote quote = new Quote(conn);
            SiebelPropertySet quoteItem = quote.find(quote_id, set);
            //System.out.println(quoteItem);
            map = new HashMap();
            map.put("8", quoteItem.getProperty("Current Quote Total Base Price"));
            list.add(map);
            map = new HashMap();
            //map.put("8", quoteItem.getProperty("PC Total Savings"));
            list.add(map);
            map = new HashMap();
            map.put("8", quoteItem.getProperty("Current Quote Total Net Price NRC"));
            list.add(map);
            map = new HashMap();
            map.put("8", quoteItem.getProperty("Freight"));
            list.add(map);
            map = new HashMap();
            map.put("8", quoteItem.getProperty("PLX Local Delivery Charges"));
            list.add(map);
            map = new HashMap();
            map.put("8", quoteItem.getProperty("PLX Quote Sub Total"));
            list.add(map);
            map = new HashMap();
            map.put("8", quoteItem.getProperty("PLX Calculate VAT"));
            list.add(map);
            map = new HashMap();
            map.put("8", quoteItem.getProperty("Quote Total"));
            list.add(map);
            parts.createCellFromList(list, new QuoteKey());
            
            
            parts.setStartRow(parts.next(27));
            list = new ArrayList();
            map = new HashMap();
            map.put("8", quoteItem.getProperty("Sales Team"));
            list.add(map);
            parts.createCellFromList(list, new ProductKey());
            my_xlsx_workbook.setForceFormulaRecalculation(true);
            input_document.close();
            String path = this.type +"_"+ this.quote_number;
            XGenerator.doCreateBook(my_xlsx_workbook, "WST-" + path);
            String filepath = XGenerator.getProperty("filepath");
            String filename = XGenerator.getProperty("filename");
            //String filepath = "/usr/app/siebel/intg/excel/weststar_TEST_02052017153845.xls";
            QuoteAttachment a = new QuoteAttachment(conn, quote_id);
            //Attach the file to siebel
            a.Attach(
                filepath,
                filename,
                Boolean.FALSE
            );
            my_xlsx_workbook.close();
            MyLogging.log(Level.INFO, billToAccount.getInvoiceNumber());
            if(billToAccount.getInvoiceNumber().equals(""))
            {
                sequence.sequence();
                sequence.writeSequence();
            }
            System.out.println("Done");
            outputs.setProperty("status", "success");
        } 
        catch (FileNotFoundException ex) 
        {
            outputs.setProperty("status", "failed");
            ex.printStackTrace(new PrintWriter(error_txt));
            outputs.setProperty("error_message", error_txt.toString());
            MyLogging.log(Level.SEVERE, "Caught File Not Found Exception: " + error_txt.toString());
        } 
        catch (IOException ex) 
        {
            outputs.setProperty("status", "failed");
            ex.printStackTrace(new PrintWriter(error_txt));
            outputs.setProperty("error_message", error_txt.toString());
            MyLogging.log(Level.SEVERE, "Caught IO Exception: " + error_txt.toString());
        } 
        catch (InvalidFormatException ex) 
        {
            outputs.setProperty("status", "failed");
            ex.printStackTrace(new PrintWriter(error_txt));
            outputs.setProperty("error_message", error_txt.toString());
            MyLogging.log(Level.SEVERE, "Caught Invalid Format Exception: " + error_txt.toString());
        } 
        catch (EncryptedDocumentException ex) 
        {
            outputs.setProperty("status", "failed");
            ex.printStackTrace(new PrintWriter(error_txt));
            outputs.setProperty("error_message", error_txt.toString());
            MyLogging.log(Level.SEVERE, "Caught Encrypted Document Exception: " + error_txt.toString());
        } 
        catch (Exception ex) 
        {
            outputs.setProperty("status", "failed");
            ex.printStackTrace(new PrintWriter(error_txt));
            outputs.setProperty("error_message", error_txt.toString());
            MyLogging.log(Level.SEVERE, "Caught Exception: " + error_txt.toString());
        }
    }
}
