/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.invoiceapplication;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 *
 * @author Adeyemi
 */
public interface IKey {
    Integer productKeyToInt(String value) throws InvalidFormatException, Exception;
}
