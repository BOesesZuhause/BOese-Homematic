package de.bo.aid.boese.homematic.xmlrpc;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;

public class XMLRPCSystemHandler {
	
	/** The logger from log4j. */
	final static Logger logger = LogManager.getLogger(XMLRPCSystemHandler.class);
	
	XMLRPCMessageHandler handler = new XMLRPCMessageHandler();
	
	public void addHandler(PropertyHandlerMapping phm){
		try {
			phm.addHandler("system", XMLRPCSystemHandler.class);
			phm.addHandler("CallbackHandler", XMLRPCMessageHandler.class);
		} catch (XmlRpcException e) {
			logger.error("Error while setting up Handlers for the XMLRPC-Server:");
			e.printStackTrace();
			System.exit(0);
		}

	}
	
	/**
	 * handles multicall-messages which contain more than one message.
	 *
	 * @param args the messages
	 */
	public Boolean[] multicall(Object[] args) {
		Boolean res[] = new Boolean[args.length];
	    for (int i=0; i<args.length; i++) {
	      Map<?, ?> call = (Map<?, ?>) args[i];
	      String method = (String)call.get("methodName");
	      Object[] margs = (Object[])call.get("params");
	      if ("event".equals(method)) {
	        // hier erfolgt der Aufruf der "event"-Methode
	        handler.event(margs[0].toString(), margs[1].toString(), margs[2].toString(), margs[3]);
	        res[i] = Boolean.TRUE;
	      }
	    }
	    return res;
	}
	
	
	public String[] listMethods(){
		System.out.println("listMethods");
		return null;	
	}

}
