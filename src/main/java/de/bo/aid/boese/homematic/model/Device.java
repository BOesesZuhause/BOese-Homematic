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
package de.bo.aid.boese.homematic.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * hibernate model-class to persist devices.
 * A device represents a distinct homematic device
 * with its parameters.
 */

@Entity
public class Device implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6215317994521160493L;



	/** The primary key. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int devid;
	
	
	/** The id under which the device is saved in the distributor. */
	   @Column(name = "idverteiler", unique = false, nullable = false)
	   private Integer idverteiler;
	
	/** The homematic-address of the device. 
	 * This is used to address and call the device in homematic*/
	   @Column(name = "address", unique = false, nullable = false)
	   private String adress;
	
	/** The homematic-type. It contains the model of the physical
	 * homematic device. */
	   @Column(name = "type", unique = false, nullable = false)
	   private String type;
	
	@Column(name = "name", unique = false, nullable = false)
	private String name;
	
	/** The homematic-version of the device. */
	@Column(name = "version", unique = false, nullable = false)
	private int version;
	
	/** The firmware-version of the device. */
	@Column(name = "firmware", unique = false, nullable = false)
	private String firmware;
	
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

	/** The components of the device. One device can have many components. One component must have one device */
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "device")
	private Set<Component> components = new HashSet<Component>(0);

	/**
	 * Instantiates a new device.
	 */
	public Device() {
	}
	
	public Device(String address, String type, String name, int version, String firmware){
		this.adress = address;
		this.type = type;
		this.name = name;
		this.version = version;
		this.firmware = firmware;
	}

	/**
	 * Gets the devid.
	 *
	 * @return the devid
	 */
	public int getDevid() {
		return this.devid;
	}

	/**
	 * Sets the devid.
	 *
	 * @param devid the new devid
	 */
	public void setDevid(int devid) {
		this.devid = devid;
	}
	
	


	/**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public void setUniqueName(String name){
        this.name = name + " (" + this.adress + ")";
    }

    /**
	 * Gets the idverteiler.
	 *
	 * @return the idverteiler
	 */
	public Integer getIdverteiler() {
		return this.idverteiler;
	}

	/**
	 * Sets the idverteiler.
	 *
	 * @param idverteiler the new idverteiler
	 */
	public void setIdverteiler(Integer idverteiler) {
		this.idverteiler = idverteiler;
	}


	/**
	 * Gets the adress.
	 *
	 * @return the adress
	 */
	public String getAdress() {
		return adress;
	}

	/**
	 * Sets the adress.
	 *
	 * @param adress the new adress
	 */
	public void setAdress(String adress) {
		this.adress = adress;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * Gets the components.
	 *
	 * @return the components
	 */
	public Set<Component> getComponents() {
		return this.components;
	}

	/**
	 * Sets the components.
	 *
	 * @param components the new components
	 */
	public void setComponents(Set<Component> components) {
		this.components = components;
	}
	
	/**
	 * Gets a unique name for the device.
	 * Format: HM_"type"_"address"
	 *
	 * @return the unique name of the device
	 */
	public String getName(){
	    return name;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((adress == null) ? 0 : adress.hashCode());
        result = prime * result + ((components == null) ? 0 : components.hashCode());
        result = prime * result + devid;
        result = prime * result + ((firmware == null) ? 0 : firmware.hashCode());
        result = prime * result + ((idverteiler == null) ? 0 : idverteiler.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + version;
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Device other = (Device) obj;
        if (adress == null) {
            if (other.adress != null)
                return false;
        } else if (!adress.equals(other.adress))
            return false;
        if (components == null) {
            if (other.components != null)
                return false;
        } else if (!components.equals(other.components))
            return false;
        if (devid != other.devid)
            return false;
        if (firmware == null) {
            if (other.firmware != null)
                return false;
        } else if (!firmware.equals(other.firmware))
            return false;
        if (idverteiler == null) {
            if (other.idverteiler != null)
                return false;
        } else if (!idverteiler.equals(other.idverteiler))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        if (version != other.version)
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Device [devid=" + devid + ", idverteiler=" + idverteiler + ", adress=" + adress + ", type=" + type
                + ", name=" + name + ", version=" + version + ", firmware=" + firmware + ", components=" + components
                + "]";
    }
	
	

}
