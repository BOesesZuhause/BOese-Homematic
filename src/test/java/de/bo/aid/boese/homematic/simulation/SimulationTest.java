package de.bo.aid.boese.homematic.simulation;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.homematic.dao.ComponentDao;
import de.bo.aid.boese.homematic.dao.ConnectorDao;
import de.bo.aid.boese.homematic.dao.DeviceDao;
import de.bo.aid.boese.homematic.model.Component;
import de.bo.aid.boese.homematic.model.Connector;
import de.bo.aid.boese.homematic.model.Device;
import de.bo.aid.boese.homematic.socket.SocketServer;

public class SimulationTest {
	@Before
	public void setUp(){
		Connector con = new Connector();
		con.setIdverteiler(-1);
		con.setName("HomeMaticDefault");
		ConnectorDao.insertConnector(con);
		
		Device dev = new Device();
		dev.setIdverteiler(-1);
		dev.setName("HMTestDevice1");
		dev.setConnector(con);
		DeviceDao.insertDevice(dev);
		
		Component comp = new Component();
		comp.setIdverteiler(-1);
		comp.setName("HMTestComponent1_1");
		comp.setDevice(dev);
		ComponentDao.insertComponent(comp);
		
		comp = new Component();
		comp.setIdverteiler(-1);
		comp.setName("HMTestComponent1_2");
		comp.setDevice(dev);
		ComponentDao.insertComponent(comp);
		
		dev = new Device();
		dev.setIdverteiler(-1);
		dev.setName("HMTestDevice2");
		dev.setConnector(con);
		DeviceDao.insertDevice(dev);
		
		comp = new Component();
		comp.setIdverteiler(-1);
		comp.setName("HMTestComponent2_1");
		comp.setDevice(dev);
		ComponentDao.insertComponent(comp);
		
		comp = new Component();
		comp.setIdverteiler(-1);
		comp.setName("HMTestComponent2_2");
		comp.setDevice(dev);
		ComponentDao.insertComponent(comp);
		
		dev = new Device();
		dev.setIdverteiler(-1);
		dev.setName("HMTestDevice3");
		dev.setConnector(con);
		DeviceDao.insertDevice(dev);

		comp = new Component();
		comp.setIdverteiler(-1);
		comp.setName("HMTestComponent1_1");
		comp.setDevice(dev);
		ComponentDao.insertComponent(comp);





		
	}

	@Test
	public void test(){
//Der Verteiler msus alufen sonst klappt der Test nicht
//		SocketServer server = new SocketServer();
//		server.start("ws://localhost:8081/events/");

try {
	Thread.sleep(10000);
} catch (InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	}
	
	@After
	public void tearDown(){
		
	}
}
