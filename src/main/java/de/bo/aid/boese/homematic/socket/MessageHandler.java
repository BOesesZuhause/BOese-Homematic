/*
 * 
 */
package de.bo.aid.boese.homematic.socket;
    // TODO: Auto-generated Javadoc
/**
     * Interface for Message Handlers, which can subscribe to the WebsocketClient.
     *
     */  
public interface MessageHandler {
    	

        /**
         * 
         * Method for handling Messages.
         * 
         * @param message Message which should be handled
         */
        public void handleMessage(String message);
        
        /**
         * Close connection.
         */
        public void closeConnection();
    }