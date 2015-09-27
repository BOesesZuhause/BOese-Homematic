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
package de.bo.aid.boese.homematic.model;
// default package
// Generated 31.08.2015 00:11:54 by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;


/**
 * model-class for connectors.
 */
public class Connector implements java.io.Serializable {

	/** The primary key. */
	private int conid;
	
	/** The id under which the connector is saved in the distributor. */
	private Integer idverteiler;
	
	/** The secret to authenticate with the distributor. */
	private String secret;
	
	/** The name of the connector. */
	private String name;
	
	/** The devices of the connector. One connector can have many devices. One device must have one connector */
	private Set devices = new HashSet(0);

	/**
	 * Instantiates a new connector.
	 */
	public Connector() {
	}

	/**
	 * Instantiates a new connector.
	 *
	 * @param conid the conid
	 */
	public Connector(int conid) {
		this.conid = conid;
	}

	/**
	 * Instantiates a new connector.
	 *
	 * @param conid the conid
	 * @param idverteiler the idverteiler
	 * @param secret the secret
	 * @param name the name
	 * @param devices the devices
	 */


	/**
	 * Gets the conid.
	 *
	 * @return the conid
	 */
	public int getConid() {
		return this.conid;
	}

	/**
	 * Sets the conid.
	 *
	 * @param conid the new conid
	 */
	public void setConid(int conid) {
		this.conid = conid;
	}

	/**
	 * Gets the idverteiler.
	 *
	 * @return the idverteiler
	 */
	public Integer getIdverteiler() {
		return this.idverteiler;
	}

	/**
	 * Sets the idverteiler.
	 *
	 * @param idverteiler the new idverteiler
	 */
	public void setIdverteiler(Integer idverteiler) {
		this.idverteiler = idverteiler;
	}

	/**
	 * Gets the secret.
	 *
	 * @return the secret
	 */
	public String getSecret() {
		return this.secret;
	}

	/**
	 * Sets the secret.
	 *
	 * @param secret the new secret
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the devices.
	 *
	 * @return the devices
	 */
	public Set getDevices() {
		return this.devices;
	}

	/**
	 * Sets the devices.
	 *
	 * @param devices the new devices
	 */
	public void setDevices(Set devices) {
		this.devices = devices;
	}

}
