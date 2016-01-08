package de.bo.aid.boese.homematic.socket;

/**
 * Interface for different types of Socketclients.
 */
public abstract class AbstractSocketClient {
    


    public abstract void sendMessage(String string);
    
    public abstract void start(String serverUri);
    
    public abstract void requestConnection();
    
    public abstract void sendValue(double value, int devId, int devCompId, long time);
    
    public abstract void sendAction(double value, int devId, int devCompId, long time);

}