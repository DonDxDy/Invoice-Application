
import com.siebel.data.SiebelPropertySet;
import common.impl.Generator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SAP Training
 */
public class Context {
    
    public static void callMethod(Generator call, SiebelPropertySet inputs, SiebelPropertySet outputs)
    {
        call.generateExcelDoc(inputs, outputs);
    }
}
