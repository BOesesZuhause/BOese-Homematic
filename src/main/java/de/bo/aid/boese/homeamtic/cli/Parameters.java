package de.bo.aid.boese.homeamtic.cli;

import com.beust.jcommander.Parameter;

import de.bo.aid.boese.homematic.cli.validators.URLValidator;

public class Parameters {
	
	@Parameter(names = "-generate", description = "Generates XML-File with all responding HomeMatic-Devices")
	private boolean generate = false;
	
	@Parameter(names = "-durl", description = "The URL of the Distributor", validateWith = URLValidator.class)
	private String durl;
	
	@Parameter(names = "-hmurl", description = "The URL of the HomeMatic-XMLRPC-Server", required = true, validateWith = URLValidator.class)
	private String hmurl;

	public boolean isGenerate() {
		return generate;
	}

	public String getDurl() {
		return durl;
	}

	public String getHmurl() {
		return hmurl;
	}
	
//	@Parameter(names = "-help", help = true)
//	private boolean help;

}
