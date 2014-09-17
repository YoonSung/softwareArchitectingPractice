package org.nhnnext.architecting;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.nhnnext.architecting.domain.HandleMap;

public class Dispatcher {
	
	public void displatch(ServerSocket serverSocket, HandleMap handleMap) {
		try {
			Socket socket = serverSocket.accept();
			new Thread(new Demultiplexer(socket, handleMap)).start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
