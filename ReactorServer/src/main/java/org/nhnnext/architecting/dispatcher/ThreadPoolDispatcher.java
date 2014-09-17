package org.nhnnext.architecting.dispatcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.nhnnext.architecting.Demultiplexer;
import org.nhnnext.architecting.constant.Constant;
import org.nhnnext.architecting.domain.HandleMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPoolDispatcher implements Dispatcher {

	
	private static final Logger log = LoggerFactory.getLogger(ThreadPoolDispatcher.class);
	boolean isOperation = true;
	
	@Override
	public void startDispatch(final ServerSocket serverSocket, final HandleMap handleMap) {
		for (int i = 0; i < (Constant.MAX_THREAD_NUM - 1); i++) {
			Thread thread = new Thread() {
				public void run() {
					while(isOperation) {
						Socket socket;
						try {
							socket = serverSocket.accept();
							log.debug("Request Accept Thread id : {}",this.getId());
							new Demultiplexer(socket, handleMap).run();
							
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			};
			
			thread.start();
		}
	}

	@Override
	public void stopDispatch() {
		// TODO Auto-generated method stub
	}

}
