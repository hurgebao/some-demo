package com.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioMain {
	public static void main(String[] args) throws IOException {
		ByteBuffer buffer=ByteBuffer.allocate(1024);
		ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.bind(new InetSocketAddress(9998));
		Selector selector=Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		while(true) {
			int select=selector.select();
			if(select==0) {
				continue;
			}
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator=selectedKeys.iterator();
			while(iterator.hasNext()) {
				SelectionKey key=iterator.next();
				if(key.isAcceptable()) {
					ServerSocketChannel channel=(ServerSocketChannel)key.channel();
					SocketChannel clientChannel = channel.accept();
					System.out.println("connection from:"+clientChannel.getRemoteAddress());
					clientChannel.configureBlocking(false);
					clientChannel.register(selector, SelectionKey.OP_READ);
				}
				if(key.isReadable()) {
					SocketChannel channel=(SocketChannel)key.channel();
					channel.read(buffer);
					String request=new String(buffer.array()).trim();
					buffer.clear();
					System.out.println(String.format("from %s:%s", channel.getRemoteAddress(), request));
					String response="Nio response "+request+"\n";
					channel.write(ByteBuffer.wrap(response.getBytes()));
					
				}
				iterator.remove();
			}
		}
	}
}
