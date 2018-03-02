
import com.plexadasi.common.element.JobCardAttachment;
import com.plexadasi.connect.siebel.SiebelConnect;
import com.siebel.data.SiebelException;
import com.siebel.eai.SiebelBusinessServiceException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SAP Training
 */
public class JobCardAttachmentTest {
    public static void main(String[] args) throws SiebelBusinessServiceException, IOException, SiebelException 
    {
        JobCardAttachment j = new JobCardAttachment(SiebelConnect.connectSiebelServer(), "");
        
        Map<String, String> regex = new HashMap();
        String replaceAll = "";
        regex.put("[\\\\\\/]", "");
        regex.put("\\s", "-");
        String filename = "ws weststar \\ 5star / next";
        for(Map.Entry<String, String> preg : regex.entrySet())
        {
            filename = filename.replaceAll(preg.getKey(), preg.getValue());
            System.out.println(filename);
            //filename = replaceAll;
        }
    }
}
