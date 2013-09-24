package mortgages.client;

import org.switchyard.component.test.mixins.http.HTTPMixIn;

public class PreQualSOAPClient {
	
	private static final String URL = "http://[app-id].rhcloud.com/mortgages/LoanProcessing";
	private static final String XML = "src/test/resources/xml/loan-request-normal.xml";

    
    public static void main(final String[] args) throws Exception {

        HTTPMixIn soapMixIn = new HTTPMixIn();
        soapMixIn.initialize();

        try {
        	soapMixIn.setDumpMessages(true);
    		String reply = soapMixIn.postFile(URL, XML);
    		System.out.println(
    				"\n========= Reply From Service =========="
    				+ "\n" + reply + "\n"
    				+ "=======================================\n");

        } finally {
            soapMixIn.uninitialize();
        }
    }
}
