package de.bo.aid.boese.homematic.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name="devices")
public class DevicesXML {
	
	private List<DeviceXML> devices;

	public List<DeviceXML> getDevices() {
		return devices;
	}
	@XmlElement( name = "device" )
	public void setDevices(List<DeviceXML> devices) {
		this.devices = devices;
	}
	

}
