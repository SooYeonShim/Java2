package sec06.exam01_serversocketchannel_socketchannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


public class ServerExample {
	
	
	// NIO�� �̿��� ��������ä�� ���� ����
	public static void main(String[] args) {
		//--------------------------------------------------//
		// 1.  ������ ��������ü�� ��ü�� ������ �������� ����
		//--------------------------------------------------//
		ServerSocketChannel serverSocketChannel = null;

		//--------------------------------------------------//
		// 2.  ��������ü�� ��ü�� ������ ���� ���� 
		//		1) ����  2) Listen 3)SocketChannel ����
		//--------------------------------------------------//
		try {
			// 2-1. ��������ä�� ��ü ����
			serverSocketChannel = ServerSocketChannel.open();
			
			// 2-2. Blocking / Non-blocking mode ����
			//      if true: Blocking or if false: Non-blocking
			serverSocketChannel.configureBlocking(true);
			
			// 2-3. Ư�� IP�ּҿ� ���ε� ��Ʈ��, IP/PORT ���ε� ����
			serverSocketChannel.
				bind(
					// IP + PORT ������ ������ �ִ� ��ü�� SocketAddress ��.
					// InetSocketAddress ��ü�� SocketAddress �� �ڽ� Ŭ������.
					// �Ʒ��� ����, IP�ּ� ���������ʰ�, ���ε� ��Ʈ ��ȣ�� �����ϸ�,
					// �� ��������ä���� ���ε� ������ ������ ����:
					//   �������� ������ ������ ��� IP�ּ�(IP ANY) + �������ε���Ʈ��ȣ
					//   �� ���ε� ����: ��, ��� IP�ּҿ� ���� ��Ʈ��ȣ�� Listen �ϰ� ��
					new InetSocketAddress(5001)
				); // bind
			
			// 2-4. Ŭ���̾�Ʈ ����ä�ηκ���, �����û�� ��������� Listen(���)����
			//      ���� ���ѷ����� ����.
			while(true) { // ���ѷ��� ����
				System.out.println( "[���� ��ٸ�]");
				
				// 2-5. Ŭ���̾�Ʈ ����ä�ηκ��� ���� �����û(Incomming request)��
				//      ����ϰ�, �����ϰ�, ��ſ� ����ä�ΰ�ü�� �����Ͽ� ��ȯ���ִ� �޼ҵ�
				//      �� �ٷ� ServerSocketChannel.accept() �޼ҵ���
				SocketChannel socketChannel = serverSocketChannel.accept();
				
				// 2-6. ��������� Ŭ���̾�Ʈ�� IP�ּ� �� ��Ʈ��ȣ �������
				InetSocketAddress isa = 
					(InetSocketAddress) socketChannel.getRemoteAddress();
				
				System.out.println("[���� ������] " + isa.getHostName());
			}
		} catch(Exception e) {;;} // try-catch: ��������ä���۾� ���� ��.
		
		
		//----------------------------------------------------------//
		// 3. ��������ä���� ��� ����� ������, �ڿ��ݳ� ����, close ����.
		//----------------------------------------------------------//
		if(serverSocketChannel.isOpen()) { // isOpen: ���� �����ֳ���? (true/false)
			
			try {
				// �׷��� ä���� �ݰڽ��ϴ�.!
				// close() �޼ҵ��, Checked Exception�� IOException �� throw��.
				serverSocketChannel.close(); 
			} catch (IOException e1) {;;} // try-catch
			
		} // if
		
	} // main
	
} // end class
