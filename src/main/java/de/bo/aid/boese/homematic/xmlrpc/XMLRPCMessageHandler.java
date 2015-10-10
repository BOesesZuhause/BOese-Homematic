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

import org.apache.log4j.Logger;

import de.bo.aid.boese.homematic.dao.ComponentDao;
import de.bo.aid.boese.homematic.model.Component;
import de.bo.aid.boese.homematic.socket.SocketClient;

/**
 * The messagehandler used by the XMLRPC-Server.
 */
public class XMLRPCMessageHandler {
	
	/** The logger from log4j. */
	final static Logger logger = Logger.getLogger(XMLRPCMessageHandler.class);

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
		}
		SocketClient.getInstance().sendValue(value, devId, devCompId, System.currentTimeMillis());		
	}
	
	/**
	 * handles multicall-messages which contain more than one message.
	 *
	 * @param args the messages
	 */
	public void multicall(Object[] args) {
	    for (int i=0; i<args.length; i++) {
	      Map<?, ?> call = (Map<?, ?>) args[i];
	      String method = (String)call.get("methodName");
	      Object[] margs = (Object[])call.get("params");
	      
	      if ("event".equals(method)) {
	        // hier erfolgt der Aufruf der "event"-Methode
	        event(margs[0].toString(), margs[1].toString(), margs[2].toString(), margs[3]);
	      }
	    }
	}
	
//	/**
//	 * List methods.
//	 */
//	public void listMethods(){
//		
//	}
//	
//	/**
//	 * List devices.
//	 */
//	public void listDevices(){
//		
//	}
}
