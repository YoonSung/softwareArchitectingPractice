/**!
* @namespace org.nhnnext.architecting!
* @brief 기본패키지
* @details Reactor서버 동작에 꼭 필요한 핵심클래스들의 패키지 
* */
package org.nhnnext.architecting;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.nhnnext.architecting.constant.Constant;
import org.nhnnext.architecting.dispatcher.ThreadPoolDispatcher;
import org.nhnnext.architecting.domain.HandlerListData;
import org.nhnnext.architecting.domain.ServerListData;
import org.nhnnext.architecting.handler.EventHandler;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @mainpage ReactorServer
 * @brief Reactor Server를 실행하는 Main함수
 * @details XML의 설정을 읽어서 서로 다른 요청(Request)에 대해 다른방식으로 응답(Response)하는 Reactor Server
 * @author Yoonsung
 * @date 2014-09-17
 */
public class ServerInitializer {
	
	private static final Logger log = LoggerFactory.getLogger(ServerInitializer.class);
	
	public static void main(String[] args) {
		log.info("ServerInitializer Main Method Called");
		
		//Reactor 클래스 초기화. 사용할 Dispatcher 클래스를 인자로 전달해야 한다.
		//Reactor reactor = new Reactor(Constant.PORT, new ThreadPerDispatcher());
		Reactor reactor = new Reactor(Constant.PORT, new ThreadPoolDispatcher());
		
		try {
			
			//XML을 읽기 위한 serializer 생성
			Serializer serializer = new Persister();
			
			//XML로부터 데이터를 읽어들인다.
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ServerInitializer.class.getClass().getResourceAsStream("/HandlerList.xml")));
			
			//Object와 XML데이터와 맵핑시킨다.
			ServerListData serverList = serializer.read(ServerListData.class,  bufferedReader);
			
			for (HandlerListData handlerListData : serverList.getServer()) {
				List<String> handlerList = handlerListData.getHandler();
				
				for (String handler : handlerList) {
					try {
						
						//XML에 해당하는 클래스를 찾아서 Reactor에 등록한다. 
						reactor.registerHandler((EventHandler)Class.forName(Constant.HANDLER_PACKAGE + "." +handler).newInstance());
						log.debug("handler : {}", handler);
					} catch (Exception e) {
						log.error("Error Occuc in Register Class From XML Data, handlerName = {}", handler);
						log.error("ErrorMessage : {}", e.getMessage());
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		reactor.startServer();
	}
}
