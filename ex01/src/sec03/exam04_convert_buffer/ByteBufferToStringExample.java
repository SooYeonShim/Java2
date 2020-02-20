package sec03.exam04_convert_buffer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;


public class ByteBufferToStringExample {
	
	
	public static void main(String[] args) {
		//--------------------------------------------//
		// 목표: String -> ByteBuffer, ByteBuffer -> String
		//--------------------------------------------//

		//--------------------------------------------//
		// ** 중요: NIO에서 문자셋(Charset) 객체를 생성하는 방법(두가지 있음)
		//--------------------------------------------//
		Charset charset = Charset.forName("UTF-8");		// 특정 문자셋 지정가능
//		charset charset = Charset.defaultCharset();		// OS 문자셋 지정
		//--------------------------------------------//

		//--------------------------------------------//
		// 위에서 얻어낸, 문자셋 객체(Charset)를 가지고, 
		// 문자열 인코딩 수행하여, ByteBuffer 객체를 생성하게 됨
		//--------------------------------------------//
		// 1) 문자열 -> 인코딩(Charset객체 이용) -> ByteBuffer
		//--------------------------------------------//
		String data = "안녕하세요";	// Original Data

		//--------------------------------------------//
		ByteBuffer byteBuffer = charset.encode(data);
		//--------------------------------------------//

		//--------------------------------------------//
		// 2) ByteBuffer -> 디코딩 -> CharBuffer -> 문자열
		//--------------------------------------------//		
		data = charset.decode(byteBuffer).toString();
		
		System.out.println("문자열 복원: " + data);
		
	} // main
	
} // end class
