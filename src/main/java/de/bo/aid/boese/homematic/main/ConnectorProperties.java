
package de.bo.aid.boese.homematic.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.bo.aid.boese.homeamtic.cli.Parameters;

// TODO: Auto-generated Javadoc
/**
 * The Class represents the properties-file of the HomeMatic-Connector. 
 * It contains all available properties as member variables. 
 */
@SuppressWarnings("serial")
public class ConnectorProperties extends Properties{
    
    /** The devices file. */
    private final String DEVICES_FILE = "known_devices_file_path";
       
    /** The url of the distributor. */
    private final String DISTRIBUTOR_URL = "distributor_url";
    
    /** The name of the connector. */
    private final String NAME = "connector_name";
    
    /** The homematic. */
    private final String HOMEMATIC = "homematic_url";
    
    /** The tls. */
    private final String TLS = "tls_enabled";
    
    private final String VALIDATE = "validate_devices_file";
    
    private final String HM_ID = "homematic_clientId";
    
    private final String OWN_IP = "own-ip-address";
    /** The logger. */
    final  Logger logger = LogManager.getLogger(ConnectorProperties.class);
    
    /**
     * Validate.
     *
     * @return true, if successful
     */
    private boolean validate(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator(); 
        Set<ConstraintViolation<ConnectorProperties>> constraintViolations =validator.validate(this);
        for(ConstraintViolation<ConnectorProperties> violation : constraintViolations ){
            logger.error(violation.getPropertyPath() + " " + violation.getMessage());
        }
        return constraintViolations.isEmpty();
    }
    
  /**
   * This method loads the properties file from the given path. 
   * The parameters are validated automatically. The connector shuts down if an error occured.
   *
   * @param path the path to the settings-file.
   */
    public void load(String path) throws FileNotFoundException{
        FileInputStream file = null;
        
            file = new FileInputStream(path);
        

        // load all the properties from the file
        try {
            this.load(file);
        } catch (IOException e) {
            logger.error("IO-Exception while loading config-file", e);
            System.exit(0);
        }

        // close the file handle
        try {
            file.close();
        } catch (IOException e) {
            logger.error("IO-Exception while closing config-file", e);
            System.exit(0);
        }
//        if(!validate()){
//            System.exit(0);
//        }
    }
    
    /**
     * Saves the current settings to a file. It can be used
     * to generate a new settings-file with default-values.
     *
     * @param path the path to the settings file.
     */
    public void save(String path){
        OutputStream output = null;
        try {
            output = new FileOutputStream(path);
        } catch (FileNotFoundException e) {
            logger.error("Could not open file: " + path);
            e.printStackTrace();
            System.exit(0);
        }

        try {
            this.store(output, null);
        } catch (IOException e) {
            logger.error("IO-Exception while saving default properties-file");
            e.printStackTrace();
            System.exit(0);
        }
    }

    
    public void setDefaultsIfNotExist(String path){
    	boolean change = false;
    	if(this.getDevicesFile() == null){
        	this.setDevicesFile(Parameters.DEFAULT_DEVICES);
        	logger.warn(DEVICES_FILE + " was not set. Using default value");
        	change = true;
    	}
    	if(this.getDistributorURL() == null){
        	this.setDistributorURL(Parameters.DEFAULT_DURL);
        	logger.warn(DISTRIBUTOR_URL + " was not set. Using default value");
        	change = true;
    	}
    	if(this.getHMClientID() == null){
        	this.setHMClientID(Parameters.DEFAULT_HMID);
        	logger.warn(HM_ID + " was not set. Using default value");
        	change = true;
    	}
    	if(this.getHomematicURL() == null){
        	this.setHomematicURL(Parameters.DEFAULT_HMURL);	
        	logger.warn(HOMEMATIC + " was not set. Using default value");
        	change = true;
    	}
    	if(this.getValidate() == null){
        	this.setValidate(Parameters.DEFAULT_VALIDATE);
        	logger.warn(VALIDATE + " was not set. Using default value");
        	change = true;
    	}
    	if(this.getTLS() == null){
        	this.setTLS(Parameters.DEFAULT_TLS);
        	logger.warn(TLS + " was not set. Using default value");
        	change = true;
    	}
    	if(this.getName() == null){
    		this.setName(Parameters.DEFAULT_NAME);
    		change = true;
    	}
    	if(this.getOwnIp() == null){
    		this.setOwnIp(Parameters.DEFAULT_IP);
    		change = true;
    	}
    	
        if(!validate()){
            System.exit(0);
        }
    	
    	if(change){
    		this.save(path);
    	}
    }
    
    public void addParams(Parameters params){
    	boolean change = false;
    	if(!params.getDevices().equals(Parameters.DEFAULT_DEVICES)){
    		this.setDevicesFile(params.getDevices());
    		change = true;
    	}
    	if(!params.getdURL().equals(Parameters.DEFAULT_DURL)){
        	this.setDistributorURL(params.getdURL());
        	change = true;
    	}
    	if(!params.getHmURL().equals(Parameters.DEFAULT_HMURL)){
        	this.setHomematicURL(params.getHmURL());   
        	change = true;
    	}
    	if(params.isTls() != Parameters.DEFAULT_TLS){
        	this.setTLS(params.isTls());  
        	change = true;
    	}
    	if(params.isValidate() != Parameters.DEFAULT_VALIDATE){
        	this.setValidate(params.isValidate()); 
        	change = true;
    	}
    	if(params.getHmID() != Parameters.DEFAULT_HMID){
        	this.setHMClientID(params.getHmID());    
        	change = true;
    	}
    	if(!params.getOwnIp().equals(Parameters.DEFAULT_IP)){
    		this.setOwnIp(params.getOwnIp());
    		change = true;
    	}
    	
        if(!validate()){
            System.exit(0);
        }
    	
    	if(change){
    		this.save(params.getConfig());
    		logger.info("Saved new values from parameters to settings-file");
    	}
    }
    
    /**
     * Sets the value of the devices-file-attribute.
     *
     * @param path the new value of the devices-file-attribute.
     */
    public void setDevicesFile(String path){
        this.setProperty(DEVICES_FILE, path);
    }
    
    /**
     * Gets the value of the devices-file-attribute.
     *
     * @return the value of the devices-file-attribute.
     */
    @NotNull
    public String getDevicesFile(){
        return this.getProperty(DEVICES_FILE);
    }
    
    public void setValidate(boolean validate){
    	this.setProperty(VALIDATE, validate + "");
    }
    
    @NotNull
    public Boolean getValidate(){
    	if(this.getProperty(VALIDATE) == null){
    		return null;
    	}else{
        	return Boolean.parseBoolean(this.getProperty(VALIDATE));
    	}
    }
    
    /**
     * Sets the value of the distributor url-attribute.
     *
     * @param path the new value of the distributor url-attribute.
     */
    public void setDistributorURL(String path){
        this.setProperty(DISTRIBUTOR_URL, path);
    }
    
    /**
     * Gets the value of the distributor url-attribute.
     *
     * @return the value of the distributor url-attribute.
     */
    @NotNull
    public String getDistributorURL(){
        return this.getProperty(DISTRIBUTOR_URL);
    }
    
    /**
     * Sets the value of the homematic url-attribute.
     *
     * @param path the new value of the homematic url-attribute.
     */
    public void setHomematicURL(String path){
        this.setProperty(HOMEMATIC, path);
    }
    
    /**
     * Gets the value of the homematic url-attribute.
     *
     * @return the value of the homematic url-attribute.
     */
    @NotNull
    public String getHomematicURL(){
        return this.getProperty(HOMEMATIC);
    }
    
    /**
     * Sets the option for the name-attribute.
     *
     * @param name the new name.
     */
    public void setName(String name){
        this.setProperty(NAME, name);
    }
    
    /**
     * Gets the name.
     *
     * @return the name
     */
    @NotNull
    public String getName(){
        return this.getProperty(NAME);
    }
    
    /**
     * Sets the option for the homematic_client_id-attribute.
     *
     * @param id the value for the homematic_client_id-attribute-atribute
     */
    public void setHMClientID(int id){
        this.setProperty(HM_ID, id + "");
    }
    
    
    /**
     * Gets the option for the homematic_client_id-attribute.
     *
     * @return  the value for the homematic_client_id-attribute-atribute
     */
    @NotNull
    public Integer getHMClientID(){
    	if(this.getProperty(HM_ID) == null){
    		return null;
    	}else{
            return Integer.parseInt(this.getProperty(HM_ID));	
    	}
    }
    
    /**
     * Sets the option for the tls-attribute.
     *
     * @param tls the value for the tls-atribute
     */
    public void setTLS(boolean tls){
        this.setProperty(TLS, tls + "");
    }
    
    /**
     * Gets the option for tls.
     *
     * @return the value ot the tls-attribute
     */
    @NotNull
    public Boolean getTLS(){
    	if(this.getProperty(TLS) == null){
    		return null;
    	}else{
            return Boolean.parseBoolean(this.getProperty(TLS));
    	}
    }
    
    public void setOwnIp(String ip){
    	this.setProperty(OWN_IP, ip);
    }
    
    @NotNull
    public String getOwnIp(){
    	if(this.getProperty(OWN_IP) == null){
    		return null;
    	}else{
    		return this.getProperty(OWN_IP);
    	}
    }
}