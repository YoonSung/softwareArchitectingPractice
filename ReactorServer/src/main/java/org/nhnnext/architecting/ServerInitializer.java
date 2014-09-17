package org.nhnnext.architecting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerInitializer {
	
	private static final Logger log = LoggerFactory.getLogger(ServerInitializer.class);
	
	public static void main(String[] args) {
		int port = 5000;
		log.info("SERVER ON : {}", port);
		
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			
			Socket connection;
			
			while (true) {
				connection = serverSocket.accept();
				InputStreamReader isr = new InputStreamReader(connection.getInputStream());
				BufferedReader br = new BufferedReader(isr);
				
				String readData = br.readLine(); 
				
				log.info("READ : {}", readData);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
