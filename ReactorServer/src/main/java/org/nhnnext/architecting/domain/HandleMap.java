package org.nhnnext.architecting.domain;

import java.util.HashMap;

import org.nhnnext.architecting.constant.Constant;
import org.nhnnext.architecting.dispatcher.Dispatcher;
import org.nhnnext.architecting.handler.EventHandler;

/**
 * @brief Header와 HandlerClass가 Key, Value형태로 저장된 Map 자료구조 클래스
 * @details 사용자의 요청별 Header를 분석해서 적절한 Handler Class를 실행시켜주기 위해 생성하는 Key-Value Table Store
 * @author Yoonsung
 * @date 2014-09-17
 */
@SuppressWarnings("serial")
public class HandleMap extends HashMap<String, EventHandler>{
	
}
