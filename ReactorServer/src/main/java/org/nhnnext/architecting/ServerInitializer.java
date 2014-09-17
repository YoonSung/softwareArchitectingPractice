package org.nhnnext.architecting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerInitializer {
	
	
	private static final Logger log = LoggerFactory.getLogger(ServerInitializer.class);
	
	public static void main(String[] args) {
		int port = 5000;
		
		log.debug("SERVER ON : {}", port);
	}
}
