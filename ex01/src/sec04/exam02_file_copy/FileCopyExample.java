package sec04.exam02_file_copy;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class FileCopyExample {
	
	
	public static void main(String[] args) throws IOException {
		//--------------------------------------------------------------//
		// 1. �ҽ����ϰ�� ��ü(from), Ÿ�����ϰ�� ��ü(to) ����
		//--------------------------------------------------------------//
		Path from = Paths.get("C:/Temp/file.txt");	// Source file.
		Path to = Paths.get("C:/Temp/file2.txt");	// Target file.

		//--------------------------------------------------------------//
		// 2. �ҽ����Ͽ� ���� FileChannel ��ü����, 
		//    Ÿ�����Ͽ� ���� FileChannel ��ü����
		//--------------------------------------------------------------//
		FileChannel fcFrom = 
			FileChannel.
				open(
					from, 
					StandardOpenOption.READ	 // ���Ͽ�����: �б��
				);
		
		FileChannel fcTo = 
			FileChannel.
				open(
					to, 
					StandardOpenOption.CREATE, 	 // ���Ͽ�����: ����
					StandardOpenOption.WRITE	 // ���Ͽ�����: �����
				);

		//--------------------------------------------------------------//
		// 3. �б�/����� ByteBuffer ��ü ���� (Direct Buffer: 100)
		//--------------------------------------------------------------//
		ByteBuffer buffer = ByteBuffer.allocateDirect(100);

		//--------------------------------------------------------------//
		// 4. ���� ���� ����(�ҽ����� �а�, Ÿ�����Ͽ� ����... �� �۾��� �ݺ� ����)
		//--------------------------------------------------------------//
		int byteCount;	// ���� �ҽ����Ͽ��� �о ����Ʈ ���� ����
		
		while(true) { // ���ѷ���
			
			buffer.clear();		// ***�����ʱ�ȭ*** (***����***�� ���ؼ�...)
			
			byteCount = fcFrom.read(buffer);	// �ҽ����Ͽ��� READ ����
			
			/*******************************/
			System.out.println("- byteCount: "+ byteCount);
			System.out.println("\t+ buffer: "+ buffer);
			/*******************************/
			
			// �о ����Ʈ ������ -1 �̸�, �ҽ������� EOF�� ����. 
			// ���ѷ��� ����.
			if(byteCount == -1) {
				break;
			} // if
			
			/********************/
			buffer.flip();			// ***����*** : ������ ��ġ�Ӽ����� �б����..
			
			System.out.println("\t+ buffer after flipping: "+ buffer);
			/********************/
			
			// Ready to read �� ByteBuffer�� Ÿ������ ä�ο� �����Ͽ�, WRITE ����.
			fcTo.write(buffer);
		} // while : �ҽ������� EOF�� ������ ������ �ݺ� ����.
		
		// �ڿ��ݳ� ����
		fcFrom.close();
		fcTo.close();
		
		System.out.println("���� ���� ����");
	} // main
	
} // end class
