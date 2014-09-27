package org.nhnnext.architecting.dispatcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.nhnnext.architecting.Demultiplexer;
import org.nhnnext.architecting.constant.Constant;
import org.nhnnext.architecting.domain.HandleMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @brief 최초실행시 기 설정된 최대 Thread 개수만큼을 생성하고 동작하는 Dispatcher Class
 * @see Constant.MAX_THREAD_NUM
 * @details 사용자의 요청별 Header를 분석해서 적절한 Handler Class를 실행시켜준다. 최초 생성된 Thread에서 더이상 Thread를 생성하지 않아, 다수의 사용자 요청시 서버 프로그램의 메모리부족으로 Exception이 발생할 위험이 없으며 최대 Thread를 넘어가는 요청시에는 대기시간이 발생할 수 있다.
 * @see Dispatcher
 * @author Yoonsung
 * @date 2014-09-17
 */
public class ThreadPoolDispatcher implements Dispatcher {

	private static final Logger log = LoggerFactory.getLogger(ThreadPoolDispatcher.class);
	
	/**
	 * 서버 동작을 결정하는 Flag. 초기값은 true이며 false로 상태가 변경될 시 서버가 종료된다. 
	 */
	boolean isOperation = true;
	
	/**
	 * 함수내부에서 while-loop를 돌며 Server 요청에 대해 Listening 하고 있다. 최초 실행시 설정된 최대갯수만큼 Thread를 생성하고 요청별로 Thread가 자동할당해서 처리해주고 있다. 
	 * @param 최초 생성된 소켓서버객체를 전달한다.
	 * @param XML파일을 통해 Header-HandlerClass로 맵핑된 Key-Value 메모리테이블 객체를 전달받는다.
	 */
	@Override
	public void startDispatch(final ServerSocket serverSocket, final HandleMap handleMap) {
		for (int i = 0; i < (Constant.MAX_THREAD_NUM - 1); i++) {
			Thread thread = new Thread() {
				public void run() {
					while(isOperation) {
						Socket socket;
						try {
							socket = serverSocket.accept();
							//log.debug("Request Accept Thread id : {}",this.getId());
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

	/**
	 * 소켓서버를 종료한다.
	 */
	@Override
	public void stopDispatch() {
		isOperation = false;
	}

}
