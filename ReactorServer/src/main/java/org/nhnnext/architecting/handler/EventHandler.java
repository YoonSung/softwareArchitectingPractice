package org.nhnnext.architecting.handler;

import java.io.InputStream;

/**
 * 사용자 요청에 대해 Header별 다른 프로토콜을 지원하기 위해 추상화 해놓은 Event Handler Interface
 * @author Yoonsung
 * @date 2014-09-17
 */
public interface EventHandler {
	
	/**
	 * @return Handler가 동작하기 원하는 Header 데이터를 반환한다. 헤더형식은 16진수이다.
	 */
	public String getHandler();
	
	/**
	 * 사용자 요청 InputStream을 분석해서, 미리 약속된 형태로 Parsing하여 데이터를 처리한다.
	 * @param 사용자 요청에 대한 스트림
	 */
	public void handleEvent(InputStream inputStream);
}
