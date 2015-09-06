/*
 * 
 */
package de.bo.aid.boese.homematic.model;
// TODO: Auto-generated Javadoc
// default package
// Generated 31.08.2015 00:11:54 by Hibernate Tools 4.3.1

/**
 * Component generated by hbm2java.
 */
public class Component implements java.io.Serializable {





	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Component [compid=" + compid + ", idverteiler=" + idverteiler + ", name=" + name + ", address="
				+ address + ", hm_id=" + hm_id + ", unit=" + unit + ", aktor=" + aktor + ", type=" + type + "]";
	}

	/** The compid. */
	private int compid;
	
	/** The device. */
	private Device device;
	
	/** The idverteiler. */
	private Integer idverteiler;
	
	/** The name. */
	private String name;
	
	/** The address. */
	private String address;
	
	/** The hm_id. */
	private String hm_id;
	
	/** The unit. */
	private String unit;
	
	/** The aktor. */
	private boolean aktor;
	
	/** The type. */
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
	 */
	public Component(int compid, Device device) {
		this.compid = compid;
		this.device = device;
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
