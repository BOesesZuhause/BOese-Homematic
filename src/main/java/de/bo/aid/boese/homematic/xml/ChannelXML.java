/*
 * 
 */
package de.bo.aid.boese.homematic.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/**
 * The Class ChannelXML.
 */
@XmlRootElement( name = "channel")
public class ChannelXML {
	
	/** The number. */
	private int number;
	
	/** The components. */
	private List<ComponentXML> components;
	
	/**
	 * Gets the number.
	 *
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}
	
	/**
	 * Sets the number.
	 *
	 * @param number the new number
	 */
	public void setNumber(int number) {
		this.number = number;
	}
	
	/**
	 * Gets the components.
	 *
	 * @return the components
	 */
	public List<ComponentXML> getComponents() {
		return components;
	}
	
	/**
	 * Sets the components.
	 *
	 * @param components the new components
	 */
	@XmlElement( name = "component" )
	public void setComponents(List<ComponentXML> components) {
		this.components = components;
	}
	

}
