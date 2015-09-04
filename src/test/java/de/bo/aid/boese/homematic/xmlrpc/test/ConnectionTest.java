package de.bo.aid.boese.homematic.xmlrpc.test;

import java.io.IOException;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.homematic.dao.ComponentDao;
import de.bo.aid.boese.homematic.dao.ConnectorDao;
import de.bo.aid.boese.homematic.dao.DeviceDao;
import de.bo.aid.boese.homematic.model.Component;
import de.bo.aid.boese.homematic.model.Connector;
import de.bo.aid.boese.homematic.model.Device;
import de.bo.aid.boese.homematic.xmlrpc.XMLRPCClient;
import de.bo.aid.boese.homematic.xmlrpc.XMLRPCServer;

public class ConnectionTest {
	
	XMLRPCClient client = XMLRPCClient.getInstance();
	
	@Before
	public void setUp(){
//		client.init();
//		client.saveKnownDevices();
//		
//		client.printDevices();		
//		client.printComponents();
//		
//		Connector con = new Connector();
//		con.setIdverteiler(-1);
//		con.setName("HomeMaticDefault");
//		ConnectorDao.insertConnector(con);
//		
//		for(Device dev : client.getDevices()){
//			dev.setConnector(con);
//			dev.setIdverteiler(-1);
//			DeviceDao.insertDevice(dev);
//		}
//		
//		for(Component comp : client.getComponents()){
//			comp.setIdverteiler(-1);
//			ComponentDao.insertComponent(comp);
//		}
	}
	
	@Test
	public void test(){
//		try {
//			client.listDevices();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	//	client.saveDevices();
	//	client.testSwitch();
//		XMLRPCServer server = new XMLRPCServer();
//		server.start();
//		client.sendInit("192.168.23.178:8082");
//		try {
//			Thread.sleep(500000000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	@After
	public void tearDown(){
		
	}
}