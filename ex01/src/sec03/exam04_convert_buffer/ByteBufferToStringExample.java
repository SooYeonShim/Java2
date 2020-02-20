package sec03.exam04_convert_buffer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;


public class ByteBufferToStringExample {
	
	
	public static void main(String[] args) {
		//--------------------------------------------//
		// ��ǥ: String -> ByteBuffer, ByteBuffer -> String
		//--------------------------------------------//

		//--------------------------------------------//
		// ** �߿�: NIO���� ���ڼ�(Charset) ��ü�� �����ϴ� ���(�ΰ��� ����)
		//--------------------------------------------//
		Charset charset = Charset.forName("UTF-8");		// Ư�� ���ڼ� ��������
//		charset charset = Charset.defaultCharset();		// OS ���ڼ� ����
		//--------------------------------------------//

		//--------------------------------------------//
		// ������ ��, ���ڼ� ��ü(Charset)�� ������, 
		// ���ڿ� ���ڵ� �����Ͽ�, ByteBuffer ��ü�� �����ϰ� ��
		//--------------------------------------------//
		// 1) ���ڿ� -> ���ڵ�(Charset��ü �̿�) -> ByteBuffer
		//--------------------------------------------//
		String data = "�ȳ��ϼ���";	// Original Data

		//--------------------------------------------//
		ByteBuffer byteBuffer = charset.encode(data);
		//--------------------------------------------//

		//--------------------------------------------//
		// 2) ByteBuffer -> ���ڵ� -> CharBuffer -> ���ڿ�
		//--------------------------------------------//		
		data = charset.decode(byteBuffer).toString();
		
		System.out.println("���ڿ� ����: " + data);
		
	} // main
	
} // end class
