package de.bo.aid.boese.homematic.xmlrpc;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class XMLRPCClient {
	
	private final int clientId = 1;
	XmlRpcClient client;
	
	public void init(){
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
	    try {
			config.setServerURL(new URL("http://127.0.0.1:8080/xmlrpc"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    client = new XmlRpcClient();
	    client.setConfig(config);
	    
	    
	}
	
	public void sendInit(String url){
		
		
		
	    Object[] params = new Object[]{url, clientId};
	    try {
			Object result = client.execute("init", params);
		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
