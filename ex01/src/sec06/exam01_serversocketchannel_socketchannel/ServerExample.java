package sec06.exam01_serversocketchannel_socketchannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


public class ServerExample {
	
	
	// NIO를 이용한 서버소켓채널 구현 예제
	public static void main(String[] args) {
		//--------------------------------------------------//
		// 1.  생성한 서버소켓체널 객체를 저장할 지역변수 선언
		//--------------------------------------------------//
		ServerSocketChannel serverSocketChannel = null;

		//--------------------------------------------------//
		// 2.  서버소켓체널 객체의 세가지 역할 수행 
		//		1) 생성  2) Listen 3)SocketChannel 생성
		//--------------------------------------------------//
		try {
			// 2-1. 서버소켓채널 객체 생성
			serverSocketChannel = ServerSocketChannel.open();
			
			// 2-2. Blocking / Non-blocking mode 설정
			//      if true: Blocking or if false: Non-blocking
			serverSocketChannel.configureBlocking(true);
			
			// 2-3. 특정 IP주소와 바인딩 포트로, IP/PORT 바인딩 수행
			serverSocketChannel.
				bind(
					// IP + PORT 정보를 가지고 있는 객체가 SocketAddress 임.
					// InetSocketAddress 객체는 SocketAddress 의 자식 클래스임.
					// 아래와 같이, IP주소 지정하지않고, 바인딩 포트 번호만 지정하면,
					// 이 서버소켓채널의 바인딩 영역은 다음과 같음:
					//   물리적인 서버가 가지는 모든 IP주소(IP ANY) + 지정바인딩포트번호
					//   로 바인딩 수행: 즉, 모든 IP주소와 동일 포트번호로 Listen 하게 됨
					new InetSocketAddress(5001)
				); // bind
			
			// 2-4. 클라이언트 소켓채널로부터, 연결요청이 들어오기까지 Listen(대기)수행
			//      보통 무한루프로 구현.
			while(true) { // 무한루프 수행
				System.out.println( "[연결 기다림]");
				
				// 2-5. 클라이언트 소켓채널로부터 들어온 연결요청(Incomming request)을
				//      대기하고, 수락하고, 통신용 소켓채널객체를 생성하여 반환해주는 메소드
				//      가 바로 ServerSocketChannel.accept() 메소드임
				SocketChannel socketChannel = serverSocketChannel.accept();
				
				// 2-6. 연결수락된 클라이언트의 IP주소 및 포트번호 정보출력
				InetSocketAddress isa = 
					(InetSocketAddress) socketChannel.getRemoteAddress();
				
				System.out.println("[연결 수락함] " + isa.getHostName());
			}
		} catch(Exception e) {;;} // try-catch: 서버소켓채널작업 영역 끝.
		
		
		//----------------------------------------------------------//
		// 3. 서버소켓채널을 모두 사용이 끝나면, 자원반납 위해, close 해줌.
		//----------------------------------------------------------//
		if(serverSocketChannel.isOpen()) { // isOpen: 아직 열려있나요? (true/false)
			
			try {
				// 그러면 채널을 닫겠습니다.!
				// close() 메소드는, Checked Exception인 IOException 을 throw함.
				serverSocketChannel.close(); 
			} catch (IOException e1) {;;} // try-catch
			
		} // if
		
	} // main
	
} // end class
