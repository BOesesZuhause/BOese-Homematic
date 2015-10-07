package de.bo.aid.boese.homematic.mapper;

import java.util.HashMap;

import org.apache.log4j.Logger;

import de.bo.aid.boese.homematic.xml.ChannelXML;
import de.bo.aid.boese.homematic.xml.ComponentXML;
import de.bo.aid.boese.homematic.xml.DeviceXML;
import de.bo.aid.boese.homematic.xml.DevicesXML;
import de.bo.aid.boese.homematic.xmlrpc.XMLRPCClient;

public class DevicesXMLMapper {
	
	/** The logger for log4j. */
	final static Logger logger = Logger.getLogger(DevicesXMLMapper.class);

	//TODO don't save empty channels
	@SuppressWarnings("unchecked")
	public static DevicesXML map(Object obj, boolean ignoreVirtual) {

		DevicesXML out = new DevicesXML();

		Object[] devices = (Object[]) obj;

		for (Object device : devices) {

			HashMap<String, Object> map = (HashMap<String, Object>) device;

			String address = (String) map.get("ADDRESS");
			// ignore channels
			if (address.contains(":")) {
				continue;
			}

			// ignore virtual devices
			if (ignoreVirtual && address.startsWith("BidCoS")) {
				continue;
			}

			// interessante Attribute holen
			String type = (String) map.get("TYPE");
			String firmware = (String) map.get("FIRMWARE");

			DeviceXML devXML = new DeviceXML();
			devXML.setFirmware(firmware);
			devXML.setModel(type);

			Object[] children = (Object[]) map.get("CHILDREN");

			// Iterate over ParamsetDescriptions
			for (Object child : children) {
				ChannelXML channelXML = new ChannelXML();

				int pos = child.toString().indexOf(":");
				String channel = child.toString().substring(pos + 1);
				channelXML.setNumber(Integer.parseInt(channel));

				Object paramsetDescription = XMLRPCClient.getInstance().getParamSets(child.toString());
				if(paramsetDescription == null){
					logger.warn(" Skipped HomeMatic Device with address: " + child.toString() + " because it's response was invalid");
					continue;
				}

				HashMap<String, Object> paramSetDescriptionMap = (HashMap<String, Object>) paramsetDescription;
				for (Object value : paramSetDescriptionMap.values()) {
					HashMap<String, Object> valueMap = (HashMap<String, Object>) value;

					ComponentXML compXML = new ComponentXML();
					if (valueMap.get("TYPE").equals("ACTION")) {
						compXML.setAktor(true);
					} else {
						compXML.setAktor(false);
					}

					compXML.setUnit((String) valueMap.get("UNIT"));
					compXML.setType((String) valueMap.get("TYPE"));
					compXML.setName((String) valueMap.get("ID"));
					channelXML.getComponents().add(compXML);
				}
				devXML.getChannels().add(channelXML);
			}

			out.getDevices().add(devXML);
		}

		return out;
	}
}