
import com.plexadasi.connect.ApplicationsConnection;
import com.plexadasi.common.element.JobCardAttachment;
import com.siebel.data.SiebelException;
import com.siebel.eai.SiebelBusinessServiceException;
import java.io.IOException;

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
        JobCardAttachment j = new JobCardAttachment(ApplicationsConnection.connectSiebelServer(), "");
    }
}
