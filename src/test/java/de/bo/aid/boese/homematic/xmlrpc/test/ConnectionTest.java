package de.bo.aid.boese.homematic.xmlrpc.test;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.homematic.xmlrpc.XMLRPCClient;

public class ConnectionTest {
	
	XMLRPCClient client = new XMLRPCClient();
	
	@Before
	public void setUp(){
	//	client.init();
	}
	
	@Test
	public void test(){
	//	client.listDevices();
	//	client.saveDevices();
	//	client.testSwitch();
	}
	
	@After
	public void tearDown(){
		
	}
}