package de.bo.aid.boese.homematic.devices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.bo.aid.boese.homematic.model.*;

public class steckdose {
	
	private List<Component> components = new ArrayList<Component>();
	
	

	public List<Component> getComponents() {
		return components;
	}



	public steckdose(String adress){
				Component comp = new Component();
				comp.setAddress(adress + ":1");
				comp.setAktor(true);
				comp.setIdverteiler(-1);
				comp.setName("Schalten");
				comp.setHM_ID("INSTALL_TEST");				
				components.add(comp);
				
				comp = new Component();
				comp.setAddress(adress + ":2");
				comp.setAktor(false);
				comp.setIdverteiler(-1);
				comp.setName("Spannung");
				comp.setHM_ID("VOLTAGE");
				comp.setUnit("V");
				components.add(comp);
	}
	
}
