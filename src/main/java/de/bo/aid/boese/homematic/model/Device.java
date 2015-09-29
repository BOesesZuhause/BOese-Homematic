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
 * model-class for devices
 */
public class Device implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6215317994521160493L;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Device [devid=" + devid + ", connector=" + connector + ", idverteiler=" + idverteiler + ", adress="
				+ adress + ", type=" + type + ", version=" + version + ", components=" + components + "]";
	}

	/** The primary key. */
	private int devid;
	
	/** The connector. */
	private Connector connector;
	
	/** The id under which the device is saved in the distributor. */
	private Integer idverteiler;
	
	/** The homematic-address of the device. */
	private String adress;
	
	/** The homematic-type. */
	private String type;
	
	/** The homematic-version of the device. */
	private int version;
	
	/** The components of the device. One device can have many components. One component must have one device */
	private Set<Component> components = new HashSet<Component>(0);

	/**
	 * Instantiates a new device.
	 */
	public Device() {
	}

	/**
	 * Instantiates a new device.
	 *
	 * @param devid the devid
	 * @param connector the connector
	 */
	public Device(int devid, Connector connector) {
		this.devid = devid;
		this.connector = connector;
	}



	/**
	 * Gets the devid.
	 *
	 * @return the devid
	 */
	public int getDevid() {
		return this.devid;
	}

	/**
	 * Sets the devid.
	 *
	 * @param devid the new devid
	 */
	public void setDevid(int devid) {
		this.devid = devid;
	}

	/**
	 * Gets the connector.
	 *
	 * @return the connector
	 */
	public Connector getConnector() {
		return this.connector;
	}

	/**
	 * Sets the connector.
	 *
	 * @param connector the new connector
	 */
	public void setConnector(Connector connector) {
		this.connector = connector;
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
	 * Gets the adress.
	 *
	 * @return the adress
	 */
	public String getAdress() {
		return adress;
	}

	/**
	 * Sets the adress.
	 *
	 * @param adress the new adress
	 */
	public void setAdress(String adress) {
		this.adress = adress;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * Gets the components.
	 *
	 * @return the components
	 */
	public Set<Component> getComponents() {
		return this.components;
	}

	/**
	 * Sets the components.
	 *
	 * @param components the new components
	 */
	public void setComponents(Set<Component> components) {
		this.components = components;
	}
	
	/**
	 * Gets a unique name for the device.
	 * Format: HM_"type"_"address"
	 *
	 * @return the unique name of the device
	 */
	public String getName(){
		return "HM_" + type + "_" + adress;
	}

}
