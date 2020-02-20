package network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ClientEx {
	//서버 소켓채널 객체 생성
	
	public static void main(String[] args) {
	SocketChannel socketChannel =null;
	
	
	try {
		socketChannel.connect(new InetSocketAddress("localhost",5001));
		//특정 IP주소와 바인딩 포트로, IP/PORT 바인딩 수행
		System.out.println("[연결 요청]");
		socketChannel.configureBlocking(true);
		socketChannel.bind(new InetSocketAddress(5001));

		System.out.println("[연결 성공]");
	}catch(Exception e) {
		;;
	}//소켓 채널작업 영역 끝
	
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
