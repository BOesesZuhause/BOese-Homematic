/*
 * 
 */
package de.bo.aid.boese.homematic.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/**
 * The Class DevicesXML.
 */
@XmlRootElement( name="devices")
public class DevicesXML {
	
	/** The devices. */
	private List<DeviceXML> devices = new ArrayList<>();;

	/**
	 * Gets the devices.
	 *
	 * @return the devices
	 */
	public List<DeviceXML> getDevices() {
		return devices;
	}
	
	/**
	 * Sets the devices.
	 *
	 * @param devices the new devices
	 */
	@XmlElement( name = "device" )
	public void setDevices(List<DeviceXML> devices) {
		this.devices = devices;
	}
	

}
