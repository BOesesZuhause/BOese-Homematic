/*
 * 
 */
package de.bo.aid.boese.homematic.main;

import java.util.List;

import de.bo.aid.boese.homematic.dao.ConnectorDao;
import de.bo.aid.boese.homematic.dao.DeviceDao;
import de.bo.aid.boese.homematic.model.Connector;
import de.bo.aid.boese.homematic.model.Device;

// TODO: Auto-generated Javadoc
/**
 * The Class DatabaseCache.
 */
public class DatabaseCache {
	
	/** The instance. */
	private static DatabaseCache instance = new DatabaseCache();
	
	/** The connector. */
	private Connector connector;
	
	/** The devices. */
	private List<Device> devices;
	
	/**
	 * Gets the single instance of DatabaseCache.
	 *
	 * @return single instance of DatabaseCache
	 */
	public static DatabaseCache getInstance(){
		return instance;
	}
	
	/**
	 * Instantiates a new database cache.
	 */
	private DatabaseCache(){
	}
	
	/**
	 * Update.
	 */
	public void update(){
		try {
			connector = ConnectorDao.getConnector();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		devices = DeviceDao.getDevices();
	}

	/**
	 * Gets the connector.
	 *
	 * @return the connector
	 */
	public Connector getConnector() {
		return connector;
	}

	/**
	 * Sets the connector.
	 *
	 * @param connector the new connector
	 */
	public void setConnector(Connector connector) {
		this.connector = connector;
	}

	/**
	 * Gets the devices.
	 *
	 * @return the devices
	 */
	public List<Device> getDevices() {
		return devices;
	}

	/**
	 * Sets the devices.
	 *
	 * @param devices the new devices
	 */
	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

}
