/*
 * 
 */
package de.bo.aid.boese.homematic.xmlrpc;

import java.io.IOException;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;


// TODO: Auto-generated Javadoc
/**
 * The Class XMLRPCServer.
 */
public class XMLRPCServer {
	final int port = 8082;
	
	/**
	 * Start.
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public int getPort() {
		return port;
	}

}
