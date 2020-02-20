package buffer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class ByterBufferEx2 {

	public static void main(String[] args) {
		//목표 String -> ByteBuffer, ByteBuffer -> String
		
		Charset charset= Charset.forName("UTF-8");
		
		String data = "안녕하세요";
				
		ByteBuffer byteBuffer = charset.encode(data);
		
		data = charset.decode(byteBuffer).toString();
		
		System.out.println("복원: " + data);
		
	}
}
