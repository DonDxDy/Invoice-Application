package SiebelApplication;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
    private static InetAddress ip = null;
    private static String hIP = "";
    private static String vlogFile = null;
    private static String templateFile = null;    
    private static String inv_templateFile = null;
    private static String inv_generated_path = null;
    private static final String OS = System.getProperty("os.name").toLowerCase();
    public static final String INVOICE_TEMPLATE = getInvoiceTemplate(); 
    public static final String INVOICE_GEN_PATH = getInvoiceGenPath();
    private static String prop_file_path;
    private static StringWriter errors = new StringWriter();
    
    
    private static Properties initializePropertyValues(){        
        
        if (OS.contains("nix") || OS.contains("nux")) 
        {
            prop_file_path = "/usr/app/siebel/intg/intg.properties";
            vlogFile = "nix_connect_logfile";
            inv_templateFile = IProperties.NIX_INPUT_KEY;
            inv_generated_path = IProperties.NIX_OUTPUT_KEY;
        } 
        else if (OS.contains("win")) 
        {
            prop_file_path =  "C:\\temp\\intg\\intg.properties";
            vlogFile = "win_connect_logfile";
            inv_templateFile = IProperties.WIN_INPUT_KEY;
            inv_generated_path = IProperties.WIN_OUTPUT_KEY;
        }
        Properties prop = new Properties();
        FileInputStream input;        
        try {
            input = new FileInputStream(prop_file_path);
            prop.load(input);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(new PrintWriter(errors));
            
        } catch (IOException ex) {
            ex.printStackTrace(new PrintWriter(errors));
            
        }
        MyLogging.log(Level.INFO, "Properties File: " + prop_file_path);
        MyLogging.log(Level.INFO, "Templates File: " + inv_templateFile);
        MyLogging.log(Level.INFO, "Generation File: " + inv_generated_path);
        return prop;
        /*ebs_database = prop.getProperty("ebs_database");
        ebs_dbuser = prop.getProperty("ebs_dbuser");
        ebs_dbpassword = prop.getProperty("ebs_dbpassword");
        entrpr_name = prop.getProperty("entrpr_name");
        gateway_server = prop.getProperty("gateway_server");
        username = prop.getProperty("username");
        password = prop.getProperty("password");
        sieb_database = prop.getProperty("sieb_database");
        sieb_username = prop.getProperty("sieb_dbuser");
        sieb_password = prop.getProperty("sieb_dbpassword");
        LOG.log(Level.INFO, "Values are entrpr_name:{0},gateway_server:{1},username:{2},password{3}"+entrpr_name+""+ gateway_server+""+username+""+password);
        LOG.log(Level.INFO, "Siebel database:{0},sieb_username:{1},sieb_password:{2}"+ sieb_database+""+sieb_username+""+sieb_password);
        logFile = prop.getProperty(vlogFile);
        LOG.log(Level.INFO, "EBS database:{0},username:{1},password{2}"+ebs_database+""+ ebs_dbuser+""+ebs_dbpassword);*/
    }
    
    private static String getInvoiceTemplate()
    {
        Properties prop = initializePropertyValues();
        return prop.getProperty(inv_templateFile);
    }
    
    private static String getInvoiceGenPath()
    {
        Properties prop = initializePropertyValues();
        return prop.getProperty(inv_generated_path);
    }
    
    public static void main(String[] args) {
        initializePropertyValues();
        System.out.println(ApplicationProperties.prop_file_path);
        System.out.println(ApplicationProperties.inv_templateFile);
        System.out.println(ApplicationProperties.inv_generated_path);
    }
}
