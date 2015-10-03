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
package de.bo.aid.boese.homematic.main;

import java.util.List;

import org.apache.log4j.Logger;

import de.bo.aid.boese.homematic.dao.ConnectorDao;
import de.bo.aid.boese.homematic.dao.DeviceDao;
import de.bo.aid.boese.homematic.model.Component;
import de.bo.aid.boese.homematic.model.Connector;
import de.bo.aid.boese.homematic.model.Device;

// TODO: Auto-generated Javadoc
/**
 * Defines a cache for the database. Singleton class.
 * Holds instances of connector and device.
 */
public class DatabaseCache {
	
	/** The logger for log4j. */
	final static Logger logger = Logger.getLogger(DatabaseCache.class);
	
	/** The instance. */
	private static DatabaseCache instance = new DatabaseCache();
	
	/** The connectorase from the datab. */
	private Connector connector;
	
	/**  A list of devices from the database. */
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
	 * Updates the cache.
	 */
	public void update(){
		try {
			connector = ConnectorDao.getConnector();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		devices = DeviceDao.getDevices();
		logger.info("The database-cache was updated");
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
	
	/**
	 * Checks if is known.
	 *
	 * @param dev the dev
	 * @return true, if is known
	 */
	public boolean isKnown(Device dev){
		for(Device devDB : devices){
			if(devDB.getAdress().equals(dev.getAdress())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if is known.
	 *
	 * @param comp the comp
	 * @return true, if is known
	 */
	public boolean isKnown(Component comp){
		return false;
		
	}
	
	/**
	 * Determines wether a connector is saved in the database.
	 *
	 * @return true if a connector is already in the database, false if not.
	 */
	public boolean hasConnector(){
		return !(connector == null);		
	}

}
