/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import SiebelApplication.ApplicationProperties;
import SiebelApplication.IProperties;
import SiebelApplication.MyLogging;
import SiebelApplication.bin.QCustomer;
import SiebelApplication.bin.QExpenses;
import SiebelApplication.bin.QLabour;
import SiebelApplication.bin.QLubricant;
import SiebelApplication.bin.QParts;
import com.siebel.data.SiebelPropertySet;
import com.siebel.eai.SiebelBusinessService;
import com.siebel.eai.SiebelBusinessServiceException;
import generate.Attachment;
import generate.CustomerRecord;
import generate.XGenerator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import generate.InvoiceExcel;
import invoiceapplication.ContactKey;
import invoiceapplication.ProductKey;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import org.apache.poi.EncryptedDocumentException;

/**
 *
 * @author gbege
 */
public class ApachePOIExcelWrite  extends SiebelBusinessService{
    private String inputFile = "";
    
    private String quote_id;
    
    private String quote_number;
    
    private final StringWriter error_txt = new StringWriter();
    
    private FileInputStream input_document;

    public ApachePOIExcelWrite() {
        this.input_document = null;
    }
    
    public static void main(String[] args) throws SiebelBusinessServiceException {
        ApachePOIExcelWrite eia = new ApachePOIExcelWrite();
        SiebelPropertySet inputs = new SiebelPropertySet();
        SiebelPropertySet outputs = new SiebelPropertySet();
        inputs.setProperty("QuoteId", "1-1028K");//1-1028K//1-1026S//1-1025Q
        inputs.setProperty("QuoteNum", "Cool Quote");
        eia.doInvokeMethod("generateExcelDoc", inputs, outputs);
    }
    
    @Override
    public void doInvokeMethod(String MethodName, SiebelPropertySet inputs, SiebelPropertySet outputs) throws SiebelBusinessServiceException {
        if(MethodName.equalsIgnoreCase("GenerateExcelDoc"))
        {
            try 
            {
                //
                IProperties AP = new ApplicationProperties();
                
                //Get excel path
                inputFile = AP.setProperties(IProperties.NIX_INPUT_KEY, IProperties.WIN_INPUT_KEY).getProperty() + XGenerator.getExcelExt();
                
                //Read Excel document first
                input_document = new FileInputStream(new File(inputFile));
                // Convert it into a POI object
                Workbook my_xlsx_workbook = WorkbookFactory.create(input_document);
                // Read excel sheet that needs to be updated
                Sheet my_worksheet = my_xlsx_workbook.getSheet("Estimate");
                // Declare a Cell object
                this.quote_id = inputs.getProperty("QuoteId");
                this.quote_number = inputs.getProperty("QuoteNum");
                CustomerRecord customerInfo = new CustomerRecord(my_xlsx_workbook, my_worksheet, 6);
                customerInfo.setQuoteId(this.quote_id);
                customerInfo.createCellFromList(new QCustomer(), new ContactKey());
                customerInfo.setNextRow(0);


                InvoiceExcel labour, parts, lubricant, expenses;

                int startRowAt = 17, nextRowAt = 0;
                labour = parts = lubricant = expenses = new InvoiceExcel(my_xlsx_workbook, my_worksheet);

                //
                labour.setQuoteId(this.quote_id);
                labour.setStartRow(startRowAt);
                labour.createCellFromList(new QLabour(), new ProductKey());
                XGenerator.doMerge(my_worksheet, labour.next(6) - 2, 0, 1, 10, false);
                XGenerator.doMerge(my_worksheet, labour.next(6) - 1, 2, 1, 5, false);
                // 
                parts.setQuoteId(this.quote_id);
                parts.setStartRow(labour.next(6));
                //System.out.println(labour.next(6));
                parts.createCellFromList(new QParts(), new ProductKey());
                XGenerator.doMerge(my_worksheet, parts.next(4) - 2, 0, 1, 10, false);
                XGenerator.doMerge(my_worksheet, parts.next(4) - 1, 2, 1, 5, false);
                // Creates the lubricant row in excel sheet
                lubricant.setQuoteId(this.quote_id);
                lubricant.setStartRow(parts.next(4));
                lubricant.createCellFromList(new QLubricant(), new ProductKey());
                //System.out.println(parts.next(5));
                XGenerator.doMerge(my_worksheet, lubricant.next(4) - 2, 0, 1, 10, false);
                XGenerator.doMerge(my_worksheet, lubricant.next(4) - 1, 2, 1, 5, false);
                // 
                expenses.setQuoteId(this.quote_id);
                expenses.setStartRow(lubricant.next(4));
                expenses.createCellFromList(new QExpenses(), new ProductKey());
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
                XGenerator.doCreateBook(my_xlsx_workbook, "weststar_" + this.quote_number.replace(" ", "_"));
                Attachment a = new Attachment("Quote", "Quote Attachment");
                String filepath = XGenerator.getProperty("filepath");
                String filename = XGenerator.getProperty("filename");
                
                //Attach the file to siebel
                a.Attach(
                    filepath, 
                    filename, 
                    Boolean.FALSE, 
                    quote_id
                );
                
                outputs.setProperty("QuoteId", quote_id);
                outputs.setProperty("filepath", filepath);
                outputs.setProperty("filename", filename);
                my_xlsx_workbook.close();
                System.out.println("Done");
            } 
            catch (FileNotFoundException ex) 
            {
                ex.printStackTrace(new PrintWriter(error_txt));
                MyLogging.log(Level.SEVERE, "Caught File Not Found Exception: " + error_txt.toString());
            } 
            catch (IOException ex) 
            {
                ex.printStackTrace(new PrintWriter(error_txt));
                MyLogging.log(Level.SEVERE, "Caught IO Exception: " + ex.getMessage());
            } 
            catch (InvalidFormatException ex) 
            {
                ex.printStackTrace(new PrintWriter(error_txt));
                MyLogging.log(Level.SEVERE, "Caught Invalid Format Exception: " + ex.getMessage());
            } 
            catch (EncryptedDocumentException ex) 
            {
                ex.printStackTrace(new PrintWriter(error_txt));
                MyLogging.log(Level.SEVERE, "Caught Encrypted Document Exception: " + ex.getMessage());
            } 
            catch (Exception ex) 
            {
                ex.printStackTrace(new PrintWriter(error_txt));
                MyLogging.log(Level.SEVERE, "Caught Exception: " + error_txt.toString());
            }
        }
    }
}