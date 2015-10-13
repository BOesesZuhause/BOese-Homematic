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
package de.bo.aid.boese.homematic.socket;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import de.bo.aid.boese.homematic.main.DatabaseCache;
import de.bo.aid.boese.homematic.model.Connector;
import de.bo.aid.boese.json.BoeseJson;
import de.bo.aid.boese.json.RequestConnection;
import de.bo.aid.boese.json.SendValue;


/**
 * class which acts as a websocketclient.
 */
@ClientEndpoint
public class SocketClient {
	
	/** logger for log4j. */
	final static Logger logger = LogManager.getLogger(SocketClient.class);
	
    /** The session of the client. */
    Session userSession = null;
    
    /** The messagehandler which is subscribed. */
    private MessageHandler messageHandler;
    
	/** The databasecache used to read data quickly. */
	DatabaseCache cache = DatabaseCache.getInstance();
	
	/** The singleton-instance. */
	private static SocketClient instance;
	
	/**
	 * Instantiates a new socket client.
	 */
	private SocketClient(){
		
	}
	
	/**
	 * Gets the single instance of SocketClient.
	 *
	 * @return single instance of SocketClient
	 */
	public static SocketClient getInstance(){
		if(instance == null){
			instance = new SocketClient();
		}
		return instance;
	}
    
    /**
	 * Starts the server and connects to it.
	 *
	 * @param serverUri the uri of the server
	 */
	public void start(String serverUri){
		URI uri = URI.create(serverUri);
		MessageHandler handler = new ProtocolHandler(this);
		addMessageHandler(handler);
		connect(uri);
	}

    
    /**
     * Opens a Connection to a Websocketserver.
     *
     * @param endpointURI URI of the Websocketserver to which the connection should be opened.
     */
    public void connect(URI endpointURI){
    	 try {
             WebSocketContainer container = ContainerProvider.getWebSocketContainer();
             container.connectToServer(this, endpointURI);
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("opening websocket");
        this.userSession = userSession;
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing websocket");
        messageHandler.closeConnection();
        this.userSession = null;
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
        	logger.info("Client received Message: " + message);
            this.messageHandler.handleMessage(message);
        }
    }
    
    /**
     * On error-method. Called when errors occur.
     *
     * @param error The Exception which occured
     */
    @OnError
    public void onError(Throwable error){
    	messageHandler.closeConnection();
    	logger.error(error.getMessage());
    }

    /**
     * register message handler.
     *
     * @param msgHandler the handler which should be registered
     */
    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    /**
     * Send a message over the connection.
     *
     * @param message the message
     */
    public void sendMessage(String message) {
    	logger.info("Client sent Message: " + message);
        this.userSession.getAsyncRemote().sendText(message);
    }


    
    
    //Protocol specific methods

		/**
		 * Sends a request-connection message to the distributor.
		 */
		public void requestConnection(){
			
			cache.update();	
			Connector con = cache.getConnector();
			
			// Request connection
			RequestConnection reqCon = new RequestConnection(con.getName(), con.getSecret(), con.getIdverteiler(), 0, System.currentTimeMillis());
			OutputStream os = new ByteArrayOutputStream();
			BoeseJson.parseMessage(reqCon, os);
			sendMessage(os.toString());
		}

		/**
		 * Sends a value to the distributor.
		 *
		 * @param value the value
		 * @param devId the id of the device, which is saved in the distributor
		 * @param devCompId the id of the deviceComponent, which is saved in the distributor
		 * @param time the timestamp of the value
		 */
		//wird von HomeMatic-Gerät aufgerufen
		public void sendValue(double value, int devId, int devCompId, long time){

			int conId = cache.getConnector().getIdverteiler();

			SendValue sendval = new SendValue(devId, devCompId, value, time, conId, 0, System.currentTimeMillis());
			OutputStream os = new ByteArrayOutputStream();
			BoeseJson.parseMessage(sendval, os);
			sendMessage(os.toString());
		}
		
		/**
		 * Sendvalue message for components with type=action.
		 * It automatically sends a second message which resets the value.
		 *
		 * @param value the value
		 * @param devId the id of the device, which is saved in the distributor
		 * @param devCompId the id of the deviceComponent, which is saved in the distributor
		 * @param time the timestamp of the value
		 */
		public void sendAction(double value, int devId, int devCompId, long time) {

			int conId = cache.getConnector().getIdverteiler();

			SendValue sendval = new SendValue(devId, devCompId, value, time, conId, 0, System.currentTimeMillis());
			OutputStream os = new ByteArrayOutputStream();
			BoeseJson.parseMessage(sendval, os);
			sendMessage(os.toString());
			
			sendval = new SendValue(devId, devCompId, 0, time, conId, 0, System.currentTimeMillis());
			os = new ByteArrayOutputStream();
			BoeseJson.parseMessage(sendval, os);
			sendMessage(os.toString());
			
		}

  

}
