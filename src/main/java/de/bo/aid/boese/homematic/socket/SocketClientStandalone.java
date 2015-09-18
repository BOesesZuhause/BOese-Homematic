/*
 * 
 */
package de.bo.aid.boese.homematic.socket;


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

import org.apache.log4j.Logger;


// TODO: Auto-generated Javadoc
/**
 * The Class SocketClientStandalone.
 */
@ClientEndpoint
public class SocketClientStandalone {
	
	final static Logger logger = Logger.getLogger(SocketClientStandalone.class);
	
    /** The user session. */
    Session userSession = null;
    
    /** The message handler. */
    private MessageHandler messageHandler;

    
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
     * On error.
     *
     * @param error the error
     */
    @OnError
    public void onError(Throwable error){
    	messageHandler.closeConnection();
    	logger.error(error.getMessage());
    }

    /**
     * register message handler.
     *
     * @param msgHandler the msg handler
     */
    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    /**
     * Send a message.
     *
     * @param message the message
     */
    public void sendMessage(String message) {
    	logger.info("Client sent Message: " + message);
        this.userSession.getAsyncRemote().sendText(message);
    }


  

}
