package org.nhnnext.architecting.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 0x7001 헤더요청에 대한 HandlerClass
 * @see EventHandler
 * @author Yoonsung
 * @date 2014-09-26
 */
public class FileWriteEventHandler implements EventHandler {

	
	private static final Logger log = LoggerFactory.getLogger(FileWriteEventHandler.class);
	
	private static final String FILE_PATH = "./src/logs/";
	
	/**
	 * Write할 파일사이즈 (byte 단위)
	 */
	private static final int DATA_SIZE = 512;
	
	/**
	 *수신데이터의 최대 Parameter 갯수
	 */
	private static final int BYTE_SIZE = 1024;

	/**
	 * 사용자 요청 InputStream을 분석해서, 미리 약속된 형태로 Parsing하여 데이터를 처리한다.
	 * 현재는 Parsing데이터를 출력하고 있다.
	 * @todo 요청에 대한 세부적 동작명시
	 * @param 사용자 요청에 대한 스트림 현재 약속된 데이터셋은 없다 
	 */
	public void handleEvent(InputStream inputStream) {
		FileOutputStream fos = null;
		
		try {
			byte[] buffer = new byte[BYTE_SIZE];
			inputStream.read(buffer);
			String fileName = "Reactor__" + new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss:SSSS").format(System.currentTimeMillis()) + ".dat";;
			fos = new FileOutputStream(new File(FILE_PATH + File.separator + fileName));

			for (int i = 0 ; i < DATA_SIZE ; i++) {
				fos.write(buffer);
			}
			
			log.info("File Write Working Done");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {}
		}
	}
	
	/**
	 * @return Handler가 동작하는 Header 데이터("0x7001")를 반환한다.
	 */	
	public String getHandler() {
		return "0x7001";
	}
}
