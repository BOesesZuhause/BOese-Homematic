package de.bo.aid.boese.homematic.socket;
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
        
        public void closeConnection();
    }