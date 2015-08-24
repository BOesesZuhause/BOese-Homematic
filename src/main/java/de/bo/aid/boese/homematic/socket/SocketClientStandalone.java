package de.bo.aid.boese.homematic.socket;


import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;


@ClientEndpoint
public class SocketClientStandalone {
	
    Session userSession = null;
    private MessageHandler messageHandler;

    
    /**
     * Opens a Connection to a Websocketserver
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
            this.messageHandler.handleMessage(message);
        }
    }
    
    @OnError
    public void onError(Throwable error){
    	messageHandler.closeConnection();
    	Logger.getLogger(SocketClientStandalone.class.getName()).log(Level.SEVERE, null, error);
    }

    /**
     * register message handler
     *
     * @param message
     */
    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    /**
     * Send a message.
     *
     * @param user
     * @param message
     */
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }


  

}
