package test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

public class PathEx2 {
	public static void main(String[] args) throws IOException {

		int capacity= 100;
	
		
		//ByteBuffer ndb = ByteBuffer.allocate((int) size);
		ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
		CharBuffer charBuffer = byteBuffer.asCharBuffer();
		ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
		LongBuffer longBuffer = byteBuffer.asLongBuffer();
		FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
		DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();
		
		System.out.println("charBuffer - " +charBuffer);
		
}}
