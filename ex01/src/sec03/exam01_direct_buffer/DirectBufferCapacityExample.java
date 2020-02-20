package sec03.exam01_direct_buffer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;

public class DirectBufferCapacityExample {
	
	
	public static void main(String[] args) {
		//--------------------------------------------------------//
		// 1. ByteBuffer Direct Buffer (OS memory에 생성) 생성
		//--------------------------------------------------------//
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
		System.out.println("1. 저장용량: " + byteBuffer.capacity() + " 바이트");
		System.out.println("\t- byteBuffer: "+ byteBuffer);
		
		//--------------------------------------------------------//
		// 2. CharBuffer Direct Buffer (OS memory에 생성) 생성
		//--------------------------------------------------------//
		CharBuffer charBuffer = ByteBuffer.allocateDirect(100).asCharBuffer();
		System.out.println("2. 저장용량: " + charBuffer.capacity() + " 문자");
		System.out.println("\t- charBuffer: "+ charBuffer);

		//--------------------------------------------------------//
		// 3. IntBuffer Direct Buffer (OS memory에 생성) 생성
		//--------------------------------------------------------//
		IntBuffer intBuffer = ByteBuffer.allocateDirect(100).asIntBuffer();
		System.out.println("3. 저장용량: " + intBuffer.capacity() + " 정수");
		System.out.println("\t- intBuffer: "+ intBuffer);
	}
}
