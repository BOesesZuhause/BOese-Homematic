
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    
    private final String HM_ID = "homematic_clientId";
    
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
  //TODO validate properties
    public void load(String path){
        FileInputStream file = null;
        try {
            file = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            logger.error("config File not found at: " + path, e);
            logger.info("Generating default properties file");
            this.setDefaults();
            this.save(path);
            System.exit(0);
        }

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
        if(!validate()){
            System.exit(0);
        }
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
    
    /**
     * Sets default-values for all attributes..
     */
    public void setDefaults(){
       this.setDevicesFile("Devices.xml");
       this.setDistributorURL("example.org:8081/events");
       this.setHomematicURL("http://example.org:2001");
       this.setTLS(true);
       this.setName("HomematicConnector");
       this.setHMClientID(666);
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
    public String getDevicesFile(){
        return this.getProperty(DEVICES_FILE);
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
    public int getHMClientID(){
        return Integer.parseInt(this.getProperty(HM_ID));
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
    public boolean getTLS(){
        return Boolean.parseBoolean(this.getProperty(TLS));
    }
}