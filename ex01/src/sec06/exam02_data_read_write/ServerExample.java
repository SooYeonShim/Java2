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
				System.out.println( "[���� ��ٸ�]");
				
				SocketChannel socketChannel = serverSocketChannel.accept();
				
				InetSocketAddress isa = 
					(InetSocketAddress) socketChannel.getRemoteAddress();
				
				System.out.println("[���� ������] " + isa.getHostName());
				
				//--------------------------------------------------//
				// ����¿� ByteBuffer ��ü ����(ũ��: 100����Ʈ)
				//--------------------------------------------------//
				ByteBuffer byteBuffer = ByteBuffer.allocate(100);
				
				//--------------------------------------------------//
				// ��ſ� ����ä�ΰ�ü�� read(ByteBuffer) �޼ҵ� ȣ���
				// ä�ηκ���, �ִ����ũ�⸸ŭ, ������ �б� ����(READ)
				//--------------------------------------------------//
				try {
					int byteCount = socketChannel.read(byteBuffer);
					System.out.println("- ä�ηκ��� ���� ����Ʈ ����: "+byteCount);
					
					if(byteCount == -1) { 
						// if EOF: Ŭ���̾�Ʈ�� ����ü���� ���������� ����(close)�ϸ�,
						throw new IOException("Ŭ���̾�Ʈ�� ����ü���� ���������� ����(close)");
					} // if
				} catch(IOException e) { // Ŭ���̾�Ʈ�� ������ �����ϸ�
					e.printStackTrace();
				} finally {
					if(socketChannel !=null && !socketChannel.isConnected()) {
						socketChannel.close();
					}
				} // try-catch-finally
				
				// �б���� ��ȯ(position->0, limit->position)
				byteBuffer.flip(); 
				
				//--------------------------------------------------//	
				// ��ȯ: ByteBuffer -> CharBuffer -> String ���� ����ȯ
				//      ���� ���ڿ��� ����ȯ�Ͽ�, ���
				//--------------------------------------------------//				
				Charset charset = Charset.forName("UTF-8");	// 1st. ���
//				Charset charset = Charset.defaultCharset(); // 2nd. ���
				
				// ���� ����ȯ ���� ����
				String message = charset.decode(byteBuffer).toString();
				
				// ���� ������ ���
				System.out.println("[������ �ޱ� ����]: " + message);	
				
				//--------------------------------------------------//			
				// Ŭ���̾�Ʈ��, ���ڿ� ���� (���� �츮�� ����� ���� �������α׷���!)
				//--------------------------------------------------//
				
				// ����ȯ ����: String -> ByteBuffer �� ��ȯ
				byteBuffer = charset.encode("Hello Client");
				
				// ������ ByteBuffer�� �����͸�, ��ſ� ����ä���� write(ByteBuffer)
				// �޼ҵ带 ����, Ŭ���̾�Ʈ�� ����
				socketChannel.write(byteBuffer);
				
				System.out.println( "[������ ������ ����]");
				//--------------------------------------------------//
			} // while
		} catch(Exception e) {
			e.printStackTrace();
			
			
		} // try-catch
		
		// �ڿ��ݳ� ���: ServerSocketChannel
		if(serverSocketChannel.isOpen()) {
			try {
				serverSocketChannel.close();
			} catch (IOException e1) {}
		} // if
		
	} // main
	
} // end class
