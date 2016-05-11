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

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * hibernate model-class to persist the connector.
 * It is used to store information such as passwords 
 * and ids of the connector.
 */

@Entity
public class Connector implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7737400320190925963L;

	/** The primary key. */
	@Id
	private int conid;
	
	/** The id under which the connector is saved in the distributor. */
	//@Column(name = "idverteiler", unique = false, nullable = false)
	private Integer idverteiler;
	
	/** The secret to authenticate with the distributor. */
	//@Column(name = "password", unique = false, nullable = false)
	private String secret;
	
	/** The name of the connector. */
	//@Column(name = "name", unique = false, nullable = false)
	private String name;
	
	/**
	 * Instantiates a new connector.
	 */
	public Connector() {
	}

	/**
	 * Instantiates a new connector with a given id.
	 *
	 * @param conid the id of the connector
	 */
	public Connector(int conid) {
		this.conid = conid;
	}

	/**
	 * Instantiates a new connector.
	 *
	 * @return the conid
	 */


	public Connector(String name) {
        setName(name);
        setIdverteiler(-1);
        setSecret("");   
    }

    /**
	 * Gets the conid.
	 *
	 * @return the conid
	 */
	public int getConid() {
		return this.conid;
	}

	/**
	 * Sets the conid.
	 *
	 * @param conid the new conid
	 */
	public void setConid(int conid) {
		this.conid = conid;
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
	 * Gets the secret.
	 *
	 * @return the secret
	 */
	public String getSecret() {
		return this.secret;
	}

	/**
	 * Sets the secret.
	 *
	 * @param secret the new secret
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + conid;
        result = prime * result + ((idverteiler == null) ? 0 : idverteiler.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((secret == null) ? 0 : secret.hashCode());
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
        Connector other = (Connector) obj;
        if (conid != other.conid)
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
        if (secret == null) {
            if (other.secret != null)
                return false;
        } else if (!secret.equals(other.secret))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Connector [conid=" + conid + ", idverteiler=" + idverteiler + ", secret=" + secret + ", name=" + name
                + "]";
    }
	
	

}
