package com.plexadasi.SiebelApplication;


import com.siebel.eai.SiebelBusinessServiceException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import java.util.logging.Level;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SAP Training
 */
public class ApplicationProperties implements IProperties{
       
    private static String propfilepath = "";
    private static String templateFile = "";    
    private static String stringOutput = "";
    private static final StringWriter ERROR = new StringWriter(); 
    private static final String OS = System.getProperty("os.name").toLowerCase();
    
    @Override
    public IProperties setProperties(String nix, String win)
    {
        if (OS.contains("nix") || OS.contains("nux")) 
        {                
            propfilepath = "/usr/app/siebel/intg/intg.properties";
            templateFile = nix;
        } 
        else if (OS.contains("win")) 
        {
            propfilepath = "C:\\temp\\intg\\intg.properties";
            templateFile = win;
        }
        else
        {
            throw new NullPointerException("This operating system is not supported.");
        }
        return this;
    }
    
    @Override
    public String getProperty() throws SiebelBusinessServiceException
    {
        FileInputStream input = null;
        try 
        {
            Properties prop = new Properties();
            input = new FileInputStream(propfilepath);
            prop.load(input);
            stringOutput = prop.getProperty(templateFile);
        } 
        catch (FileNotFoundException ex) 
        {
            ex.printStackTrace(new PrintWriter(ERROR));
            MyLogging.log(Level.SEVERE, "Caught FileNotFoundExceptionn:" + ERROR.toString());
            throw new SiebelBusinessServiceException("FILE_NOT_FOUND_EXCEPT", "Template could not be found or does not exist. Please ask your administratr for more information.");
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace(new PrintWriter(ERROR));
            MyLogging.log(Level.SEVERE, "Caught IOException:" + ERROR.toString());
            throw new SiebelBusinessServiceException("CAUGHT_EXCEPT", ex.getMessage());
        } 
        catch(NullPointerException ex)
        {
            ex.printStackTrace(new PrintWriter(ERROR));
            MyLogging.log(Level.SEVERE, "Caught IOException:" + ERROR.toString());
            throw new SiebelBusinessServiceException("NULL_POINTER_EXCEPT", ex.getMessage());
        }
        finally 
        {
            try 
            {
                input.close();
            } 
            catch (IOException ex) 
            {
                ex.printStackTrace(new PrintWriter(ERROR));
                MyLogging.log(Level.SEVERE, "Caught IOException:" + ERROR.toString());
                throw new SiebelBusinessServiceException("FILE_NOT_CLOSED_EXCEPT", ex.getMessage());
            }
        }
        return stringOutput;
    }
}
