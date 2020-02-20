package test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

public class BufferSizeExample {
	public static void main(String[] args) throws IOException {
		
	//다이렉트 버퍼(OS memory에 생성)
	ByteBuffer directBuffer = ByteBuffer.allocateDirect(200*1024*1024);
	
	System.out.println("다이렉트 버퍼가 생성되었습니다.");
	System.out.println(" - 다이렉트 버퍼 :" +directBuffer);
	System.out.println(" - 다이렉트 버퍼 저장용량 :" +directBuffer.capacity() +"바이트");
	
	ByteBuffer nonDirectBuffer = ByteBuffer.allocate(200*1024*1024);
	System.out.println("넌다이렉트 버퍼가 생성되었습니다.");
	System.out.println(" - 다이렉트 버퍼 :" +nonDirectBuffer);
	
	CharBuffer charBuffer = ByteBuffer.allocateDirect(100).asCharBuffer();
	System.out.println(" charBuffer 저장용량 : " + charBuffer.capacity() + " 문자");
	System.out.println("\t- charBuffer: " +charBuffer);
	
	
	
	}
}
