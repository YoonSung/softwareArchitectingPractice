package org.nhnnext.architecting;

import java.io.IOException;
import java.net.ServerSocket;

import org.nhnnext.architecting.domain.HandleMap;
import org.nhnnext.architecting.handler.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Reactor {
	
	private static final Logger log = LoggerFactory.getLogger(Reactor.class);
	private ServerSocket serverSocket;
	private boolean isOperation = true; 
	private HandleMap handleMap; 
	
	public Reactor(int port) {
		
		handleMap = new HandleMap();
		
		try {
			serverSocket = new ServerSocket(port);
			log.info("SERVER ON : {}", port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void startServer() {
		Dispatcher dispatcher = new Dispatcher();
		
		while(isOperation) {
			dispatcher.displatch(serverSocket, handleMap);
		}
	}
	
	public void stopServer() {
		this.isOperation = false;
	}
	
	public void registerHandler(EventHandler handler) {
		handleMap.put(handler.getHandler(), handler);
	}
	
	public void removeHandler(EventHandler handler) {
		handleMap.remove(handler.getHandler());
	}
}
