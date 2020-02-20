package network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class ServerEx {

	
	public static void main(String[] args) {
		
		
		//���� ����ä�� ��ü ����
		ServerSocketChannel serverSocketChannel =null;
		
		
		try {
			serverSocketChannel = ServerSocketChannel.open();
			
			//Ư�� IP�ּҿ� ���ε� ��Ʈ��, IP/PORT ���ε� ����
			serverSocketChannel.configureBlocking(true);
			serverSocketChannel.bind(new InetSocketAddress(5001));

			//Ŭ���̾�Ʈ ����ä�ηκ���, �����û�� ��������� Listen(���)����
			//���� ���ѷ����� ����
			while(true) { //���ѷ��� ����
				System.out.println("[���� ��ٸ�]");
				SocketChannel socketChannel = serverSocketChannel.accept();
				InetSocketAddress isa = (InetSocketAddress) socketChannel.getRemoteAddress();
				System.out.println("[���� ������]" + isa.getHostName());
				ByteBuffer byteBuffer = ByteBuffer.allocate(100);
				int byteCount= socketChannel.read(byteBuffer);
				byteBuffer.flip();
				//
				Charset charset = Charset.forName("UTF-8");
				
				String message = charset.decode(byteBuffer).toString();
				System.out.println("[�����͹ޱ� ����]: " + message);
				
			}
			
		}catch(Exception e) {
			;;
		}//�������� ä���۾� ���� ��
		
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
