package sec03.exam01_direct_buffer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;

public class DirectBufferCapacityExample {
	
	
	public static void main(String[] args) {
		//--------------------------------------------------------//
		// 1. ByteBuffer Direct Buffer (OS memory�� ����) ����
		//--------------------------------------------------------//
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
		System.out.println("1. ����뷮: " + byteBuffer.capacity() + " ����Ʈ");
		System.out.println("\t- byteBuffer: "+ byteBuffer);
		
		//--------------------------------------------------------//
		// 2. CharBuffer Direct Buffer (OS memory�� ����) ����
		//--------------------------------------------------------//
		CharBuffer charBuffer = ByteBuffer.allocateDirect(100).asCharBuffer();
		System.out.println("2. ����뷮: " + charBuffer.capacity() + " ����");
		System.out.println("\t- charBuffer: "+ charBuffer);

		//--------------------------------------------------------//
		// 3. IntBuffer Direct Buffer (OS memory�� ����) ����
		//--------------------------------------------------------//
		IntBuffer intBuffer = ByteBuffer.allocateDirect(100).asIntBuffer();
		System.out.println("3. ����뷮: " + intBuffer.capacity() + " ����");
		System.out.println("\t- intBuffer: "+ intBuffer);
	}
}
