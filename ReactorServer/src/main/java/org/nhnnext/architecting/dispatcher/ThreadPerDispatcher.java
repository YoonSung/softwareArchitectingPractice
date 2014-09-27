package org.nhnnext.architecting.dispatcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.nhnnext.architecting.Demultiplexer;
import org.nhnnext.architecting.domain.HandleMap;

/**
 * @brief 무제한 Thread로 동작하는 Dispatcher Class
 * @details 사용자의 요청별 Header를 분석해서 적절한 Handler Class를 실행시켜준다. 요청별로 Thread를 계속 생성하므로, 다수의 사용자 요청시 서버 프로그램의 메모리부족으로 Exception이 발생할 수 있다.
 * @see Dispatcher
 * @exception OutOfMemoryException   
 * @author Yoonsung
 * @date 2014-09-17
 */
public class ThreadPerDispatcher implements Dispatcher{
	
	/**
	 * 서버 동작을 결정하는 Flag. 초기값은 true이며 false로 상태가 변경될 시 서버가 종료된다. 
	 */
	private boolean isOperation = true;
	
	/**
	 * 함수내부에서 while-loop를 돌며 Server 요청에 대해 Listening 하고 있다. 요청별 새로운 Thread를 생성한다.
	 * @param 최초 생성된 소켓서버객체를 전달한다.
	 * @param XML파일을 통해 Header-HandlerClass로 맵핑된 Key-Value 메모리테이블 객체를 전달받는다.
	 */
	@Override
	public void startDispatch(ServerSocket serverSocket, HandleMap handleMap) {
		
		while(isOperation) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				new Thread(new Demultiplexer(socket, handleMap)).start();
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (socket != null)
					try {socket.close();}catch(Exception e){};
			}
		}
	}

	/**
	 * 소켓서버를 종료한다.
	 */
	@Override
	public void stopDispatch() {
		this.isOperation = false;
	}
}
