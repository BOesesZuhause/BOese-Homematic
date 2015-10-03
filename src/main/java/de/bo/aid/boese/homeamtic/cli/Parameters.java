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

import com.beust.jcommander.Parameter;


// TODO: Auto-generated Javadoc
/**
 * This class defines the commandline-parameters.
 */
public class Parameters {
	
	//TODO
	/**  Parameter for the generation of an xml-file with all devices. */
	@Parameter(names = "-generate", description = "Generates XML-File with all responding HomeMatic-Devices")
	private boolean generate = false;
	
	/** The validate. */
	//TODO
	@Parameter(names = "-validate", description = "Validates the content of the xml-file against homematic")
	private boolean validate = false;
	
	/** The config. */
	//TODO file in the same directory
	@Parameter(names = "-config", description = "Path to the config-file", required=true)
	private String config;
	
	
	/** The gen config. */
	//TODO
	@Parameter(names = "-genconfig", description = "Generates a default config file at the location configured with -config")
	private boolean genConfig = false;
	
	/**
	 * Checks if is validate.
	 *
	 * @return true, if is validate
	 */
	public boolean isValidate() {
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

	/**
	 * Checks if is gen config.
	 *
	 * @return true, if is gen config
	 */
	public boolean isGenConfig() {
		return genConfig;
	}



	/**
	 * Checks if is generate.
	 *
	 * @return true, if is generate
	 */
	public boolean isGenerate() {
		return generate;
	}


	


}
