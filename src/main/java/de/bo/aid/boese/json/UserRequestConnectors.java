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
 * The Class UserRequestConnectors.
 */
public class UserRequestConnectors extends BoeseJson{
	
	/** The connector ids. */
	HashSet<Integer> connectorIds;

	/**
	 * Instantiates a new user request connectors.
	 *
	 * @param connectorIds the connector ids
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public UserRequestConnectors(HashSet<Integer> connectorIds, int connectorId, int status,
			long timestamp) {
		super(MessageType.USERREQUESTCONNECTORS, connectorId, status, timestamp);
		this.connectorIds = connectorIds;
	}
	
	/**
	 * Instantiates a new user request connectors.
	 *
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public UserRequestConnectors(int connectorId, int status,
			long timestamp) {
		super(MessageType.USERREQUESTALLCONNECTORS, connectorId, status, timestamp);
		this.connectorIds = null;
	}
	
	/**
	 * Gets the connector ids.
	 *
	 * @return the connector ids
	 */
	public HashSet<Integer> getConnectorIds() {
		return connectorIds;
	}

}
