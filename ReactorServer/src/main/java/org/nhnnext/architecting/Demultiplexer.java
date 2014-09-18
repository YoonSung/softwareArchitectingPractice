package org.nhnnext.architecting;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.nhnnext.architecting.constant.Constant;
import org.nhnnext.architecting.domain.HandleMap;

/**
 * @brief 사용자 요청별 HandlerClass를 호출하는 역할을 한다. 요청별로 새로생성되는 클래스이다.
 * @details 사용자 요청에서 헤더를 읽어들여 메모리상에 저장된 헤더-핸들러 클래스 테이블을 조회하고, 조회된 결과클래스를 실행시켜준다.
 * @exception 요청헤더에 해당하는 데이터가 테이블에 존재하지 않을경우 NullPointerException이 발생한다.
 * @see ThreadPerDispatcher
 * @see ThreadPoolDispatcher
 * @author Yoonsung
 * @date 2014-09-17
 */
public class Demultiplexer implements Runnable {
	
	Socket socket;
	HandleMap handleMap;
	
	/**
	 * 
	 * @param 사용자 요청에 대한 Socket객체
	 * @param 메모리 key-value 객체 reference 전달
	 */
	public Demultiplexer(Socket socket, HandleMap handleMap) {
		this.socket = socket;
		this.handleMap = handleMap;
	}

	/**
	 * 메모리 key-value 스코어에서 헤더에 해당하는 handler를 조회해서 실행한다.
	 * @exception 요청헤더에 해당하는 데이터가 테이블에 존재하지 않을경우 NullPointerException이 발생한다.
	 */
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
