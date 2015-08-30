package de.bo.aid.boese.homematic.main;


import de.bo.aid.boese.homematic.socket.SocketServer;


public class Main {
	
	
	public static void main(String[] args){
				SocketServer server = new SocketServer();
				server.start("ws://localhost:8081/events/");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


}
