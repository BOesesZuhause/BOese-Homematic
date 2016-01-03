package de.bo.aid.boese.homematic.socket;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import de.bo.aid.boese.homematic.main.DatabaseCache;
import de.bo.aid.boese.homematic.model.Connector;
import de.bo.aid.boese.json.BoeseJson;
import de.bo.aid.boese.json.RequestConnection;
import de.bo.aid.boese.json.SendValue;

@WebSocket
public class SecureSocketClient extends AbstractSocketClient
{
	/** logger for log4j. */
	final static Logger logger = LogManager.getLogger(SecureSocketClient.class);
	
    /** The session of the client. */
    Session userSession = null;
    
    /** The messagehandler which is subscribed. */
    private MessageHandler messageHandler;
    
	/** The databasecache used to read data quickly. */
	DatabaseCache cache = DatabaseCache.getInstance();
	
	 WebSocketClient client;
	
	/** The singleton-instance. */
	private static SecureSocketClient instance;
	
	/**
	 * Instantiates a new socket client.
	 */
	private SecureSocketClient(){
		
	}
	
	/**
	 * Gets the single instance of SocketClient.
	 *
	 * @return single instance of SocketClient
	 */
	public static SecureSocketClient getInstance(){
		if(instance == null){
			instance = new SecureSocketClient();
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
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setTrustAll(true); 

        client = new WebSocketClient(sslContextFactory);
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
    		 SecureSocketClient socket = new SecureSocketClient();
    		 client.connect(socket, endpointURI);
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
    }
    

    

    @OnWebSocketConnect
    public void onConnect(Session sess)
    {
        System.out.println("opening websocket");
        this.userSession = sess;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason)
    {
        System.out.println("closing websocket");
        messageHandler.closeConnection();
        this.userSession = null;
    }

    @OnWebSocketError
    public void onError(Throwable cause)
    {
    	messageHandler.closeConnection();
    	logger.error(cause.getMessage());
    }

    @OnWebSocketMessage
    public void onMessage(String msg)
    {
    	 if (this.messageHandler != null) {
         	logger.info("Client received Message: " + msg);
             this.messageHandler.handleMessage(msg);
         }
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
        try {
			this.userSession.getRemote().sendString(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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