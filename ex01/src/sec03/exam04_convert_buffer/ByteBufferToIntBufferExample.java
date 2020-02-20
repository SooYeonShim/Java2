package sec03.exam04_convert_buffer;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

public class ByteBufferToIntBufferExample {
	
	
	public static void main(String[] args) throws Exception {
		//----------------------------------------------------//
		// 변환목표: 1. int[] -> 2. IntBuffer -> 3. ByteBuffer
		//----------------------------------------------------//		

		//----------------------------------------------------//
		int[] writeData = { 10, 20 };	// int 배열생성
		//----------------------------------------------------//

		//----------------------------------------------------//
		// 기존 배열을 래핑(wrapping)하여, NIO Buffer 생성
		//----------------------------------------------------//
		// int[] -> IntBuffer로 변환
		IntBuffer writeIntBuffer = IntBuffer.wrap(writeData);

		//----------------------------------------------------//
		// 명심할 것!!!: NIO는 항시, ByteBuffer로 입/출력을 수행
		//----------------------------------------------------//
		// NIO Channel I/O를 위한 ByteBuffer 생성(Non-direct Buffer)
		ByteBuffer writeByteBuffer= 
			ByteBuffer.allocate(writeIntBuffer.capacity()*4);

		//----------------------------------------------------//
		// 생성한 ByteBuffer에, put기본타입명() 메소드를 통해,
		// 기본 타입의 데이터를, ByteBuffer에 저장(쓰기)
		//----------------------------------------------------//
		for(int i=0; i<writeIntBuffer.capacity(); i++) {
			// IntBuffer -> ByteBuffer로 변환
			writeByteBuffer.putInt(writeIntBuffer.get(i));
		} // for
		
		//----------- int[] > ByteBuffer로의 변환 끝 ------------//

		
		//----------- ByteBuffer > int[] 로의 역변환------------//
		
		
		//----------------------------------------------------//
		// ByteBuffer에 모든 데이터를 쓰고나면, 읽을 준비를 할때: flip() 호출
		//----------------------------------------------------//
		writeByteBuffer.flip();	// ready to read.

		//----------------------------------------------------//
		// * 변환목표: 1. ByteBuffer -> 2. IntBuffer -> 3. int[]
		//----------------------------------------------------//
		ByteBuffer readByteBuffer = writeByteBuffer;	

		//----------------------------------------------------//
		// 1. ByteBuffer의 as기본타입Buffer() 메소드로 변환
		//    ByteBuffer --> IntBuffer
		//----------------------------------------------------//
		IntBuffer readIntBuffer = readByteBuffer.asIntBuffer();

		//----------------------------------------------------//
		// 기본타입Buffer(여기서는, IntBuffer) -> int[] 배열로 역변환
		//----------------------------------------------------//
		int[] readData = new int[readIntBuffer.capacity()];		// int[] 생성
		
		readIntBuffer.get(readData);	// int[] 배열에 데이터 저장
		
		System.out.println("배열 복원: " + Arrays.toString(readData));
		
	} // main
	
} // end class
