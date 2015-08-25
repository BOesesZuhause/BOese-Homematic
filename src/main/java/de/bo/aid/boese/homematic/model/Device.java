package de.bo.aid.boese.homematic.model;

import java.util.HashSet;
import java.util.Set;

public class Device {
	
	private int id;
	
	private int idVerteiler;
	
	private String name;
	
	private Set<Component> components = new HashSet<>();

	public Set<Component> getComponents() {
		return components;
	}

	public void setComponents(Set<Component> components) {
		this.components = components;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIdVerteiler() {
		return idVerteiler;
	}

	public void setIdVerteiler(int idVerteiler) {
		this.idVerteiler = idVerteiler;
	}

	

}
