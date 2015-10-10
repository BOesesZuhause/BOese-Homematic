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
package de.bo.aid.boese.json;

// TODO: Auto-generated Javadoc
/**
 * The Class UserDevice.
 */
public class UserDevice {
	
	/** The device name. */
	private String deviceName;
	
	/** The device id. */
	private int deviceId;
	
	/** The zone id. */
	private int zoneId;
	
	/** The connector id. */
	private int connectorId;
	
	/**
	 * Instantiates a new user device.
	 *
	 * @param name the name
	 * @param id the id
	 * @param zone the zone
	 * @param connector the connector
	 */
	public UserDevice(String name, int id, int zone, int connector) {
		this.connectorId = connector;
		this.deviceId = id;
		this.deviceName = name;
		this.zoneId = zone;
	}

	/**
	 * Gets the device name.
	 *
	 * @return the device name
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * Gets the device id.
	 *
	 * @return the device id
	 */
	public int getDeviceId() {
		return deviceId;
	}

	/**
	 * Gets the zone id.
	 *
	 * @return the zone id
	 */
	public int getZoneId() {
		return zoneId;
	}

	/**
	 * Gets the connector id.
	 *
	 * @return the connector id
	 */
	public int getConnectorId() {
		return connectorId;
	}
	
	
	
}
