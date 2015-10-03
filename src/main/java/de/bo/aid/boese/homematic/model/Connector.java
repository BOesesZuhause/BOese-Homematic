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


// TODO: Auto-generated Javadoc
/**
 * model-class for connectors.
 */
public class Connector implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7737400320190925963L;

	/** The primary key. */
	private int conid;
	
	/** The id under which the connector is saved in the distributor. */
	private Integer idverteiler;
	
	/** The secret to authenticate with the distributor. */
	private String secret;
	
	/** The name of the connector. */
	private String name;
	
	/** The hm url. */
	private String hmURL;
	
	/** The distr url. */
	private String distrURL;
	
	/** The known devices file. */
	private String knownDevicesFile;
	
	
	

	/**
	 * Instantiates a new connector.
	 */
	public Connector() {
	}

	/**
	 * Instantiates a new connector.
	 *
	 * @param conid the conid
	 */
	public Connector(int conid) {
		this.conid = conid;
	}

	/**
	 * Instantiates a new connector.
	 *
	 * @return the conid
	 */


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

	/**
	 * Gets the hm url.
	 *
	 * @return the hm url
	 */
	public String getHmURL() {
		return hmURL;
	}

	/**
	 * Sets the hm url.
	 *
	 * @param hmURL the new hm url
	 */
	public void setHmURL(String hmURL) {
		this.hmURL = hmURL;
	}

	/**
	 * Gets the distr url.
	 *
	 * @return the distr url
	 */
	public String getDistrURL() {
		return distrURL;
	}

	/**
	 * Sets the distr url.
	 *
	 * @param distrURL the new distr url
	 */
	public void setDistrURL(String distrURL) {
		this.distrURL = distrURL;
	}

	/**
	 * Gets the known devices file.
	 *
	 * @return the known devices file
	 */
	public String getKnownDevicesFile() {
		return knownDevicesFile;
	}

	/**
	 * Sets the known devices file.
	 *
	 * @param knownDevicesFile the new known devices file
	 */
	public void setKnownDevicesFile(String knownDevicesFile) {
		this.knownDevicesFile = knownDevicesFile;
	}



}
