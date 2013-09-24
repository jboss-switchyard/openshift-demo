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
public class QualifyTest {

	@ServiceOperation("QualificationService")
	private Invoker service;

	@Test
    public void lowCredit() throws Exception {
        
        // Build the request message
        Applicant applicant = new Applicant();
        applicant.setName("Peter Gibbons");
        applicant.setCreditScore(400);
        
        // Invoke the service
        Applicant reply = service.operation("process")
        		.sendInOut(applicant).getContent(Applicant.class);

        // validate the results
        Assert.assertTrue(!reply.isApproved());
    }
	
	@Test
    public void goodCredit() throws Exception {
        
        // Build the request message
        Applicant applicant = new Applicant();
        applicant.setName("Bill Lumbergh");
        applicant.setCreditScore(700);
        
        // Invoke the service
        service.operation("process").sendInOut(applicant);

        // validate the results
        Assert.assertTrue(applicant.isApproved());
    }

}
