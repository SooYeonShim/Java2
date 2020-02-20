package network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class ServerEx {

	
	public static void main(String[] args) {
		
		
		//서버 소켓채널 객체 생성
		ServerSocketChannel serverSocketChannel =null;
		
		
		try {
			serverSocketChannel = ServerSocketChannel.open();
			
			//특정 IP주소와 바인딩 포트로, IP/PORT 바인딩 수행
			serverSocketChannel.configureBlocking(true);
			serverSocketChannel.bind(new InetSocketAddress(5001));

			//클라이언트 소켓채널로부터, 연결요청이 들어오기까지 Listen(대기)수행
			//보통 무한루프로 구현
			while(true) { //무한루프 수행
				System.out.println("[연결 기다림]");
				SocketChannel socketChannel = serverSocketChannel.accept();
				InetSocketAddress isa = (InetSocketAddress) socketChannel.getRemoteAddress();
				System.out.println("[연결 수락함]" + isa.getHostName());
				ByteBuffer byteBuffer = ByteBuffer.allocate(100);
				int byteCount= socketChannel.read(byteBuffer);
				byteBuffer.flip();
				//
				Charset charset = Charset.forName("UTF-8");
				
				String message = charset.decode(byteBuffer).toString();
				System.out.println("[데이터받기 성공]: " + message);
				
			}
			
		}catch(Exception e) {
			;;
		}//서버소켓 채널작업 영역 끝
		
		if(serverSocketChannel.isOpen())
		{
			try {
				serverSocketChannel.close();
			}catch(IOException e) {
				;;
			}
		}
		}
}
