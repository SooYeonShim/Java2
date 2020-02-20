package sec03.exam01_direct_buffer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

public class CreateEachDataTypeWrapBuffer {

	
	public static void main(String[] args) {

		//------------------------------------------------------//
		byte[] byteArray = new byte[100];
		char[] charArray = new char[100];
		short[] shortArray = new short[100];
		int[] intArray = new int[100];
		long[] longArray = new long[100];
		float[] floatArray = new float[100];
		double[] doubleArray = new double[100];
		//------------------------------------------------------//
		
		
		//------------------------------------------------------//		
		ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
		CharBuffer charBuffer = CharBuffer.wrap(charArray);
		ShortBuffer shortBuffer = ShortBuffer.wrap(shortArray);
		IntBuffer intBuffer = IntBuffer.wrap(intArray);
		LongBuffer longBuffer = LongBuffer.wrap(longArray);
		FloatBuffer floatBuffer = FloatBuffer.wrap(floatArray);
		DoubleBuffer doubleBuffer = DoubleBuffer.wrap(doubleArray);
		//------------------------------------------------------//	
		
		
		System.out.println("- byteBuffer: "+byteBuffer);
		
		System.out.println("- charBuffer: "+charBuffer);
		System.out.println("\t+ capacity: "+charBuffer.capacity());
		System.out.println("\t+ limit: "+charBuffer.limit());
		System.out.println("\t+ position: "+charBuffer.position());
		
		System.out.println("- shortBuffer: "+shortBuffer);
		System.out.println("- intBuffer: "+intBuffer);
		System.out.println("- longBuffer: "+longBuffer);
		System.out.println("- floatBuffer: "+floatBuffer);
		System.out.println("- doubleBuffer: "+doubleBuffer);
		
		//------------------------------------------------------//		
		int offset = 0;
		int length = 50;
		
		byteBuffer = ByteBuffer.wrap(byteArray, offset, length);
		charBuffer = CharBuffer.wrap(charArray, offset, length);
		shortBuffer = ShortBuffer.wrap(shortArray, offset, length);
		intBuffer = IntBuffer.wrap(intArray, offset, length);
		longBuffer = LongBuffer.wrap(longArray, offset, length);
		floatBuffer = FloatBuffer.wrap(floatArray, offset, length);
		doubleBuffer = DoubleBuffer.wrap(doubleArray, offset, length);
		
		System.out.println("----------------------------------------------");
		System.out.println("- byteBuffer: "+byteBuffer);
		
		System.out.println("- charBuffer: "+charBuffer);
		System.out.println("\t+ capacity: "+charBuffer.capacity());
		System.out.println("\t+ limit: "+charBuffer.limit());
		System.out.println("\t+ position: "+charBuffer.position());
		
		System.out.println("- shortBuffer: "+shortBuffer);
		System.out.println("- intBuffer: "+intBuffer);
		System.out.println("- longBuffer: "+longBuffer);
		System.out.println("- floatBuffer: "+floatBuffer);
		System.out.println("- doubleBuffer: "+doubleBuffer);
		
		
		//------------------------------------------------------//
		String str = "Hello, World!!!";
		charBuffer = CharBuffer.wrap(str);
//		charBuffer = CharBuffer.wrap(str, offset, str.length());	// OK
		
		System.out.println("----------------------------------------------");
		System.out.println("- charBuffer: "+charBuffer);
		System.out.println("\t+ capacity: "+charBuffer.capacity());
		System.out.println("\t+ limit: "+charBuffer.limit());
		System.out.println("\t+ position: "+charBuffer.position());
		
	} // main

} // end class
