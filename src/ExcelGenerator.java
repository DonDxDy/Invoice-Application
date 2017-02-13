
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SAP Training
 */
public class ExcelGenerator {
    
    
    public void writeCustomerDetailsInInvoice(String templateFile,List<Map> customerDetails) throws FileNotFoundException, IOException{        
        MyLogging.log(Level.INFO, "======= In writeCustomerDetailsInInvoice ========");
        MyLogging.log(Level.INFO, "Customer Details count:"+customerDetails.size());
        Map tmpMap = customerDetails.get(0);
        FileInputStream fis = new FileInputStream(new File(templateFile));
        XSSFWorkbook workbook = new XSSFWorkbook (fis);
        MyLogging.log(Level.INFO, "Work book initialized");
	XSSFSheet sheet = workbook.getSheetAt(0);
	XSSFRow row1 = sheet.getRow(7);
	XSSFCell cell1 = row1.getCell(2);
	cell1.setCellValue((String)tmpMap.get("Name"));
	XSSFRow row2 = sheet.getRow(8);
	XSSFCell cell2 = row2.getCell(2);
	cell2.setCellValue((String)tmpMap.get("Address"));
        XSSFRow row3 = sheet.getRow(9);
	XSSFCell cell3 = row3.getCell(2);
	cell3.setCellValue((String)tmpMap.get("Address2"));
        XSSFRow row4 = sheet.getRow(10);
	XSSFCell cell4 = row4.getCell(2);
	cell4.setCellValue((String)tmpMap.get("Main Phone Number"));
	fis.close();
	FileOutputStream fos =new FileOutputStream(new File("C:\\TEMP\\excel\\test.xlsx"));
	workbook.write(fos);
	fos.close();
	System.out.println("Done");
        
    }
    
    
    
    //write Header details
    public boolean writeHeaderDetails(String fileName){
        return true;
    }
    
    //write labour details
    public boolean writeLabourDetails(String fileName){
        return true;
    }
    
    //write parts details
    public boolean writePartsDetails(String fileName){
        return true;
    }
    
    //write lubricats and expense details
    public boolean writeLubricantsAndExpenseDetails(String fileName){
        return true;
    }
}
