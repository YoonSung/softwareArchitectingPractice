package org.nhnnext.architecting.handler;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.CompletionHandler;

public interface NioEventHandler extends CompletionHandler<Integer, ByteBuffer> {
	public String getHandle();
	
	public int getDataSize();
	
	public void initialize(AsynchronousServerSocketChannel channel, ByteBuffer buffer);
}
