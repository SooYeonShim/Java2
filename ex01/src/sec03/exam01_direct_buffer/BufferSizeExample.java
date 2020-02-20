package sec03.exam01_direct_buffer;

import java.nio.ByteBuffer;

public class BufferSizeExample {
	
	
	public static void main(String[] args) {
		//--------------------------------------------------------//
		// 1. Direct Buffer (OS memory�� ����) ����
		//--------------------------------------------------------//
		ByteBuffer directBuffer = ByteBuffer.allocateDirect(200 * 1024 * 1024); 
		System.out.println("���̷�Ʈ ���۰� �����Ǿ����ϴ�.");
		System.out.println("- directBuffer: "+ directBuffer);
		//--------------------------------------------------------//

		//--------------------------------------------------------//
		// 2. Non-Direct Buffer (JVM heap memory�� ����) ����
		//--------------------------------------------------------//
		ByteBuffer nonDirectBuffer = ByteBuffer.allocate(200 * 1024 * 1024);
		System.out.println("�ʹ��̷�Ʈ ���۰� �����Ǿ����ϴ�.");
		System.out.println("- nonDirectBuffer: "+ nonDirectBuffer);
		//--------------------------------------------------------//
	} // main
	
} // end  class
