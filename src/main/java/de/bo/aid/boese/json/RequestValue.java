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

// TODO: Auto-generated Javadoc
/**
 * The Class RequestValue.
 */
public class RequestValue extends BoeseJson {
	
	/** The device id. */
	private int deviceId;
	
	/** The device component id. */
	private int deviceComponentId;

	/**
	 * Instantiates a new request value.
	 *
	 * @param deviceId the device id
	 * @param deviceComponentId the device component id
	 * @param connectorId the connector id
	 * @param seqNr the seq nr
	 * @param ackNr the ack nr
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public RequestValue(int deviceId, int deviceComponentId, int connectorId, int seqNr, int ackNr, int status, long timestamp) {
		super(MessageType.REQUESTVALUE, connectorId, seqNr, ackNr, status, timestamp);
		this.deviceId = deviceId;
		this.deviceComponentId = deviceComponentId;
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
	 * Gets the device component id.
	 *
	 * @return the device component id
	 */
	public int getDeviceComponentId() {
		return deviceComponentId;
	}
}
