/*
 * 
 */
package de.bo.aid.boese.json;

import java.util.HashSet;

// TODO: Auto-generated Javadoc
/**
 * The Class SendDeviceComponents.
 */
public class SendDeviceComponents extends BoeseJson {
	
	/** The components. */
	private HashSet<DeviceComponents> components;
	
	/** The device id. */
	private int deviceId;

	/**
	 * Instantiates a new send device components.
	 *
	 * @param deviceId the device id
	 * @param components the components
	 * @param connectorId the connector id
	 * @param seqNr the seq nr
	 * @param ackNr the ack nr
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public SendDeviceComponents(int deviceId, HashSet<DeviceComponents> components, int connectorId, int seqNr, int ackNr, int status,
			long timestamp) {
		super(MessageType.SENDDEVICECOMPONENTS, connectorId, seqNr, ackNr, status, timestamp);
		this.components = components;
		this.deviceId = deviceId;
	}

	/**
	 * Gets the components.
	 *
	 * @return the components
	 */
	public HashSet<DeviceComponents> getComponents() {
		return components;
	}

	/**
	 * Gets the device id.
	 *
	 * @return the device id
	 */
	public int getDeviceId() {
		return deviceId;
	}
}
