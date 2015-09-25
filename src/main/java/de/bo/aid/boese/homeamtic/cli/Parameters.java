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
 * <sebasian.lechte@hs-bochum.de> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Sebastian Lechte
 * ----------------------------------------------------------------------------
 */
package de.bo.aid.boese.homeamtic.cli;

import com.beust.jcommander.Parameter;

import de.bo.aid.boese.homematic.cli.validators.URLValidator;

/**
 * This class defines the commandline-parameters.
 */
public class Parameters {
	
	/** Parameter for the generation of an xml-file with all devices */
	@Parameter(names = "-generate", description = "Generates XML-File with all responding HomeMatic-Devices")
	private boolean generate = false;
	
	/** Parameter for the distributor-url */
	@Parameter(names = "-durl", description = "The URL of the Distributor", validateWith = URLValidator.class)
	private String durl;
	
	/** Parameter for the homematic-url */
	@Parameter(names = "-hmurl", description = "The URL of the HomeMatic-XMLRPC-Server", required = true, validateWith = URLValidator.class)
	private String hmurl;

	/**
	 * Checks if is generate.
	 *
	 * @return true, if is generate
	 */
	public boolean isGenerate() {
		return generate;
	}

	/**
	 * Gets the durl.
	 *
	 * @return the durl
	 */
	public String getDurl() {
		return durl;
	}

	/**
	 * Gets the hmurl.
	 *
	 * @return the hmurl
	 */
	public String getHmurl() {
		return hmurl;
	}
	
//	@Parameter(names = "-help", help = true)
//	private boolean help;

}
