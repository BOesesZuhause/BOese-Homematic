package de.bo.aid.boese.homematic.cli.validators;

import org.apache.commons.validator.UrlValidator;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class URLValidator implements IParameterValidator {

	@Override
	public void validate(String name, String value) throws ParameterException {
		String[] schemes = { "http", "https", "ws", "wss" }; // Protocols
		UrlValidator validator = new UrlValidator(schemes);
		if (!validator.isValid(value)) {
			throw new ParameterException("Parameter " + name + " is no valid URI");
		}
	}

}
