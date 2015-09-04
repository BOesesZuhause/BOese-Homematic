package de.bo.aid.boese.homematic.xmlrpc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
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


public class XMLRPCClient {
	
	private static XMLRPCClient instance = new XMLRPCClient();
	
	private XMLRPCClient(){
		
	}
	
	public static XMLRPCClient getInstance(){
		return instance;
	}
	
	final static Logger logger = Logger.getLogger(SocketServer.class);
	
	List<Device> devices = new ArrayList<Device>();
	List<Component> components = new ArrayList<Component>();
	
	private final String clientId = "123"; //TODO save in DB
	private XmlRpcClient client;
	
	DevicesXML xml;
	
	Object paramsetDescription;
	
	public void init(){
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
	    try {
			config.setServerURL(new URL("http://192.168.23.33:2001"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    client = new XmlRpcClient();
	    client.setConfig(config);	
	    
	    //XML einlesen
	    xml = readXML("Devices.xml");
	}  
	
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
	
	public void saveDevices(){
		Object[] params = new Object[]{};
		Object[] result = new Object[]{};
		HashMap<String, Object> map = new HashMap<>();
		
		
		
		try {
			result = (Object[]) client.execute("listDevices", params);
		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Object device : result){
			map = (HashMap<String, Object>) device;
			Device dev = new Device();
			
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
					
					//Kanäle abfragen
					Object[] children = (Object[]) map.get("CHILDREN");
					for(Object child : children){
						try {					
							
							paramsetDescription = requestParamSets((String) child, "VALUES");
						} catch (XmlRpcException e) {
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
							}
					}
						
					}

					

					

		}
	}
	
	
	public void saveKnownDevices(){
		Object[] params = new Object[]{};
		Object[] result = new Object[]{};
		HashMap<String, Object> map = new HashMap<>();
		
		
		
		try {
			result = (Object[]) client.execute("listDevices", params);
		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Object device : result){
			map = (HashMap<String, Object>) device;
			//TODO Als Klasse auslagern
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
					//XML aauswerten
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
									comp.setName(compXML.getDescription());
									comp.setHm_id(compXML.getName());
									comp.setType(compXML.getType() + ":" + channelID);
									components.add(comp);
									logger.info("Added Component via XML: " + comp);
								}
							}
						}
					}
					
					
		}
	}
	
	public void printDevices(){
		System.out.println("Size: " + devices.size());
		for(Device dev : devices){
			System.out.println(dev);
		}
	}
	
	public void printComponents(){
		System.out.println("Size: " + components.size());
		for(Component comp : components){
			System.out.println(comp);
		}
	}
	
	public List<Device> getDevices(){
		return devices;
	}
	
	public List<Component> getComponents(){
		return components;
	}
	
	public void listDevices() throws IOException{
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
							paramsetDescription = requestParamSets((String) map.get("ADDRESS"), (String)paramset);
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
	
	public Object requestParamSets(String adress, String typ) throws XmlRpcException{
		Object[] params = new Object[]{adress, typ};
		Object result = null;
			result = client.execute("getParamsetDescription", params);
		return result;
	}
	
	
	public void sendInit(String url){
			
	    Object[] params = new Object[]{url, clientId};
	    try {
			client.execute("init", params);
			

		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testSwitch() {
		// TODO Auto-generated method stub
		Object[] params = new Object[]{"LEQ0930959:1", "STATE", false};
	    try {
			Object result = client.execute("setValue", params);
			System.out.println(result.toString());

		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}

	public void setValue(String address, String name, double value) {
		boolean bool = false;
		if(value==1){
			bool=true;
		}
		Object[] params = new Object[]{address, name, bool};
	    try {
			Object result = client.execute("setValue", params);
			System.out.println(result.toString());
			System.out.println(System.currentTimeMillis());

		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void cleanTempData() {
		devices = null;
		components = null;
	}
	
	

}
