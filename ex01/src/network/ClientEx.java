package network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ClientEx {
	//���� ����ä�� ��ü ����
	
	public static void main(String[] args) {
	SocketChannel socketChannel =null;
	
	
	try {
		socketChannel.connect(new InetSocketAddress("localhost",5001));
		//Ư�� IP�ּҿ� ���ε� ��Ʈ��, IP/PORT ���ε� ����
		System.out.println("[���� ��û]");
		socketChannel.configureBlocking(true);
		socketChannel.bind(new InetSocketAddress(5001));

		System.out.println("[���� ����]");
	}catch(Exception e) {
		;;
	}//���� ä���۾� ���� ��
	
	if(socketChannel.isOpen())
	{
		try {
			socketChannel.close();
		}catch(IOException e) {
			;;
		}
	}
	}
}
