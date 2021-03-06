package practice.nio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerInitializerTest {

	private static final Logger log = LoggerFactory.getLogger(ServerInitializerTest.class);

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
	
	@Test
	public void socketConnectionTest7001Protocol() {
		log.info("CLIENT ON, Protocol : 7001");
		
		try (Socket socket = new Socket("127.0.0.1", 5000)){
			OutputStream out = socket.getOutputStream();
			String message = "0x7001|";
			
			out.write(message.getBytes());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
