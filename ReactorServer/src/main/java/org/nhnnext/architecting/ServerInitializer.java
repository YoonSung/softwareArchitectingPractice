package org.nhnnext.architecting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerInitializer {
	
	private static final Logger log = LoggerFactory.getLogger(ServerInitializer.class);
	public static final int PORT = 5000; 
	
	public static void main(String[] args) {
		log.info("ServerInitializer Main Method Called");
		Reactor reactor = new Reactor(PORT);
		
		reactor.registerHandler(new StreamSayHelloEventHandler());
		reactor.registerHandler(new StreamUpdateProfileEventHandler());
		
		reactor.startServer();
	}
}
