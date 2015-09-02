package de.bo.aid.boese.homematic.xmlrpc;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import de.bo.aid.boese.homematic.dao.ComponentDao;
import de.bo.aid.boese.homematic.dao.DeviceDao;
import de.bo.aid.boese.homematic.devices.steckdose;
import de.bo.aid.boese.homematic.model.Component;
import de.bo.aid.boese.homematic.model.Connector;
import de.bo.aid.boese.homematic.model.Device;


public class XMLRPCClient {
	
	List<Device> devices = new ArrayList<Device>();
	List<Component> components = new ArrayList<Component>();
	
	private final String clientId = "123";
	private XmlRpcClient client;
	
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
			Device dev = new Device();
			
					//ignore virtual devices
					if(new String(map.get("ADDRESS").toString()).startsWith("BidCoS")){
						continue;
					}
					
					//ignore channels
					if(new String(map.get("ADDRESS").toString()).contains(":")){
						continue;
					}
					switch((String) map.get("TYPE")){
					//TODO Switch als Liste
					case "HM-ES-PMSw1-Pl":
						steckdose steckdose = new steckdose(map.get("ADDRESS").toString());
						dev.setAdress(map.get("ADDRESS").toString());
						dev.setType(map.get("TYPE").toString());
						dev.setVersion((int)map.get("VERSION"));
						devices.add(dev);	
						for(Component component : steckdose.getComponents()){
							component.setDevice(dev);
							components.add(component);
						}
						break;						
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
	
	public void listDevices(){
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
						Object[] paramsets = (Object[]) map.get(key);
						for(Object paramset : paramsets){
							System.out.println("   " + paramset);
							try{
							paramsetDescription = requestParamSets((String) map.get("ADDRESS"), (String)paramset);
							HashMap<String, Object> paramSetDescriptionMap = (HashMap<String, Object>) paramsetDescription;
							for(String keypD : paramSetDescriptionMap.keySet()){
							System.out.println("      " + keypD + ": " + paramSetDescriptionMap.get(keypD));
						}
						}catch (Exception e){
							System.out.println("      Fehler");
						}
						}
					}else{
						System.out.println(key + ": " + map.get(key));
					}

				}
				System.out.println();
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
			Object[] result = (Object[]) client.execute("init", params);
			

		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testSwitch() {
		// TODO Auto-generated method stub
		Object[] params = new Object[]{"LEQ0930959:1", "INSTALL_TEST", true};
	    try {
			Object result = client.execute("setValue", params);
			System.out.println(result.toString());

		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}

	public void setValue(String address, String name, double value) {
		Object[] params = new Object[]{address, name, value};
	    try {
			Object result = client.execute("setValue", params);
			System.out.println(result.toString());

		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
