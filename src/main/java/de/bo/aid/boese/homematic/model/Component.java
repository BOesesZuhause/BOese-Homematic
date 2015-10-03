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
package de.bo.aid.boese.homematic.model;

// TODO: Auto-generated Javadoc
// default package
// Generated 31.08.2015 00:11:54 by Hibernate Tools 4.3.1

/**
 * Model class for components.
 */
public class Component implements java.io.Serializable {

	/** id for serialisation. */
	private static final long serialVersionUID = -3549055388306323793L;


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Component [compid=" + compid + ", idverteiler=" + idverteiler + ", name=" + name + ", address="
				+ address + ", hm_id=" + hm_id + ", unit=" + unit + ", aktor=" + aktor + ", type=" + type + "]";
	}

	/** The primary ey. */
	private int compid;
	
	/** The device. One Device can have many components. One component must have one device */
	private Device device;
	
	/** The id under which the component is saved in the distributor. */
	private Integer idverteiler;
	
	/** The name of the component. */
	private String name;
	
	/** The homematic-address of the component. */
	private String address;
	
	/** The homematic-id. */
	private String hm_id;
	
	/** The unit of the component. */
	private String unit;
	
	/** Defines whether the component is an acrot. */
	private boolean aktor;
	
	/** The homematic-type of the component. Values: ACTION, FLOAT, DOUBLE, BOOLEAN, INT */
	private String type;
	

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}


	/**
	 * Gets the unit.
	 *
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * Sets the unit.
	 *
	 * @param unit the new unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * Checks if is aktor.
	 *
	 * @return true, if is aktor
	 */
	public boolean isAktor() {
		return aktor;
	}

	/**
	 * Sets the aktor.
	 *
	 * @param aktor the new aktor
	 */
	public void setAktor(boolean aktor) {
		this.aktor = aktor;
	}

	/**
	 * Instantiates a new component.
	 */
	public Component() {
	}


	/**
	 * Instantiates a new component.
	 *
	 * @param compid the compid
	 * @param device the device
	 * @param idverteiler the idverteiler
	 * @param name the name
	 */
	public Component(int compid, Device device, Integer idverteiler, String name) {
		this.compid = compid;
		this.device = device;
		this.idverteiler = idverteiler;
		this.name = name;
	}

	/**
	 * Gets the compid.
	 *
	 * @return the compid
	 */
	public int getCompid() {
		return this.compid;
	}

	/**
	 * Sets the compid.
	 *
	 * @param compid the new compid
	 */
	public void setCompid(int compid) {
		this.compid = compid;
	}

	/**
	 * Gets the device.
	 *
	 * @return the device
	 */
	public Device getDevice() {
		return this.device;
	}

	/**
	 * Sets the device.
	 *
	 * @param device the new device
	 */
	public void setDevice(Device device) {
		this.device = device;
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
	 * Gets the hm_id.
	 *
	 * @return the hm_id
	 */
	public String getHm_id() {
		return hm_id;
	}

	/**
	 * Sets the hm_id.
	 *
	 * @param hm_id the new hm_id
	 */
	public void setHm_id(String hm_id) {
		this.hm_id = hm_id;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type=type;		
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	

}
