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
import de.bo.aid.boese.homematic.model.Component;
import de.bo.aid.boese.homematic.socket.SocketClient;

// TODO: Auto-generated Javadoc
/**
 * The messagehandler used by the XMLRPC-Server.
 */

//TODO move non system-method in own handler
public class XMLRPCMessageHandler {
	
	/** The logger from log4j. */
	final static Logger logger = LogManager.getLogger(XMLRPCMessageHandler.class);

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
	
		Component comp = ComponentDao.getComponentByAddressAndName(address, value_key);
		if(comp == null){
			return;
		}
		

		int devCompId = comp.getIdverteiler();
		int devId = comp.getDevice().getIdverteiler();
		
		//Check if all components are confirmed
		if(devCompId == -1 || devId == -1){
			return;
		}
		
		if(value_key.equals("UNREACH")){
			if(valueObj.equals(true)){
				SocketClient.getInstance().sendStatus(devCompId, Status.ACTOR_DOES_NOT_REACT, -1);
			}else{
				SocketClient.getInstance().sendStatus(devCompId, Status.ACTIVE, -1);
			}
			
		}else if(value_key.equals("LOWBAT")){
			if(valueObj.equals(true)){
				SocketClient.getInstance().sendStatus(devCompId, Status.BATTERY, -1);
			}else{
				SocketClient.getInstance().sendStatus(devCompId, Status.ACTIVE, -1);
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
			SocketClient.getInstance().sendAction(value, devId, devCompId, System.currentTimeMillis());
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
		SocketClient.getInstance().sendValue(value, devId, devCompId, System.currentTimeMillis());		
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
	 * New devices.
	 *
	 * @param interfaceId the interface id
	 * @param deviceDescriptions the device descriptions
	 * @return the integer
	 */
	public Integer newDevices(String interfaceId, Object[] deviceDescriptions){
		System.out.println("new device");
		return null;
	}
	
	/**
	 * List devices.
	 *
	 * @param interfaceId the interface id
	 * @return the object[]
	 */
	public Object[] listDevices(String interfaceId){
		System.out.println("listDevices");
		return null;
	}
	

}
