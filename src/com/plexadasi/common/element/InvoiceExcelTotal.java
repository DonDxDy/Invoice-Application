/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.common.element;

import com.plexadasi.invoiceapplication.IKey;
import com.plexadasi.invoiceapplication.ProductKey;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import java.util.List;
import java.util.Map;


/**
 *
 * @author hp
 */
public class InvoiceExcelTotal extends AExcel{
    private final char lastColumn;
    private String total;
    
    public InvoiceExcelTotal()
    {
        super();
        this.lastColumn = ((char)'A' + 9);
    }

    public InvoiceExcelTotal(Workbook book, Sheet sheet) {
        super(book, sheet);
        this.lastColumn = ((char)'A' + 9);
    }
    
    public InvoiceExcelTotal(Workbook book, Sheet sheet, int startRow) {
        super(book, sheet, startRow);
        this.lastColumn = ((char)'A' + 9);
    }
    /**
     *
     * @param book
     * @param sheet
     * @param qM
     * @param iKey
     * @throws java.lang.Exception
     */ 
    @Override
    public void createCellFromList( Impl qM, IKey iKey, Workbook book, Sheet sheet) throws Exception
    {
        workbook = book;
        worksheet = sheet;
        createCell(qM.getItems(quote_id), iKey);
        Total(iKey);
    }
    
    @Override
    public void createCellFromList(Impl qM, IKey iKey) throws Exception
    {
        createCell(qM.getItems(quote_id), iKey);
        Total(iKey);
    }
    
    public void createCellFromList(List<Map<String, String>> qM, IKey iKey) throws Exception
    {
        createCell(qM, iKey);
        Total(iKey);
    }
    
    public String getTotal()
    {
        return this.total;
    }
    
    public void Total(IKey iKey)
    {
        if(rowCount % 1 == 0)
        {
            
        }
        int sumFirstRow = (firstRow), sumLastRow = firstRow + (rowCount) + 1;
        CellReference cr = new CellReference(lastColumn + String.valueOf(sumLastRow));
        if(iKey instanceof ProductKey){
            this.sheetrow = worksheet.getRow(cr.getRow());
            this.sheetcell = this.sheetrow.getCell(cr.getCol());
                XGenerator.doMerge(worksheet, sumLastRow - 1, 7, 1, 2, false);
            if(sumFirstRow != sumLastRow){
                this.total = "SUM(SUM("+lastColumn+sumFirstRow+":"+lastColumn+(sumLastRow-1)+")+0.00)";
            }else{
                this.total = "SUM(0.00)";
            }
            this.sheetcell.setCellFormula(this.total);
        }
    }
}   
