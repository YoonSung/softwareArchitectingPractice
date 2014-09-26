package practice.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NioSayHelloEventHandler implements NioEventHandler {

	
	private static final Logger log = LoggerFactory.getLogger(NioSayHelloEventHandler.class);
	
	private static final int TOKEN_NUM = 2;
	private AsynchronousServerSocketChannel channel;
	private ByteBuffer buffer;
	
	@Override
	public void completed(Integer result, ByteBuffer attachment) {
		if (result == -1) {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (result > 0) {
			buffer.flip();
			String msg = new String(buffer.array());
			
			String[] params = new String[TOKEN_NUM];
			StringTokenizer token = new StringTokenizer(msg, "|");
			
			int i = 0;
			while (token.hasMoreTokens()) {
				params[i] = token.nextToken();
				i++;
			}
			
			log.info("SayHello -> name  : {}, age : {}", params[0], params[1]);
			
			try {
				buffer.clear();
				channel.close();
			} catch(IOException e) {
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
		return "0x5001";
	}

	@Override
	public int getDataSize() {
		return 512;
	}

	@Override
	public void initialize(AsynchronousServerSocketChannel channel,
			ByteBuffer buffer) {
		this.channel = channel;
		this.buffer = buffer;
	}

}
