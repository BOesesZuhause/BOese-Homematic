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


package de.bo.aid.boese.homematic.socket;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import de.bo.aid.boese.homematic.dao.ComponentDao;
import de.bo.aid.boese.homematic.dao.ConnectorDao;
import de.bo.aid.boese.homematic.dao.DeviceDao;
import de.bo.aid.boese.homematic.main.DatabaseCache;
import de.bo.aid.boese.homematic.model.Component;
import de.bo.aid.boese.homematic.model.Connector;
import de.bo.aid.boese.homematic.model.Device;
import de.bo.aid.boese.homematic.xmlrpc.XMLRPCClient;
import de.bo.aid.boese.json.BoeseJson;
import de.bo.aid.boese.json.ConfirmConnection;
import de.bo.aid.boese.json.ConfirmDeviceComponents;
import de.bo.aid.boese.json.ConfirmDevices;
import de.bo.aid.boese.json.ConfirmValue;
import de.bo.aid.boese.json.DeviceComponents;
import de.bo.aid.boese.json.RequestAllDevices;
import de.bo.aid.boese.json.RequestConnection;
import de.bo.aid.boese.json.RequestDeviceComponents;
import de.bo.aid.boese.json.SendDeviceComponents;
import de.bo.aid.boese.json.SendDevices;
import de.bo.aid.boese.json.SendValue;
import javassist.NotFoundException;



// TODO externer Handler?
/**
 * This singleton class defines a Websocketendpoint for Websocketconnections
 */
public class SocketServer implements MessageHandler{
	
	/** The singleton instance. */
	private static SocketServer instance = new SocketServer();
	
	/** The client used by the connection. */
	private SocketClientStandalone client;
	
	/** Is used to check, wether the connection should be closed. */
	boolean connectionClosed = false;
	
	/** The databasecache used to read data quickly. */
	DatabaseCache cache = DatabaseCache.getInstance();
	
	/** The logger for log4j. */
	final static Logger logger = Logger.getLogger(SocketServer.class);
	
	/**
	 * Instantiates a new socket server.
	 */
	private SocketServer(){
		
	}
	
	/**
	 * Gets the single instance of SocketServer.
	 *
	 * @return single instance of SocketServer
	 */
	public static SocketServer getInstance(){
		return instance;
	}
	
	/**
	 * Starts the server and connects to it.
	 *
	 * @param serverUri the uri of the server
	 */
	public void start(String serverUri){
		URI uri = URI.create(serverUri);
		client = new SocketClientStandalone();
		client.addMessageHandler(this);
		client.connect(uri);
	}

	/* (non-Javadoc)
	 * @see de.bo.aid.boese.homematic.socket.MessageHandler#handleMessage(java.lang.String)
	 */
	@Override
	public synchronized void handleMessage(String message) {
		

		BoeseJson bjMessage = BoeseJson.readMessage(new ByteArrayInputStream(message.getBytes()));

		if (bjMessage == null) {
			return; // TODO Fehlermeldungen
		}

		switch (bjMessage.getType()) {

		case CONFIRMCONNECTION:
			handleConfirmconnection((ConfirmConnection) bjMessage);
			break;

		case REQUESTALLDEVICES:
			handleRequestAllDevices((RequestAllDevices) bjMessage);
			break;

		case CONFIRMDEVICES:
			handleConfirmDevices((ConfirmDevices) bjMessage);
			break;

		case REQUESTDEVICECOMPONENTS:
			handleRequestDeviceComponents((RequestDeviceComponents) bjMessage);
			break;

		case CONFIRMDEVICECOMPONENTS:
			handleConfirmDeviceComponents((ConfirmDeviceComponents) bjMessage);
			break;

		case CONFIRMVALUE:
			handleConfirmValue((ConfirmValue) bjMessage);
			break;
			
		case SENDVALUE:
			handleSendvalue((SendValue) bjMessage);
			break;
		default:
			//TODO Exception
			break;

		}
	}
	
	/**
	 * Sends a value to the distributor.
	 *
	 * @param value the value
	 * @param devId the id of the device, which is saved in the distributor
	 * @param devCompId the id of the deviceComponent, which is saved in the distributor
	 * @param time the timestamp of the value
	 */
	//wird von HomeMatic-Gerät aufgerufen
	public void sendValue(double value, int devId, int devCompId, long time){

		int conId = cache.getConnector().getIdverteiler();

		SendValue sendval = new SendValue(devId, devCompId, value, time, conId, 0, 0, 0, System.currentTimeMillis());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(sendval, os);
		client.sendMessage(os.toString());
	}
	
	
	/**
	 * Handle the sendvalueMessage and switches devices. Receives a value from the distibutor and sends a message to the homematic-device.
	 *
	 * @param bjMessage the bj message
	 */
	private void handleSendvalue(SendValue bjMessage) {
		Component comp = null;
		try {
			comp = ComponentDao.getComponent(bjMessage.getDeviceComponentId());
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO Client auslager und über Methodenaufruf regeln
		XMLRPCClient.getInstance().setValue(comp.getAddress(), comp.getHm_id(), bjMessage.getValue(), comp.getType());		
	}

	/**
	 * Handle thes confirm value message.
	 * Doesnt do anything at the moment
	 *
	 * @param bjMessage the bj message
	 */
	private void handleConfirmValue(ConfirmValue bjMessage) {
		//TODO Abgleich ob confirmValue ankommt	
	}
	
	/**
	 * Handles confirm device components message and updates the database with the new information gained from the distributor.
	 *
	 * @param bjMessage the bj message
	 */
	private void handleConfirmDeviceComponents(ConfirmDeviceComponents bjMessage) {
		
		//find Device
				int deviceId = bjMessage.getDeviceId();	
				Device dev = null;
				for(Device d : cache.getDevices()){
					if(d.getIdverteiler()== deviceId){
						dev = d;
					}
				}
				if(dev == null){
					//TODO add Error handling
				}
				
				//Confirmed Components
				HashMap<String, Integer> compMap = bjMessage.getComponents();
				
				//actual Components
				Set<Component> actualComponents = dev.getComponents();	
				
				
				for (String componentName : compMap.keySet()) {	
					for(Component component : actualComponents){
						if(component.getName().equals(componentName)){
							component.setIdverteiler(compMap.get(componentName));
							ComponentDao.updateComponent(component); 
						}
					}
				}
		cache.update();
		System.out.println("The cache was updated");
	}
	
	/**
	 * Handles the request device component message.
	 * Sends all components to the distributor
	 *
	 * @param bjMessage the bj message
	 */
	private void handleRequestDeviceComponents(RequestDeviceComponents bjMessage) {

		//find Device
		Device requestedDevice = null;
		for(Device dev : cache.getDevices()){
			if(dev.getIdverteiler() == bjMessage.getDeviceId()){
				requestedDevice = dev;
			}
		}
		
		if(requestedDevice == null){
			//TODO Exception
		}
		
		int conId = cache.getConnector().getIdverteiler();
		
		//Convert Set of Components to HashSet of DeviceComponents
		HashSet<DeviceComponents> components = new HashSet<>();
		for(Component comp : requestedDevice.getComponents()){
			DeviceComponents devComp = new DeviceComponents(comp.getIdverteiler(), comp.getName(), 0, System.currentTimeMillis(), comp.getUnit(), comp.getName(), comp.isAktor());
			components.add(devComp);
		}
		
		//Send Components
		SendDeviceComponents sendDevComp = new SendDeviceComponents(requestedDevice.getIdverteiler(), components, conId, 0, 0, 0, System.currentTimeMillis());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(sendDevComp, os);
		client.sendMessage(os.toString());
		
	}
	
	/**
	 * Handles the confirm device message.
	 * Updates the device-data in the database.
	 *
	 * @param bjMessage the bj message
	 */
	private void handleConfirmDevices(ConfirmDevices bjMessage) {
		HashMap<String, Integer> devMap = bjMessage.getDevices();
		for (String deviceName : devMap.keySet()) {	
			for(Device dev : cache.getDevices()){
				if(dev.getName().equals(deviceName)){
					dev.setIdverteiler(devMap.get(deviceName));
					DeviceDao.updateDevice(dev);
				}
			}
		}
		cache.update();
	}
	
	/**
	 * Handles the request all devices message.
	 * Sends all known devices to the distributor
	 *
	 * @param bjMessage the bj message
	 */
	private void handleRequestAllDevices(RequestAllDevices bjMessage) {

		HashMap<String, Integer> devHash = new HashMap<>();
		for(Device dev : cache.getDevices()){
			devHash.put(dev.getName(), dev.getIdverteiler());
		}
		
		int conId = cache.getConnector().getIdverteiler();
		
		SendDevices sendDevs = new SendDevices(devHash, conId, 0, 0, 0, System.currentTimeMillis());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(sendDevs, os);
		client.sendMessage(os.toString());
		
	}
	
	/**
	 * Handles the confirmconnection message.
	 * Updates the connector in the database.
	 *
	 * @param bjMessage the bj message
	 */
	private void handleConfirmconnection(ConfirmConnection bjMessage) {
		// TODO test
		Connector con = cache.getConnector();
		if(con.getIdverteiler()==bjMessage.getConnectorId()){
			//TODO was passiert wenn der Connector schon eine ID hat?
		}else if(con.getIdverteiler()== -1){
			con.setIdverteiler(bjMessage.getConnectorId());
			con.setSecret(bjMessage.getPassword());
			ConnectorDao.insertConnector(con);
			cache.update();
		}else{
			//TODO Exception
		}
		
	}
	
	/**
	 * Sends a request-connection message to the distributor.
	 */
	public void requestConnection(){
		
		cache.update();	
		Connector con = cache.getConnector();
		
		// Request connection
		RequestConnection reqCon = new RequestConnection(con.getName(), con.getSecret(), con.getIdverteiler(), 0, 0, 0, System.currentTimeMillis());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(reqCon, os);
		client.sendMessage(os.toString());
	}
	
	
	/* (non-Javadoc)
	 * @see de.bo.aid.boese.homematic.socket.MessageHandler#closeConnection()
	 */
	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub
	}

	/**
	 * Sendvalue message for components with type=action.
	 * It automatically sends a second message which resets the value.
	 *
	 * @param value the value
	 * @param devId the id of the device, which is saved in the distributor
	 * @param devCompId the id of the deviceComponent, which is saved in the distributor
	 * @param time the timestamp of the value
	 */
	//TODO run in another Thread
	public void sendAction(double value, int devId, int devCompId, long time) {

		int conId = cache.getConnector().getIdverteiler();

		SendValue sendval = new SendValue(devId, devCompId, value, time, conId, 0, 0, 0, System.currentTimeMillis());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(sendval, os);
		client.sendMessage(os.toString());
//		
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		sendval = new SendValue(devId, devCompId, 0, time, conId, 0, 0, 0, System.currentTimeMillis());
		os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(sendval, os);
		client.sendMessage(os.toString());
		
	}

}
