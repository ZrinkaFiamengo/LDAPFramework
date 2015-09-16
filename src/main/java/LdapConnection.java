import java.util.Hashtable;

import javax.naming.AuthenticationNotSupportedException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.ericsson.commonlibrary.remotecli.exceptions.AuthenticationException;

/**
 * Singleton class used to maintain ldap connection
 * @author ezrifia
 *
 */
public class LdapConnection {

	private static LdapConnection instance = null;
	private LdapContext ctx = null;
	private static Hashtable<String,String> env;
	private static Configuration config;


	/**
	 * Constructor of the class which returns existing instance of the class if there is one or  creates new one if it is not already created.
	 * Function also creates connection parameters variable.
	 * @return instance of the class LdapConnection
	 */
	public static LdapConnection getInstance() {
		if(instance == null) {
			instance = new LdapConnection();
			config = new Configuration();
			env = new Hashtable<String,String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, config.getProvider_url());
			env.put(Context.PROVIDER_URL, config.getHost());
			env.put(Context.SECURITY_AUTHENTICATION, config.getAuthentication());
			env.put(Context.SECURITY_PRINCIPAL, config.getUsername());
			env.put(Context.SECURITY_CREDENTIALS, config.getPassword());
		}
		return instance;
	}

	/**
	 * Function used for connecting to ldap server
	 */
	public void connect()
	{
		try {
			ctx = new InitialLdapContext(env, null);
			System.out.println("CONNECTED");
			System.out.println("********");
		} catch (AuthenticationNotSupportedException ex) {
			System.out.println("The authentication is not supported by the server");
			//ex.printStackTrace();
		} catch (AuthenticationException ex) {
			System.out.println("incorrect password or username");
			//ex.printStackTrace();
		} catch (NamingException ex) {
			System.out.println("error when trying to create the context");
			//ex.printStackTrace();
		}
	}

	/**
	 * Function used to disconnect from ldap server
	 */
	public void disconnect()
	{
		try{
			ctx.close();
			System.out.println("********");
			System.out.println("DISCONNECTED");
		} catch (NamingException ex) {
			System.out.println("error with the context");
			//ex.printStackTrace();
		}
	}

	/**
	 * Function used for ldap searches
	 * @param searchRoot root level of the search. All searches will be done from this point and below.
	 * @param searchFilter filter used for searching
	 * @param outputAttribute attribute of the result nodes user wants to see.
	 * If user wants to see all attributes he should set outputAtribute to null
	 */
	public void search(String searchRoot, String searchFilter, String outputAttribute)
	{
		System.out.println();
		System.out.println("Search from the root: " + searchRoot);
		System.out.println("Search filter: " + searchFilter);
		if(outputAttribute!=null)
			System.out.println("Showing in results: "+ outputAttribute);
		
		System.out.println("\nSEARCH RESULTS:");
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		NamingEnumeration<SearchResult> results = null;

		try {
			results = ctx.search(searchRoot, searchFilter, searchControls);
			SearchResult searchResult = null;

			while(results.hasMoreElements()) {
				searchResult = (SearchResult) results.nextElement();
				Attributes attr= searchResult.getAttributes();
				if(outputAttribute==null)
					System.out.println("-->" + attr);
				else
					System.out.println("-->" + attr.get(outputAttribute));
			}
			System.out.println();
		} catch (NamingException e) {
			System.out.println("error when trying to create the context");
			//e.printStackTrace();
		}
	}
	
	/**
	 * Function which adds new node to the ldap database
	 * @param nodeName name of the new node
	 * @param addRoot name of the root node in which new node will be added
	 * @param Clases clases that need to be added to the node
	 * @param Attributes attributes that need to be added to the node
	 * @param AttributesValues values of the attributes that need to be added to the node
	 */
	public void Add(String nodeName, String addRoot, String [] Clases, String [] Attributes, String [] AttributeValues)
	{
		BasicAttributes attrs = new BasicAttributes();
		Attribute classes = new BasicAttribute("objectclass");
		
		for(int i=0; i<Clases.length;i++)
			classes.add(Clases[i]);
		
        attrs.put(classes);
        
        for(int i=0; i<Attributes.length;i++)
        	attrs.put(Attributes[i], AttributeValues[i]);
        
        try {
			ctx.createSubcontext("ldap://172.17.87.89:389/cn=" + nodeName + "," + addRoot, attrs);
		} catch (NamingException e) {
			System.out.println("error when trying to create the context");
			e.printStackTrace();
		}
        System.out.println("********");
        System.out.println("NodeAdded");
        System.out.println("********");
	}
}
