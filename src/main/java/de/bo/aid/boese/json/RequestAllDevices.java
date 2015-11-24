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
	 * Checks if is user request.
	 *
	 * @return true, if is user request
	 */
	public boolean isUserRequest() {
		return userRequest;
	}

}
