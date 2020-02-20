package sec03.exam01_direct_buffer;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

public class PerformanceExample {
	
	
	public static void main(String[] args) throws Exception {
		//--------------------------------------------------------//
		// 1. 3�� ���Ͽ� ���� ���(Path) ��ü ����
		//--------------------------------------------------------//
		Path from = Paths.get("C:/Temp/Kalimba.mp3");
		
		Path to1 = Paths.get("C:/temp/Kalimba-1.mp3");
		Path to2 = Paths.get("C:/temp/Kalimba-2.mp3");
		//--------------------------------------------------------//

		//--------------------------------------------------------//
		// 2. from Path��ü�� ����Ű�� ���Ͽ� ���� ����ũ�� ���� ���
		//--------------------------------------------------------//
		long size = Files.size(from);	// Files.size() �����޼ҵ� Ȱ��
		//--------------------------------------------------------//

		//--------------------------------------------------------//
		// 3. NIO ä���� �̿��Ͽ�, from ���Ϸκ��� to ���Ϸ� ���Ϻ��� ����
		//--------------------------------------------------------//
		// ä�� ���� �ڵ�� ���� �������� �����ŵ� �������ϴ�. �ϴ� ����غ��ô�!!
	    FileChannel fileChannel_from = FileChannel.open(from);
	    
	    FileChannel fileChannel_to1 = 
    		FileChannel.open(
				to1, 
				EnumSet.of(
					StandardOpenOption.CREATE, 
					StandardOpenOption.WRITE
				) // of
			); // open
	    
	    FileChannel fileChannel_to2 = 
    		FileChannel.open(
				to2, 
				EnumSet.of(
					StandardOpenOption.CREATE, 
					StandardOpenOption.WRITE
				) // of
			); // open

		//--------------------------------------------------------//
		// 4. NIO ���۸� �ΰ����� ����(���̷�Ʈ����, �ʹ��̷�Ʈ����)
		//--------------------------------------------------------//
	    // size �Ű�����: ������, from Path��ü�� ����ũ�⸦ ���� ��(Ÿ��: long)
	    // So, allocate/allocateDirect() �޼ҵ��� �Ű�����Ÿ���� int ��!(Ÿ�Ժ�ȯ�ʿ�)
	    ByteBuffer nonDirectBuffer = ByteBuffer.allocate((int) size);
	    ByteBuffer directBuffer = ByteBuffer.allocateDirect((int)size);
	    
	    // ���������� ���� �� ���� ����
	    long start, end;
    	
	    start = System.nanoTime();

			//--------------------------------------------------------//
		    for(int i=0; i<100; i++) {
			    fileChannel_from.read(nonDirectBuffer);	// from ���� �б�
			    
		    	nonDirectBuffer.flip();					// ���۸� ���� �غ��ϱ�
		    	
		    	fileChannel_to1.write(nonDirectBuffer);	// to1 ���Ͽ� ����
		    	
		    	nonDirectBuffer.clear();				// ���� �ʱ�ȭ(�����غ�)
		    } // for
		    
			//--------------------------------------------------------//
		    
    	end = System.nanoTime();
    	System.out.println("1. �ʹ��̷�Ʈ:\t" + (end-start) + " ns");

		//--------------------------------------------------------//
    	fileChannel_from.position(0);					// ä�������غ�
		//--------------------------------------------------------//

		//--------------------------------------------------------//
	    start = System.nanoTime();	  
	    
			//--------------------------------------------------------//
	    	for(int i=0; i<100; i++) {
			    fileChannel_from.read(directBuffer);	// from ���� �б�
			    
		    	directBuffer.flip();					// ���������غ��ϱ�
		    	
		    	fileChannel_to2.write(directBuffer);	// to2 ���Ͽ� ����
		    	
		    	directBuffer.clear();					// �����ʱ�ȭ(����)
	    	} // for
			//--------------------------------------------------------//
	    	
    	end = System.nanoTime();
    	System.out.println("2. ���̷�Ʈ:\t" + (end-start) + " ns");

		//--------------------------------------------------------//
    	fileChannel_from.close();
    	fileChannel_to1.close();
    	fileChannel_to2.close();
		//--------------------------------------------------------//
	} // main
	
} // end class
