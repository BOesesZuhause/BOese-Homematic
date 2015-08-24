package de.bo.aid.boese.homematic.model;

public class Connector {
	
	private int id;
	
	private int idVerteiler;
	
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
