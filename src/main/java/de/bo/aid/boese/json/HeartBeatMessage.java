package de.bo.aid.boese.json;

public class HeartBeatMessage extends BoeseJson{


	public HeartBeatMessage(int connectorId, int status, long timestamp) {
		super(MessageType.HEARTBEATMESSAGE, connectorId, status, timestamp);
	}
	
	

}
