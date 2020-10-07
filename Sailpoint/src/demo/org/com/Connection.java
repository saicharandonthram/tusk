package demo.org.com;

import sailpoint.api.SailPointContext;
import java.text.SimpleDateFormat;
import sailpoint.object.AuditEvent;
import sailpoint.api.SailPointFactory;
import sailpoint.connector.ExpiredPasswordException;
import sailpoint.object.Identity;
import sailpoint.object.IdentitySelector;
import sailpoint.object.IdentityTrigger;
import sailpoint.object.QueryOptions;
import sailpoint.object.Rule;
import sailpoint.spring.SpringStarter;
import sailpoint.tools.GeneralException;

import java.util.*;

public class Connection 
{
	


	
	 public static SailPointContext context;
	 /**
	  * @param args
	  * @throws GeneralException 
	  * @throws ExpiredPasswordException 
	  */
	 public static void main(String[] args) throws GeneralException {
	  //IIQ propeties file must be present in java project.
	  SpringStarter starter = new SpringStarter("iiqBeans"); 
	  starter.start();
	  SailPointContext context = SailPointFactory.createContext();
	  Identity identity = context.getObject(Identity.class, "spadmin");
	  String displayName=identity.getDisplayName();
	       System.out.println("Identity Details " + identity.getFirstname() + identity.getLastname());
	       starter.close();

	 
	}
}
