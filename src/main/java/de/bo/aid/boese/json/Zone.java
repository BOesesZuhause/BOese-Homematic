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
package de.bo.aid.boese.json;

// TODO: Auto-generated Javadoc
/**
 * The Class Zone.
 */
public class Zone {
	
	/** The zone id. */
	private int zoneId;
	
	/** The super zone id. */
	private int superZoneId;
	
	/** The zone name. */
	private String zoneName;
	
	/**
	 * Instantiates a new zone.
	 *
	 * @param zoneId the zone id
	 * @param superZoneId the super zone id
	 * @param name the name
	 */
	public Zone(int zoneId, int superZoneId, String name) {
		this.zoneId = zoneId;
		this.superZoneId = superZoneId;
		this.zoneName = name;
	}

	/**
	 * Gets the zone id.
	 *
	 * @return the zone id
	 */
	public int getZoneId() {
		return zoneId;
	}

	/**
	 * Gets the super zone id.
	 *
	 * @return the super zone id
	 */
	public int getSuperZoneId() {
		return superZoneId;
	}

	/**
	 * Gets the zone name.
	 *
	 * @return the zone name
	 */
	public String getZoneName() {
		return zoneName;
	}
	
}
