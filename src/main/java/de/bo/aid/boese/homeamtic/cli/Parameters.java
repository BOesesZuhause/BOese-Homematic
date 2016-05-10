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
package de.bo.aid.boese.homeamtic.cli;

import java.net.InetAddress;

import com.beust.jcommander.Parameter;

/**
 * This class defines the commandline-parameters for the
 * HomeMatic-connector.
 */
public class Parameters {
	
	public static final boolean DEFAULT_VALIDATE = false;
	public static final String DEFAULT_DURL = "127.0.0.1:8081/events/";
	public static final String DEFAULT_DEVICES = "Devices.xml";
	public static final int DEFAULT_HMID = 666;
	public static final String DEFAULT_HMURL = "http://homematic-ccu2:2001";
	public static final boolean DEFAULT_TLS = true;
	public static final String DEFAULT_NAME = "BOese-HomeMatic";
	public static final String DEFAULT_IP = createDefaultIP();
	
	private static String createDefaultIP() {
	    try {
	        return InetAddress.getLocalHost().getHostAddress();
	    } catch (final Exception e) {
	        return "127.0.0.1";
	    }  
	}
	
	/**  Describes the path to the config file. */
	@Parameter(names = "-c", description = "Path to the config-file")
	private String config = "settings.properties";
	
	/**  Parameter for the generation of an xml-file with all devices. */
	@Parameter(names = "-generate", description = "Generates XML-File with all responding HomeMatic-Devices")
	private boolean generate = false;
	
	/**  If set, the devices configured in the xml-file are validated against the homematic system. */
	@Parameter(names = "-validate", description = "Validates the content of the xml-file against homematic", arity = 1)
	private boolean validate = DEFAULT_VALIDATE;
	
	/**  Describes the path to the config file. */
	@Parameter(names = "-dURL", description = "The URL of the Distributor.")
	private String dURL = DEFAULT_DURL;
	
	/**  Describes the path to the config file. */
	@Parameter(names = "-d", description = "The path to the devices-file.")
	private String devices = DEFAULT_DEVICES;
	
	/**  Describes the path to the config file. */
	@Parameter(names = "-hmID", description = "The client ID for the connection with HomeMatic.")
	private Integer hmID = DEFAULT_HMID;
	
	/**  Describes the path to the config file. */
	@Parameter(names = "-hmURL", description = "The URL of the Distributor of the HomeMatic CCU.")
	private String hmURL = DEFAULT_HMURL;
	
	/**  Describes the path to the config file. */
	@Parameter(names = "-tls", description = "encrypt the connection to the distributor.", arity = 1)
	private boolean tls = DEFAULT_TLS;
	
	/**  Describes the path to the config file. */
	@Parameter(names = "-ip", description = "The ip of this machine, reachable by the HomeMatic-CCU.", arity = 1)
	private String ownIp = DEFAULT_IP;
	
    @Parameter(names = "-h", description="Display this message", help = true)
    private boolean help = false;
	
	/**
	 * Checks if is validate.
	 *
	 * @return true, if is validate
	 */
	public Boolean isValidate() {
		return validate;
	}

	/**
	 * Gets the config.
	 *
	 * @return the config
	 */
	public String getConfig() {
		return config;
	}
	
	public Integer getHmID(){
		return hmID;
	}



	/**
	 * Checks if is generate.
	 *
	 * @return true, if is generate
	 */
	public boolean isGenerate() {
		return generate;
	}

	public String getdURL() {
		return dURL;
	}

	public String getDevices() {
		return devices;
	}

	public String getHmURL() {
		return hmURL;
	}

	public Boolean isTls() {
		return tls;
	}
	
	public boolean isHelp(){
	    return help;
	}
	
	public String getOwnIp(){
		return ownIp;
	}


	


}
