package sec04.exam01_file_read_write;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

// ���Ͽ� ���� ����
public class FileChannelWriteExample {
	
	
	public static void main(String[] args) throws IOException {
		// 1. Path ��ü�� �����۾��� ���� ��� ����
		Path path = Paths.get("C:/Temp/file.txt");
		
		// 2. Path ��ü�� ������ ��λ�, �������� �ʴ� ��� ������ �ڵ� ����
		Files.createDirectories(path.getParent());
		
		// 3. FileChannel ��ü ����(�⺻���)
		FileChannel fileChannel = 
			FileChannel.open(
					path,			// �����۾��� ���ϰ������
					
					// ���Ͽ���ɼ��� 1�� �̻� ��������(��������(...), �� �迭)
					StandardOpenOption.CREATE,  // ����ɼ�1: �������
					StandardOpenOption.WRITE	// ����ɼ�2: ������
			); // open
		
		// 4. Path ��ü�� ������Ͽ� ������ ������(���ڿ�) ����
		String data = "�ȳ��ϼ���";
		
		// 5. String -> ByteBuffer �� ��ȯ�� �ʿ���, Charset ��ü ����(�⺻���)
		Charset charset = Charset.defaultCharset();		// ù��° ���(MS949)
//		Charset charset = Charset.forName("UTF-8");		// �ι�° ���
		
		// 6. String -> ByteBuffer �� ��ȯ using Charset.encode() method
		ByteBuffer byteBuffer = charset.encode(data);
		byteBuffer.flip();
		System.out.println("- byteBuffer: "+ byteBuffer);
		
		// 7. FileChannel.write(ByteBuffer) �޼ҵ�� ���Ͽ� ���� ����.
		//    ���� ���Ͽ� ������ ����Ʈ�� ��ȯ(byteCount)
		int byteCount = fileChannel.write(byteBuffer);  
		
		System.out.println("file.txt : " + byteCount + " bytes written");
		
		// 8. FileChannel �۾��� ��� ��������, �ݵ�� �ڿ��ݳ� ����
		fileChannel.close();		// ä�� �ݱ�.
		
	} // main
	
} // end class
