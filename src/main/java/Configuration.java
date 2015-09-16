import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



/**
 * Class which enables connection to xml wiht connection configuration
 * @author ezrifia
 *
 */
public class Configuration {
	private String host;
	private String username;
	private String password;
	private String authentication;
	private String provider_url;



	/**
	 * Constructor of the class which connects to the xml file, reads data from it and saves that data to its private variables.
	 * Value of those variables can be fetched using getter methods.
	 */
	public Configuration()
	{
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse("src/main/java/config.xml");

			document.getDocumentElement().normalize();
			NodeList nodeList = document.getElementsByTagName("Configuration");
			Node node = nodeList.item(0);

			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element elem =(Element)node;
				this.host = elem.getElementsByTagName("host").item(0).getTextContent();
				this.username = elem.getElementsByTagName("username").item(0).getTextContent();        
				this.password = elem.getElementsByTagName("password").item(0).getTextContent();
				this.authentication = elem.getElementsByTagName("authentication").item(0).getTextContent();
				this.provider_url = elem.getElementsByTagName("provider_url").item(0).getTextContent();
			}
		}
		catch (Exception e)
		{
			System.out.println("Erorr");
			e.printStackTrace();
		}
	}

	/**
	 * @return host data from xml configuration file
	 */
	public String getHost()
	{
		return this.host;
	}

	/**
	 * @return username from xml configuration file
	 */
	public String getUsername()
	{
		return this.username;
	}

	/**
	 * @return password for the current user obtained from xml configuration file
	 */
	public String getPassword()
	{
		return this.password;
	}

	/**
	 * @return authentication type from xml configuration file
	 */
	public String getAuthentication()
	{
		return this.authentication;
	}

	/**
	 * @return providers URL obtained from xml configuration file
	 */
	public String getProvider_url()
	{
		return this.provider_url;
	}

}
