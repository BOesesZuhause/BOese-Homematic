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

@SuppressWarnings("serial")
public class ConnectorProperties extends Properties{
    
    private final String DEVICES_FILE = "KnownDevicesFilePath";
    
    private final String DISTRIBUTOR_URL = "DistributorURL";
    
    private final String NAME = "ConnectorName";
    
    private final String HOMEMATIC = "HomematicURL";
    
    private final String TLS = "tls_enabled";
    
    /** The logger. */
    final  Logger logger = LogManager.getLogger(ConnectorProperties.class);
    
    private boolean validate(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator(); 
        Set<ConstraintViolation<ConnectorProperties>> constraintViolations =validator.validate(this);
        for(ConstraintViolation<ConnectorProperties> violation : constraintViolations ){
            logger.error(violation.getPropertyPath() + " " + violation.getMessage());
        }
        return constraintViolations.isEmpty();
    }
    
  //TODO validate properties
    public void load(String path){
        FileInputStream file = null;
        try {
            file = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            logger.error("config File not found at: " + path, e);
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
    
    public void setDefaults(){
       this.setDevicesFile("Devices.xml");
       this.setDistributorURL("ws:\\\\example.org:8081\\events");
       this.setHomematicURL("http:\\\\example.org:2001");
       this.setTLS(false);
       this.setName("HomematicConnector");
    }
    
    public void setDevicesFile(String path){
        this.setProperty(DEVICES_FILE, path);
    }
    
    public String getDevicesFile(){
        return this.getProperty(DEVICES_FILE);
    }
    
    public void setDistributorURL(String path){
        this.setProperty(DISTRIBUTOR_URL, path);
    }
    
    public String getDistributorURL(){
        return this.getProperty(DISTRIBUTOR_URL);
    }
    
    public void setHomematicURL(String path){
        this.setProperty(HOMEMATIC, path);
    }
    
    public String getHomematicURL(){
        return this.getProperty(HOMEMATIC);
    }
    
    public void setName(String name){
        this.setProperty(NAME, name);
    }
    
    public String getName(){
        return this.getProperty(NAME);
    }
    
    public void setTLS(boolean tls){
        this.setProperty(TLS, tls + "");
    }
    
    public boolean getTLS(){
        return Boolean.parseBoolean(this.getProperty(TLS));
    }
}