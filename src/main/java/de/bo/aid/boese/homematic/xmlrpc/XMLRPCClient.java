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

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import de.bo.aid.boese.homematic.mapper.DeviceMapper;
import de.bo.aid.boese.homematic.model.Device;

import de.bo.aid.boese.homematic.xml.DeviceXML;
import de.bo.aid.boese.homematic.xml.DevicesXML;

// TODO: Auto-generated Javadoc
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
	public List<Device> getHMDevices() {

		Object[] params = new Object[] {};
		Object obj = null;
		try {
			obj = makeRequest("listDevices", params);
		} catch (XmlRpcException e) {
			logger.error("error while sending xmlrpc-request");
			e.printStackTrace();
		}
		if (obj == null) {
			logger.error("no devices returned from homematic");
		}

		return DeviceMapper.map(obj, true);
	}

	/**
	 * Converts all found Homematic-Devices to the format used in the
	 * known-devices-xml-file and outputs it.
	 * 
	 * @return
	 */

	// TODO test
	public DevicesXML getDevicesAsXML() {
		DevicesXML out = new DevicesXML();

		for (Device dev : getHMDevices()) {
			DeviceXML devXML = new DeviceXML();
			devXML.setFirmware(dev.getFirmware());
			devXML.setModel(dev.getType());

			out.getDevices().add(devXML);
		} // TODO getChannels and Components
		return out;
	}

	/**
	 * Sends the init-message to the homematic XMLRPC-Server. THis is needed to
	 * register a callback for events
	 *
	 * @param url
	 *            the url
	 */
	public void sendInit(String url) {

		Object[] params = new Object[] { url, clientId };
		try {
			makeRequest("init", params);
		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		try {
			makeRequest("setValue", params);
		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Sends a request to the XMLRPC-Server.
	 *
	 * @param method
	 *            the methodname of the called method
	 * @param params
	 *            the parameters for the method
	 * @return the answer
	 * @throws XmlRpcException
	 *             the xml rpc exception
	 */
	private Object makeRequest(String method, Object[] params) throws XmlRpcException {
		try {
			logger.info("Send XMLRPC-Request: " + method);
			return client.execute(method, params);
		} catch (XmlRpcException e) {
			if (e.getCause() instanceof ConnectException) {
				logger.error("Timeout while sending request to HomeMatic XML-RPC-Server");
			} else {
				throw e;// TODO Exception anders werfen
			}
			// System.exit(0);
		}
		return null;
	}

}
