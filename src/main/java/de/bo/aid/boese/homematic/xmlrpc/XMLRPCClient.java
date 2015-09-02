package de.bo.aid.boese.homematic.xmlrpc;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import de.bo.aid.boese.homematic.model.Device;


public class XMLRPCClient {
	
	List<Device> devices = new ArrayList<Device>();
	
	private final String clientId = "123";
	private XmlRpcClient client;
	
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

		}
	}
	
	public void printDevices(){
		System.out.println("Size: " + devices.size());
		for(Device dev : devices){
			System.out.println(dev);
		}
	}
	
	public List<Device> getDevices(){
		return devices;
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
							Object paramsetDescription = requestParamSets((String) map.get("ADDRESS"), (String)paramset);
							HashMap<String, Object> paramSetDescriptionMap = (HashMap<String, Object>) paramsetDescription;
							for(String keypD : paramSetDescriptionMap.keySet()){
							System.out.println("      " + key + ": " + paramSetDescriptionMap.get(keypD));
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
	
	

}
