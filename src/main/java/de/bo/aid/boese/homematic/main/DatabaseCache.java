package de.bo.aid.boese.homematic.main;

import java.util.List;

import de.bo.aid.boese.homematic.dao.ConnectorDao;
import de.bo.aid.boese.homematic.dao.DeviceDao;
import de.bo.aid.boese.homematic.model.Connector;
import de.bo.aid.boese.homematic.model.Device;

public class DatabaseCache {
	
	private static DatabaseCache instance = new DatabaseCache();
	
	private Connector connector;
	private List<Device> devices;
	
	public static DatabaseCache getInstance(){
		return instance;
	}
	
	private DatabaseCache(){
	}
	
	public void update(){
		try {
			connector = ConnectorDao.getConnector();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		devices = DeviceDao.getDevices();
	}

	public Connector getConnector() {
		return connector;
	}

	public void setConnector(Connector connector) {
		this.connector = connector;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

}
