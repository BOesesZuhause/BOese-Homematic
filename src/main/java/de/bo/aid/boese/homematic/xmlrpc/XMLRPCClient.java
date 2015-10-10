/*             
 * 			  (                       
 *			 ( )\         (        (   
 *			 )((_)  (    ))\ (    ))\  
 *			((_)_   )\  /((_))\  /((_) 
 *			 | _ ) ((_)(_)) ((_)(_))   
 *			 | _ \/ _ \/ -_)(_-</ -_)  
 *			 |___/\___/\___|/__/\___|
 *       
 *           			;            
 *		      +        ;;;         + 
 *			  +       ;;;;;        + 
 *			  +      ;;;;;;;       + 
 *			  ++    ;;;;;;;;;     ++ 
 *			  +++++;;;;;;;;;;;+++++  
 *			   ++++;;;;;;;;;;;+++++  
 *				++;;;;;;;;;;;;;++    
 *			     ;;;;;;;;;;;;;;;     
 *			    ;;;;;;;;;;;;;;;;;     
 *				:::::::::::::::::    
 * 				:::::::::::::::::      
 *  			:::::::::::::::::    
 *   			::::::@@@@@::::::    
 *				:::::@:::::@:::::    
 *				::::@:::::::@::::    
 * 				:::::::::::::::::    
 *  			:::::::::::::::::      
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <sebastian.lechte@hs-bochum.de> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Sebastian Lechte
 * ----------------------------------------------------------------------------
 */
package de.bo.aid.boese.homematic.xmlrpc;

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 * Client to send requests to the HomeMatic-XMLRPC-Server.
 */
public class XMLRPCClient {

	/** The singleton-instance. */
	private static XMLRPCClient instance = new XMLRPCClient();

	/**
	 * Instantiates a new XMLRPC client.
	 */
	private XMLRPCClient() {

	}

	/**
	 * Gets the single instance of XMLRPCClient.
	 *
	 * @return single instance of XMLRPCClient
	 */
	public static XMLRPCClient getInstance() {
		return instance;
	}

	/** The logger for log4j. */
	final static Logger logger = Logger.getLogger(XMLRPCClient.class);

	/**
	 * The id of the client. This is used by the homematic-server to identify
	 * the client.
	 */
	private final String clientId = "123"; // TODO save in DB

	/** The client. */
	private XmlRpcClient client;

	/** The paramset description. */
	Object paramsetDescription;

	/**
	 * Initalizes the client and reads the xml-File (Devices.xml in the root
	 * directory of the classpath)
	 *
	 * @param url
	 *            the url of the homematic XMLRPS-Server
	 */
	public void init(String url) {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		try {
			config.setServerURL(new URL(url));
		} catch (MalformedURLException e) {
			logger.error("Homematic url is invalid: " + url);
			e.printStackTrace();
			System.exit(0);
		}
		client = new XmlRpcClient();
		client.setConfig(config);

	}

	/**
	 * Gets the HM devices.
	 *
	 * @return the HM devices
	 */
	public Object getDevices() {

		Object[] params = new Object[] {};
		Object obj = null;
		obj = makeRequest("listDevices", params);
		if (obj == null) {
			logger.error("no devices returned from homematic");
		}

		return obj;
	}

	/**
	 * Gets the param sets.
	 *
	 * @param address the address
	 * @return the param sets
	 */
	public Object getParamSets(String address) {

		Object[] params = new Object[] { address, "VALUES" };
		Object obj = null;
		obj = makeRequest("getParamsetDescription", params);
		if (obj == null) {
			logger.warn("no paramsets returned from homematic for device with address: " + address);
		}

		return obj;
	}

	/**
	 * Sends the init-message to the homematic XMLRPC-Server. This is needed to
	 * register a callback for events
	 *
	 * @param url of the xmlrpc-server for callbacks
	 *           
	 */
	public void sendInit(String url) {

		Object[] params = new Object[] { url, clientId };
		makeRequest("init", params);
	}

	/**
	 * Sets a value in the homematic-system.
	 *
	 * @param address
	 *            the address of the device
	 * @param name
	 *            the name of the value to be set
	 * @param value
	 *            the value
	 * @param type
	 *            the type of the value (BOOL, FLOAT, ACTION, INTEGER, ENUM)
	 */

	// TODO Dataformat for String and Enum
	public void setValue(String address, String name, double value, String type) {
		Object valueSent = null;
		switch (type) {
		case "BOOL":
			valueSent = false;
			if (value == 1) {
				valueSent = true;
			}
			break;
		case "FLOAT":
			valueSent = value;
			break;
		case "ACTION":
			valueSent = true;
			break;
		case "INTEGER":
			valueSent = (int) value;
			break;
		case "ENUM":
			// value =
			break;
		case "STRING":
			// value=
			break;
		default:
		}
		Object[] params = new Object[] { address, name, valueSent };
		makeRequest("setValue", params);
	}

	/**
	 * Sends a request to the XMLRPC-Server.
	 *
	 * @param method            the methodname of the called method
	 * @param params            the parameters for the method
	 * @return the answer
	 */
	private Object makeRequest(String method, Object[] params) {
		try {
			logger.info("Send XMLRPC-Request: " + method);
			return client.execute(method, params);
		} catch (XmlRpcException e) {
			if (e.getCause() instanceof ConnectException) {
				logger.error("Timeout while sending request to HomeMatic XML-RPC-Server");
			} else {
				logger.error("error while sending xmlrpc-request:");
				e.printStackTrace();
			}
		}
		return null;
	}

}
