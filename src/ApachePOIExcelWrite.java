/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author gbege
 */
public class ApachePOIExcelWrite {
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
         //Read Excel document first
                FileInputStream input_document = new FileInputStream(new File("C:\\libs\\atemplate.xlsx"));
                // convert it into a POI object
                XSSFWorkbook my_xlsx_workbook = new XSSFWorkbook(input_document); 
                // Read excel sheet that needs to be updated
                XSSFSheet my_worksheet = my_xlsx_workbook.getSheet("Estimate");
                // declare a Cell object
                XSSFCell cell = null; 
                XSSFCellStyle snCellStyle = my_xlsx_workbook.createCellStyle();
                XSSFCellStyle descriptionCellStyle = my_xlsx_workbook.createCellStyle();
                XSSFCellStyle labourRateCellStyle = my_xlsx_workbook.createCellStyle();
                XSSFCellStyle hourlyRateCellStyle = my_xlsx_workbook.createCellStyle();               
                XSSFCellStyle amountRateCellStyle = my_xlsx_workbook.createCellStyle();
                XSSFCellStyle computerProgrammingCellStyle = my_xlsx_workbook.createCellStyle();
                // Access the cell first to update the value
                CellReference cr = new CellReference("D7");
                XSSFRow sheetrow = my_worksheet.getRow(cr.getRow());               
                cell = sheetrow.getCell(cr.getCol());
                
                // Get current value and reduce 5 from it
                cell.setCellValue("Foo");
                cr = new CellReference("D8");
                sheetrow = my_worksheet.getRow(cr.getRow());
                cell = sheetrow.getCell(cr.getCol());  
                
                cell.setCellValue("4 Lagbaja Road");
                cr = new CellReference("D9");
                sheetrow = my_worksheet.getRow(cr.getRow());
                cell = sheetrow.getCell(cr.getCol());  
                cell.setCellValue("Ogudu, Lagos");
                cr = new CellReference("D10");
                sheetrow = my_worksheet.getRow(cr.getRow());
                cell = sheetrow.getCell(cr.getCol());  
                cell.setCellValue("+2348081234321");
                
                List<POILabour> labourList = new ArrayList();
                POILabour labour = new POILabour();
                labour.setSN("1");
                labour.setDescription("Brake Pad Change");
                labour.setHourlyRate("5000");
                labour.setLabourRate("2");
                labour.setAmount("10000");
                
                labourList.add(labour);
                
                labour = new POILabour();
                labour.setSN("2");
                labour.setDescription("Car Engine Servicing");
                labour.setHourlyRate("10000");
                labour.setLabourRate("6");
                labour.setAmount("60000");
                labourList.add(labour);
                
                
                labour = new POILabour();
                labour.setSN("3");
                labour.setDescription("Servicing");
                labour.setHourlyRate("2000");
                labour.setLabourRate("6");
                labour.setAmount("18000");
                labourList.add(labour);
                
                labour.setSN("4");
                labour.setDescription("Brake Pad Change");
                labour.setHourlyRate("5000");
                labour.setLabourRate("2");
                labour.setAmount("10000");
                
                labourList.add(labour);
                
                labour = new POILabour();
                labour.setSN("5");
                labour.setDescription("Car Engine Servicing");
                labour.setHourlyRate("10000");
                labour.setLabourRate("6");
                labour.setAmount("60000");
                labourList.add(labour);
                
                
                labour = new POILabour();
                labour.setSN("6");
                labour.setDescription("Servicing");
                labour.setHourlyRate("2000");
                labour.setLabourRate("6");
                labour.setAmount("18000");
                labourList.add(labour);
                labour = new POILabour();
                labour.setSN("6");
                labour.setDescription("Servicing");
                labour.setHourlyRate("2000");
                labour.setLabourRate("6");
                labour.setAmount("18000");
                labourList.add(labour);
                labour = new POILabour();
                labour.setSN("6");
                labour.setDescription("Servicing");
                labour.setHourlyRate("2000");
                labour.setLabourRate("6");
                labour.setAmount("18000");
                labourList.add(labour);
                int initialRow = 18;
                int labourRow = 18; 
                int cnt = labourList.size();
                
                for (int i = 0; i < cnt; i++) {
                    POILabour tempLabour = labourList.get(i);
                    if(labourRow == 18){                                            
                        cr = new CellReference("A"+String.valueOf(labourRow));
                        sheetrow = my_worksheet.getRow(cr.getRow());
                        cell = sheetrow.getCell(cr.getCol());  
                        snCellStyle.cloneStyleFrom(cell.getCellStyle());
                        cell.setCellValue(tempLabour.getSN());
                        cr = new CellReference("B"+String.valueOf(labourRow));
                        sheetrow = my_worksheet.getRow(cr.getRow());
                        cell = sheetrow.getCell(cr.getCol());
                        descriptionCellStyle.cloneStyleFrom(cell.getCellStyle());
                        cell.setCellValue(tempLabour.getDescription());
                        cr = new CellReference("I"+String.valueOf(labourRow));
                        sheetrow = my_worksheet.getRow(cr.getRow());
                        cell = sheetrow.getCell(cr.getCol());
                        labourRateCellStyle.cloneStyleFrom(cell.getCellStyle());
                        cell.setCellValue(tempLabour.getLabourRate());
                        cr = new CellReference("J"+String.valueOf(labourRow));
                        sheetrow = my_worksheet.getRow(cr.getRow());
                        cell = sheetrow.getCell(cr.getCol());  
                        hourlyRateCellStyle.cloneStyleFrom(cell.getCellStyle());                        
                        cell.setCellValue(tempLabour.getHourlyRate());
                        cr = new CellReference("K"+String.valueOf(labourRow));
                        sheetrow = my_worksheet.getRow(cr.getRow());
                        cell = sheetrow.getCell(cr.getCol());
                        amountRateCellStyle.cloneStyleFrom(cell.getCellStyle());
                        cell.setCellType(CellType.NUMERIC);
                        cell.setCellValue(Integer.parseInt(tempLabour.getAmount()));
                        cr = new CellReference("I21");
                        sheetrow = my_worksheet.getRow(cr.getRow());
                        cell = sheetrow.getCell(cr.getCol());
                        computerProgrammingCellStyle.cloneStyleFrom(cell.getCellStyle());
                    }else if(labourRow>18){
                        my_worksheet.shiftRows(labourRow-1, 21+cnt, 1);
                        //my_worksheet.shiftRows(labourRow-1, my_worksheet.getLastRowNum(), 1);
                        XSSFRow row = my_worksheet.createRow(labourRow-1);
                        XSSFCell newCell = row.createCell(0);                        
                        newCell.setCellStyle(snCellStyle);
                        //cr = new CellReference("A"+String.valueOf(labourRow));
                        //sheetrow = my_worksheet.getRow(cr.getRow());
                        //cell = sheetrow.getCell(cr.getCol());                         
                        newCell.setCellValue(tempLabour.getSN());
                        newCell = row.createCell(1);                        
                        newCell.setCellStyle(descriptionCellStyle);
                        newCell.setCellValue(tempLabour.getDescription());
                        XSSFCellStyle my_style = my_xlsx_workbook.createCellStyle();
                        my_style.setBorderBottom(BorderStyle.THIN);
                        for (int j = 2; j < 8; j++) {
                            newCell = row.createCell(j);                        
                            newCell.setCellStyle(my_style);
                        }
                        newCell = row.createCell(8);
                        labourRateCellStyle.setBorderLeft(BorderStyle.THIN);
                        newCell.setCellStyle(labourRateCellStyle);
                        newCell.setCellValue(tempLabour.getLabourRate());
                        newCell = row.createCell(9);                        
                        newCell.setCellStyle(hourlyRateCellStyle);
                        newCell.setCellValue(tempLabour.getHourlyRate());
                        newCell = row.createCell(10);                        
                        newCell.setCellStyle(amountRateCellStyle);
                        newCell.setCellType(CellType.NUMERIC);                        
                        newCell.setCellValue(Integer.parseInt(tempLabour.getAmount()));
                        /*cr = new CellReference("B"+String.valueOf(labourRow+1));
                        sheetrow = my_worksheet.getRow(cr.getRow());
                        cell = sheetrow.getCell(cr.getCol());  
                        cell.setCellValue(tempLabour.getDescription());
                        cr = new CellReference("I"+String.valueOf(labourRow+1));
                        sheetrow = my_worksheet.getRow(cr.getRow());
                        cell = sheetrow.getCell(cr.getCol());  
                        cell.setCellValue(tempLabour.getLabourRate());
                        cr = new CellReference("J"+String.valueOf(labourRow+1));
                        sheetrow = my_worksheet.getRow(cr.getRow());
                        cell = sheetrow.getCell(cr.getCol());  
                        cell.setCellValue(tempLabour.getHourlyRate());
                        cr = new CellReference("K"+String.valueOf(labourRow+1));
                        sheetrow = my_worksheet.getRow(cr.getRow());
                        cell = sheetrow.getCell(cr.getCol());  
                        cell.setCellValue(tempLabour.getAmount());*/
                        //my_worksheet.shiftRows(labourRow+3, 23, 1);
                        //XSSFRow arow = my_worksheet.createRow(labourRow+3);
                        //XSSFCell anewCell = arow.createCell(0);                        
                    }
                    labourRow++;
                }
                cr = new CellReference("K"+String.valueOf(labourRow));
                sheetrow = my_worksheet.getRow(cr.getRow());
                cell = sheetrow.getCell(cr.getCol());
                cell.setCellType(CellType.FORMULA);                
                labourRow = labourRow - 1;                                
                cell.setCellFormula("SUM(K18:K"+labourRow+")");
                
                
                /*my_worksheet.shiftRows(labourRow+2, 22, 1);
                XSSFRow arow = my_worksheet.createRow(labourRow+2);
                XSSFCell anewCell = arow.createCell(8); 
                anewCell.setCellStyle(computerProgrammingCellStyle);
                anewCell.setCellValue("Computer Programming");
                anewCell = arow.createCell(9);
                computerProgrammingCellStyle.setBorderRight(BorderStyle.THIN);
                anewCell.setCellStyle(computerProgrammingCellStyle); 
                XSSFCellStyle computerProgrammingCellStyle2 = my_xlsx_workbook.createCellStyle();                
                anewCell = arow.createCell(10);
                computerProgrammingCellStyle2.setBorderRight(BorderStyle.THIN);
                computerProgrammingCellStyle2.setBorderBottom(BorderStyle.THIN);
                anewCell.setCellStyle(computerProgrammingCellStyle2);
                my_worksheet.shiftRows(labourRow+3, 23, 1);
                XSSFRow brow = my_worksheet.createRow(labourRow+3);
                XSSFCell bnewCell = brow.createCell(0);*/
                
                input_document.close();                
                FileOutputStream outputStream = new FileOutputStream("C:\\libs\\atemplate_upd.xlsx");
                my_xlsx_workbook.write(outputStream);
                my_xlsx_workbook.close();
                outputStream.close();
                System.out.println("Done");
    }
    
}
 


