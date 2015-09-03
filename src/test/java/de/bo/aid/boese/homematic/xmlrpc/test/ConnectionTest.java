package de.bo.aid.boese.homematic.xmlrpc.test;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.homematic.xmlrpc.XMLRPCClient;
import de.bo.aid.boese.homematic.xmlrpc.XMLRPCServer;

public class ConnectionTest {
	
	XMLRPCClient client = XMLRPCClient.getInstance();
	
	@Before
	public void setUp(){
		//client.init();
	}
	
	@Test
	public void test(){
	//	client.listDevices();
	//	client.saveDevices();
	//	client.testSwitch();
//		XMLRPCServer server = new XMLRPCServer();
//		server.start();
//		client.sendInit("192.168.23.178:8082");
//		try {
//			Thread.sleep(50000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	@After
	public void tearDown(){
		
	}
}