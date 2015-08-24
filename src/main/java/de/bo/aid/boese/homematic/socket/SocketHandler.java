package de.bo.aid.boese.homematic.socket;
import java.io.ByteArrayInputStream;

import de.bo.aid.boese.json.BoeseJson;
import de.bo.aid.boese.json.ConfirmConnection;
import de.bo.aid.boese.json.ConfirmDeviceComponents;
import de.bo.aid.boese.json.ConfirmDevices;
import de.bo.aid.boese.json.ConfirmValue;
import de.bo.aid.boese.json.RequestAllDevices;
import de.bo.aid.boese.json.RequestDeviceComponents;

public class SocketHandler implements MessageHandler{
	
	SocketClientStandalone client;
	boolean connectionClosed = false;
	
	
	@Override
	public void handleMessage(String message) {
		System.out.println("Client received Message: " + message);

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
		default:
			break;

		}
	}
	private void handleConfirmValue(ConfirmValue bjMessage) {
		// TODO Auto-generated method stub
		
	}
	private void handleConfirmDeviceComponents(ConfirmDeviceComponents bjMessage) {
		// TODO Auto-generated method stub
		
	}
	private void handleRequestDeviceComponents(RequestDeviceComponents bjMessage) {
		// TODO Auto-generated method stub
		
	}
	private void handleConfirmDevices(ConfirmDevices bjMessage) {
		// TODO Auto-generated method stub
		
	}
	private void handleRequestAllDevices(RequestAllDevices bjMessage) {
		// TODO Auto-generated method stub
		
	}
	private void handleConfirmconnection(ConfirmConnection bjMessage) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub
		
	}

}
