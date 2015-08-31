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



public class SocketServer implements MessageHandler{
	
	private SocketClientStandalone client;
	boolean connectionClosed = false;
	DatabaseCache cache = DatabaseCache.getInstance();
	
	final static Logger logger = Logger.getLogger(SocketServer.class);
	
	public void start(String server){
		URI uri = URI.create(server);
		client = new SocketClientStandalone();
		client.addMessageHandler(this);
		client.connect(uri);
		
		requestConnection();
	}

	@Override
	public synchronized void handleMessage(String message) {
		logger.info("Client received Message: " + message);

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
			handleSendalue((SendValue) bjMessage);
			break;
		default:
			//TODO Exception
			break;

		}
	}
	//wird von HomeMatic-Gerät aufgerufen
	public void sendValue(double value, int devId, int devCompId, long time){

		int conId = cache.getConnector().getIdverteiler();

		SendValue sendval = new SendValue(devId, devCompId, value, time, conId, 0, 0, 0, System.currentTimeMillis());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(sendval, os);
		client.sendMessage(os.toString());
	}
	
	private void handleSendalue(SendValue bjMessage) {
		
		
		
		// TODO HomeMatic-Gerät schalten
		
	}

	private void handleConfirmValue(ConfirmValue bjMessage) {
		System.out.println("Server confirmed value");		
	}
	private void handleConfirmDeviceComponents(ConfirmDeviceComponents bjMessage) {
		// TODO test
		
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
	private void handleRequestDeviceComponents(RequestDeviceComponents bjMessage) {
		// TODO test
		
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
			DeviceComponents devComp = new DeviceComponents(comp.getIdverteiler(), comp.getName(), 0, System.currentTimeMillis());
			components.add(devComp);
		}
		
		//Send Components
		SendDeviceComponents sendDevComp = new SendDeviceComponents(requestedDevice.getIdverteiler(), components, conId, 0, 0, 0, System.currentTimeMillis());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(sendDevComp, os);
		client.sendMessage(os.toString());
		
	}
	private void handleConfirmDevices(ConfirmDevices bjMessage) {
		// TODO test
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
		System.out.println("The cache was updated");
	}
	private void handleRequestAllDevices(RequestAllDevices bjMessage) {
		// TODO test
		
		
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
	private void handleConfirmconnection(ConfirmConnection bjMessage) {
		// TODO test
		Connector con = cache.getConnector();
		if(con.getIdverteiler()==bjMessage.getConnectorId()){
			//TODO nichts
		}else if(con.getIdverteiler()== -1){
			con.setIdverteiler(bjMessage.getConnectorId());
			con.setSecret(bjMessage.getPassword());
			ConnectorDao.insertConnector(con);
			cache.update();
		}else{
			//TODO Exception
		}
		
	}
	
	public void requestConnection(){
		
		cache.update();	
		System.out.println("The cache was updated");
		Connector con = cache.getConnector();
		
		// Request connection
		RequestConnection reqCon = new RequestConnection(con.getName(), con.getSecret(), con.getIdverteiler(), 0, 0, 0, System.currentTimeMillis());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(reqCon, os);
		client.sendMessage(os.toString());
	}
	
	public void sendValue(double value, int deviceId, int deviceComponentId){
		//TODO		
	}
	
	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub
	}

}
