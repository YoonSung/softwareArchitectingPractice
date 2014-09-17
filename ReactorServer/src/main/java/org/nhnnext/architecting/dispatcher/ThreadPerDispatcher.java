package org.nhnnext.architecting.dispatcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.nhnnext.architecting.Demultiplexer;
import org.nhnnext.architecting.domain.HandleMap;

public class ThreadPerDispatcher implements Dispatcher{
	
	private boolean isOperation = true;
	
	@Override
	public void startDispatch(ServerSocket serverSocket, HandleMap handleMap) {
		
		while(isOperation) {
			try {
				Socket socket = serverSocket.accept();
				new Thread(new Demultiplexer(socket, handleMap)).start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void stopDispatch() {
		this.isOperation = false;
	}
}
