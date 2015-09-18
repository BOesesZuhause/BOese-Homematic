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
 * The Class DeviceXML.
 */
@XmlRootElement( name = "Device")
public class DeviceXML {
	
	/** The model. */
	private String model;
	
	/** The firmware. */
	private String firmware;
	
	/** The channels. */
	private List<ChannelXML> channels = new ArrayList<>();
	
	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	
	/**
	 * Sets the model.
	 *
	 * @param model the new model
	 */
	public void setModel(String model) {
		this.model = model;
	}
	
	/**
	 * Gets the firmware.
	 *
	 * @return the firmware
	 */
	public String getFirmware() {
		return firmware;
	}
	
	/**
	 * Sets the firmware.
	 *
	 * @param firmware the new firmware
	 */
	public void setFirmware(String firmware) {
		this.firmware = firmware;
	}
	
	/**
	 * Gets the channels.
	 *
	 * @return the channels
	 */
	public List<ChannelXML> getChannels() {
		return channels;
	}
	
	/**
	 * Sets the channels.
	 *
	 * @param channels the new channels
	 */
	@XmlElement( name = "channel" )
	public void setChannels(List<ChannelXML> channels) {
		this.channels = channels;
	}
	

}
