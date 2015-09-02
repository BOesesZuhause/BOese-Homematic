package de.bo.aid.boese.homematic.xmlrpc.model;

public class Device {
	
	private String adress;
	private String type;
	private int version;
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "Device [adress=" + adress + ", type=" + type + ", version=" + version + "]";
	}
	


}
