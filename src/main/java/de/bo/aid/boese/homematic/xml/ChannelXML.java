package de.bo.aid.boese.homematic.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "channel")
public class ChannelXML {
	private int number;
	private List<ComponentXML> components;
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public List<ComponentXML> getComponents() {
		return components;
	}
	@XmlElement( name = "component" )
	public void setComponents(List<ComponentXML> components) {
		this.components = components;
	}
	

}
