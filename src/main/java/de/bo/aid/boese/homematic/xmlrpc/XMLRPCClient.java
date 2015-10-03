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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import de.bo.aid.boese.homematic.mapper.DeviceMapper;
import de.bo.aid.boese.homematic.model.Device;
import de.bo.aid.boese.homematic.xml.ChannelXML;
import de.bo.aid.boese.homematic.xml.ComponentXML;
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
	private XMLRPCClient(){
		
	}
	
	/**
	 * Gets the single instance of XMLRPCClient.
	 *
	 * @return single instance of XMLRPCClient
	 */
	public static XMLRPCClient getInstance(){
		return instance;
	}
	
	/** The logger for log4j. */
	final static Logger logger = Logger.getLogger(XMLRPCClient.class);
	

	
	/** The id of the client. This is used by the homematic-server to identify the client. */
	private final String clientId = "123"; //TODO save in DB
	
	/** The client. */
	private XmlRpcClient client;
	

	
	/** The paramset description. */
	Object paramsetDescription;
	
	/**
	 * Initalizes the client and reads the xml-File (Devices.xml in the root directory of the classpath)
	 *
	 * @param url the url of the homematic XMLRPS-Server
	 */
	public void init(String url){
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
	 * Converts all found Homematic-Devices to the format used in the known-devices-xml-file and outputs it.
	 */

	//TODO test
	public void generateXML(){
		Object[] params = new Object[]{};
		Object[] result = new Object[]{};
		HashMap<String, Object> map = new HashMap<>();
		DevicesXML devicesXML = new DevicesXML();
		
		
		//Get devices
		try {
			result = (Object[]) makeRequest("listDevices", params);
		} catch (XmlRpcException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for(Object device : result){
			map = (HashMap<String, Object>) device;
			Device dev = new Device();
			DeviceXML devXML = new DeviceXML();
			
					//ignore virtual devices
					if(new String(map.get("ADDRESS").toString()).startsWith("BidCoS")){
						continue;
					}
					
					//ignore channels
					if(new String(map.get("ADDRESS").toString()).contains(":")){
						continue;
					}
								
					dev.setAdress((String) map.get("ADDRESS"));
					dev.setType((String) map.get("TYPE"));
					dev.setVersion((int) map.get("VERSION"));
					
					devXML.setFirmware((String) map.get("FIRMWARE"));
					devXML.setModel((String) map.get("TYPE"));
					
					devicesXML.getDevices().add(devXML);
					
					//Kanäle abfragen
					Object[] children = (Object[]) map.get("CHILDREN");
					for(Object child : children){
						
						ChannelXML channelXML = new ChannelXML();
						
						int pos = child.toString().indexOf(":");
						String channel = child.toString().substring(pos +1);

						channelXML.setNumber(Integer.parseInt(channel));
						devXML.getChannels().add(channelXML); //TODO nur nichtleere hinzufügen
						
						try {					
							params = new Object[]{(String) child, "VALUES"};
							paramsetDescription = makeRequest("getParamsetDescription", params); //TODO test	
						} catch (XmlRpcException e) { //fehlerhafte Geräte
							// TODO Auto-generated catch block
							e.printStackTrace();
							continue; 
						}
						HashMap<String, Object> paramSetDescriptionMap = (HashMap<String, Object>) paramsetDescription;
						for(Object value : paramSetDescriptionMap.values()){
							HashMap<String, Object> valueMap = (HashMap<String, Object>) value;
							

							
							//Schaltaktor
							if(valueMap.get("TYPE").equals("ACTION")){
								
								ComponentXML compXML = new ComponentXML();
								compXML.setAktor(true);
								compXML.setUnit((String) valueMap.get("UNIT"));
								compXML.setType((String) valueMap.get("TYPE"));
								compXML.setName((String) valueMap.get("ID"));
								channelXML.getComponents().add(compXML);
							}
							
							//Sensor
							if(valueMap.get("UNIT") != null){
								
								ComponentXML compXML = new ComponentXML();
								compXML.setAktor(false);
								compXML.setUnit((String) valueMap.get("UNIT"));
								compXML.setType((String) valueMap.get("TYPE"));
								compXML.setName((String) valueMap.get("ID"));
								channelXML.getComponents().add(compXML);
							}
					}
						
			 }
		}
		 try {
		        JAXBContext ctx = JAXBContext.newInstance(devicesXML.getClass());
		        Marshaller marshaller = ctx.createMarshaller();
		        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		        marshaller.marshal(devicesXML, System.out);
		    }
		    catch (Exception
		            e) {
		    			e.printStackTrace();
		              //catch exception 
		    }
		
	}
	
	/**
	 * Gets the HM devices.
	 *
	 * @return the HM devices
	 */
	public List<Device> getHMDevices(){
		
		Object[] params = new Object[]{};
		Object obj = null;
		try {
			obj = makeRequest("listDevices", params);
		} catch (XmlRpcException e) {
			logger.error("error while sending xmlrpc-request");
			e.printStackTrace();
		}
//		obj = readDump();
		if(obj == null){
			logger.error("no devices returned from homematic");
		}
		
		return DeviceMapper.map(obj, true);
	}


	/**
	 * Dumps all devices with all information to the file: HM.txt
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	
	//TODO
	public void listDevicesToFile() throws IOException{
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File("HM.txt"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Object[] params = new Object[]{};
		Object[] result = new Object[]{};
		HashMap<String, Object> map = new HashMap<>();
		int i = 1;
		try {
			result = (Object[]) client.execute("listDevices", params);
			for(Object device : result){
				System.out.println("DeviceNr: " + i++);
				map = (HashMap<String, Object>) device;
				for(String key :map.keySet()){
					if(key.equals("PARAMSETS")){
						System.out.println("PARAMSETS: ");
						fw.write("PARAMSETS: \n");
						Object[] paramsets = (Object[]) map.get(key);
						for(Object paramset : paramsets){
							System.out.println("   " + paramset);
							fw.write("   " + paramset + "\n");
							try{
							params = new Object[]{(String) map.get("ADDRESS"), (String)paramset};
							paramsetDescription = makeRequest("getParamsetDescription", params); //TODO test					
							
							HashMap<String, Object> paramSetDescriptionMap = (HashMap<String, Object>) paramsetDescription;
							for(String keypD : paramSetDescriptionMap.keySet()){
							System.out.println("      " + keypD + ": " + paramSetDescriptionMap.get(keypD));
							fw.write("      " + keypD + ": " + paramSetDescriptionMap.get(keypD) + "\n");
						}
						}catch (Exception e){
							System.out.println("      Fehler");
							fw.write("      Fehler\n");
						}
						}
					}else{
						System.out.println(key + ": " + map.get(key));
						fw.write(key + ": " + map.get(key) + "\n");
					}

				}
				System.out.println();
				fw.write("\n");
			}

		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	
	/**
	 * Sends the init-message to the homematic XMLRPC-Server.
	 * THis is needed to register a callback for events
	 *
	 * @param url the url
	 */
	public void sendInit(String url){
			
	    Object[] params = new Object[]{url, clientId};
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
	 * @param address the address of the device
	 * @param name the name of the value to be set
	 * @param value the value
	 * @param type the type of the value (BOOL, FLOAT, ACTION, INTEGER, ENUM)
	 */
	
	//TODO Dataformat for String and Enum
	public void setValue(String address, String name, double value, String type) {
		Object valueSent = null;
		switch(type){
		case "BOOL":
			valueSent = false;
			if(value==1){
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
			//value = 
			break;
		case "STRING":
			//value=
			break;
			default:
		}
		Object[] params = new Object[]{address, name, valueSent};
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
	 * @param method the methodname of the called method
	 * @param params the parameters for the method
	 * @return the answer
	 * @throws XmlRpcException the xml rpc exception
	 */
	private Object makeRequest(String method, Object[] params) throws XmlRpcException{
		try {
			logger.info("Send XMLRPC-Request: " + method);
			return client.execute(method, params);			
		} catch (XmlRpcException e) {
			if(e.getCause() instanceof ConnectException){
				logger.error("Timeout while sending request to HomeMatic XML-RPC-Server");
			}else{
				throw e;//TODO Exception anders werfen
			}
			//System.exit(0);
		}
		return null;		
	}

	/**
	 * Read dump.
	 *
	 * @return the object
	 */
	public Object readDump(){
		  try {
			ObjectInputStream o = new ObjectInputStream(new FileInputStream("response"));
			try {
				return o.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return null;
	}
	
	

}
