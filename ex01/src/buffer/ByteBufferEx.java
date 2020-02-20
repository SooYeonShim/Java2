package buffer;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

public class ByteBufferEx {

	
	public static void main(String[] args) throws Exception{
		int[] writeData = {10,20};
		
		IntBuffer writeIntBuffer = IntBuffer.wrap(writeData);

		//NIO�� �׽�, ByteBuffer�� ��/����� ����
		ByteBuffer writeByteBuffer = ByteBuffer.allocate(writeIntBuffer.capacity()*4);
		
		for(int i = 0; i<writeIntBuffer.capacity(); i++) {
			writeByteBuffer.putInt(writeIntBuffer.get(i));
		}
		
		writeByteBuffer.flip();//ready to read
		
		ByteBuffer readByteBuffer = writeByteBuffer;
		
		IntBuffer readIntBuffer = readByteBuffer.asIntBuffer();
		
		int[] readData =new int[readIntBuffer.capacity()];
		
		readIntBuffer.get(readData);
		
		System.out.println("�迭 ����: " + Arrays.toString(readData) );
		
		
		
		
		
		
		
		
	}
}
