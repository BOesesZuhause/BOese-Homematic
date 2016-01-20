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
package de.bo.aid.boese.homematic.xmlrpc;

import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import de.bo.aid.boese.constants.Status;
import de.bo.aid.boese.homematic.dao.ComponentDao;
import de.bo.aid.boese.homematic.main.DatabaseCache;
import de.bo.aid.boese.homematic.main.HMConnector;
import de.bo.aid.boese.homematic.model.Component;
import de.bo.aid.boese.homematic.socket.SocketClient;


/**
 * The messagehandler used by the XMLRPC-Server.
 * Implements the XMLRPC-method which can be called by a client.
 * In this case the HomeMatic-CCU will call these method to
 * submit events.
 */

//TODO move non system-method in own handler
public class XMLRPCMessageHandler {
	
	/** The logger from log4j. */
	final static Logger logger = LogManager.getLogger(XMLRPCMessageHandler.class);
	
	SocketClient wsClient = SocketClient.getInstance();

	/**
	 * receives event-messages from the HomeMatic-System.
	 *
	 * @param interface_id the id of the XMLRPC-Server
	 * @param address the address of the component
	 * @param value_key name of the value
	 * @param valueObj the value 
	 */
	public void event(String interface_id, String address, String value_key, Object valueObj){
		logger.info("received event: address: " + address + ", value_key: " + value_key + ", value: " + valueObj.toString());
	
		//TODO use databasecache
		
		Component comp = ComponentDao.getComponentByAddressAndName(address, value_key);
		if(comp == null){
			return;
		}
		

		int devCompId = comp.getIdverteiler();
		int devId = comp.getDevice().getIdverteiler();
		
		//check if component is confirmed
		if(comp.getIdverteiler()==-1){
			return;
		}
		
		if(value_key.equals("UNREACH")){
		    logger.warn("Device with address: " + address + " is unreachable");
			if(valueObj.equals(true)){
				wsClient.sendStatus(devCompId, Status.ACTOR_DOES_NOT_REACT, -1);
			}else{
			    wsClient.sendStatus(devCompId, Status.ACTIVE, -1);
			}
			return;
		}else if(value_key.equals("LOWBAT")){
			if(valueObj.equals(true)){
			    wsClient.sendStatus(devCompId, Status.BATTERY, -1);
			}else{
			    wsClient.sendStatus(devCompId, Status.ACTIVE, -1);
			}
			
		}

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
			wsClient.sendAction(value, devId, devCompId, System.currentTimeMillis());
			return;
		case "INTEGER":
			value = Integer.parseInt(valueObj.toString());
			break;
		case "ENUM":
			break;
		case "STRING":
			break;
			default:
				logger.error("Unknown type: " + type + " for component with address: " + address);
		}
		wsClient.sendValue(value, devId, devCompId, System.currentTimeMillis());		
	}
	
	/**
	 * handles multicall-messages which contain more than one message.
	 *
	 * @param args the messages
	 * @return the boolean[]
	 */
	public Boolean[] multicall(Object[] args) {
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
	 * Handles "addDevice" calls..
	 *
	 * @param interfaceId the interface id
	 * @param deviceDescriptions the device descriptions
	 * @return the integer
	 */
	//TODO geht nicht
	public Integer newDevices(String interfaceId, Object[] deviceDescriptions){
		System.out.println("new device");
		return null;
	}
	
	/**
	 * Handles "listDevices calls.
	 *
	 * @param interfaceId the interface id
	 * @return the object[]
	 */
	//TODO geht nicht
	public Object[] listDevices(String interfaceId){
		System.out.println("listDevices");
		return null;
	}
	

}
