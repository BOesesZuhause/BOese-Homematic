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

import de.bo.aid.boese.homematic.model.Device;

// TODO: Auto-generated Javadoc
/**
 * Mapping class for Devices.
 */
public class DeviceMapper {
	
	/**
	 * Maps the homematic devices to the device-model.
	 *
	 * @param obj the result-object from the listdevices request
	 * @param ignoreVirtual If true virtual devices are ignored
	 * @return a list of Devices
	 */
	public static List<Device> map(Object obj, boolean ignoreVirtual){
		Object[] devices = (Object[]) obj;
		List<Device> out = new ArrayList<>();
		
		for(Object device : devices){
			@SuppressWarnings("unchecked")
			HashMap<String, Object> map = (HashMap<String, Object>) device;
			
			
			String address = (String)map.get("ADDRESS");
			//ignore channels
			if(address.contains(":")){
				continue;
			}
			
			//ignore virtual devices
			if(ignoreVirtual && address.startsWith("BidCoS")){
					continue;
			}
			
			//interessante Attribute holen
			String type = (String)map.get("TYPE");
			String firmware = (String)map.get("FIRMWARE");
			int version = (int)map.get("VERSION");
			
			//TODO add unit from homematic if homematic has one
			Device dev = new Device();
			dev.setAdress(address);
			dev.setVersion(version);
			dev.setType(type);
			dev.setFirmware(firmware);
			out.add(dev);

		}
		return out;	
	}

}
