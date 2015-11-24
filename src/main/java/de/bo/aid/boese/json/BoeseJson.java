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
 */



package de.bo.aid.boese.json;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.json.*;


// TODO: Auto-generated Javadoc
/**
 * This class is the base for all Boese Json Messages.
 * It also provides methods to parse Json to Boese Message and vies versa.
 */
public class BoeseJson {
	
	/** The message type. */
	protected MessageType messageType = null;
	
	/** The connector id. */
	protected int connectorId = -1;
	
	/** The status. */
	protected int status = -1;
	
	/** The timestamp. */
	protected long timestamp = -1;
	
	/**
	 * Enumeration with all available message types.
	 */
	public enum MessageType {
		
		/** The multi. */
		MULTI,
		/** The requestconnection. */
		REQUESTCONNECTION, 
		/** The confirmconnection. */
		CONFIRMCONNECTION, 
		/** The requestalldevices. */
		REQUESTALLDEVICES, 
		/** The senddevices. */
		SENDDEVICES, 
		/** The confirmdevices. */
		CONFIRMDEVICES, 
		/** The requestdevicecomponents. */
		REQUESTDEVICECOMPONENTS,
		/** The senddevicecomponents. */
		SENDDEVICECOMPONENTS, 
		/** The confirmdevicecomponents. */
		CONFIRMDEVICECOMPONENTS, 
		/** The sendvalue. */
		SENDVALUE, 
		/** The confirmvalue. */
		CONFIRMVALUE, 
		/** The requestvalue. */
		REQUESTVALUE,
		/** The sendnotification. */
		SENDNOTIFICATION,
		
		/** The sendstatus. */
		SENDSTATUS,
		
		/** The confirmstatus. */
		CONFIRMSTATUS,
		
		/** The heartbeatmessage. */
		HEARTBEATMESSAGE
	}

	/**
	 * Getter for the message type.
	 *
	 * @return the message type
	 */
	public MessageType getType() {
		return messageType;
	}
	
	/**
	 * Getter for the connector ID.
	 *
	 * @return the connector ID
	 */
	public int getConnectorId() {
		return connectorId;
	}
	
	/**
	 * Getter for the status flag.
	 *
	 * @return the status flag
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * Getter for the timestamp of the message.
	 *
	 * @return the timestamp of the message
	 */
	public long getTimestamp() {
		return timestamp;
	}
	
	/**
	 * Protected constructor for child classes only.
	 *
	 * @param messageType the message type
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	protected BoeseJson(MessageType messageType, int connectorId, int status, long timestamp) {
		this.messageType = messageType;
		this.connectorId = connectorId;
		this.status = status;
		this.timestamp = timestamp;
	}
	
	/**
	 * Reads an input stream and parses it to a BoeseJson Message type.
	 * With getType() method you can check which message is returned.
	 * @param is InputStream with Json message
	 * @return an BoeseJson object
	 */
	public static BoeseJson readMessage(InputStream is) {
		JsonReader jr = Json.createReader(is);
		JsonObject jo = null;
		try {
			jo = jr.readObject();
		} catch(Exception e) {
			e.printStackTrace();
			jr.close();
			return null;
		}
		jr.close();
		BoeseJson bj = null;
		
		int headerConnectorID;
		int headerStatus;
		long headerTimestamp;

		JsonObject header =  jo.getJsonObject("Header");
		if (header == null) {
			return null;
		}
		headerConnectorID = header.getInt("ConnectorId", -1);
		headerStatus = header.getInt("Status", -1);
		headerTimestamp = header.getInt("Timestamp", -1);
		
		switch(header.getInt("MessageType")) {
		case 0: //TODO TEST
			MultiMessage multi = new MultiMessage(headerConnectorID, headerStatus, headerTimestamp);
			if (jo.containsKey("Messages")) {
				JsonArray messages = jo.getJsonArray("Messages");
				for(JsonValue message : messages){
					InputStream in = new ByteArrayInputStream(message.toString().getBytes());	
					BoeseJson bs = readMessage(in);
					multi.addMessage(bs);
				}
			} else {}
			bj = multi;
			break;
		case 1: // RequestConnection
			String connectorNameRC = jo.getString("ConnectorName");
			String passwordRC = null;
			boolean userConnectorRC = false;
			if (jo.containsKey("Password")) {
				passwordRC = jo.getString("Password");
			}
			if (jo.containsKey("IsUserConnector")) {
				userConnectorRC = jo.getBoolean("IsUserConnector");
			}
			bj = new RequestConnection(connectorNameRC, passwordRC, headerConnectorID, headerStatus, headerTimestamp, userConnectorRC);
			break;
		case 2: // ConfirmConnection
			String passwordCC = jo.getString("Password");
			bj = new ConfirmConnection(passwordCC, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 3: // RequestAllDevices
			bj = new RequestAllDevices(headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 4: // SendDevices
			HashMap<String, Integer> devicesSD = new HashMap<>(); // name / id
			if (jo.containsKey("Devices")) {
				JsonArray devArSD = jo.getJsonArray("Devices");
				for (int i = 0; i < devArSD.size(); i++) {
					JsonObject device = devArSD.getJsonObject(i);
					devicesSD.put(device.getString("DeviceName"), device.getInt("DeviceId", -1));
				}
			} else {}
			bj = new SendDevices(devicesSD, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 5: // ConfirmDevices
			HashMap<String, Integer> devicesCD = new HashMap<>(); // name / id
			if (jo.containsKey("Devices")) {
				JsonArray devArCD = jo.getJsonArray("Devices");
				for (int i = 0; i < devArCD.size(); i++) {
					JsonObject device = devArCD.getJsonObject(i);
					devicesCD.put(device.getString("DeviceName"), device.getInt("DeviceId", -1));
				}
			} else {}
			bj = new ConfirmDevices(devicesCD, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 6: // RequestDeviceComponents
			int deviceIdRDC = jo.getInt("DeviceId", -1);
			bj = new RequestDeviceComponents(deviceIdRDC, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 7: // SendDeviceComponents
			int deviceIdSDC = jo.getInt("DeviceId", -1);
			HashSet<DeviceComponents> componentsSDC= new HashSet<>();
			if (jo.containsKey("Components")) {
				JsonArray sendArSDC = jo.getJsonArray("Components");
				for (int i = 0; i < sendArSDC.size(); i++) {
					JsonObject components = sendArSDC.getJsonObject(i);
					String unit = null; //TODO wenn unit != null soll in db angelegt werden
					if (components.getJsonString("Unit") != null) {
						unit = components.getString("Unit");
					}
					String description = null;
					if (components.getJsonString("Description") != null) {
						description = components.getString("Description");
					}
					componentsSDC.add(
							new DeviceComponents(components.getInt("DeviceComponentId", -1), 
									components.getString("ComponentName"), 
									components.getJsonNumber("Value").doubleValue(), 
									components.getJsonNumber("Timestamp").longValue(),
									unit,
									description,
									components.getBoolean("Actor", false)));
				}
			} else {}
			bj = new SendDeviceComponents(deviceIdSDC, componentsSDC, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 8: // ConfirmDeviceComponents
			int deviceIdCDC = jo.getInt("DeviceId", -1);
			HashMap<String, Integer> componentsCDC = new HashMap<>(); // name / id
			JsonArray compCDC = jo.getJsonArray("Components");
			if (compCDC != null) {
				for (int i = 0; i < compCDC.size(); i++) {
					JsonObject device = compCDC.getJsonObject(i);
					componentsCDC.put(device.getString("ComponentName"), device.getInt("DeviceComponentId", -1));
				}
			}
			bj = new ConfirmDeviceComponents(deviceIdCDC, componentsCDC, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 9: // SendValue
			int deviceIdSV = jo.getInt("DeviceId", -1);
			int deviceComponentIdSV = jo.getInt("DeviceComponentId", -1);
			double valueSV = jo.getJsonNumber("Value").doubleValue();
			long timestampSV = jo.getJsonNumber("Timestamp").longValue();
			bj = new SendValue(deviceIdSV, deviceComponentIdSV, valueSV, timestampSV, headerConnectorID, 
					headerStatus, headerTimestamp);
			break;
		case 10: // ConfirmValue
			int deviceIdCV = jo.getInt("DeviceId", -1);
			int deviceComponentIdCV = jo.getInt("DeviceComponentId", -1);
			bj = new ConfirmValue(deviceIdCV, deviceComponentIdCV, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 11://RequestValue
			int deviceIdRV = jo.getInt("DeviceId", -1);
			int deviceComponentIdRV = jo.getInt("DeviceComponentId", -1);
			bj = new RequestValue(deviceIdRV, deviceComponentIdRV, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 12: // SendNotification
			int deviceIdSN = jo.getInt("DeviceId", -1);
			int deviceComponentIdSN = jo.getInt("DeviceComponentId", -1);
			int notificationType = jo.getInt("NotificationType", -1);
			long timestampSN = jo.getJsonNumber("Timestamp").longValue();
			String notificationStringSN = jo.getString("NotificationText", "");
			bj = new SendNotification(deviceIdSN, deviceComponentIdSN, notificationType, timestampSN, notificationStringSN, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 13: // SendStatus
			int deviceComponentIdSS = jo.getInt("DeviceComponentId", -1);
			int statusCodeSS = jo.getInt("StatusCode", -1);
			long timestampSS = jo.getJsonNumber("Timestamp").longValue();
			bj = new SendStatus(deviceComponentIdSS, statusCodeSS, timestampSS, true, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 14: // ConfirmStatus
			int deviceComponentIdCS = jo.getInt("DeviceComponentId", -1);
			int statusCodeCS = jo.getInt("StatusCode", -1);
			long timestampCS = jo.getJsonNumber("Timestamp").longValue();
			bj = new ConfirmStatus(deviceComponentIdCS, statusCodeCS, timestampCS, false, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 120: //HeartBeatMessage
			bj = new HeartBeatMessage(headerConnectorID, headerStatus, headerTimestamp);			
		break;
		default:
			break;
		}
		return bj;
	}
	
	/**
	 * Private method to create a JsonObject with the Json header.
	 *
	 * @param messageType the message type
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 * @return JsonObjectBuilder containing the Message Header
	 */
	private static JsonObjectBuilder addHeader(int messageType, int connectorId, int status, long timestamp) {
		JsonObjectBuilder header = Json.createObjectBuilder();
		header.add("MessageType", messageType);
		header.add("ConnectorId", connectorId);
		header.add("Status", status);
		header.add("Timestamp", timestamp);
		return header;
	}
	
	/**
	 * Generate builder.
	 *
	 * @param message the message
	 * @return the json object builder
	 */
	private static JsonObjectBuilder generateBuilder(BoeseJson message) {
		JsonObjectBuilder job = Json.createObjectBuilder();
		switch (message.getType()) {
		case MULTI:
			//TODO TEST
			MultiMessage multi = (MultiMessage)message;
			job.add("Header", addHeader(0, multi.getConnectorId(), multi.getStatus(), multi.getTimestamp()));
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			JsonObjectBuilder objectBuilder;
			for(BoeseJson singleMessage : multi.getMessages()){
				objectBuilder = generateBuilder(singleMessage);
				arrayBuilder.add(objectBuilder);
			}
			job.add("Messages", arrayBuilder);
			break;
		case REQUESTCONNECTION:
			RequestConnection rc = (RequestConnection)message;
			job.add("Header", addHeader(1, rc.getConnectorId(), rc.getStatus(), rc.getTimestamp()));
			job.add("ConnectorName", rc.getConnectorName());
			if (rc.getPassword() != null) {
				job.add("Password", rc.getPassword());
			}
			break;
		case CONFIRMCONNECTION:
			ConfirmConnection cc = (ConfirmConnection)message;
			job.add("Header", addHeader(2, cc.getConnectorId(), cc.getStatus(), cc.getTimestamp()));
			job.add("Password", cc.getPassword());
			job.add("ConnectorId", cc.getConnectorId());
			break;
		case REQUESTALLDEVICES:
			RequestAllDevices rad = (RequestAllDevices)message;
			job.add("Header", addHeader(3, rad.getConnectorId(), rad.getStatus(), rad.getTimestamp()));
			break;
		case SENDDEVICES:
			SendDevices sd = (SendDevices)message;
			job.add("Header", addHeader(4, sd.getConnectorId(), sd.getStatus(), sd.getTimestamp()));
			JsonArrayBuilder devicesSDAr = Json.createArrayBuilder();
			JsonObjectBuilder deviceSD;
			for (Entry<String, Integer> entry : sd.getDevices().entrySet()) {
				deviceSD = Json.createObjectBuilder();
				deviceSD.add("DeviceName", entry.getKey());
				deviceSD.add("DeviceId", entry.getValue());
				devicesSDAr.add(deviceSD);
			}
			job.add("Devices", devicesSDAr);
			break;
		case CONFIRMDEVICES:
			ConfirmDevices cd = (ConfirmDevices)message;
			job.add("Header", addHeader(5, cd.getConnectorId(), cd.getStatus(), cd.getTimestamp()));
			JsonArrayBuilder devicesCDAr = Json.createArrayBuilder();
			JsonObjectBuilder deviceCD;
			for (Entry<String, Integer> entry : cd.getDevices().entrySet()) {
				deviceCD = Json.createObjectBuilder();
				deviceCD.add("DeviceName", entry.getKey());
				deviceCD.add("DeviceId", entry.getValue());
				devicesCDAr.add(deviceCD);
			}
			job.add("Devices", devicesCDAr);
			break;
		case REQUESTDEVICECOMPONENTS:
			RequestDeviceComponents rdc = (RequestDeviceComponents)message;
			job.add("Header", addHeader(6, rdc.getConnectorId(), rdc.getStatus(), rdc.getTimestamp()));
			job.add("DeviceId", rdc.getDeviceId());
			break;
		case SENDDEVICECOMPONENTS:
			SendDeviceComponents sdc = (SendDeviceComponents)message;
			job.add("Header", addHeader(7, sdc.getConnectorId(), sdc.getStatus(), sdc.getTimestamp()));
			job.add("DeviceId", sdc.getDeviceId());
			JsonArrayBuilder deviceComponentsSDCAr = Json.createArrayBuilder();
			JsonObjectBuilder deviceComponentSDC;
			for (DeviceComponents deviceComponent : sdc.getComponents()) {
				deviceComponentSDC = Json.createObjectBuilder();
				deviceComponentSDC.add("DeviceComponentId", deviceComponent.getDeviceComponentId());
				deviceComponentSDC.add("ComponentName", deviceComponent.getComponentName());
				deviceComponentSDC.add("Value", deviceComponent.getValue());
				deviceComponentSDC.add("Timestamp", deviceComponent.getTimestamp());
				if (deviceComponent.getUnit() != null) {
					deviceComponentSDC.add("Unit", deviceComponent.getUnit());
				}
				if (deviceComponent.getDescription() != null) {					
					deviceComponentSDC.add("Description", deviceComponent.getDescription());
				}
				deviceComponentSDC.add("Actor", deviceComponent.isActor());
				deviceComponentsSDCAr.add(deviceComponentSDC);
			}
			job.add("Components", deviceComponentsSDCAr);
			break;
		case CONFIRMDEVICECOMPONENTS:
			ConfirmDeviceComponents cdc = (ConfirmDeviceComponents)message;
			job.add("Header", addHeader(8, cdc.getConnectorId(), cdc.getStatus(), cdc.getTimestamp()));
			job.add("DeviceId", cdc.getDeviceId());
			JsonArrayBuilder componentsCDCA = Json.createArrayBuilder();
			JsonObjectBuilder componentCDC;
			for (Entry<String, Integer> entry : cdc.getComponents().entrySet()) {
				componentCDC = Json.createObjectBuilder();
				componentCDC.add("DeviceComponentId", entry.getValue());
				componentCDC.add("ComponentName", entry.getKey());
				componentsCDCA.add(componentCDC);
			}
			job.add("Components", componentsCDCA);
			break;
		case SENDVALUE:
			SendValue sv = (SendValue)message;
			job.add("Header", addHeader(9, sv.getConnectorId(), sv.getStatus(), sv.getTimestamp()));
			job.add("DeviceId", sv.getDeviceId());
			job.add("DeviceComponentId", sv.getDeviceComponentId());
			job.add("Value", sv.getValue());
			job.add("Timestamp", sv.getValueTimestamp());
			break;
		case CONFIRMVALUE:
			ConfirmValue cv = (ConfirmValue)message;
			job.add("Header", addHeader(10, cv.getConnectorId(), cv.getStatus(), cv.getTimestamp()));
			job.add("DeviceId", cv.getDeviceId());
			job.add("DeviceComponentId", (cv.getDeviceComponentId()));
			break;
		case REQUESTVALUE:
			RequestValue rv = (RequestValue)message;
			job.add("Header", addHeader(11, rv.getConnectorId(), rv.getStatus(), rv.getTimestamp()));
			job.add("DeviceId", rv.getDeviceId());
			job.add("DeviceComponentId", rv.getDeviceComponentId());
			break;
		case SENDNOTIFICATION:
			SendNotification sn = (SendNotification)message;
			job.add("Header", addHeader(12, sn.getConnectorId(), sn.getStatus(), sn.getTimestamp()));
			job.add("DeviceId", sn.getDeviceId());
			job.add("DeviceComponentId", sn.getDeviceComponentId());
			job.add("NotificationType", sn.getNotificationType());
			job.add("Timestamp", sn.getNotificationTimestamp());
			job.add("NotificationText", sn.getNotificationText());
			break;
		case SENDSTATUS:
			SendStatus ss = (SendStatus)message;
			job.add("Header", addHeader(13, ss.getConnectorId(), ss.getStatus(), ss.getTimestamp()));
			job.add("DeviceComponentId", ss.getDeviceComponentId());
			job.add("StatusCode", ss.getStatusCode());
			job.add("Timestamp", ss.getStatusTimestamp());
			break;
		case CONFIRMSTATUS:
			ConfirmStatus cs = (ConfirmStatus)message;
			job.add("Header", addHeader(14, cs.getConnectorId(), cs.getStatus(), cs.getTimestamp()));
			job.add("DeviceComponentId", cs.getDeviceComponentId());
			job.add("StatusCode", cs.getStatusCode());
			job.add("Timestamp", cs.getStatusTimestamp());
			break;
		case HEARTBEATMESSAGE:
			HeartBeatMessage hm = (HeartBeatMessage) message;
			job.add("Header", addHeader(120, hm.getConnectorId(), hm.getStatus(), hm.getTimestamp()));
		break;
		default:
			break;
		}
		return job;
	}
	
	/**
	 * Writes a BoeseJson Message to a given output stream.
	 *
	 * @param message the BoeseJson Message
	 * @param os the OutputStream to write in. If null a new ByteArrayOutputStream is generated.
	 * @return true, if writing was successful
	 */
	public static boolean parseMessage(BoeseJson message, OutputStream os) {
		boolean output = true;
		JsonObjectBuilder job = generateBuilder(message);
		if (os == null) {
			os = new ByteArrayOutputStream();
		}
		JsonWriter writer = Json.createWriter(os);
		writer.writeObject(job.build());
		writer.close();
		return output;
	}
	
	/**
	 * Parses the message.
	 *
	 * @param message the message
	 * @return the output stream
	 */
	public static OutputStream parseMessage(BoeseJson message) {
		OutputStream os = new ByteArrayOutputStream();
		parseMessage(message, os);
		return os;
	}
}
