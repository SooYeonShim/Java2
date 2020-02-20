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
		// 1. 3개 파일에 대한 경로(Path) 객체 생성
		//--------------------------------------------------------//
		Path from = Paths.get("C:/Temp/Kalimba.mp3");
		
		Path to1 = Paths.get("C:/temp/Kalimba-1.mp3");
		Path to2 = Paths.get("C:/temp/Kalimba-2.mp3");
		//--------------------------------------------------------//

		//--------------------------------------------------------//
		// 2. from Path객체가 가리키는 파일에 대한 파일크기 정보 얻기
		//--------------------------------------------------------//
		long size = Files.size(from);	// Files.size() 정적메소드 활용
		//--------------------------------------------------------//

		//--------------------------------------------------------//
		// 3. NIO 채널을 이용하여, from 파일로부터 to 파일로 파일복사 수행
		//--------------------------------------------------------//
		// 채널 생성 코드는 지금 이해하지 않으셔도 괜찮습니다. 일단 사용해봅시다!!
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
		// 4. NIO 버퍼를 두가지로 생성(다이렉트버퍼, 넌다이렉트버퍼)
		//--------------------------------------------------------//
	    // size 매개변수: 위에서, from Path객체의 파일크기를 얻은 값(타입: long)
	    // So, allocate/allocateDirect() 메소드의 매개변수타입은 int 임!(타입변환필요)
	    ByteBuffer nonDirectBuffer = ByteBuffer.allocate((int) size);
	    ByteBuffer directBuffer = ByteBuffer.allocateDirect((int)size);
	    
	    // 성능측정을 위한 두 변수 선언
	    long start, end;
    	
	    start = System.nanoTime();

			//--------------------------------------------------------//
		    for(int i=0; i<100; i++) {
			    fileChannel_from.read(nonDirectBuffer);	// from 파일 읽기
			    
		    	nonDirectBuffer.flip();					// 버퍼를 읽을 준비하기
		    	
		    	fileChannel_to1.write(nonDirectBuffer);	// to1 파일에 쓰기
		    	
		    	nonDirectBuffer.clear();				// 버퍼 초기화(재사용준비)
		    } // for
		    
			//--------------------------------------------------------//
		    
    	end = System.nanoTime();
    	System.out.println("1. 넌다이렉트:\t" + (end-start) + " ns");

		//--------------------------------------------------------//
    	fileChannel_from.position(0);					// 채널재사용준비
		//--------------------------------------------------------//

		//--------------------------------------------------------//
	    start = System.nanoTime();	  
	    
			//--------------------------------------------------------//
	    	for(int i=0; i<100; i++) {
			    fileChannel_from.read(directBuffer);	// from 파일 읽기
			    
		    	directBuffer.flip();					// 버퍼읽을준비하기
		    	
		    	fileChannel_to2.write(directBuffer);	// to2 파일에 쓰기
		    	
		    	directBuffer.clear();					// 버퍼초기화(재사용)
	    	} // for
			//--------------------------------------------------------//
	    	
    	end = System.nanoTime();
    	System.out.println("2. 다이렉트:\t" + (end-start) + " ns");

		//--------------------------------------------------------//
    	fileChannel_from.close();
    	fileChannel_to1.close();
    	fileChannel_to2.close();
		//--------------------------------------------------------//
	} // main
	
} // end class
