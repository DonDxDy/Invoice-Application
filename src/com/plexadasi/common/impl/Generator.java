/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plexadasi.common.impl;

import com.siebel.data.SiebelPropertySet;
import com.siebel.eai.SiebelBusinessServiceException;


/**
 *
 * @author SAP Training
 */
public interface Generator {

    /**
     *
     * @param inputs the value of inputs
     * @param outputs the value of outputs
     * @throws com.siebel.eai.SiebelBusinessServiceException
     */
    public void generateExcelDoc(SiebelPropertySet inputs, SiebelPropertySet outputs) throws SiebelBusinessServiceException;
}
