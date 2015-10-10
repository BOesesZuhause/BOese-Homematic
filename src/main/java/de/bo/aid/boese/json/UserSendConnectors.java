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

import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class UserSendConnectors.
 */
public class UserSendConnectors extends BoeseJson{
	
	/** The connectors. */
	HashMap<Integer, String> connectors;

	/**
	 * Instantiates a new user send connectors.
	 *
	 * @param connectors the connectors
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public UserSendConnectors(HashMap<Integer, String> connectors, int connectorId, int status,
			long timestamp) {
		super(MessageType.USERSENDCONNETORS, connectorId, status, timestamp);
		this.connectors = connectors;
	}
	
	/**
	 * Gets the connectors.
	 *
	 * @return the connectors
	 */
	public HashMap<Integer, String> getConnectors() {
		return connectors;
	}

}
