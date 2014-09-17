package org.nhnnext.architecting;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerInitializer {
	
	private static final Logger log = LoggerFactory.getLogger(ServerInitializer.class);
	public static final int PORT = 5000; 
	
	public static void main(String[] args) {
		log.info("ServerInitializer Main Method Called");
		Reactor reactor = new Reactor(PORT);
		
		try {
			Serializer serializer = new Persister();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ServerInitializer.class.getClass().getResourceAsStream("/HandlerList.xml")));
			ServerListData serverList = serializer.read(ServerListData.class,  bufferedReader);
			
			for (HandlerListData handlerListData : serverList.getServer()) {
				List<String> handlerList = handlerListData.getHandler();
				
				for (String handler : handlerList) {
					try {
						reactor.registerHandler((EventHandler)Class.forName("org.nhnnext.architecting."+handler).newInstance());
						log.debug("handler : {}", handler);
					} catch (Exception e) {
						log.error("Error Occuc in Register Class From XML Data, handlerName = {}", handler);
						log.error("ErrorMessage : {}", e.getMessage());
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		reactor.startServer();
	}
}
