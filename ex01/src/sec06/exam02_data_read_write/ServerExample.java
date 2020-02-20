package sec06.exam02_data_read_write;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class ServerExample {
	
	public static void main(String[] args) {
		ServerSocketChannel serverSocketChannel = null;
		
		try {
			
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(true);		
			serverSocketChannel.bind(new InetSocketAddress(5001));
			
			while(true) {
				System.out.println( "[연결 기다림]");
				
				SocketChannel socketChannel = serverSocketChannel.accept();
				
				InetSocketAddress isa = 
					(InetSocketAddress) socketChannel.getRemoteAddress();
				
				System.out.println("[연결 수락함] " + isa.getHostName());
				
				//--------------------------------------------------//
				// 입출력용 ByteBuffer 객체 생성(크기: 100바이트)
				//--------------------------------------------------//
				ByteBuffer byteBuffer = ByteBuffer.allocate(100);
				
				//--------------------------------------------------//
				// 통신용 소켓채널객체의 read(ByteBuffer) 메소드 호출로
				// 채널로부터, 최대버퍼크기만큼, 데이터 읽기 수행(READ)
				//--------------------------------------------------//
				try {
					int byteCount = socketChannel.read(byteBuffer);
					System.out.println("- 채널로부터 받은 바이트 갯수: "+byteCount);
					
					if(byteCount == -1) { 
						// if EOF: 클라이언트가 소켓체널을 정상적으로 종료(close)하면,
						throw new IOException("클라이언트가 소켓체널을 정상적으로 종료(close)");
					} // if
				} catch(IOException e) { // 클라이언트가 비정상 종료하면
					e.printStackTrace();
				} finally {
					if(socketChannel !=null && !socketChannel.isConnected()) {
						socketChannel.close();
					}
				} // try-catch-finally
				
				// 읽기모드로 전환(position->0, limit->position)
				byteBuffer.flip(); 
				
				//--------------------------------------------------//	
				// 변환: ByteBuffer -> CharBuffer -> String 으로 역변환
				//      받은 문자열로 역변환하여, 출력
				//--------------------------------------------------//				
				Charset charset = Charset.forName("UTF-8");	// 1st. 방법
//				Charset charset = Charset.defaultCharset(); // 2nd. 방법
				
				// 실제 역변환 과정 수행
				String message = charset.decode(byteBuffer).toString();
				
				// 수신 데이터 출력
				System.out.println("[데이터 받기 성공]: " + message);	
				
				//--------------------------------------------------//			
				// 클라이언트로, 문자열 전송 (현재 우리가 만드는 것은 서버프로그래임!)
				//--------------------------------------------------//
				
				// 순변환 수행: String -> ByteBuffer 로 변환
				byteBuffer = charset.encode("Hello Client");
				
				// 생성한 ByteBuffer의 데이터를, 통신용 소켓채널의 write(ByteBuffer)
				// 메소드를 통해, 클라이언트로 전송
				socketChannel.write(byteBuffer);
				
				System.out.println( "[데이터 보내기 성공]");
				//--------------------------------------------------//
			} // while
		} catch(Exception e) {
			e.printStackTrace();
			
			
		} // try-catch
		
		// 자원반납 대상: ServerSocketChannel
		if(serverSocketChannel.isOpen()) {
			try {
				serverSocketChannel.close();
			} catch (IOException e1) {}
		} // if
		
	} // main
	
} // end class
