package buffer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class ByterBufferEx2 {

	public static void main(String[] args) {
		//��ǥ String -> ByteBuffer, ByteBuffer -> String
		
		Charset charset= Charset.forName("UTF-8");
		
		String data = "�ȳ��ϼ���";
				
		ByteBuffer byteBuffer = charset.encode(data);
		
		data = charset.decode(byteBuffer).toString();
		
		System.out.println("����: " + data);
		
	}
}
