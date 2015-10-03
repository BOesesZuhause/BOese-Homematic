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

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

// TODO: Auto-generated Javadoc
/**
 * Defines an XMLRPC-server. The server can be registered as callback in the homematic-central.
 */
public class XMLRPCServer {
	
	/** The port of the server. */
	final int port = 8082;
	
	/** The logger from log4j. */
	final static Logger logger = Logger.getLogger(XMLRPCServer.class);
	
	/**
	 * Starts the server with the properties defined in MyHandlers.properties.
	 */
	public void start(){


	          WebServer webServer = new WebServer(port);
	        
	          XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
	        
	          PropertyHandlerMapping phm = new PropertyHandlerMapping();
	          /* Load handler definitions from a property file.
	           * The property file might look like:
	           *   Calculator=org.apache.xmlrpc.demo.Calculator
	           *   org.apache.xmlrpc.demo.proxy.Adder=org.apache.xmlrpc.demo.proxy.AdderImpl
	           */
	          try {
				phm.load(Thread.currentThread().getContextClassLoader(),
				           "MyHandlers.properties");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlRpcException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	          /* You may also provide the handler classes directly,
	           * like this:
	           * phm.addHandler("Calculator",
	           *     org.apache.xmlrpc.demo.Calculator.class);
	           * phm.addHandler(org.apache.xmlrpc.demo.proxy.Adder.class.getName(),
	           *     org.apache.xmlrpc.demo.proxy.AdderImpl.class);
	           */
	          xmlRpcServer.setHandlerMapping(phm);
	        
	          XmlRpcServerConfigImpl serverConfig =
	              (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
	          serverConfig.setEnabledForExtensions(true);
	          serverConfig.setContentLengthOptional(false);

	          try {
				webServer.start();
				logger.info("XMLRPC-Server started");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

}
