package demo.org.com;

import java.util.Iterator;
import java.util.List;

import sailpoint.api.SailPointContext;
import sailpoint.api.SailPointFactory;
import sailpoint.object.Filter;
import sailpoint.object.Identity;
import sailpoint.object.IdentityEntitlement;
import sailpoint.object.Link;
import sailpoint.object.ProvisioningPlan;
import sailpoint.object.ProvisioningPlan.AttributeRequest;
import sailpoint.object.QueryOptions;
import sailpoint.object.Source;
import sailpoint.spring.SpringStarter;

public class RemovingEntitlements {

	public static void main(String args[]) {

		try {

			SpringStarter starter = new SpringStarter("iiqBeans");
			starter.start();
			SailPointContext context = SailPointFactory.createContext();
			String identityName = "randy orton";
			Identity identity = context.getObjectByName(Identity.class, identityName);

			ProvisioningPlan plan = new ProvisioningPlan();

			List<Link> linksAccount = identity.getLinks();
			Iterator<Link> itrlinksAccount = linksAccount.iterator();
			while (itrlinksAccount.hasNext()) {
				Link account = (Link) itrlinksAccount.next();
				ProvisioningPlan.AccountRequest acctReq = new ProvisioningPlan.AccountRequest();

				String appName = account.getApplicationName();
				System.out.println(appName);
				String accountName = account.getNativeIdentity();
				System.out.println(accountName);

				if (null != appName) {
					acctReq.setApplication(appName);
					acctReq.setNativeIdentity(accountName);

					acctReq.setOperation(ProvisioningPlan.AccountRequest.Operation.Disable);

					Filter filter1 = Filter.eq("application.name", appName);
					Filter filter2 = Filter.eq("identity.name", identityName);
					Filter filter = Filter.and(filter1, filter2);

					QueryOptions qo = new QueryOptions();
					qo.addFilter(filter);

					Iterator<IdentityEntitlement> it = context.search(IdentityEntitlement.class, qo);
					if (it.hasNext()) {
						while (it.hasNext()) {
							IdentityEntitlement idEntitlement = (IdentityEntitlement) it.next();

							System.out.println(idEntitlement.getName());
							System.out.println(idEntitlement.getValue());

							acctReq.add(new AttributeRequest(idEntitlement.getName(), ProvisioningPlan.Operation.Remove,
									idEntitlement.getValue()));

						}
					}
					plan.add(acctReq);
				}
			}
			plan.setIdentity(identity);
			plan.setSource(Source.Workflow);
			plan.setSourceName("leaver workflow:" + identityName);
			System.out.println(plan);

			starter.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}