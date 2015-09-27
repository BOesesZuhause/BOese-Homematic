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
 * <sebasian.lechte@hs-bochum.de> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Sebastian Lechte
 * ----------------------------------------------------------------------------
 */
package de.bo.aid.boese.homematic.xmlrpc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import de.bo.aid.boese.homematic.model.Component;
import de.bo.aid.boese.homematic.model.Device;
import de.bo.aid.boese.homematic.socket.SocketServer;
import de.bo.aid.boese.homematic.xml.ChannelXML;
import de.bo.aid.boese.homematic.xml.ComponentXML;
import de.bo.aid.boese.homematic.xml.DeviceXML;
import de.bo.aid.boese.homematic.xml.DevicesXML;


/**
 * Client to send requests to the HomeMatic-XMLRPC-Server
 */
public class XMLRPCClient {
	
	/** The sigleton-instance. */
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
	final static Logger logger = Logger.getLogger(SocketServer.class);
	
	/** List of devices to store the homematic devices temporarely. */
	List<Device> devices = new ArrayList<Device>();
	
	/** List of components to store the homematic components temporarely. */
	List<Component> components = new ArrayList<Component>();
	
	/** The id of the client. This is used by the homematic-server to identify the client. */
	private final String clientId = "123"; //TODO save in DB
	
	/** The client. */
	private XmlRpcClient client;
	
	/** The object-reprasentation of the xml-file with known devices. */
	DevicesXML xml;
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    client = new XmlRpcClient();
	    client.setConfig(config);	
	    
	    //XML einlesen
	    xml = readXML("Devices.xml");
	}  
	
	/**
	 * Reads the xml-file with the known devices.
	 *
	 * @param location the location of the file
	 * @return the object representation of the xml
	 */
	public DevicesXML readXML(String location){
		try {
			File file = new File(location);
			JAXBContext context = JAXBContext.newInstance( DevicesXML.class );
			Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
			return (DevicesXML)jaxbUnmarshaller.unmarshal(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * Converts all found Homematic-Devices to the format used in the known-devices-xml-file and outputs it.
	 */
	//TODO save as XML
	//TODO Mapper schreiben der HM-Antworten in Objekte parst
	//TODO test
	public void saveAllDevices(){
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
					devices.add(dev);
					
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
								Component comp = new Component();
								comp.setIdverteiler(-1);
								comp.setDevice(dev);
								comp.setAddress((String) child);
								comp.setName((String) valueMap.get("ID"));
								comp.setAktor(true);
								components.add(comp);
								
								ComponentXML compXML = new ComponentXML();
								compXML.setAktor(true);
								compXML.setUnit((String) valueMap.get("UNIT"));
								compXML.setType((String) valueMap.get("TYPE"));
								compXML.setName((String) valueMap.get("ID"));
								channelXML.getComponents().add(compXML);
							}
							
							//Sensor
							if(valueMap.get("UNIT") != null){
								Component comp = new Component();
								comp.setIdverteiler(-1);
								comp.setDevice(dev);
								comp.setAddress((String) child);
								comp.setName((String) valueMap.get("ID"));
								comp.setUnit((String) valueMap.get("UNIT"));
								components.add(comp);
								
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
		System.out.println("test");
		//TODO kommt nicht bis hierhin
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
	 * Saves all known devices temporarily.
	 * The devices are compared to the devices defined in the xml-file and saved when found.
	 */
	public void saveKnownDevices(){
		Object[] params = new Object[]{};
		Object[] result = new Object[]{};
		HashMap<String, Object> map = new HashMap<>();
		
		try {
			result = (Object[]) makeRequest("listDevices", params);
		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //TODO test

		for(Object device : result){
			map = (HashMap<String, Object>) device;
			//TODO Als Parser-Klasse auslagern
			String address = (String)map.get("ADDRESS");
			String type = (String)map.get("TYPE");
			int version = (int)map.get("VERSION");
			
					//ignore virtual devices
					if(address.startsWith("BidCoS")){
						continue;
					}
					
					//ignore channels
					if(address.contains(":")){
						continue;
					}
					//XML auswerten
					//TODO loggen fals nicht gefunden
					//TODO Validierung der XML-Eingaben mittels Abfragen am System
					for(DeviceXML devXML : xml.getDevices()){						
						if(devXML.getModel().equals(type)){
							Device dev = new Device();
							dev.setAdress(address);
							dev.setType(type);
							dev.setVersion(version);
							devices.add(dev);
							logger.info("Added Device via XML: " + dev);
							for(ChannelXML channelXML : devXML.getChannels()){
								int channelID = channelXML.getNumber();
								for(ComponentXML compXML : channelXML.getComponents()){
									Component comp = new Component();
									comp.setDevice(dev);
									comp.setAddress(address + ":" + channelID);
									comp.setAktor(compXML.isAktor());
									comp.setName(compXML.getDescription() + ":" + channelID);
									comp.setHm_id(compXML.getName());
									comp.setType(compXML.getType());
									components.add(comp);
									logger.info("Added Component via XML: " + comp);
								}
							}
						}
					}
					
					
		}
	}
	
	/**
	 * Prints the devices to the console.
	 */
	public void printDevices(){
		System.out.println("Size: " + devices.size());
		for(Device dev : devices){
			System.out.println(dev);
		}
	}
	
	/**
	 * Prints the components to the console.
	 */
	public void printComponents(){
		System.out.println("Size: " + components.size());
		for(Component comp : components){
			System.out.println(comp);
		}
	}
	
	/**
	 * Gets the devices.
	 *
	 * @return the devices
	 */
	public List<Device> getDevices(){
		return devices;
	}
	
	/**
	 * Gets the components.
	 *
	 * @return the components
	 */
	public List<Component> getComponents(){
		return components;
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
	 * Sets a value in the homematic-system
	 *
	 * @param address the address of the device
	 * @param name the name of the value to be set
	 * @param value the value
	 * @param type the type of the value (BOOL, FLOAT, ACTION, INTEGER, ENUM)
	 */
	
	//TODO add STRING-type
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
	 * @throws XmlRpcException 
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
			e.printStackTrace();
			//System.exit(0);
		}
		return null;		
	}

	/**
	 * Clears the temporarily saved devices and components
	 */
	public void cleanTempData() {
		devices = null;
		components = null;
	}
	
	

}
