package de.bo.aid.boese.homematic.socket;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.concurrent.Future;

import javax.persistence.EntityManager;

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

import de.bo.aid.boese.homematic.dao.ConnectorDao;
import de.bo.aid.boese.homematic.db.JPAUtil;
import de.bo.aid.boese.homematic.model.Connector;
import de.bo.aid.boese.json.BoeseJson;
import de.bo.aid.boese.json.RequestConnection;
import de.bo.aid.boese.json.SendStatus;
import de.bo.aid.boese.json.SendValue;

/**
 * This class manages a websocketclient with secured connection.
 */
@WebSocket
public class SocketClient
{
	private ConnectorDao connectorDao = new ConnectorDao();
	
	/** logger for log4j. */
	final static Logger logger = LogManager.getLogger(SocketClient.class);
	
    /** The session of the client. */
    Session userSession = null;
    
    /** The messagehandler which is subscribed to receive messages. */
    private MessageHandler messageHandler;
    	
	 /** The client. */
 	WebSocketClient client;
	
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
	 * Starts the client and connects to the server.
	 *
	 * @param serverUri the uri of the server
	 */
	public void start(String serverUri){
        URI uri = URI.create(serverUri);

        
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setTrustAll(true); 

        client = new WebSocketClient(sslContextFactory);

        
        try {
            client.start();
            SocketClient socket = this.getInstance();
            
            MessageHandler handler = new ProtocolHandler(socket);
            addMessageHandler(handler);
            
            Future<Session> fut = client.connect(socket,uri);
            userSession = fut.get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * Is called when a new connection is opened.
     * Saves the session for later use.
     *
     * @param sess the session object of the new session.
     */
    @OnWebSocketConnect
    public void onConnect(Session sess)
    {
        System.out.println("opening websocket");
        this.userSession = sess;
    }

    /**
     * Is called when a connection is closed.
     * Deletes the saved session.
     *
     * @param statusCode the status code
     * @param reason the reason
     */
    @OnWebSocketClose
    public void onClose(int statusCode, String reason)
    {
        System.out.println("closing websocket");
        messageHandler.closeConnection();
        this.userSession = null;
    }

    /**
     * Is called when an error occured and logs the error.
     *
     * @param cause the cause of the error
     */
    @OnWebSocketError
    public void onError(Throwable cause)
    {
    	messageHandler.closeConnection();
    	logger.error(cause.getMessage());
    }

    /**
     * Is called when a message is received. Forwards the message to the messagehandler.
     *
     * @param msg the received message
     */
    @OnWebSocketMessage
    public void onMessage(String msg)
    {
    	 if (this.messageHandler != null) {
         	logger.info("Client received Message: " + msg);
             this.messageHandler.handleMessage(msg);
         }
    }
    
    /**
     * registers a message handler.
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
        
    	EntityManager em = JPAUtil.getEntityManager();
    	
    	em.getTransaction().begin();
        Connector con = connectorDao.get(em);
        em.getTransaction().commit();
        
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

    	
    	EntityManager em = JPAUtil.getEntityManager();
    	
    	em.getTransaction().begin();
        Connector con = connectorDao.get(em);
        em.getTransaction().commit();
    	
        int conId = con.getIdverteiler();
        
        SendValue sendval = new SendValue(devId, devCompId, value, time, conId, 0, System.currentTimeMillis());
        OutputStream os = new ByteArrayOutputStream();
        BoeseJson.parseMessage(sendval, os);
        sendMessage(os.toString());
    }
    
    /**
     * Sendvalue message for components with type=action.
     * It automatically sends a second message which resets the value.
     * It is used for switches which send a single "true" value when pressed,
     * but never send a reset value.
     *
     * @param value the value
     * @param devId the id of the device, which is saved in the distributor
     * @param devCompId the id of the deviceComponent, which is saved in the distributor
     * @param time the timestamp of the value
     */
    public void sendAction(double value, int devId, int devCompId, long time) {

    	EntityManager em = JPAUtil.getEntityManager();
    	
    	em.getTransaction().begin();
        Connector con = connectorDao.get(em);
        em.getTransaction().commit();
        
        int conId = con.getIdverteiler();

        SendValue sendval = new SendValue(devId, devCompId, value, time, conId, 0, System.currentTimeMillis());
        OutputStream os = new ByteArrayOutputStream();
        BoeseJson.parseMessage(sendval, os);
        sendMessage(os.toString());
        
        sendval = new SendValue(devId, devCompId, 0, time, conId, 0, System.currentTimeMillis());
        os = new ByteArrayOutputStream();
        BoeseJson.parseMessage(sendval, os);
        sendMessage(os.toString());
        
    }

    /**
     * Senda a status message to the distributor. It can be used when the status of
     * a device changes to inform the distributor.
     *
     * @param devCompId the dev comp id
     * @param statusCode the status code
     * @param statusTimestamp the status timestamp
     */
    public void sendStatus(int devCompId, int statusCode, long statusTimestamp) {
    	
    	EntityManager em = JPAUtil.getEntityManager();
    	
    	em.getTransaction().begin();
        Connector con = connectorDao.get(em);
        em.getTransaction().commit();
        
        int conId = con.getIdverteiler();
        
        SendStatus ss = new SendStatus(devCompId, statusCode, statusTimestamp, true, conId, 0, System.currentTimeMillis());
        OutputStream os = new ByteArrayOutputStream();
        BoeseJson.parseMessage(ss, os);
        sendMessage(os.toString());
    }


}