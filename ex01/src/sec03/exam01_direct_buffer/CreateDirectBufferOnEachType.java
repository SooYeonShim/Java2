package sec03.exam01_direct_buffer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;


public class CreateDirectBufferOnEachType {
	
	
	public static void main(String[] args) {
		//--------------------------------------------------------//
		// 1. 100 ����Ʈ ũ���� ���̷�Ʈ ���� ����
		//--------------------------------------------------------//
		int capacity = 100;	// 100 bytes.
		
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(capacity);
		//--------------------------------------------------------//

		//--------------------------------------------------------//
		// 2. ������ ������ ByteBuffer ��ü�κ���, �ٸ� �⺻Ÿ�Կ� �����ϴ�
		//    �� Ÿ�Ժ� ���۰�ü�� ����(ByteBuffer.asXXXXBuffer() �޼ҵ��...)
		//--------------------------------------------------------//
		CharBuffer charBuffer = byteBuffer.asCharBuffer();
		ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
		IntBuffer intBuffer = byteBuffer.asIntBuffer();
		LongBuffer longBuffer = byteBuffer.asLongBuffer();
		FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
		DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();

		//--------------------------------------------------------//
		// 3. ������ ������ �� Ÿ�Ժ� Buffer ��ü�� ����!!!!
		//--------------------------------------------------------//
		System.out.println("----------------------------------------------");
		System.out.println("- byteBuffer: "+byteBuffer);
		
		System.out.println("- charBuffer: "+charBuffer);
		System.out.println("\t+ capacity: "+charBuffer.capacity());	// ���ۿ뷮?
		System.out.println("\t+ limit: "+charBuffer.limit());      	// �����Ѱ�?
		System.out.println("\t+ position: "+charBuffer.position());	// ������ġ?
		
		System.out.println("- shortBuffer: "+shortBuffer);
		System.out.println("- intBuffer: "+intBuffer);
		System.out.println("- longBuffer: "+longBuffer);
		System.out.println("- floatBuffer: "+floatBuffer);
		System.out.println("- doubleBuffer: "+doubleBuffer);
		
	} // main

} // end class
