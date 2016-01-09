
package de.bo.aid.boese.homematic.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import de.bo.aid.boese.homeamtic.cli.Parameters;
import de.bo.aid.boese.homematic.dao.ComponentDao;
import de.bo.aid.boese.homematic.dao.ConnectorDao;
import de.bo.aid.boese.homematic.dao.DeviceDao;
import de.bo.aid.boese.homematic.mapper.DeviceMapper;
import de.bo.aid.boese.homematic.mapper.DevicesXMLMapper;
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
 * The mainclass for the HomeMatic connector.
 */
public class HMConnector {

	/** The confirmed. */
	public static boolean confirmed = false;

    /** The XMLRPC-client. */
	private XMLRPCClient client;

	/** The XMLRPC-Server. */
	XMLRPCServer XMLserver;

	/** The Websocketclient. */
	private SocketClient socketClient;
	
	/** The props. */
	ConnectorProperties props;

	/** The path to the config file. */
	private String configFilePath;


	/** Stores if known devices should be validated. */
	private boolean validate;


	/** The Constant logger for log4j. */
	final static Logger logger = LogManager.getLogger(HMConnector.class);

	/** The object-reprasentation of the xml-file with known devices. */
	DevicesXML xml;

	/**
	 * The main method. Starts the connector
	 *
	 * @param args the commandline-arguments
	 */
	public static void main(String[] args) {

		HMConnector connector = new HMConnector();

		connector.checkArguments(args);
		connector.loadProperties(); 
		connector.saveSettings();
//		connector.loadXML();
//		connector.initializeXMLRPCClient();
//		List<Device> devices = connector.getDevices();
//		connector.initDatabase(devices);
		connector.initWebsocketServer();
		connector.startFlow();
//		connector.initXMLRPCServer();
	}

	/**
	 * Start flow with the distributor by requesting a connection.
	 */
	private void startFlow() {
		socketClient.requestConnection();
	}

	/**
	 * Inits the xmlrpc server and register it with the homematic system.
	 */
	private void initXMLRPCServer() {
		XMLserver = new XMLRPCServer();
		XMLserver.start();
		client.sendInit(getOwnIP() + ":" + XMLserver.getPort());
	}

	/**
	 * Inits the websocket client and starts it.
	 */
	private void initWebsocketServer() {
	    socketClient = SocketClient.getInstance();
		socketClient.start(props.getDistributorURL()); // returns when server is started
									// http://stackoverflow.com/questions/4483928/is-an-embedded-jetty-server-guaranteed-to-be-ready-for-business-when-the-call
	}

	/**
	 * Save the settings for the connector in the database.
	 */
	private void saveSettings() {
		Connector con = null;
		try { // check if connector is already saved in database

			con = ConnectorDao.getConnector();

			if (con == null) {
				// insert defaultConnector
				con = new Connector();
				con.setName(props.getName());
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
	 * Initalises the database and saves new devices.
	 *
	 * @param devices the devices returned from HomeMatic
	 */
	private void initDatabase(List<Device> devices) {

		// check device and insert
		for (Device device : devices) {
			Device deviceDB = DeviceDao.getByAddress(device.getAdress());
			if (deviceDB == null) {
				device.setIdverteiler(-1);
				DeviceDao.insertDevice(device);

				// insert all components of new device
				for (Component comp : device.getComponents()) {
						comp.setIdverteiler(-1);
						ComponentDao.insertComponent(comp);
				}
			} else {
				// check if new components exist for device
				// TODO test
				for (Component comp : device.getComponents()) {
					if (ComponentDao.getComponentByAddressAndName(comp.getAddress(), comp.getHm_id()) == null) {
						comp.setIdverteiler(-1);
						comp.setDevice(deviceDB);
						ComponentDao.insertComponent(comp);
					}
				}
			}
		}

	}

	/**
	 * Gets all devices from HomeMatic.
	 *
	 * @return the devices
	 */
	private List<Device> getDevices() {
		List<Device> devices = DeviceMapper.map(client.getDevices(), true);
		devices = filterDevices(devices);
		return devices;
	}

	/**
	 * Initialises the xmlrpc client.
	 */
	private void initializeXMLRPCClient() {
		client = XMLRPCClient.getInstance();
		client.init(props.getHomematicURL());
	}

	/**
	 * Filters the devices. Returns only the devices which are defined in the
	 * known devices file
	 *
	 * @param devices
	 *            the devices to be filtered
	 * @return the filtered devices
	 */
	private List<Device> filterDevices(List<Device> devices) {
		List<Device> out = new ArrayList<>();

		xml: for (DeviceXML devXML : xml.getDevices()) {
			for (Device dev : devices) {
				if (devXML.getModel().equals(dev.getType())) {

					for (ChannelXML channelXML : devXML.getChannels()) {
						int channelID = channelXML.getNumber();
						for (ComponentXML compXML : channelXML.getComponents()) {
							if (validate) {
								// TODO check if components in xml match the
								// components in homematic
							}
							Component comp = new Component();
							comp.setDevice(dev);
							comp.setAddress(dev.getAdress() + ":" + channelID);
							comp.setAktor(compXML.isAktor());
							comp.setName(compXML.getDescription() + ":" + channelID);
							comp.setHm_id(compXML.getName());
							comp.setType(compXML.getType());
							comp.setUnit(compXML.getUnit());
							dev.getComponents().add(comp);
							logger.info("Added Component via XML: " + comp);
						}
					}
					out.add(dev);
					logger.info("Added Device via XML: " + dev);
					continue xml;
				} else {
				}

			}
			logger.info("Device: " + devXML.getModel() + " defined in xml not found in homematic");
		}
		return out;
	}

	/**
	 * Load the known devices xml-file.
	 */
	private void loadXML() {
		try {
			File file = new File(props.getDevicesFile());
			JAXBContext context = JAXBContext.newInstance(DevicesXML.class);
			Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
			xml = (DevicesXML) jaxbUnmarshaller.unmarshal(file);
		} catch (Exception e) {
			logger.error("Failed to load xml-file with devices");
			e.printStackTrace();
		}
	}

	/**
	 * Checks the commandline arguments.
	 *
	 * @param args
	 *            the arguments for the programm
	 */
	private void checkArguments(String[] args) {

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

		if (params.isGenConfig()) {
			props.setDefaults();
			props.save(configFilePath);
			logger.info("created default properties-file at: " + configFilePath);
			System.exit(0);
		}

		if (params.isGenerate()) {
			generateXML();
			logger.info("created xml-file with all devices found");
			System.exit(0);
		}

	}

	/**
	 * Generates the xml-file with all devices from homematic.
	 */
	private void generateXML() {

		loadProperties();
		initializeXMLRPCClient();
		DevicesXML devicesXML = DevicesXMLMapper.map(client.getDevices(), true);

		JAXBContext ctx = null;
		Marshaller marshaller = null;
		try {
			ctx = JAXBContext.newInstance(devicesXML.getClass());
			marshaller = ctx.createMarshaller();
		} catch (JAXBException e) {
			logger.error("Error while mapping xml:");
			e.printStackTrace();
		}
		try {
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		} catch (PropertyException e1) {
			logger.error("Error while setting the properties for jaxb:");
			e1.printStackTrace();
		}
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(props.getDevicesFile());
		} catch (FileNotFoundException e) {
			logger.error("File \"" + props.getDevicesFile() + "\" not found:");
			e.printStackTrace();
		}
		try {
			marshaller.marshal(devicesXML, out);
		} catch (JAXBException e) {
			logger.error("Error while writing the xml:");
			e.printStackTrace();
		}

	}

	/**
	 * Loads the properties-file.
	 */
	private void loadProperties() {
		props = new ConnectorProperties();
		props.load(configFilePath);
	}
	
	/**
	 * Adds  a new device while the connector is running.
	 */
	//TODO is called when a new device is connected
	public void addDevice(){
		
	}

	/**
	 * Gets the own ip-adress.
	 *
	 * @return the own ip-adress
	 */
	public static String getOwnIP(){
	    String ip = null;
	    try {
	        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
	        while (interfaces.hasMoreElements()) {
	            NetworkInterface iface = interfaces.nextElement();
	            // filters out 127.0.0.1 and inactive interfaces
	            if (iface.isLoopback() || !iface.isUp())
	                continue;

	            Enumeration<InetAddress> addresses = iface.getInetAddresses();
	            while(addresses.hasMoreElements()) {
	                InetAddress addr = addresses.nextElement();
	                ip = addr.getHostAddress();
	            }
	        }
	    } catch (SocketException e) {
	        throw new RuntimeException(e);
	    }
	    return ip;
	}


}
