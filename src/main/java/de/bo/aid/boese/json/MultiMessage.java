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
 * The Class MultiMessage.
 */
public class MultiMessage extends BoeseJson {
	
	/** The boeses. */
	private HashSet<BoeseJson> boeses;
	
	/**
	 * Instantiates a new multi message.
	 *
	 * @param boese the boese
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public MultiMessage(HashSet<BoeseJson> boese, int connectorId, int status, long timestamp) {
		super(MessageType.MULTI, connectorId, status, timestamp);
		this.boeses = boese;
	}
	
	/**
	 * Instantiates a new multi message.
	 *
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public MultiMessage(int connectorId, int status, long timestamp) {
		super(MessageType.MULTI, connectorId, status, timestamp);
		this.boeses = new HashSet<>();
	}
	
	/**
	 * Adds the message.
	 *
	 * @param message the message
	 */
	public void addMessage(BoeseJson message ){
		boeses.add(message);
	}
	
	/**
	 * Gets the messages.
	 *
	 * @return the messages
	 */
	public HashSet<BoeseJson> getMessages() {
		return boeses;
	}


}
