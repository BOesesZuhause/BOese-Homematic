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

import java.util.HashSet;

// TODO: Auto-generated Javadoc
/**
 * The Class UserSendDevices.
 */
public class UserSendDevices extends BoeseJson {
	
	/** The device list. */
	HashSet<UserDevice> deviceList;

	/**
	 * Instantiates a new user send devices.
	 *
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public UserSendDevices(int connectorId, int status,
			long timestamp) {
		super(MessageType.USERSENDDEVICES, connectorId, status, timestamp);
		deviceList = new HashSet<>();
	}
	
	/**
	 * Instantiates a new user send devices.
	 *
	 * @param deviceList the device list
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public UserSendDevices(HashSet<UserDevice> deviceList, int connectorId, int status,
			long timestamp) {
		super(MessageType.USERSENDDEVICES, connectorId, status, timestamp);
		this.deviceList = deviceList;
	}
	
	/**
	 * Adds the device.
	 *
	 * @param device the device
	 */
	public void addDevice(UserDevice device) {
		deviceList.add(device);
	}
	
	/**
	 * Gets the devices.
	 *
	 * @return the devices
	 */
	public HashSet<UserDevice> getDevices() {
		return deviceList;
	}
}
