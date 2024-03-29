/*             
 * 			  (                       
 *			 ( )\         (        (   
 *			 )((_)  (    ))\ (    ))\  
 *			((_)_   )\  /((_))\  /((_) 
 *			 | _ ) ((_)(_)) ((_)(_))   
 *			 | _ \/ _ \/ -_)(_-</ -_)  
 *			 |___/\___/\___|/__/\___|
 *       
 *           			;            
 *		      +        ;;;         + 
 *			  +       ;;;;;        + 
 *			  +      ;;;;;;;       + 
 *			  ++    ;;;;;;;;;     ++ 
 *			  +++++;;;;;;;;;;;+++++  
 *			   ++++;;;;;;;;;;;+++++  
 *				++;;;;;;;;;;;;;++    
 *			     ;;;;;;;;;;;;;;;     
 *			    ;;;;;;;;;;;;;;;;;     
 *				:::::::::::::::::    
 * 				:::::::::::::::::      
 *  			:::::::::::::::::    
 *   			::::::@@@@@::::::    
 *				:::::@:::::@:::::    
 *				::::@:::::::@::::    
 * 				:::::::::::::::::    
 *  			:::::::::::::::::      
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <sebastian.lechte@hs-bochum.de> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Sebastian Lechte
 * ----------------------------------------------------------------------------
 */
package de.bo.aid.boese.homematic.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.bo.aid.boese.homematic.model.Component;

/**
 * This class maps raw parametersets from HomeMatic to component-objects
 * of the hibernate model classes for further processing.
 */
public class ComponentMapper {

	/**
	 * Maps the homematic parametersets to components.
	 *
	 * @param obj The answer from the getParamsets-request for Homematic
	 * @return A list of Components mapped from homematic
	 */
	@SuppressWarnings("unchecked")
	public List<Component> map(Object obj) {
		HashMap<String, Object> paramsetDescriptionMap = (HashMap<String, Object>) obj;
		List<Component> out = new ArrayList<>();

		for (Object value : paramsetDescriptionMap.values()) {
			HashMap<String, Object> valueMap = (HashMap<String, Object>) value;
			Component comp = new Component();
			comp.setUnit((String) valueMap.get("UNIT"));
			comp.setType((String) valueMap.get("TYPE"));
			comp.setName((String) valueMap.get("ID"));

			// Schaltaktor
			if (valueMap.get("TYPE").equals("ACTION")) {
				comp.setAktor(true);
			}

			// Sensor
			if (valueMap.get("UNIT") != null) {
				comp.setAktor(false);
			}

			out.add(comp);
		}

		return out;
	}

}
