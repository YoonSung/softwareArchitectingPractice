package org.nhnnext.architecting.dispatcher;

import java.net.ServerSocket;

import org.nhnnext.architecting.domain.HandleMap;

/**
 * @brief 요청에 대해 적절한 Handler를 실행시켜주는 클래스
 * @details 사용자의 요청별 Header를 분석해서 메모리상에 미리 저장된 Header에 해당하는 HandlerClass를 알맞게 실행시켜주는 역할을 한다
 * @author Yoonsung
 * @date 2014-09-17
 */
public interface Dispatcher {
	
	/**
	 * 함수내부에서 while-loop를 돌며 Server 요청에 대해 Listening 하고 있다.
	 * @param 최초 생성된 소켓서버객체를 전달한다.
	 * @param XML파일을 통해 Header-HandlerClass로 맵핑된 Key-Value 메모리테이블 객체를 전달받는다.
	 */
	public void startDispatch(ServerSocket serverSocket, HandleMap handleMap);
	
	/**
	 * 소켓서버를 종료한다.
	 */
	public void stopDispatch();
}
