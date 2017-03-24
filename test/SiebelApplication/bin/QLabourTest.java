/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SiebelApplication.bin;

import com.siebel.data.SiebelBusComp;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Adeyemi
 */
public class QLabourTest {
    
    public QLabourTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getQuoteItems method, of class QLabour.
     */
    @Test
    public void testGetQuoteItems() throws Exception {
        System.out.println("getQuoteItems");
        String quote_id = "";
        QLabour instance = null;
        List<Map<String, String>> expResult = null;
        List<Map<String, String>> result = instance.getQuoteItems(quote_id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchSpec method, of class QLabour.
     */
    @Test
    public void testSearchSpec() throws Exception {
        System.out.println("searchSpec");
        SiebelBusComp sbBC = null;
        QLabour instance = null;
        instance.searchSpec(sbBC);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getExtraParam method, of class QLabour.
     */
    @Test
    public void testGetExtraParam() {
        System.out.println("getExtraParam");
        SiebelBusComp sbBC = null;
        QLabour instance = null;
        instance.getExtraParam(sbBC);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
