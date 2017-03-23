package SiebelApplication;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

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
    private static InetAddress ip = null;
    private static String hIP = "";
    private static String vlogFile = null;
    private static String templateFile = null;    
    private static final String OS = System.getProperty("os.name").toLowerCase();
    public static String invoice_template ; 
    
    /**
     *
     * @throws UnknownHostException
     */
    public ApplicationProperties() throws UnknownHostException
    {
        ip = InetAddress.getLocalHost();
        hIP = ip.getHostAddress();
    }
    
    @Override
    public IProperties setProperties(String nix, String win)
    {
        if (OS.contains("nix") || OS.contains("nux")) 
        {                
            vlogFile = "nix_logfile";
            templateFile = nix;
        } 
        else if (OS.contains("win")) 
        {
            propfilepath = "C:\\temp\\intg\\intg.properties";
            vlogFile = "win_logfile";
            templateFile = win;
        }
        return this;
    }
    
    @Override
    public String getProperty() throws FileNotFoundException, IOException{
        Properties prop = new Properties();
        FileInputStream input = new FileInputStream(propfilepath);
        prop.load(input);
        return prop.getProperty(templateFile);
    }
}
