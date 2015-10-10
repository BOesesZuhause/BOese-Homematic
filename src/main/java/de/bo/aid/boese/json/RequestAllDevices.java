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
 * The Class RequestAllDevices.
 */
public class RequestAllDevices extends BoeseJson {
	
	/** The user request. */
	boolean userRequest = false;
	/**
	 * Instantiates a new request all devices.
	 *
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public RequestAllDevices(int connectorId, int status, long timestamp) {
		super(MessageType.REQUESTALLDEVICES, connectorId, status, timestamp);
	}
	
	/**
	 * Instantiates a new request all devices.
	 *
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 * @param isUserRequest the is user request
	 */
	public RequestAllDevices(int connectorId, int status, long timestamp, boolean isUserRequest) {
		super(MessageType.USERREQUESTALLDEVICES, connectorId, status, timestamp);
		this.userRequest = isUserRequest;
	}
	
	/**
	 * Checks if is user request.
	 *
	 * @return true, if is user request
	 */
	public boolean isUserRequest() {
		return userRequest;
	}

}
