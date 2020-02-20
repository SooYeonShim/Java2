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
			// 1. 서버소켓채널객체 생성방식과 동일하게 open() 메소드 호출로 생성.
			socketChannel = SocketChannel.open();
			
			// 2. Blocking / Non-blocking Mode 설정
			socketChannel.configureBlocking(true);  // T: block, F: non-block
			
			System.out.println( "[연결 요청]");
			
			// 3. 서버소켓채널에 연결요청 생성하여 전송.
			//    이때 요청할 서버를 지정하기 위하여, 서버의 IP주소+바인딩포트 번호를
			//    제공하여 연결요청 시도.
			socketChannel.connect(			// 서버소켓체널로 연결요청 전송 메소드
				new InetSocketAddress(
					"localhost",			// 서버소켓채널의 Listen IP주소 지정
					5001					// 서버소켓채널의 Listen Port번호 지정
				)
			); // connect
			
			System.out.println( "[연결 성공]");
		} catch(Exception e) {;;}
	
		//------------------------------------------//
		// 통신용 소켓채널의 사용이 모두 끝나면, 자원반납
		//------------------------------------------//
		if(socketChannel.isOpen()) {	// 아직 열려있나요?
			try {
				socketChannel.close();	// 그럼 닫아주세요.
			} catch (IOException e1) {}		
		} // if
		
	} // main
	
} // end class