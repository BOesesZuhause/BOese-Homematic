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
package de.bo.aid.boese.homematic.simulation;


import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.homematic.dao.ComponentDao;
import de.bo.aid.boese.homematic.dao.ConnectorDao;
import de.bo.aid.boese.homematic.dao.DeviceDao;
import de.bo.aid.boese.homematic.model.Component;
import de.bo.aid.boese.homematic.model.Connector;
import de.bo.aid.boese.homematic.model.Device;
import de.bo.aid.boese.homematic.socket.ProtocolHandler;
import de.bo.aid.boese.homematic.xmlrpc.XMLRPCClient;
import de.bo.aid.boese.homematic.xmlrpc.XMLRPCServer;

// TODO: Auto-generated Javadoc
/**
 * The Class SimulationTest.
 */
public class SimulationTest {
	
	/** The client. */
	XMLRPCClient client = XMLRPCClient.getInstance();
	
	
	
	/**
	 * Sets the up.
	 */
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
		
//		Device dev = new Device();
//		dev.setIdverteiler(-1);
//		dev.setName("HMTestDevice1");
//		dev.setConnector(con);
//		DeviceDao.insertDevice(dev);
//		
//		Component comp = new Component();
//		comp.setIdverteiler(-1);
//		comp.setName("HMTestComponent1_1");
//		comp.setDevice(dev);
//		ComponentDao.insertComponent(comp);
//		
//		comp = new Component();
//		comp.setIdverteiler(-1);
//		comp.setName("HMTestComponent1_2");
//		comp.setDevice(dev);
//		ComponentDao.insertComponent(comp);
//		
//		dev = new Device();
//		dev.setIdverteiler(-1);
//		dev.setName("HMTestDevice2");
//		dev.setConnector(con);
//		DeviceDao.insertDevice(dev);
//		
//		comp = new Component();
//		comp.setIdverteiler(-1);
//		comp.setName("HMTestComponent2_1");
//		comp.setDevice(dev);
//		ComponentDao.insertComponent(comp);
//		
//		comp = new Component();
//		comp.setIdverteiler(-1);
//		comp.setName("HMTestComponent2_2");
//		comp.setDevice(dev);
//		ComponentDao.insertComponent(comp);
//		
//		dev = new Device();
//		dev.setIdverteiler(-1);
//		dev.setName("HMTestDevice3");
//		dev.setConnector(con);
//		DeviceDao.insertDevice(dev);
//
//		comp = new Component();
//		comp.setIdverteiler(-1);
//		comp.setName("HMTestComponent1_1");
//		comp.setDevice(dev);
//		ComponentDao.insertComponent(comp);





		
	}

	/**
	 * Test.
	 */
	@Test
	public void test(){
//Der Verteiler muss laufen sonst klappt der Test nicht
//		SocketServer server = SocketServer.getInstance();
//		server.start("ws://localhost:8081/events/");
//		server.requestConnection();
//
//try {
//	Thread.sleep(5000);
//} catch (InterruptedException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//}
//XMLRPCServer XMLserver = new XMLRPCServer();
//XMLserver.start();
//try {
//	client.sendInit(InetAddress.getLocalHost().getHostAddress() + ":8082");
//} catch (UnknownHostException e1) {
//	// TODO Auto-generated catch block
//	e1.printStackTrace();
//}
//try {
//	Thread.sleep(500000000);
//} catch (InterruptedException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//}
	}
	
	/**
	 * Tear down.
	 */
	@After
	public void tearDown(){
		
	}
}
