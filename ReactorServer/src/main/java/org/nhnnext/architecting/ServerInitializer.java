package org.nhnnext.architecting;

import java.io.IOException;
import java.net.ServerSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerInitializer {
	
	private static final Logger log = LoggerFactory.getLogger(ServerInitializer.class);
	private static boolean isOperation = true;
	
	public static void main(String[] args) {
		startServer();
	}
	
	public static void stopServer() {
		isOperation = false;
	}

	public static void startServer() {
		int port = 5000;
		log.info("SERVER ON : {}", port);
		
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			
			Dispatcher dispatcher = new Dispatcher();
			while (isOperation) {
				dispatcher.displatch(serverSocket);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
