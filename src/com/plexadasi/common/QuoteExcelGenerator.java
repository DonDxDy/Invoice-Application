package com.plexadasi.common;


import com.plexadasi.Helper.HelperAP;
import com.plexadasi.connect.siebel.SiebelConnect;
import com.plexadasi.SiebelApplication.MyLogging;
import com.plexadasi.SiebelApplication.object.QCustomer;
import com.plexadasi.SiebelApplication.object.QExpenses;
import com.plexadasi.SiebelApplication.object.QLabour;
import com.plexadasi.SiebelApplication.object.QLubricant;
import com.plexadasi.SiebelApplication.object.QParts;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelPropertySet;
import com.siebel.eai.SiebelBusinessServiceException;
import com.plexadasi.common.element.Attachment;
import com.plexadasi.common.element.InvoiceExcel;
import com.plexadasi.common.element.InvoiceExcelTotal;
import com.plexadasi.common.element.QuoteAttachment;
import com.plexadasi.common.element.XGenerator;
import com.plexadasi.common.impl.Generator;
import com.plexadasi.invoiceapplication.ContactKey;
import com.plexadasi.invoiceapplication.ProductKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
public class QuoteExcelGenerator implements Generator{

    private String inputFile = "";
    
    private String quote_id;
    
    private String quote_number;
    
    private String type;
    
    private Boolean output;
    
    private final StringWriter error_txt = new StringWriter();
    
    private FileInputStream input_document;

    public QuoteExcelGenerator() {
        this.quote_number = "";
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
        try {
            //
            //IProperties AP = new ApplicationProperties2();
            SiebelDataBean conn = SiebelConnect.connectSiebelServer();
            //Get excel path
            inputFile = HelperAP.getInvoiceTemplate();
            //Read Excel document first
            input_document = new FileInputStream(new File(inputFile));
            // Convert it into a POI object
            Workbook my_xlsx_workbook = WorkbookFactory.create(input_document);
            // Read excel sheet that needs to be updated
            Sheet my_worksheet = my_xlsx_workbook.getSheet("Estimate");
            // Declare a Cell object
            this.quote_id = inputs.getProperty("QuoteId");
            this.quote_number = inputs.getProperty("QuoteNum");
            this.type = inputs.getProperty("Type");
            InvoiceExcel customerInfo = new InvoiceExcel(my_xlsx_workbook, my_worksheet, 6);
            customerInfo.setJobId(this.quote_id);
            customerInfo.createCellFromList(new QCustomer(conn), new ContactKey());
            customerInfo.setNextRow(0);
            InvoiceExcelTotal labour;
            InvoiceExcelTotal parts, lubricant, expenses;
            int startRowAt = 17, nextRowAt;
            labour = parts = lubricant = expenses = new InvoiceExcelTotal(my_xlsx_workbook, my_worksheet);
            //
            labour.setJobId(this.quote_id);
            labour.setStartRow(startRowAt);
            labour.createCellFromList(new QLabour(conn), new ProductKey());
            XGenerator.doMerge(my_worksheet, labour.next(6) - 2, 0, 1, 10, false);
            XGenerator.doMerge(my_worksheet, labour.next(6) - 1, 2, 1, 5, false);
            //
            parts.setJobId(this.quote_id);
            parts.setStartRow(labour.next(6));
            //System.out.println(labour.next(6));
            parts.createCellFromList(new QParts(conn), new ProductKey());
            XGenerator.doMerge(my_worksheet, parts.next(4) - 2, 0, 1, 10, false);
            XGenerator.doMerge(my_worksheet, parts.next(4) - 1, 2, 1, 5, false);
            // Creates the lubricant row in excel sheet
            lubricant.setJobId(this.quote_id);
            lubricant.setStartRow(parts.next(4));
            lubricant.createCellFromList(new QLubricant(conn), new ProductKey());
            //System.out.println(parts.next(5));
            XGenerator.doMerge(my_worksheet, lubricant.next(4) - 2, 0, 1, 10, false);
            XGenerator.doMerge(my_worksheet, lubricant.next(4) - 1, 2, 1, 5, false);
            //
            expenses.setJobId(this.quote_id);
            expenses.setStartRow(lubricant.next(4));
            expenses.createCellFromList(new QExpenses(conn), new ProductKey());
            nextRowAt = expenses.next(5);
            XGenerator.doMerge(my_worksheet, (nextRowAt - 2), 7, 1, 3, false);
            for(int i = 0; i < 9; i++){
                for(int j = i; j < 10; j++){
                    if(i > 0){
                        if(j == 4){
                            XGenerator.doMerge(my_worksheet, (nextRowAt + (i + j)), 0, 1, 7, false);
                        }
                        if(j == 9){
                            XGenerator.doMerge(my_worksheet, ((nextRowAt - 1) + i), 7, 1, 2, false);
                        }
                    }
                }
            }   
            my_xlsx_workbook.setForceFormulaRecalculation(true);
            input_document.close();
            XGenerator.doCreateBook(my_xlsx_workbook, "weststar_" + this.type.replace(" ", "-") + "_" + this.quote_number.replace(" ", "_"));
            Attachment a = new QuoteAttachment(conn, quote_id);
            String filepath = XGenerator.getProperty("filepath");
            String filename = XGenerator.getProperty("filename");
            //Attach the file to siebel
            a.Attach(
                filepath,
                filename,
                Boolean.FALSE
            );
            //outputs.setProperty("getFileReturn", a.getProperty("aGetFileReturn"));
            boolean logoff = conn.logoff();
            my_xlsx_workbook.close();
            System.out.println("Done");
            output = outputs.setProperty("status", "success");
        } 
        catch (FileNotFoundException ex) 
        {
            outputs.setProperty("status", "failed");
            outputs.setProperty("error_message", ex.getMessage());
            ex.printStackTrace(new PrintWriter(error_txt));
            MyLogging.log(Level.SEVERE, "Caught File Not Found Exception: " + error_txt.toString());
        } 
        catch (IOException ex) 
        {
            outputs.setProperty("status", "failed");
            outputs.setProperty("error_message", ex.getMessage());
            ex.printStackTrace(new PrintWriter(error_txt));
            MyLogging.log(Level.SEVERE, "Caught IO Exception: " + error_txt.toString());
        } 
        catch (InvalidFormatException ex) 
        {
            outputs.setProperty("status", "failed");
            outputs.setProperty("error_message", ex.getMessage());
            ex.printStackTrace(new PrintWriter(error_txt));
            MyLogging.log(Level.SEVERE, "Caught Invalid Format Exception: " + error_txt.toString());
        } 
        catch (EncryptedDocumentException ex) 
        {
            outputs.setProperty("status", "failed");
            outputs.setProperty("error_message", ex.getMessage());
            ex.printStackTrace(new PrintWriter(error_txt));
            MyLogging.log(Level.SEVERE, "Caught Encrypted Document Exception: " + error_txt.toString());
        } 
        catch (Exception ex) 
        {
            outputs.setProperty("status", "failed");
            outputs.setProperty("error_message", ex.getMessage());
            ex.printStackTrace(new PrintWriter(error_txt));
            MyLogging.log(Level.SEVERE, "Caught Exception: " + error_txt.toString());
        }
    }
}
