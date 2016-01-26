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

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

/**
 * THis class manages a XMLRPC-server.
 * The server can be registered as callback in the homematic-central.
 */
public class XMLRPCServer {
	
	/** The port of the server. */
	final int port = 8082;
	
	/** The logger from log4j. */
	final static Logger logger = LogManager.getLogger(XMLRPCServer.class);
	
	/**
	 * Starts the server with the properties defined in MyHandlers.properties.
	 */
	public void start(){


	          WebServer webServer = new WebServer(port);
	        
	          XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
	        
	          PropertyHandlerMapping phm = new PropertyHandlerMapping();
//	          XMLRPCMessageHandler handler = new XMLRPCMessageHandler();
	          //XMLRPCSystemHandler handler = new XMLRPCSystemHandler();
	          //handler.addHandler(phm);
	          
	          
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
	          
	          xmlRpcServer.setHandlerMapping(phm);
	        
	          XmlRpcServerConfigImpl serverConfig =
	              (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
	          serverConfig.setEnabledForExtensions(true);
	          serverConfig.setContentLengthOptional(false);

	          try {
				webServer.start();
				logger.info("XMLRPC-Server started");
			} catch (IOException e) {
				logger.error("Error while starting xmlrpc-server:");
				e.printStackTrace();
				System.exit(0);
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
