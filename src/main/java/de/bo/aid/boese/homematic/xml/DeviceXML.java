package de.bo.aid.boese.homematic.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "Device")
public class DeviceXML {
	
	private String model;
	private String firmware;
	private List<ChannelXML> channels;
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getFirmware() {
		return firmware;
	}
	public void setFirmware(String firmware) {
		this.firmware = firmware;
	}
	public List<ChannelXML> getChannels() {
		return channels;
	}
	@XmlElement( name = "channel" )
	public void setChannels(List<ChannelXML> channels) {
		this.channels = channels;
	}
	

}
