package org.nhnnext.architecting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerInitializerTest {

	
	private static final Logger log = LoggerFactory.getLogger(ServerInitializerTest.class);
	private static Reactor reactor;
	
	@BeforeClass
	public static void startServer() throws InterruptedException {
		
		new Thread() {
			public void run() {
				reactor = new Reactor(ServerInitializer.PORT);
				
				//Register Class From XML Data
				Serializer serializer = new Persister();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/HandlerList.xml")));

				ServerListData serverList = null;
				try {
					serverList = serializer.read(ServerListData.class,  bufferedReader);
					for (HandlerListData handlerListData : serverList.getServer()) {
						List<String> handlerList = handlerListData.getHandler();
						
						for (String handler : handlerList) {
							reactor.registerHandler((EventHandler)Class.forName("org.nhnnext.architecting."+handler).newInstance());
							log.debug("handler : {}", handler);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				reactor.startServer();
			}
		}.start();
		
		//For Prepare Server Start
		Thread.sleep(200);
	}
	
	@AfterClass
	public static void stopServer() {
		reactor.stopServer();
	}
	
	@Test
	public void socketConnectionTest5001Protocol() {
		log.info("CLIENT ON, Protocol : 5001");
		
		try (Socket socket = new Socket("127.0.0.1", 5000)){
			OutputStream out = socket.getOutputStream();
			String message = "0x5001|홍길동|22";
			
			out.write(message.getBytes());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void socketConnectionTest6001Protocol() {
		log.info("CLIENT ON, Protocol : 6001");
		
		try (Socket socket = new Socket("127.0.0.1", 5000)){
			OutputStream out = socket.getOutputStream();
			String message = "0x6001|hong|1234|홍길동|22|남성";
			
			out.write(message.getBytes());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
