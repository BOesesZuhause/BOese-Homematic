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
 * <sebasian.lechte@hs-bochum.de> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Sebastian Lechte
 * ----------------------------------------------------------------------------
 */

package de.bo.aid.boese.json;

import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class SendDevices.
 */
public class SendDevices extends BoeseJson {
	
	/** The devices. */
	private HashMap<String, Integer> devices;
	
	/**
	 * Instantiates a new send devices.
	 *
	 * @param devices the devices
	 * @param connectorId the connector id
	 * @param seqNr the seq nr
	 * @param ackNr the ack nr
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public SendDevices(HashMap<String, Integer> devices, int connectorId, int seqNr, int ackNr, int status, long timestamp) {
		super(MessageType.SENDDEVICES, connectorId, seqNr, ackNr, status, timestamp);
		this.devices = devices;
	}

	/**
	 * Gets the devices.
	 *
	 * @return the devices
	 */
	public HashMap<String, Integer> getDevices() {
		return devices;
	}
}
