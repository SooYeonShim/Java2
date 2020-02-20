package sec03.exam04_convert_buffer;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

public class ByteBufferToIntBufferExample {
	
	
	public static void main(String[] args) throws Exception {
		//----------------------------------------------------//
		// ��ȯ��ǥ: 1. int[] -> 2. IntBuffer -> 3. ByteBuffer
		//----------------------------------------------------//		

		//----------------------------------------------------//
		int[] writeData = { 10, 20 };	// int �迭����
		//----------------------------------------------------//

		//----------------------------------------------------//
		// ���� �迭�� ����(wrapping)�Ͽ�, NIO Buffer ����
		//----------------------------------------------------//
		// int[] -> IntBuffer�� ��ȯ
		IntBuffer writeIntBuffer = IntBuffer.wrap(writeData);

		//----------------------------------------------------//
		// ����� ��!!!: NIO�� �׽�, ByteBuffer�� ��/����� ����
		//----------------------------------------------------//
		// NIO Channel I/O�� ���� ByteBuffer ����(Non-direct Buffer)
		ByteBuffer writeByteBuffer= 
			ByteBuffer.allocate(writeIntBuffer.capacity()*4);

		//----------------------------------------------------//
		// ������ ByteBuffer��, put�⺻Ÿ�Ը�() �޼ҵ带 ����,
		// �⺻ Ÿ���� �����͸�, ByteBuffer�� ����(����)
		//----------------------------------------------------//
		for(int i=0; i<writeIntBuffer.capacity(); i++) {
			// IntBuffer -> ByteBuffer�� ��ȯ
			writeByteBuffer.putInt(writeIntBuffer.get(i));
		} // for
		
		//----------- int[] > ByteBuffer���� ��ȯ �� ------------//

		
		//----------- ByteBuffer > int[] ���� ����ȯ------------//
		
		
		//----------------------------------------------------//
		// ByteBuffer�� ��� �����͸� ������, ���� �غ� �Ҷ�: flip() ȣ��
		//----------------------------------------------------//
		writeByteBuffer.flip();	// ready to read.

		//----------------------------------------------------//
		// * ��ȯ��ǥ: 1. ByteBuffer -> 2. IntBuffer -> 3. int[]
		//----------------------------------------------------//
		ByteBuffer readByteBuffer = writeByteBuffer;	

		//----------------------------------------------------//
		// 1. ByteBuffer�� as�⺻Ÿ��Buffer() �޼ҵ�� ��ȯ
		//    ByteBuffer --> IntBuffer
		//----------------------------------------------------//
		IntBuffer readIntBuffer = readByteBuffer.asIntBuffer();

		//----------------------------------------------------//
		// �⺻Ÿ��Buffer(���⼭��, IntBuffer) -> int[] �迭�� ����ȯ
		//----------------------------------------------------//
		int[] readData = new int[readIntBuffer.capacity()];		// int[] ����
		
		readIntBuffer.get(readData);	// int[] �迭�� ������ ����
		
		System.out.println("�迭 ����: " + Arrays.toString(readData));
		
	} // main
	
} // end class
