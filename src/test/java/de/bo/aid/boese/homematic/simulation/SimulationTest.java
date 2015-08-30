package de.bo.aid.boese.homematic.simulation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.homematic.dao.ConnectorDao;
import de.bo.aid.boese.homematic.dao.DeviceDao;
import de.bo.aid.boese.homematic.model.Component;
import de.bo.aid.boese.homematic.model.Connector;
import de.bo.aid.boese.homematic.model.Device;

public class SimulationTest {
	@Before
	public void setUp(){
		Connector con = new Connector();
		con.setIdVerteiler(-1);
		con.setName("HomeMaticDefault");
		
		Device dev = new Device();
		dev.setIdVerteiler(-1);
		dev.setName("HMTestDevice");
		
		Component comp = new Component();
		comp.setIdVerteiler(-1);
		comp.setName("HMTestComponent");
		
		dev.getComponents().add(comp);
		
		con.getDevices().add(dev);
		
		ConnectorDao.insertConnector(con);
		
	}

	@Test
	public void test(){
		//Device dev = DeviceDao.getDevices().get(0);
		//System.out.println(dev.getName());
	}
	
	@After
	public void tearDown(){
		
	}
}
