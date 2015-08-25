package de.bo.aid.boese.homematic.model;

import java.util.HashSet;
import java.util.Set;

public class Connector {
	
	private int id;
	
	private int idVerteiler;
	
	private Set<Device> devices = new HashSet<>();
	
	public Set<Device> getDevices() {
		return devices;
	}

	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}

	public int getIdVerteiler() {
		return idVerteiler;
	}

	public void setIdVerteiler(int idVerteiler) {
		this.idVerteiler = idVerteiler;
	}

	private String name;
	
	private String password;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
