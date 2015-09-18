/*
 * 
 */
package de.bo.aid.boese.homematic.xmlrpc;

import java.util.Map;

import org.apache.log4j.Logger;

import de.bo.aid.boese.homematic.dao.ComponentDao;
import de.bo.aid.boese.homematic.model.Component;
import de.bo.aid.boese.homematic.socket.SocketServer;

// TODO: Auto-generated Javadoc
/**
 * The Class MessageHandler.
 */
public class MessageHandler {
	
	final static Logger logger = Logger.getLogger(MessageHandler.class);

	/**
	 * Event.
	 *
	 * @param interface_id the interface_id
	 * @param address the address
	 * @param value_key the value_key
	 * @param valueObj the value obj
	 */
	public void event(String interface_id, String address, String value_key, Object valueObj){
		logger.info("received event: address: " + address + ", value_key: " + value_key + ", value: " + valueObj.toString());
		
		Component comp = ComponentDao.getComponentByAddressAndName(address, value_key);
		if(comp == null){
			return;
		}
	
		int devCompId = comp.getIdverteiler();
		int devId = comp.getDevice().getIdverteiler();
		double value = 0;
		String type = comp.getType();
		switch(type){
		case "BOOL":
			if(valueObj.toString().equals("false")){
				value = 0;
			}else if(valueObj.toString().equals("true")){
				value = 1;
			}
			break;
		case "FLOAT":
			value = Float.parseFloat(valueObj.toString());
			break;
		case "ACTION":
			value = 1;
			SocketServer.getInstance().sendAction(value, devId, devCompId, System.currentTimeMillis());
			return;
		case "INTEGER":
			value = Integer.parseInt(valueObj.toString());
			break;
		case "ENUM":
			//value = 
			break;
			default:
		}
		SocketServer.getInstance().sendValue(value, devId, devCompId, System.currentTimeMillis());		
	}
	
	/**
	 * Multicall.
	 *
	 * @param args the args
	 * @return the boolean[]
	 */
	public Boolean[] multicall(Object[] args) {
		//System.out.println("multicall received");
	    Boolean res[] = new Boolean[args.length]; 
	    for (int i=0; i<args.length; i++) {
	      Map<?, ?> call = (Map<?, ?>) args[i];
	      String method = (String)call.get("methodName");
	      Object[] margs = (Object[])call.get("params");
	      
	      if ("event".equals(method)) {
	        // hier erfolgt der Aufruf der "event"-Methode
	        event(margs[0].toString(), margs[1].toString(), margs[2].toString(), margs[3]);
	        res[i] = Boolean.TRUE;
	      }
	    }
		return res;
	}
	
	/**
	 * List methods.
	 */
	public void listMethods(){
		
	}
	
	/**
	 * List devices.
	 */
	public void listDevices(){
		
	}
}
