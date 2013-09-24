package mortgages;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.component.test.mixins.cdi.CDIMixIn;
import org.switchyard.test.Invoker;
import org.switchyard.test.ServiceOperation;
import org.switchyard.test.SwitchYardRunner;
import org.switchyard.test.SwitchYardTestCaseConfig;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(mixins = CDIMixIn.class, config = SwitchYardTestCaseConfig.SWITCHYARD_XML)
public class CreditTest {

	@ServiceOperation("CreditService")
	private Invoker service;

	@Test
    public void childrenHaveHorribleCredit() throws Exception {
        // Build the request message
        Applicant applicant = new Applicant();
        applicant.setName("Peter Gibbons");
        applicant.setAge(8);
        
        // Invoke the service
        service.operation("process").sendInOut(applicant);

        // validate the results
        Assert.assertEquals(160, applicant.getCreditScore());
    }

	@Test
    public void adultsAreResponsible() throws Exception {
        
		// Build the request message
        Applicant request = new Applicant();
        request.setName("Bill Lumbergh");
        request.setAge(39);
        
        // Invoke the service
        Applicant reply = service.operation("process").sendInOut(request).getContent(Applicant.class);

        // validate the results
        Assert.assertNotSame(780, reply.getCreditScore());
    }
}
