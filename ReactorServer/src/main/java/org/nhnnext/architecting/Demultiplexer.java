package org.nhnnext.architecting;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.nhnnext.architecting.constant.Constant;
import org.nhnnext.architecting.domain.HandleMap;

public class Demultiplexer implements Runnable {
	
	Socket socket;
	HandleMap handleMap;
	
	public Demultiplexer(Socket socket, HandleMap handleMap) {
		this.socket = socket;
		this.handleMap = handleMap;
	}

	@Override
	public void run() {
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
