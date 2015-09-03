package de.bo.aid.boese.homematic.xmlrpc;

import java.util.Map;

public class MessageHandler {

	public void event(String interface_id, String address, String value_key, Object value){
		System.out.println("event ausgelöst");
		System.out.println("interface_id: " + interface_id);
		System.out.println("Address: " + address);
		System.out.println("value_key: " + value_key);
		System.out.println("value: " + value.toString());
	}
	
	public Boolean[] multicall(Object[] args) {
		System.out.println("multicall received");
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
}
