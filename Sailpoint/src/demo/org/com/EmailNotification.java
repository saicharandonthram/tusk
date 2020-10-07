package demo.org.com;

import sailpoint.api.SailPointContext;
import sailpoint.api.SailPointFactory;
import sailpoint.object.EmailOptions;
import sailpoint.object.EmailTemplate;
import sailpoint.object.Identity;
import sailpoint.spring.SpringStarter;
import sailpoint.tools.EmailException;
import sailpoint.tools.GeneralException;

public class EmailNotification 
{
	public static void main(String[] args) throws EmailException, GeneralException 
	{
		
		SpringStarter starter = new SpringStarter("iiqBeans");
		starter.start();
		SailPointContext context = SailPointFactory.createContext();
		String identityName = "gold berg";
		Identity identity = context.getObjectByName(Identity.class, identityName);
		               
		identity.setPassword("Passw0rd1@");
		context.saveObject(identity); 
		context.commitTransaction();
		EmailOptions options = new EmailOptions();
		String email = identity.getName()+"@demo.com";
		options.setTo(email);
		System.out.println(email);
		EmailTemplate template = null;
		try 
		{
			template = context.getObject(EmailTemplate.class, "LCM User Notification");
		    
		}
		
		catch (GeneralException e)
		{
			e.printStackTrace();
		}
		template.setBody("An new user has been Created.\r\n" +  "\r\n" +  " Application: IdentityIQ\r\n" + " Account : "+identity.getName()+"\r\n" + "PSW: "+context.decrypt(identity.getPassword()));
		context.sendEmailNotification(template, options);	
		starter.close();
	}
}
