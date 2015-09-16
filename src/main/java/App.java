public class App {

	public static void main(String[] args) {

		String searchRoot = "cn=admin,dc=example,dc=com";
		String searchFilter = "objectClass=posixGroup";
		String outputAttribute = "cn";
		
		String addRoot = "cn=admin,dc=example,dc=com";
		String newNodeName = "New-Team";
		String [] Clases = {"top", "posixGroup"};
		String [] Attributes = {"gidNumber"};
		String [] AttributeValues = {"5000"};
		
		

		LdapConnection.getInstance().connect();

		LdapConnection.getInstance().search(searchRoot, searchFilter, outputAttribute);
		LdapConnection.getInstance().Add(newNodeName, addRoot, Clases, Attributes, AttributeValues);
		LdapConnection.getInstance().search(searchRoot, searchFilter, outputAttribute);

		LdapConnection.getInstance().disconnect();
	}

}
