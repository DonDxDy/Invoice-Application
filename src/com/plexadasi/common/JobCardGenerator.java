package com.plexadasi.common;


import com.plexadasi.Helper.HelperAP;
import com.plexadasi.SiebelApplication.ApplicationsConnection;
import com.plexadasi.SiebelApplication.MyLogging;
import com.plexadasi.SiebelApplication.object.JCard;
import com.plexadasi.SiebelApplication.object.Job;
import com.plexadasi.SiebelApplication.object.OParts;
import com.plexadasi.common.element.Attachment;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelPropertySet;
import com.plexadasi.common.element.InvoiceExcel;
import com.plexadasi.common.element.JobCardAttachment;
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
public class JobCardGenerator implements Generator{
    private String inputFile = "";
    
    private String job_id;
    
    private String job_number;
    
    private final StringWriter error_txt = new StringWriter();
    
    private FileInputStream input_document;
    private String account_id;

    public JobCardGenerator() {
        this.job_number = "";
        this.input_document = null;
    }
    
    /**
     *
     * @param inputs the value of inputs
     * @param outputs the value of outputs
     */
    @Override
    public void generateExcelDoc(SiebelPropertySet inputs, SiebelPropertySet outputs)
    {
        try {
            //
            //IProperties AP = new ApplicationProperties();
            SiebelDataBean conn = ApplicationsConnection.connectSiebelServer();
            //Get excel path
            inputFile = HelperAP.getJobCardTemplate();
            //Read Excel document first
            MyLogging.log(Level.INFO, inputFile);
            input_document = new FileInputStream(new File(inputFile));
            // Convert it into a POI object
            Workbook my_xlsx_workbook = WorkbookFactory.create(input_document);
            // Read excel sheet that needs to be updated
            Sheet my_worksheet = my_xlsx_workbook.getSheet("Sheet1");
            // Declare a Cell object
            this.job_id = inputs.getProperty("JobId");
            this.job_number = inputs.getProperty("JobNum");
            this.account_id = inputs.getProperty("AccId");
            
            InvoiceExcel jobCardInfo = new InvoiceExcel(my_xlsx_workbook, my_worksheet, 5);
            //jobCardInfo.setJobId(this.account_id);
            //jobCardInfo.createCellFromList(new JAccount(conn), new ContactKey());
            JCard jCard = new JCard(conn);
            jobCardInfo.setJobId(this.job_id);
            jobCardInfo.setStartRow(8);
            jobCardInfo.createCellFromList(jCard, new ContactKey());
            jobCardInfo.setJobId(this.job_id);
            jobCardInfo.setStartRow(jobCardInfo.next(4));
            jobCardInfo.createCellFromList(new Job(conn), new ProductKey());
           
            
            
            InvoiceExcel parts;
            
            int startRowAt = 17;
            parts = new InvoiceExcel(my_xlsx_workbook, my_worksheet);
            
            //
            parts.setStartRow(startRowAt);
            parts.setJobId(job_id);
            parts.createCellFromList(new OParts(conn), new ProductKey());
            my_xlsx_workbook.setForceFormulaRecalculation(true);
            input_document.close();
            XGenerator.doCreateBook(my_xlsx_workbook, "weststar_" + this.job_number.replace(" ", "_"));
            String filepath = XGenerator.getProperty("filepath");
            String filename = XGenerator.getProperty("filename");
            
            Attachment a = new JobCardAttachment(conn, job_id);
            //Attach the file to siebel
            
            a.Attach(
                filepath,
                filename,
                Boolean.FALSE
            );
            
            boolean logoff = conn.logoff();
            my_xlsx_workbook.close();
            System.out.println("Done");
            outputs.setProperty("status", "success");
        } 
        catch (FileNotFoundException ex) 
        {
            ex.printStackTrace(new PrintWriter(error_txt));
            MyLogging.log(Level.SEVERE, "Caught File Not Found Exception: " + ex.getMessage() + error_txt.toString());
            outputs.setProperty("status", "failed");
            outputs.setProperty("error_message", error_txt.toString());
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace(new PrintWriter(error_txt));
            MyLogging.log(Level.SEVERE, "Caught IO Exception: " + ex.getMessage() + error_txt.toString());
            outputs.setProperty("status", "failed");
            outputs.setProperty("error_message", error_txt.toString());
        } 
        catch (InvalidFormatException ex) 
        {
            ex.printStackTrace(new PrintWriter(error_txt));
            MyLogging.log(Level.SEVERE, "Caught Invalid Format Exception: " + ex.getMessage() + error_txt.toString());
            outputs.setProperty("status", "failed");
            outputs.setProperty("error_message", error_txt.toString());
        } 
        catch (EncryptedDocumentException ex) 
        {
            ex.printStackTrace(new PrintWriter(error_txt));
            MyLogging.log(Level.SEVERE, "Caught Encrypted Document Exception: " + ex.getMessage() + error_txt.toString());
            outputs.setProperty("status", "failed");
            outputs.setProperty("error_message", error_txt.toString());
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace(new PrintWriter(error_txt));
            MyLogging.log(Level.SEVERE, "Caught Exception: " + ex.getMessage() + error_txt.toString());
            outputs.setProperty("status", "failed");
            outputs.setProperty("error_message", error_txt.toString());
        }
    }
}
