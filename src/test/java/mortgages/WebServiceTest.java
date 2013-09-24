
package mortgages;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.test.SwitchYardRunner;
import org.switchyard.test.SwitchYardTestCaseConfig;
import org.switchyard.component.test.mixins.cdi.CDIMixIn;
import org.switchyard.component.test.mixins.http.HTTPMixIn;
import org.switchyard.transform.config.model.TransformSwitchYardScanner;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(
        config = SwitchYardTestCaseConfig.SWITCHYARD_XML,
        scanners = TransformSwitchYardScanner.class,
        mixins = {CDIMixIn.class, HTTPMixIn.class},
        exclude = "jms")
public class WebServiceTest {

    private HTTPMixIn httpMixIn;

    @Test
    public void applyForLoan() throws Exception {
        
    	httpMixIn.setDumpMessages(true);
        httpMixIn.postResourceAndTestXML(
                "http://localhost:18080/mortgages/LoanProcessing", 
                "/xml/loan-request-normal.xml",
                "/xml/loan-reply-normal.xml");
    }
}

