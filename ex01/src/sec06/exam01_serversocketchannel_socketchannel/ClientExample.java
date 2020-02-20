package sec06.exam01_serversocketchannel_socketchannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class ClientExample {
	
	
	public static void main(String[] args) {	
		//------------------------------------------//
		SocketChannel socketChannel = null;
	
		//------------------------------------------//
		try {
			// 1. ��������ä�ΰ�ü ������İ� �����ϰ� open() �޼ҵ� ȣ��� ����.
			socketChannel = SocketChannel.open();
			
			// 2. Blocking / Non-blocking Mode ����
			socketChannel.configureBlocking(true);  // T: block, F: non-block
			
			System.out.println( "[���� ��û]");
			
			// 3. ��������ä�ο� �����û �����Ͽ� ����.
			//    �̶� ��û�� ������ �����ϱ� ���Ͽ�, ������ IP�ּ�+���ε���Ʈ ��ȣ��
			//    �����Ͽ� �����û �õ�.
			socketChannel.connect(			// ��������ü�η� �����û ���� �޼ҵ�
				new InetSocketAddress(
					"localhost",			// ��������ä���� Listen IP�ּ� ����
					5001					// ��������ä���� Listen Port��ȣ ����
				)
			); // connect
			
			System.out.println( "[���� ����]");
		} catch(Exception e) {;;}
	
		//------------------------------------------//
		// ��ſ� ����ä���� ����� ��� ������, �ڿ��ݳ�
		//------------------------------------------//
		if(socketChannel.isOpen()) {	// ���� �����ֳ���?
			try {
				socketChannel.close();	// �׷� �ݾ��ּ���.
			} catch (IOException e1) {}		
		} // if
		
	} // main
	
} // end class