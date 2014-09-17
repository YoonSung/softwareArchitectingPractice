package org.nhnnext.architecting;

import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamUpdateProfileEventHandler implements EventHandler{

	private static final int DATA_SIZE = 1024;
	private static final int TOKEN_NUM = 5;
	
	
	private static final Logger log = LoggerFactory.getLogger(StreamUpdateProfileEventHandler.class);
	
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

	@Override
	public String getHandler() {
		return "0x6001";
	}

}
