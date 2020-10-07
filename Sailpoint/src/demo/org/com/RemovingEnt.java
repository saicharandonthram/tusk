package demo.org.com;

import sailpoint.api.IdentityService;
import sailpoint.api.SailPointContext;
import sailpoint.api.SailPointFactory;
import sailpoint.object.ProvisioningPlan;
import sailpoint.object.ProvisioningPlan.AccountRequest;
import sailpoint.object.EntitlementGroup;
import sailpoint.object.Identity;
import sailpoint.object.ProvisioningPlan.AttributeRequest;
import sailpoint.spring.SpringStarter;
import sailpoint.tools.GeneralException;
 

public class RemovingEnt
{
	public static ProvisioningPlan main(String[] args) throws GeneralException
	{
		
		ProvisioningPlan plan = new ProvisioningPlan();
		
		
		
		SpringStarter starter = new SpringStarter("iiqBeans");
		starter.start();
		SailPointContext context = SailPointFactory.createContext();
		String identityName = "ramesh yadav";
		Identity identity = context.getObjectByName(Identity.class, identityName);

        
		if (identity != null) 
		{

            for (EntitlementGroup entGroup : identity.getExceptions()) 
            {
                AccountRequest acctReq = new AccountRequest(AccountRequest.Operation.Modify,
                entGroup.getApplicationName(), null, entGroup.getNativeIdentity());
                for (String attribute : entGroup.getAttributeNames())
                {
                    AttributeRequest attReq = new AttributeRequest(attribute, ProvisioningPlan.Operation.Remove,
                            entGroup.getAttributes().get(attribute));
                    acctReq.add(attReq);
                }
                plan.add(acctReq);
            }
            //log.error("Removed User Entitlements is : "+plan.toXml());
            return plan;
        } else {
            return null;
        }
		starter.close();
		
	}
}
