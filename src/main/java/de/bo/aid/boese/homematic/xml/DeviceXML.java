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
package de.bo.aid.boese.homematic.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The model class for devices to save them to an xml-file via jaxb.
 */
@XmlRootElement( name = "Device")
public class DeviceXML {
	
	/**  The model of the device (equivalent to the homematic-modellnumber). */
	private String model;
	
	private String name;
	
	/** The firmware-version of the device. */
	private String firmware;
	
	/** A list of all channels of the device. */
	private List<ChannelXML> channels = new ArrayList<>();
	
	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	
	/**
	 * Sets the model.
	 *
	 * @param model the new model
	 */
	public void setModel(String model) {
		this.model = model;
	}
	
	/**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
	 * Gets the firmware.
	 *
	 * @return the firmware
	 */
	public String getFirmware() {
		return firmware;
	}
	
	/**
	 * Sets the firmware.
	 *
	 * @param firmware the new firmware
	 */
	public void setFirmware(String firmware) {
		this.firmware = firmware;
	}
	
	/**
	 * Gets the channels.
	 *
	 * @return the channels
	 */
	public List<ChannelXML> getChannels() {
		return channels;
	}
	
	/**
	 * Sets the channels.
	 *
	 * @param channels the new channels
	 */
	@XmlElement( name = "channel" )
	public void setChannels(List<ChannelXML> channels) {
		this.channels = channels;
	}
	

}
