package org.nhnnext.architecting;

import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamSayHelloEventHandler implements EventHandler{

	private static final int DATA_SIZE = 512;
	private static final int TOKEN_NUM = 2;
	
	
	private static final Logger log = LoggerFactory.getLogger(StreamSayHelloEventHandler.class);
	
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
			
			log.info("SayHello -> name  : {}, age : {}", params[0], params[1]);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getHandler() {
		// TODO Auto-generated method stub
		return null;
	}

}
