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
package de.bo.aid.boese.json;

// TODO: Auto-generated Javadoc
/**
 * The Class SendNotification.
 */
public class SendNotification extends BoeseJson {
	/** The device id. */
	private int deviceId;
	
	/** The device component id. */
	private int deviceComponentId;
	
	/** The notification type. */
	private int notificationType;
	
	/** The notification timestamp. */
	private long notificationTimestamp;
	
	/** The notification text. */
	private String notificationText;

	/**
	 * Instantiates a new send notification.
	 *
	 * @param deviceId the device id
	 * @param deviceComponentId the device component id
	 * @param notificationType the notification type
	 * @param notificationTimestamp the notification timestamp
	 * @param notificationText the notification text
	 * @param connectorId the connector id
	 * @param seqNr the seq nr
	 * @param ackNr the ack nr
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public SendNotification(int deviceId, int deviceComponentId, int notificationType, long notificationTimestamp, String notificationText,
			int connectorId, int seqNr, int ackNr, int status, long timestamp) {
		super(MessageType.SENDNOTIFICATION, connectorId, seqNr, ackNr, status, timestamp);
		this.deviceId = deviceId;
		this.deviceComponentId = deviceComponentId;
	}

	/**
	 * Gets the device id.
	 *
	 * @return the device id
	 */
	public int getDeviceId() {
		return deviceId;
	}
	
	/**
	 * Gets the device component id.
	 *
	 * @return the device component id
	 */
	public int getDeviceComponentId() {
		return deviceComponentId;
	}
	
	/**
	 * Gets the notification type.
	 *
	 * @return the notification type
	 */
	public int getNotificationType() {
		return notificationType;
	}
	
	/**
	 * Gets the notification timestamp.
	 *
	 * @return the notification timestamp
	 */
	public long getNotificationTimestamp() {
		return notificationTimestamp;
	}
	
	/**
	 * Gets the notification text.
	 *
	 * @return the notification text
	 */
	public String getNotificationText() {
		return notificationText;
	}
}
