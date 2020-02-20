package sec04.exam01_file_read_write;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class FileChannelReadExample {
	
	// FileChannel�� ���ؼ�, ������ ������ �����͸� ��� �б�!!
	public static void main(String[] args) throws IOException {	
		//------------------------------------------//
		// 1. Read�� ������ ��θ� Path��ü�� ����	
		//------------------------------------------//
		Path path = Paths.get("C:/Temp/file.txt");
		
		//------------------------------------------//
		// 2. Path ��ΰ� ������ ���Ͽ� ����, FileChannel ����
		//    ��������� �ΰ���: FileChannel.open() or 
		//					  Input/OutputStream.getChannel()
		//------------------------------------------//
		FileChannel fileChannel = FileChannel.open(
			path,						// 1st. �Ű�����: ���ϰ�� ���� 
			// ���� �б������ ����ɼ� ����
			StandardOpenOption.READ		// 2nd. �Ű�����: ���Ͽ���ɼ�����(���Ż����)
		);
		
		//------------------------------------------//
		// 3. Read�� ������ �����͸� ������ ByteBuffer ����	
		//------------------------------------------//
		ByteBuffer byteBuffer = 
			ByteBuffer.allocate(100);	// Non-direct Buffer(capacity=100) ����
		
		//------------------------------------------//
		// 4. Read�� ������ ������, text �����̶��, �ᱹ
		//    ByteBuffer -> String ��ȯ�� �ʿ��ϹǷ�, Charset ��ü ����
		//------------------------------------------//
		// Charset charset = Charset.forName(Ư�����ڼ�����);
		Charset charset = Charset.defaultCharset();		// �ΰ��� �����, �⺻����
		
		String data = "";		// ���� ������ ������ ������ ����
		int byteCount;			// Read �Ҷ�����, ������ ����Ʈ ��������
		
		while(true) {	// ������ ������ ������, ������ ���鼭 read �۾� ����
			// �ѹ� FileChannel.read() ������ ������, ���� ������ ����Ʈ ������ ��ȯ!!!
			byteCount = fileChannel.read(byteBuffer);
			
			// ������ ����Ʈ ������ -1�̸�, ������ ��(End-Of-File, EOF) ����
			// �̶� ���� ����
			if(byteCount == -1) {	// ���ѷ����� �������� ����
				break;
			} // if
			
			// flip()�� ����, ByteBuffer�� �����͸� ���� �غ��, ��ġ�Ӽ� �缳��
			// limit -> position���� �̵�, position -> 0 �� �ε����� �̵�
			byteBuffer.flip();
			
			// Charset ��ü�� decode() �޼ҵ��, ������ ByteBuffer�� �����͸�
			// ���ڿ�(String)�� ����ȯ: ByteBuffer -> CharBuffer -> String
			data += charset.decode(byteBuffer).toString();
			// �� �ڵ���ο���, String data ������ ���մ��Կ�����(+=)�� ������Ŵ!
			
			// ByteBuffer �� ó�� ������ ���� ��ġ�Ӽ������� �ǵ���(������� ���?)
			// ��, �̶�, ��ġ�Ӽ����� �缳��������, ���۾��� �����ʹ� ������ �������� ����
			byteBuffer.clear();	// ���� �ʱ�ȭ
		} // while
		
		// �ڿ��ݳ�.
		fileChannel.close();	// Channel ��ü�� �ڿ���ü���� ����!!!
		
		// ������ �����͸� String���� ��� �о ����� ���
		System.out.println("file.txt : " + data);
	} // main
	
} // end class
