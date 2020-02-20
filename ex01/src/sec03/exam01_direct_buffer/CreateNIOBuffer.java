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
		
		
		System.in.read();	// ǥ���Է½�Ʈ���� read() �޼ҵ�ȣ��: ������Է�������
							// ���α׷� ���Ḧ ����!

	} // main

} // end clas
