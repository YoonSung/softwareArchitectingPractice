package org.nhnnext.architecting.handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 0x6001 헤더요청에 대한 HandlerClass
 * @see EventHandler
 * @author Yoonsung
 * @date 2014-09-17
 */
public class StreamUpdateProfileEventHandler implements EventHandler{

	/**
	 * 수신데이터 최대 사이즈
	 */
	private static final int DATA_SIZE = 1024;
	
	/**
	 *수신데이터의 최대 Parameter 갯수
	 */
	private static final int TOKEN_NUM = 5;
	
	
	private static final Logger log = LoggerFactory.getLogger(StreamUpdateProfileEventHandler.class);
	
	/**
	 * 사용자 요청 InputStream을 분석해서, 미리 약속된 형태로 Parsing하여 데이터를 처리한다.
	 * 현재는 Parsing데이터를 출력하고 있다.
	 * @todo 요청에 대한 세부적 동작명시
	 * @param 사용자 요청에 대한 스트림, Parameter는 name과 age로 구성되어 있으며 "0x6001|hong|1234|홍길동|22|남성" 와 같은 형태이다. 
	 */
	public void handleEvent(InputStream inputStream) {
		try {
			byte[] buffer = new byte[DATA_SIZE];
			inputStream.read(buffer);
			String data = new String(buffer);
			
			String[] params = new String[TOKEN_NUM];
			StringTokenizer token = new StringTokenizer(data, "|");
			
			int i = 0;
			while(token.hasMoreElements()) {
				params[i] = token.nextToken();
				++i;
			}
			
			log.info("updateProfile -> "
					+ "id : {}, "
					+ "password : {}"
					+ "name : {}"
					+ "age : {}"
					+ "gender : {}"
					
					, params[0], params[1], params[2], params[3], params[4]);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return Handler가 동작하는 Header 데이터("0x6001")를 반환한다.
	 */
	@Override
	public String getHandler() {
		return "0x6001";
	}

}
