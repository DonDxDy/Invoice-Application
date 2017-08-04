/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.common.element;

import com.plexadasi.invoiceapplication.IKey;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.plexadasi.SiebelApplication.object.Impl.Impl;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hp
 */
public class InvoiceExcel  extends AExcel{
    
    public InvoiceExcel()
    {
        this.firstRow = 0;
        this.nextRow = 0;
    }

    public InvoiceExcel(Workbook book, Sheet sheet) {
        super(book, sheet);
    }
    
    public InvoiceExcel(Workbook book, Sheet sheet, int startRow)
    {
        super(book, sheet, startRow);
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
    }
    
    @Override
    public void createCellFromList(Impl qM, IKey iKey) throws Exception
    {
        createCell(qM.getItems(quote_id), iKey);
    }
    
    public void createCellFromList(List<Map<String, String>> qM, IKey iKey) throws Exception
    {
        createCell(qM, iKey);
    }
}