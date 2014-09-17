package org.nhnnext.architecting;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.nhnnext.architecting.constant.Constant;
import org.nhnnext.architecting.domain.HandleMap;

public class Dispatcher {
	
	public void displatch(ServerSocket serverSocket, HandleMap handleMap) {
		try {
			Socket socket = serverSocket.accept();
			demultiplex(socket, handleMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void demultiplex(Socket socket, HandleMap handleMap) {
		InputStream inputStream;
		try {
			inputStream = socket.getInputStream();
			
			byte[] buffer = new byte[Constant.HEADER_SIZE];
			inputStream.read(buffer);
			String header = new String(buffer);
			
			handleMap.get(header).handleEvent(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
