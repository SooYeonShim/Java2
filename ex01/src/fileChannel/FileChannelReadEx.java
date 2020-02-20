package fileChannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileChannelReadEx {
	public static void main(String[] args) throws IOException{
		
		
		//Read�� ���� ���
		Path path = Paths.get("C:/app/file.txt");
		
		
		FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ);
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(100);
		
		
		//ByteBuffer -> String ��ȯ�� �ʿ��ϹǷ� Charset ��ü ����
		Charset charset = Charset.defaultCharset();
		
		String data = "";
		int byteCount;
		
		while(true) {
			
			byteCount = fileChannel.read(byteBuffer);
			if(byteCount == -1) {
				break;
			}
			//flip�� ����, ByteBuffer�� �����͸� ���� �غ�� ��ġ�Ӽ� �缳��
			//limit -> position���� �̵�, position -> 0�� �ε����� �̵�
			byteBuffer.flip();
			
			data += charset.decode(byteBuffer).toString();
			
			//ByteBuffer�� ó�� ������ ���� ��ġ �Ӽ������� �ǵ���. �̶�, ��ġ�Ӽ����� �缳��������, ���۾��� �����ʹ� �������� ����
			byteBuffer.clear(); //���� �ʱ�ȭ
		}
		
		fileChannel.close();//�ڿ� �ݳ�
		
		System.out.println(data);
		
	}
}
