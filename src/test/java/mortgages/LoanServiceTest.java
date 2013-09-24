package mortgages;

import mortgages.Applicant;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.component.test.mixins.cdi.CDIMixIn;
import org.switchyard.test.Invoker;
import org.switchyard.test.MockHandler;
import org.switchyard.test.ServiceOperation;
import org.switchyard.test.SwitchYardRunner;
import org.switchyard.test.SwitchYardTestCaseConfig;
import org.switchyard.test.SwitchYardTestKit;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(mixins = CDIMixIn.class, config = SwitchYardTestCaseConfig.SWITCHYARD_XML, exclude = "jms")
public class LoanServiceTest {

    @ServiceOperation("LoanProcessing")
    private Invoker service;

    private SwitchYardTestKit testKit;
    
    @Test
    public void testCreditAndQualificationCalled() throws Exception {
        // Build the request message
        Applicant request = new Applicant();
        request.setName("Mike Mock");
        request.setAge(11);
        
        // Mock our service references
        MockHandler credit = testKit.replaceService("CreditService").forwardInToOut();
        MockHandler qualification = testKit.replaceService("QualificationService").forwardInToOut();
        
        // Invoke the routing service
        service.operation("process").sendInOut(request);
        
        // Verify that each service reference has been invoked by the route
        Assert.assertEquals(1, credit.getMessages().size());
        Assert.assertEquals(1, qualification.getMessages().size());
    }

    @Test
    public void testDeniedTooYoung() throws Exception {
        
        // Build the request message
        Applicant request = new Applicant();
        request.setName("Bill Lumbergh");
        request.setAge(8);
        
        // Invoke the service
        Applicant reply = service.operation("process").sendInOut(request).getContent(Applicant.class);

        // validate the results
        Assert.assertNotSame(0, reply.getCreditScore());
        Assert.assertFalse(reply.isApproved());
    }
    
    @Test
    public void testApproved() throws Exception {
        
        // Build the request message
        Applicant applicant = new Applicant();
        applicant.setName("Peter Gibbons");
        applicant.setAge(32);
        
        // Invoke the service
        service.operation("process").sendInOut(applicant);

        // validate the results
        Assert.assertTrue(applicant.isApproved());
    }

}
