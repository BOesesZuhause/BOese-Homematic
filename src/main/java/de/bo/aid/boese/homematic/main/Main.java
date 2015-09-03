package de.bo.aid.boese.homematic.main;


import de.bo.aid.boese.homematic.dao.ComponentDao;
import de.bo.aid.boese.homematic.dao.ConnectorDao;
import de.bo.aid.boese.homematic.dao.DeviceDao;
import de.bo.aid.boese.homematic.model.Component;
import de.bo.aid.boese.homematic.model.Connector;
import de.bo.aid.boese.homematic.model.Device;
import de.bo.aid.boese.homematic.socket.SocketServer;
import de.bo.aid.boese.homematic.xmlrpc.XMLRPCClient;


public class Main {
	
	
	
	
	public static void main(String[] args){
		
				//XMLRPC Server starten
		
				//XMLRPC-Client starten und Geräte abfragen
				//TODO vorhandene Geräte behandeln
				XMLRPCClient client = XMLRPCClient.getInstance();
				client.saveKnownDevices(); //saves temporaerly
				initDatase(client);
		
				//Websocketserver starten
				SocketServer server = new SocketServer();
				server.start("ws://localhost:8081/events/");
				//Beim Verteiler anmelden
				server.requestConnection();
	}

	private static void initDatase(XMLRPCClient client) {
		//Create connector in Database
		Connector con = new Connector();
		con.setIdverteiler(-1);
		con.setName("HomeMaticDefault");
		ConnectorDao.insertConnector(con);
		
		//Create Devices in Database
		for(Device dev : client.getDevices()){
			dev.setConnector(con);
			dev.setIdverteiler(-1);
			DeviceDao.insertDevice(dev);
		}
		
		//Create Components in Database
		for(Component comp : client.getComponents()){
			comp.setIdverteiler(-1);
			ComponentDao.insertComponent(comp);
		}
		client.cleanTempData(); //clean up
	}
	


}
