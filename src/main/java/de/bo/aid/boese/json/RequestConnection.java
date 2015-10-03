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
 * The Class RequestConnection.
 */
public class RequestConnection extends BoeseJson {
	
	/** The connector name. */
	private String connectorName;
	
	/** The password. */
	private String password;
	
	/**
	 * Instantiates a new request connection.
	 *
	 * @param name the name
	 * @param password the password
	 * @param idConnector the id connector
	 * @param seqNr the seq nr
	 * @param ackNr the ack nr
	 * @param status the status
	 * @param headerTimestamp the header timestamp
	 */
	public RequestConnection(String name, String password,
			int idConnector, int seqNr, int ackNr, int status, long headerTimestamp) {
		super(MessageType.REQUESTCONNECTION, idConnector, seqNr, ackNr, status, headerTimestamp);
		this.connectorName = name;
		this.password = password;
	}
	
	/**
	 * Gets the connector name.
	 *
	 * @return the connector name
	 */
	public String getConnectorName() {
		return connectorName;
	}
	
	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
}