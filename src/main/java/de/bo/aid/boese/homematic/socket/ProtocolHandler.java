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


package de.bo.aid.boese.homematic.socket;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.logging.log4j.LogManager;

import de.bo.aid.boese.constants.Status;
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
import de.bo.aid.boese.json.HeartBeatMessage;
import de.bo.aid.boese.json.RequestAllDevices;
import de.bo.aid.boese.json.RequestDeviceComponents;
import de.bo.aid.boese.json.SendDeviceComponents;
import de.bo.aid.boese.json.SendDevices;
import de.bo.aid.boese.json.SendValue;
import javassist.NotFoundException;


/**
 * THis class is a handler for the BOese protocol. It is 
 * used to send messages according to the protocol 
 * and implements the messagehandler interface. SO it can be
 * registered to receive websocket-messages and handle them.
 */
public class ProtocolHandler implements MessageHandler{
	
	
	/** The websocketclient used to connect to the distributor */
	private SocketClient client;
	
	/** Is used to check, wether the connection should be closed. */
	boolean connectionClosed = false;
	
	/** The databasecache used to read data quickly. */
	DatabaseCache cache = DatabaseCache.getInstance();
	
	/** The logger for log4j. */
	final static Logger logger = LogManager.getLogger(ProtocolHandler.class);
	
	/**
	 * Instantiates a new socket server.
	 *
	 * @param client websocketclient
	 */
	public ProtocolHandler(SocketClient client){
		this.client = client;
	}

	/* (non-Javadoc)
	 * @see de.bo.aid.boese.homematic.socket.MessageHandler#handleMessage(java.lang.String)
	 */
	@Override
	public synchronized void handleMessage(String message) {
		BoeseJson bjMessage = BoeseJson.readMessage(new ByteArrayInputStream(message.getBytes()));

		if (bjMessage == null) {
			logger.error("Failed to parse message: " + message);
			return; 
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
		case HEARTBEATMESSAGE:
			handleHeartBeat((HeartBeatMessage) bjMessage);
			break;
		default:
			logger.warn("Unknown Messagetype: " + bjMessage.getType());
			break;

		}
	}
	

	
	
	/**
	 * Handles an incoming heartbeatmessage and sends it back
	 * to the distributor.
	 *
	 * @param bjMessage the object for the heartbeatmessage
	 */
	private void handleHeartBeat(HeartBeatMessage bjMessage) {
		BoeseJson bj = new HeartBeatMessage(bjMessage.getConnectorId(), bjMessage.getStatus(), System.currentTimeMillis());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bj, os);
		client.sendMessage(os.toString());
	}

	/**
	 * Handle the sendvalueMessage and switches devices. 
	 * Receives a value from the distributor and sends a message to the homematic-device.
	 *
	 * @param bjMessage the object for the sendvalue message
	 */
	private void handleSendvalue(SendValue bjMessage) {
		Component comp = null;
		try {
			comp = ComponentDao.getByVertID(bjMessage.getDeviceComponentId());
		} catch (NotFoundException e) {
			logger.warn("requested component with id: " + bjMessage.getDeviceComponentId() + " not found");
			e.printStackTrace();
		}
		try {
            XMLRPCClient.getInstance().setValue(comp.getAddress(), comp.getHm_id(), bjMessage.getValue(), comp.getType());
        } catch (XmlRpcException e) {
            logger.error("Could not set component: " + comp.getName() + " to: " + bjMessage.getValue());
            client.sendStatus(comp.getIdverteiler(), Status.ACTOR_DOES_NOT_REACT, System.currentTimeMillis());   
        }		
	}

	/**
	 * Handle the confirm value message.
	 * Doesn't do anything at the moment
	 *
	 * @param bjMessage the object for the confirmvalue message
	 */
	private void handleConfirmValue(ConfirmValue bjMessage) {
		//TODO Abgleich ob confirmValue ankommt	
	}
	
	/**
	 * Handles confirm device components message and updates 
	 * the database with the new information gained from the distributor.
	 *
	 * @param bjMessage the object of the confirmdevicecomponents message
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
					logger.warn("Could not find the device with id: " + bjMessage.getDeviceId() + " in the database");
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
	}
	
	/**
	 * Handles the request device component message and
	 * sends all components of the requested device to the distributor
	 *
	 * @param bjMessage the object for the requestdevicecomponent message
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
			logger.warn("Could not find the device with id: " + bjMessage.getDeviceId() + " in the database");
		}
		
		int conId = cache.getConnector().getIdverteiler();
		
		//Convert Set of Components to HashSet of DeviceComponents
		HashSet<DeviceComponents> components = new HashSet<>();
		for(Component comp : requestedDevice.getComponents()){
			double value = 0;
			DeviceComponents devComp;
			//check if component is reachable
            try {
                value = XMLRPCClient.getInstance().getValue(comp.getAddress(), comp.getType(), comp.getHm_id());
                devComp = new DeviceComponents(comp.getIdverteiler(), comp.getName(), value, System.currentTimeMillis(), comp.getUnit(), comp.getName(), comp.isAktor());
            } catch (Exception e) {
                logger.warn("Unable to call component with id: " + comp.getCompid(), e);
                devComp = new DeviceComponents(comp.getIdverteiler(), comp.getName(), value, System.currentTimeMillis(), comp.getUnit(), comp.getName(), comp.isAktor(), Status.UNAVAILABLE);
            }		
            components.add(devComp);
		}
		
		//Send Components
		SendDeviceComponents sendDevComp = new SendDeviceComponents(requestedDevice.getIdverteiler(), components, conId, 0, System.currentTimeMillis());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(sendDevComp, os);
		client.sendMessage(os.toString());
		
	}
	
	/**
	 * Handles the confirm device message
	 * and updates the device-data in the database.
	 *
	 * @param bjMessage the object for the confirmdevices message
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
	 * Handles the request all devices message and
	 * sends all known devices to the distributor
	 *
	 * @param bjMessage the object for the requestAllDevices message
	 */
	private void handleRequestAllDevices(RequestAllDevices bjMessage) {

		HashMap<String, Integer> devHash = new HashMap<>();
		for(Device dev : cache.getDevices()){
			devHash.put(dev.getName(), dev.getIdverteiler());
		}
		
		int conId = cache.getConnector().getIdverteiler();
		
		SendDevices sendDevs = new SendDevices(devHash, conId, 0, System.currentTimeMillis());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(sendDevs, os);
		client.sendMessage(os.toString());	
	}
	
	/**
	 * Handles the confirmconnection message and
	 * updates the connector data in the database.
	 *
	 * @param bjMessage the object of the confirmConnection message
	 */
	private void handleConfirmconnection(ConfirmConnection bjMessage) {
		Connector con = cache.getConnector();
		//Connector is confirmed
		if(con.getIdverteiler()==bjMessage.getConnectorId()){
		
		//Connector is not confirmed
		}else if(con.getIdverteiler()== -1){
			con.setIdverteiler(bjMessage.getConnectorId());
			con.setSecret(bjMessage.getPassword());
			ConnectorDao.insertConnector(con);
			cache.update();
			
		//unknown id
		}else{
			logger.error("Unknown identifier for the connector: " + con.getIdverteiler());
			System.exit(0); //TODO wird aufgerufen falls der Konektor
		}
		
	}
	
	
	
	/* (non-Javadoc)
	 * @see de.bo.aid.boese.homematic.socket.MessageHandler#closeConnection()
	 */
	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub
	}



}
