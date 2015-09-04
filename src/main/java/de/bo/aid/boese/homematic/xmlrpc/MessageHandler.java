package de.bo.aid.boese.homematic.xmlrpc;

import java.util.Map;

import de.bo.aid.boese.homematic.dao.ComponentDao;
import de.bo.aid.boese.homematic.dao.DeviceDao;
import de.bo.aid.boese.homematic.model.Component;
import de.bo.aid.boese.homematic.model.Device;
import de.bo.aid.boese.homematic.socket.SocketServer;

public class MessageHandler {

	public void event(String interface_id, String address, String value_key, Object valueObj){
		//TODO Nachricht an Verteiler senden
		if(address.startsWith("LEQ0789433")){
			System.out.println("event ausgelöst");
			System.out.println("interface_id: " + interface_id);
			System.out.println("Address: " + address);
			System.out.println("value_key: " + value_key);
			System.out.println("value: " + valueObj.toString());
			System.out.println(System.currentTimeMillis());
			System.out.println();

		}

		
		Component comp = ComponentDao.getComponentByAddressAndName(address, value_key);
		if(comp == null){
			return;
		}
	
		int devCompId = comp.getIdverteiler();
		int devId = comp.getDevice().getIdverteiler();
		double value = 0;
		String type = comp.getType().substring(0, comp.getType().indexOf(":"));
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
			break;
		case "INTEGER":
			//value = 
			break;
		case "ENUM":
			//value = 
			break;
			default:
		}
		SocketServer.getInstance().sendValue(value, devId, devCompId, System.currentTimeMillis());		
	}
	
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
	
	public void listMethods(){
		
	}
	
	public void listDevices(){
		
	}
}
