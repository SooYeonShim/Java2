package sec03.exam01_direct_buffer;

import java.io.IOException;
import java.nio.ByteBuffer;


public class CreateNIOBuffer {

		
	public static void main(String[] args) 
		throws IOException {
		//--------------------------------------------------------------------//
		ByteBuffer directByteBuffer = ByteBuffer.allocateDirect(1024*1024*500); // 200MB
		System.out.println("- directByteBuffer: "+ directByteBuffer);
		//--------------------------------------------------------------------//
		

		//--------------------------------------------------------------------//
		ByteBuffer nonDirectByteBuffer = ByteBuffer.allocate(1024*1024*20);	// 2GB
		System.out.println("- nonDirectByteBuffer: "+ nonDirectByteBuffer);
		//--------------------------------------------------------------------//
		
		
		System.in.read();	// 표준입력스트림의 read() 메소드호출: 사용자입력전까지
							// 프로그램 종료를 막음!

	} // main

} // end clas
