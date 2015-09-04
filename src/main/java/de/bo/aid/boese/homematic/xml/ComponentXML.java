package de.bo.aid.boese.homematic.xml;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "component")
public class ComponentXML {
	private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	private String name;
	private boolean aktor;
	private String unit;
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isAktor() {
		return aktor;
	}
	public void setAktor(boolean aktor) {
		this.aktor = aktor;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}
