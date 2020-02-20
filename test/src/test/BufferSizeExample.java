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
		
	//���̷�Ʈ ����(OS memory�� ����)
	ByteBuffer directBuffer = ByteBuffer.allocateDirect(200*1024*1024);
	
	System.out.println("���̷�Ʈ ���۰� �����Ǿ����ϴ�.");
	System.out.println(" - ���̷�Ʈ ���� :" +directBuffer);
	System.out.println(" - ���̷�Ʈ ���� ����뷮 :" +directBuffer.capacity() +"����Ʈ");
	
	ByteBuffer nonDirectBuffer = ByteBuffer.allocate(200*1024*1024);
	System.out.println("�ʹ��̷�Ʈ ���۰� �����Ǿ����ϴ�.");
	System.out.println(" - ���̷�Ʈ ���� :" +nonDirectBuffer);
	
	CharBuffer charBuffer = ByteBuffer.allocateDirect(100).asCharBuffer();
	System.out.println(" charBuffer ����뷮 : " + charBuffer.capacity() + " ����");
	System.out.println("\t- charBuffer: " +charBuffer);
	
	
	
	}
}
