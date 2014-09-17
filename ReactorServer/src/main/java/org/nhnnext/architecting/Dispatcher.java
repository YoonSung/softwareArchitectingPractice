package org.nhnnext.architecting;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Dispatcher {
	
	private final int HEADER_SIZE = 6;
	
	public void displatch(ServerSocket serverSocket) {
		try {
			Socket socket = serverSocket.accept();
			demultiplex(socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void demultiplex(Socket socket) {
		InputStream inputStream;
		try {
			inputStream = socket.getInputStream();
			
			byte[] buffer = new byte[HEADER_SIZE];
			inputStream.read(buffer);
			String header = new String(buffer);
			
			switch (header) {
			case "0x5001":
				new StreamSayHelloProtocol().handleEvent(inputStream);
				break;
			case "0x6001":
				new StreamUpdateProfileProtocol().handleEvent(inputStream);
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
