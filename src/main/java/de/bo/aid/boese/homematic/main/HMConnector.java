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
package de.bo.aid.boese.homematic.main;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import de.bo.aid.boese.homeamtic.cli.Parameters;
import de.bo.aid.boese.homematic.dao.ComponentDao;
import de.bo.aid.boese.homematic.dao.ConnectorDao;
import de.bo.aid.boese.homematic.dao.DeviceDao;
import de.bo.aid.boese.homematic.model.Component;
import de.bo.aid.boese.homematic.model.Connector;
import de.bo.aid.boese.homematic.model.Device;
import de.bo.aid.boese.homematic.socket.SocketClient;
import de.bo.aid.boese.homematic.xml.ChannelXML;
import de.bo.aid.boese.homematic.xml.ComponentXML;
import de.bo.aid.boese.homematic.xml.DeviceXML;
import de.bo.aid.boese.homematic.xml.DevicesXML;
import de.bo.aid.boese.homematic.xmlrpc.XMLRPCClient;
import de.bo.aid.boese.homematic.xmlrpc.XMLRPCServer;


// TODO: Auto-generated Javadoc
/**
 * Mainclass for the connector.
 */
public class HMConnector {
	
	/** The client. */
	private XMLRPCClient client;
	
	XMLRPCServer XMLserver;
	
	/** The server. */
	private SocketClient socketClient;
	
	/** The durl. */
	private String durl;
	
	/** The hmurl. */
	private String hmurl;
	
	/** The config path. */
	private String configFilePath;
	
	/** The name. */
	private String name;
	
	private String devFilePath;
	
	private boolean validate;
	
	
	/**
	 * Gets the durl.
	 *
	 * @return the durl
	 */
	public String getDurl() {
		return durl;
	}

	/**
	 * Sets the durl.
	 *
	 * @param durl the new durl
	 */
	public void setDurl(String durl) {
		this.durl = durl;
	}

	/**
	 * Gets the hmurl.
	 *
	 * @return the hmurl
	 */
	public String getHmurl() {
		return hmurl;
	}

	/**
	 * Sets the hmurl.
	 *
	 * @param hmurl the new hmurl
	 */
	public void setHmurl(String hmurl) {
		this.hmurl = hmurl;
	}

	/** The Constant logger for log4j. */
	final static Logger logger = Logger.getLogger(HMConnector.class);
	
	/** The object-reprasentation of the xml-file with known devices. */
	DevicesXML xml;
	
	/**
	 * The main method. Starts the connector
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){
		
		HMConnector connector = new HMConnector();
		
		connector.checkArguments(args);	
		connector.loadProperties();		//TODO	validate the config		
		connector.saveSettings();
		connector.loadXML(); 
		connector.initializeXMLRPCClient();	
		List<Device> devices = connector.getDevices();		
		connector.initDatabase(devices);		
		connector.initWebsocketServer();						
		connector.startFlow();
		//TODO wait until flow with the distributor is finished before starting xmlrpc-server
		connector.initXMLRPCServer();

	}

	/**
	 * Start flow.
	 */
	private void startFlow() {
		socketClient.requestConnection();
	}

	/**
	 * Inits the xmlrpc server.
	 */
	private void initXMLRPCServer() {
		XMLserver = new XMLRPCServer();
		XMLserver.start();
		try {
			client.sendInit(InetAddress.getLocalHost().getHostAddress() + ":" + XMLserver.getPort());
		} catch (UnknownHostException e1) {
			logger.error("Unknown Host: " + e1.getMessage());
			e1.printStackTrace();
			System.exit(0);
		}		
	}

	/**
	 * Inits the websocket server.
	 */
	private void initWebsocketServer() {
		socketClient = SocketClient.getInstance();
		socketClient.start(durl);	//returns when server is started http://stackoverflow.com/questions/4483928/is-an-embedded-jetty-server-guaranteed-to-be-ready-for-business-when-the-call	
	}
	
	/**
	 * Save settings.
	 */
	private void saveSettings(){
		Connector con = null;
		try { //check if connector is already saved in database

			con = ConnectorDao.getConnector();
			
				if(con == null){
					//insert defaultConnector 
					con = new Connector();
					con.setName(name);
					con.setIdverteiler(-1);
					con.setSecret(null); 
					ConnectorDao.insertConnector(con);
					logger.info("created initial data for connector in database");
			}
		} catch (Exception e) {
			logger.error("Unable to load connector details from database");
			e.printStackTrace();
		}
	}

	/**
	 * Inits the database.
	 *
	 * @param devices the devices
	 */
	private void initDatabase(List<Device> devices) {
		
		
		//check device and insert
		for (Device device : devices){
			Device deviceDB = DeviceDao.getByAddress(device.getAdress());
			if(deviceDB == null){
				device.setIdverteiler(-1);
				DeviceDao.insertDevice(device);
				
				//insert all components of new device
				for(Component comp : device.getComponents()){
					comp.setIdverteiler(-1);
					ComponentDao.insertComponent(comp);
				}
			}else{
				//check if new components exist for device
				//TODO test
				for(Component comp : device.getComponents()){
					if(ComponentDao.getComponentByAddressAndName(comp.getAddress(), comp.getName()) == null){
						comp.setIdverteiler(-1);
						comp.setDevice(deviceDB);
						ComponentDao.insertComponent(comp);
					}
				}
			}
		}
		
		
	}
	

	/**
	 * Gets the devices.
	 *
	 * @return the devices
	 */
	private List<Device> getDevices() {
		List<Device> devices = client.getHMDevices();
		devices = filterDevices(devices);
		return devices;
	}

	/**
	 * Initialize xmlrpc client.
	 */
	private void initializeXMLRPCClient() {
		client = XMLRPCClient.getInstance();
		client.init(hmurl);	
	}

	/**
	 * Filter devices.
	 *
	 * @param devices the devices
	 * @return the list
	 */
	private List<Device> filterDevices(List<Device> devices) {
		//TODO Validierung der XML-Eingaben mittels Abfragen am System
		List<Device> out = new ArrayList<>();
		
		xml:	for(DeviceXML devXML : xml.getDevices()){	
				for(Device dev : devices){
				if(devXML.getModel().equals(dev.getType())){

					for(ChannelXML channelXML : devXML.getChannels()){
						int channelID = channelXML.getNumber();
						for(ComponentXML compXML : channelXML.getComponents()){
							if(validate){
								//TODO check if components in xml match the components in homematic
							}
							Component comp = new Component();
							comp.setDevice(dev);
							comp.setAddress(dev.getAdress() + ":" + channelID);
							comp.setAktor(compXML.isAktor());
							comp.setName(compXML.getDescription() + ":" + channelID);
							comp.setHm_id(compXML.getName());
							comp.setType(compXML.getType());
							dev.getComponents().add(comp);
							logger.info("Added Component via XML: " + comp);
						}
					}
					out.add(dev);
					logger.info("Added Device via XML: " + dev);
					continue xml;
				}else{
				}
				
			}	
				logger.info("Device: " + devXML.getModel() + " defined in xml not found in homematic");
	
		}
		return out;
		
	}
	
	/**
	 * Load xml.
	 *
	 * @param location the location
	 */
	private void loadXML(){
		try {
			File file = new File(devFilePath);
			JAXBContext context = JAXBContext.newInstance( DevicesXML.class );
			Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
			xml = (DevicesXML)jaxbUnmarshaller.unmarshal(file);
		} catch (Exception e) {
			logger.error("Failed to load xml-file with devices");
			e.printStackTrace();
		}
	}


	/**
	 * Check arguments.
	 *
	 * @param args the args
	 */
	private void checkArguments(String[] args){
		
		Parameters params = new Parameters();
		JCommander cmd = new JCommander(params);
		
		try {
	        cmd.parse(args);

	    } catch (ParameterException ex) {
	        System.out.println(ex.getMessage());
	        cmd.usage();
	        System.exit(0);
	    }
		
		configFilePath = params.getConfig();
		validate = params.isValidate();
		
		
		if(params.isGenConfig()){
			createDefaultProperties();
			logger.info("created default properties-file at: " + configFilePath);
			System.exit(0);
		}
		
		if(params.isGenerate()){
			generateXML();
			logger.info("created xml-file with all devices found");
			System.exit(0);
		}
		
		
	}
	
	private void generateXML() {
	 
			 	DevicesXML devicesXML= client.getDevicesAsXML();
			 	

			 	
		        JAXBContext ctx = null;
				try {
					ctx = JAXBContext.newInstance(devicesXML.getClass());
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        Marshaller marshaller = null;
				try {
					marshaller = ctx.createMarshaller();
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        try {
					marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				} catch (PropertyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        FileOutputStream out = null;
				try {
					out = new FileOutputStream(devFilePath);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        try {
					marshaller.marshal(devicesXML, out);
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}

	/**
	 * Load properties.
	 */
	private void loadProperties(){

	    Properties props = new Properties();
	    FileInputStream file = null;

	    //load the file handle
	    try {
			file = new FileInputStream(configFilePath);
		} catch (FileNotFoundException e) {
			logger.error("config File not found at: " + configFilePath);
			e.printStackTrace();
			System.exit(0);
		}

	    //load all the properties from the file
	    try {
			props.load(file);
		} catch (IOException e) {
			logger.error("IO-Exception while loading config-file");
			e.printStackTrace();
			System.exit(0);
		}

	    //close the file handle
	    try {
			file.close();
		} catch (IOException e) {
			logger.error("IO-Exception while closing config-file");
			e.printStackTrace();
			System.exit(0);
		}

	    //retrieve the properties
	    hmurl = props.getProperty("HomematicURL");
	    durl = props.getProperty("DistributorURL");
	    name = props.getProperty("ConnectorName");
	    devFilePath = props.getProperty("KnownDevicesFilePath");
	}
	
	/**
	 * Creates the default properties.
	 */
	private void createDefaultProperties(){
		Properties prop = new Properties();
		OutputStream output = null;
		
		prop.setProperty("HomematicURL", "http://example.org:9090");
		prop.setProperty("DistributorURL", "ws://example.org:8080/events");
		prop.setProperty("ConnectorName", "HomematicConnector");
		prop.setProperty("KnownDevicesFilePath", "Devices.xml");
		
		try {
			output = new FileOutputStream(configFilePath);
		} catch (FileNotFoundException e) {
			logger.error("Could not open file: " + configFilePath);
			e.printStackTrace();
			System.exit(0);
		}
		
		try {
			prop.store(output, null);
		} catch (IOException e) {
			logger.error("IO-Exception while saving default properties-file");
			e.printStackTrace();
			System.exit(0);
		}
	}
	


}
