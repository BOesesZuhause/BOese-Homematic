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
 * <sebasian.lechte@hs-bochum.de> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Sebastian Lechte
 * ----------------------------------------------------------------------------
 */
package de.bo.aid.boese.homematic.main;


import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import de.bo.aid.boese.homeamtic.cli.Parameters;
import de.bo.aid.boese.homematic.dao.ComponentDao;
import de.bo.aid.boese.homematic.dao.ConnectorDao;
import de.bo.aid.boese.homematic.dao.DeviceDao;
import de.bo.aid.boese.homematic.model.Component;
import de.bo.aid.boese.homematic.model.Connector;
import de.bo.aid.boese.homematic.model.Device;
import de.bo.aid.boese.homematic.socket.SocketServer;
import de.bo.aid.boese.homematic.xmlrpc.XMLRPCClient;
import de.bo.aid.boese.homematic.xmlrpc.XMLRPCServer;


// TODO: Auto-generated Javadoc
/**
 * The Class Main.
 */
public class Main {
	
	/** The Constant logger. */
	final static Logger logger = Logger.getLogger(Main.class);
	
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){
		Parameters params = new Parameters();
		JCommander cmd = new JCommander(params);
		
		try {
	        cmd.parse(args);

	    } catch (ParameterException ex) {
	        System.out.println(ex.getMessage());
	        cmd.usage();
	        System.exit(0);
	    }
		
		String durl = params.getDurl();
		String hmurl = params.getHmurl();
		
		durl = "ws://127.0.0.1:8081/events/";
		hmurl = "http://192.168.23.33:2001";
		
		XMLRPCClient client = XMLRPCClient.getInstance();

		client.init(hmurl);
		
		if(params.isGenerate()){
			client.saveAllDevices();
			logger.info("Saved all devices in allDevices.xml");
			System.exit(0);
		}
		client.saveKnownDevices();
		initDatabase(client);
		
		
		SocketServer server = SocketServer.getInstance();
		server.start(durl);
		//TODO wait for startup http://stackoverflow.com/questions/4483928/is-an-embedded-jetty-server-guaranteed-to-be-ready-for-business-when-the-call
		server.requestConnection(); //TODO nach dem abschicken warten bevor der XML-Server gestartet wird


		//TODO wait until flow with the distributor is finished
		XMLRPCServer XMLserver = new XMLRPCServer();
		XMLserver.start();
		try {
			client.sendInit(InetAddress.getLocalHost().getHostAddress() + ":" + XMLserver.getPort());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	/**
	 * Inits the datase.
	 *
	 * @param client the client
	 */
	private static void initDatabase(XMLRPCClient client) {
		//Create connector in Database
		Connector con = new Connector();
		con.setIdverteiler(-1);
		con.setName("HomeMaticDefault");
		ConnectorDao.insertConnector(con);
		
		//Create Devices in Database
		for(Device dev : client.getDevices()){
			dev.setConnector(con);
			dev.setIdverteiler(-1);
			DeviceDao.insertDevice(dev);
		}
		
		//Create Components in Database
		for(Component comp : client.getComponents()){
			comp.setIdverteiler(-1);
			ComponentDao.insertComponent(comp);
		}
		client.cleanTempData(); //clean up
	}
	


}
