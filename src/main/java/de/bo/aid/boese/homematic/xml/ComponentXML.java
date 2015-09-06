/*
 * 
 */
package de.bo.aid.boese.homematic.xml;

import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/**
 * The Class ComponentXML.
 */
@XmlRootElement(name = "component")
public class ComponentXML {
	
	/** The description. */
	private String description;
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/** The name. */
	private String name;
	
	/** The aktor. */
	private boolean aktor;
	
	/** The unit. */
	private String unit;
	
	/** The type. */
	private String type;
	
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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
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
	
}
