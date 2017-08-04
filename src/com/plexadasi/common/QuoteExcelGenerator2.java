package com.plexadasi.common;


import com.plexadasi.Helper.HelperAP;
import com.plexadasi.SiebelApplication.MyLogging;
import com.plexadasi.SiebelApplication.object.QOrderBillToAccount;
import com.plexadasi.SiebelApplication.object.QParts2;
import com.plexadasi.SiebelApplication.object.Quote;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelPropertySet;
import com.plexadasi.common.element.Attachment;
import com.plexadasi.common.element.InvoiceExcel;
import com.plexadasi.common.element.InvoiceExcelTotal2;
import com.plexadasi.common.element.QuoteAttachment;
import com.plexadasi.common.element.XGenerator;
import com.plexadasi.common.impl.Generator;
import com.plexadasi.connect.ebs.EbsConnect;
import com.plexadasi.connect.siebel.SiebelConnect;
import com.plexadasi.invoiceapplication.ContactKey;
import com.plexadasi.invoiceapplication.ProductKey;
import com.siebel.eai.SiebelBusinessServiceException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
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
            inputFile = HelperAP.getInvoiceTemplate2();
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
            InvoiceExcel qParts;
            contact = new InvoiceExcel(my_xlsx_workbook, my_worksheet);
            
            int startRowAt = 3;
            contact.setJobId(this.quote_id);
            contact.setStartRow(startRowAt);
            contact.createCellFromList(new QOrderBillToAccount(conn), new ContactKey());
            
            //
            startRowAt = 16;
            qParts = parts = new InvoiceExcel(my_xlsx_workbook, my_worksheet);
            parts.setJobId(this.quote_id);
            parts.setStartRow(startRowAt);
            parts.createCellFromList(new QParts2(conn, ebsConn), new ProductKey());
            
            SiebelPropertySet set = new SiebelPropertySet();
            set.setProperty("PLX Quote Total", "PLX Quote Total");
            set.setProperty("Current Quote Total Net Price", "Current Quote Total Net Price");
            set.setProperty("Sales Team", "Sales Team");
            
            parts.setStartRow(parts.next(0));
            Quote quote = new Quote(conn);
            SiebelPropertySet get = quote.find(quote_id, set);
            Map<String, String> map = new HashMap();
            map.put("8", get.getProperty("Current Quote Total Net Price"));
            list.add(map);
            parts.createCellFromList(list, new ProductKey());
            
            parts.setStartRow(qParts.next(1));
            quote = new Quote(conn);
            get = quote.find(quote_id, set);
            list = new ArrayList();
            map = new HashMap();
            map.put("8", get.getProperty("PLX Quote Total"));
            list.add(map);
            parts.createCellFromList(list, new ProductKey());
            
            parts.setStartRow(parts.next(19));
            quote = new Quote(conn);
            get = quote.find(quote_id, set);
            list = new ArrayList();
            map = new HashMap();
            map.put("8", get.getProperty("Sales Team"));
            list.add(map);
            parts.createCellFromList(list, new ProductKey());
            my_xlsx_workbook.setForceFormulaRecalculation(true);
            input_document.close();
            XGenerator.doCreateBook(my_xlsx_workbook, "WST-" + this.type.replace(" ", "-") + "_" + this.quote_number.replace(" ", "_"));
            String filepath = XGenerator.getProperty("filepath");
            String filename = XGenerator.getProperty("filename");
            //String filepath = "/usr/app/siebel/intg/excel/weststar_TEST_02052017153845.xls";
            parts = null;
            Attachment a = new QuoteAttachment(conn, quote_id);
            //Attach the file to siebel
            /*
            a.Attach(
                filepath,
                filename,
                Boolean.FALSE
            );*/
            a = null;
            my_xlsx_workbook.close();
            System.out.println("Done");
            outputs.setProperty("status", "success");
        } 
        catch (FileNotFoundException ex) 
        {
            outputs.setProperty("status", "failed");
            outputs.setProperty("error_message", ex.getMessage());
            ex.printStackTrace(new PrintWriter(error_txt));
            MyLogging.log(Level.SEVERE, "Caught File Not Found Exception: " + ex.getMessage() + error_txt.toString());
        } 
        catch (IOException ex) 
        {
            outputs.setProperty("status", "failed");
            outputs.setProperty("error_message", ex.getMessage());
            ex.printStackTrace(new PrintWriter(error_txt));
            MyLogging.log(Level.SEVERE, "Caught IO Exception: " + ex.getMessage() + error_txt.toString());
        } 
        catch (InvalidFormatException ex) 
        {
            outputs.setProperty("status", "failed");
            outputs.setProperty("error_message", error_txt.toString());
            ex.printStackTrace(new PrintWriter(error_txt));
            MyLogging.log(Level.SEVERE, "Caught Invalid Format Exception: " + ex.getMessage() + error_txt.toString());
        } 
        catch (EncryptedDocumentException ex) 
        {
            outputs.setProperty("status", "failed");
            outputs.setProperty("error_message", ex.getMessage());
            ex.printStackTrace(new PrintWriter(error_txt));
            MyLogging.log(Level.SEVERE, "Caught Encrypted Document Exception: " + ex.getMessage() + error_txt.toString());
        } 
        catch (Exception ex) 
        {
            outputs.setProperty("status", "failed");
            outputs.setProperty("error_message", ex.getMessage());
            ex.printStackTrace(new PrintWriter(error_txt));
            MyLogging.log(Level.SEVERE, "Caught Exception: " + ex.getMessage() + error_txt.toString());
        }
    }
}
