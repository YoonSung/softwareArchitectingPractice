package org.nhnnext.architecting;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.nhnnext.architecting.domain.NioHandleMap;
import org.nhnnext.architecting.handler.NioEventHandler;
import org.nhnnext.architecting.handler.NioFileWriteEventHandler;
import org.nhnnext.architecting.handler.NioSayHelloEventHandler;
import org.nhnnext.architecting.handler.NioUpdateProfileEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerInitializer {
	private static int PORT = 5000;
	private static int THREAD_POOL_SIZE = 8;
	private static int INITIAL_SIZE = 4;
	private static int BACK_LOG = 50;
	
	
	private static final Logger log = LoggerFactory.getLogger(ServerInitializer.class);
	
	public static void main(String[] args) {
		log.info("SERVER START!");
		
		NioHandleMap handleMap = new NioHandleMap();
		
		NioEventHandler sayHelloHandler = new NioSayHelloEventHandler();
		NioEventHandler sayUpdateProfileHandler = new NioUpdateProfileEventHandler();
		NioEventHandler fileWriteHandler = new NioFileWriteEventHandler();
		
		handleMap.put(sayHelloHandler.getHandle(), sayHelloHandler);
		handleMap.put(sayUpdateProfileHandler.getHandle(), sayUpdateProfileHandler);
		handleMap.put(fileWriteHandler.getHandle(), fileWriteHandler);
		
		// 고정 스레드풀 생성. threadPoolSize 만큼의 스레드만 사용한다
		ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		
		// 캐시 스레드 풀 생성 - 초기에 스레드를 만들고 새 스레드는 필요한 만큼 생성한다
		// 이전에 만든 스레드를 이용할 수 있다면 재사용할 수도 있다
		try {
			AsynchronousChannelGroup group = AsynchronousChannelGroup.withCachedThreadPool(executor, INITIAL_SIZE); 
			
			// 스트림 지향의 리스닝 소켓을 위한 비동기 채널
			AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open(group);
			listener.bind(new InetSocketAddress(PORT), BACK_LOG);
			
			// 접속의 결과를 CompletionHandler로 비동기 IO작업에 콜백 형식으로 작업 결과를 받는다.
			listener.accept(listener, new Dispatcher(handleMap));
		} catch (IOException e) {
			log.error("AsynchrounousChannelGroup Generate Error : {}", e);
			e.printStackTrace();
		}
	}
}
