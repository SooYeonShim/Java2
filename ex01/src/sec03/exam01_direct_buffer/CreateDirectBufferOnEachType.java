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
		// 1. 100 바이트 크기의 다이렉트 버퍼 생성
		//--------------------------------------------------------//
		int capacity = 100;	// 100 bytes.
		
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(capacity);
		//--------------------------------------------------------//

		//--------------------------------------------------------//
		// 2. 위에서 생성한 ByteBuffer 객체로부터, 다른 기본타입에 대응하는
		//    각 타입별 버퍼객체를 얻어내기(ByteBuffer.asXXXXBuffer() 메소드로...)
		//--------------------------------------------------------//
		CharBuffer charBuffer = byteBuffer.asCharBuffer();
		ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
		IntBuffer intBuffer = byteBuffer.asIntBuffer();
		LongBuffer longBuffer = byteBuffer.asLongBuffer();
		FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
		DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();

		//--------------------------------------------------------//
		// 3. 위에서 생성한 각 타입별 Buffer 객체를 찍어보자!!!!
		//--------------------------------------------------------//
		System.out.println("----------------------------------------------");
		System.out.println("- byteBuffer: "+byteBuffer);
		
		System.out.println("- charBuffer: "+charBuffer);
		System.out.println("\t+ capacity: "+charBuffer.capacity());	// 버퍼용량?
		System.out.println("\t+ limit: "+charBuffer.limit());      	// 버퍼한계?
		System.out.println("\t+ position: "+charBuffer.position());	// 버퍼위치?
		
		System.out.println("- shortBuffer: "+shortBuffer);
		System.out.println("- intBuffer: "+intBuffer);
		System.out.println("- longBuffer: "+longBuffer);
		System.out.println("- floatBuffer: "+floatBuffer);
		System.out.println("- doubleBuffer: "+doubleBuffer);
		
	} // main

} // end class
