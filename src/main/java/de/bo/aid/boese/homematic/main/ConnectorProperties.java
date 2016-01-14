
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
 * The Class ConnectorProperties.
 */
@SuppressWarnings("serial")
public class ConnectorProperties extends Properties{
    
    /** The devices file. */
    private final String DEVICES_FILE = "KnownDevicesFilePath";
    
    /** The distributor url. */
    private final String DISTRIBUTOR_URL = "DistributorURL";
    
    /** The name. */
    private final String NAME = "ConnectorName";
    
    /** The homematic. */
    private final String HOMEMATIC = "HomematicURL";
    
    /** The tls. */
    private final String TLS = "tls_enabled";
    
    private final String HM_ID = "homematic-clientID";
    
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
   * Load.
   *
   * @param path the path
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
     * Save.
     *
     * @param path the path
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
     * Sets the defaults.
     */
    public void setDefaults(){
       this.setDevicesFile("Devices.xml");
       this.setDistributorURL("ws:\\\\example.org:8081\\events");
       this.setHomematicURL("http:\\\\example.org:2001");
       //this.setTLS(true);
       this.setName("HomematicConnector");
       this.setHMClientID(666);
    }
    
    /**
     * Sets the devices file.
     *
     * @param path the new devices file
     */
    public void setDevicesFile(String path){
        this.setProperty(DEVICES_FILE, path);
    }
    
    /**
     * Gets the devices file.
     *
     * @return the devices file
     */
    public String getDevicesFile(){
        return this.getProperty(DEVICES_FILE);
    }
    
    /**
     * Sets the distributor url.
     *
     * @param path the new distributor url
     */
    public void setDistributorURL(String path){
        this.setProperty(DISTRIBUTOR_URL, path);
    }
    
    /**
     * Gets the distributor url.
     *
     * @return the distributor url
     */
    public String getDistributorURL(){
        return this.getProperty(DISTRIBUTOR_URL);
    }
    
    /**
     * Sets the homematic url.
     *
     * @param path the new homematic url
     */
    public void setHomematicURL(String path){
        this.setProperty(HOMEMATIC, path);
    }
    
    /**
     * Gets the homematic url.
     *
     * @return the homematic url
     */
    public String getHomematicURL(){
        return this.getProperty(HOMEMATIC);
    }
    
    /**
     * Sets the name.
     *
     * @param name the new name
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
    
    public void setHMClientID(int id){
        this.setProperty(HM_ID, id + "");
    }
    
    public int getHMClientID(){
        return Integer.parseInt(this.getProperty(HM_ID));
    }
    
    /**
     * Sets the tls.
     *
     * @param tls the new tls
     */
    public void setTLS(boolean tls){
        this.setProperty(TLS, tls + "");
    }
    
    /**
     * Gets the tls.
     *
     * @return the tls
     */
    public boolean getTLS(){
        return Boolean.parseBoolean(this.getProperty(TLS));
    }
}