package org.nhnnext.architecting.dispatcher;

import java.net.ServerSocket;

import org.nhnnext.architecting.domain.HandleMap;

public interface Dispatcher {
	public void startDispatch(ServerSocket serverSocket, HandleMap handleMap);
	public void stopDispatch();
}