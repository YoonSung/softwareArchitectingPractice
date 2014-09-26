package org.nhnnext.architecting.handler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NioFileWriteEventHandler implements NioEventHandler {

	
	private static final Logger log = LoggerFactory.getLogger(NioFileWriteEventHandler.class);
	private static final String FILE_PATH
	= "./src/logs/Proactor__"+ new SimpleDateFormat("yyyy-MM-dd_HH:mm").format(System.currentTimeMillis()) + ".dat";

	private AsynchronousSocketChannel channel;
	private ByteBuffer buffer;
	
	/**
	 * Write할 파일사이즈 (byte 단위)
	 */
	private static final int DATA_SIZE = 200;
	
	/**
	 *수신데이터의 최대 Parameter 갯수
	 */
	private static final int BYTE_SIZE = 1024;
	
	
	
	@Override
	public void completed(Integer result, ByteBuffer attachment) {
		if (result == -1) {
			log.error("Complete Result is -1");
			try {
				channel.close();
			} catch (IOException e) {
				log.error("IOException in Completed : {}", e.getMessage());
				e.printStackTrace();
			}
			
		} else if (result > 0) {
			buffer.flip();

			ByteBuffer newBuffer = ByteBuffer.allocate(getDataSize());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < getDataSize(); i++) {
				sb.append('x');
			}
			newBuffer = ByteBuffer.wrap((sb.toString().getBytes()));

			Path filePath = Paths.get(FILE_PATH);
			AsynchronousFileChannel asynchronousFileChannel;
			
			try {
				asynchronousFileChannel = AsynchronousFileChannel.open(filePath,
						StandardOpenOption.CREATE, StandardOpenOption.WRITE);

				Future<Integer> future = asynchronousFileChannel.write(newBuffer, 0);
				while (!future.isDone()) {
				}

				log.info("File Write Complete.");

				asynchronousFileChannel.close();
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getHandle() {
		return "0x7001";
	}

	@Override
	public int getDataSize() {
		return BYTE_SIZE * DATA_SIZE;
	}

	@Override
	public void initialize(AsynchronousSocketChannel channel, ByteBuffer buffer) {
		this.channel = channel;
		this.buffer = buffer;
	}

}
