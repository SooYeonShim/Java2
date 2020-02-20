package sec03.exam01_direct_buffer;

import java.nio.ByteBuffer;

public class BufferSizeExample {
	
	
	public static void main(String[] args) {
		//--------------------------------------------------------//
		// 1. Direct Buffer (OS memory에 생성) 생성
		//--------------------------------------------------------//
		ByteBuffer directBuffer = ByteBuffer.allocateDirect(200 * 1024 * 1024); 
		System.out.println("다이렉트 버퍼가 생성되었습니다.");
		System.out.println("- directBuffer: "+ directBuffer);
		//--------------------------------------------------------//

		//--------------------------------------------------------//
		// 2. Non-Direct Buffer (JVM heap memory에 생성) 생성
		//--------------------------------------------------------//
		ByteBuffer nonDirectBuffer = ByteBuffer.allocate(200 * 1024 * 1024);
		System.out.println("넌다이렉트 버퍼가 생성되었습니다.");
		System.out.println("- nonDirectBuffer: "+ nonDirectBuffer);
		//--------------------------------------------------------//
	} // main
	
} // end  class
