package org.nhnnext.architecting;

import java.io.IOException;
import java.net.ServerSocket;

import org.nhnnext.architecting.dispatcher.Dispatcher;
import org.nhnnext.architecting.domain.HandleMap;
import org.nhnnext.architecting.handler.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @brief Reactor Pattern에 해당하는 중심클래스
 * @details 서버에서는 들어온 요청들을 분류(demux)해서 적절한 request handler 에 dispatch한다.
 * @author Yoonsung
 * @date 2014-09-17
 */
public class Reactor {
	
	private static final Logger log = LoggerFactory.getLogger(Reactor.class);
	
	//웹서버소켓
	private ServerSocket serverSocket;
	
	//Header요청과 Handler Class가 Key,Value로 저장된 HashMap
	private HandleMap handleMap; 
	
	//Header를 분석하고, 그에 맞춰 적절한 Handler Class를 실행해준다.
	private Dispatcher dispatcher;
	
	/**
	 * 서버초기화 및 Reactor에서 필요한 Dispatcher, HandleMap 클래스 초기화 및 저장
	 * @param 포트번호
	 * @param 단일쓰레드, 혹은 쓰레드풀을 사용하는 Dispatcher를 주입받는다.
	 * @see ThreadPerDispatcher
	 * @see ThreadPoolDispatcher
	 */
	public Reactor(int port, Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
		handleMap = new HandleMap();
		
		try {
			serverSocket = new ServerSocket(port);
			log.info("SERVER ON : {}", port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 웹서버에 대한 요청을 dispatcher가 listen하기 시작한다.
	 */
	public void startServer() {
		dispatcher.startDispatch(serverSocket, handleMap);
	}
	
	/**
	 * 웹서버를 종료한다.
	 */
	public void stopServer() {
		dispatcher.stopDispatch();
		try {
			serverSocket.close();
		} catch (IOException e) {
			log.error("Server Close Exception : {}", e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * @breif HandleMap에 EventHandler를 등록한다.
	 * @detail Header를 키값으로 하고, 해당 Header와 일치하는 요청에 대해 적절한 EventHandler를 실행시켜 주기 위해 HashMap에 저장한다.
	 * @param EventHandler 클래스
	 */
	public void registerHandler(EventHandler handler) {
		handleMap.put(handler.getHandler(), handler);
	}
	
	/**
	 * 메모리상에 Handler에 해당하는 Header요청을 더이상 지원하지 않는다.
	 * @param EventHandler 클래스
	 */
	public void removeHandler(EventHandler handler) {
		handleMap.remove(handler.getHandler());
	}
}
