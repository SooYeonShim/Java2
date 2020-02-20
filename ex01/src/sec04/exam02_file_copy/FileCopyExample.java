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
		// 1. 소스파일경로 객체(from), 타겟파일경로 객체(to) 생성
		//--------------------------------------------------------------//
		Path from = Paths.get("C:/Temp/file.txt");	// Source file.
		Path to = Paths.get("C:/Temp/file2.txt");	// Target file.

		//--------------------------------------------------------------//
		// 2. 소스파일에 대한 FileChannel 객체생성, 
		//    타겟파일에 대한 FileChannel 객체생성
		//--------------------------------------------------------------//
		FileChannel fcFrom = 
			FileChannel.
				open(
					from, 
					StandardOpenOption.READ	 // 파일열기모드: 읽기용
				);
		
		FileChannel fcTo = 
			FileChannel.
				open(
					to, 
					StandardOpenOption.CREATE, 	 // 파일열기모드: 생성
					StandardOpenOption.WRITE	 // 파일열기모드: 쓰기용
				);

		//--------------------------------------------------------------//
		// 3. 읽기/쓰기용 ByteBuffer 객체 생성 (Direct Buffer: 100)
		//--------------------------------------------------------------//
		ByteBuffer buffer = ByteBuffer.allocateDirect(100);

		//--------------------------------------------------------------//
		// 4. 파일 복사 수행(소스파일 읽고, 타겟파일에 쓰고... 이 작업을 반복 수행)
		//--------------------------------------------------------------//
		int byteCount;	// 실제 소스파일에서 읽어낸 바이트 갯수 저장
		
		while(true) { // 무한루프
			
			buffer.clear();		// ***버퍼초기화*** (***재사용***을 위해서...)
			
			byteCount = fcFrom.read(buffer);	// 소스파일에서 READ 수행
			
			/*******************************/
			System.out.println("- byteCount: "+ byteCount);
			System.out.println("\t+ buffer: "+ buffer);
			/*******************************/
			
			// 읽어낸 바이트 개수가 -1 이면, 소스파일의 EOF에 도달. 
			// 무한루프 종료.
			if(byteCount == -1) {
				break;
			} // if
			
			/********************/
			buffer.flip();			// ***강조*** : 버퍼의 위치속성값을 읽기모드로..
			
			System.out.println("\t+ buffer after flipping: "+ buffer);
			/********************/
			
			// Ready to read 된 ByteBuffer를 타겟파일 채널에 전달하여, WRITE 수행.
			fcTo.write(buffer);
		} // while : 소스파일의 EOF에 도달할 때까지 반복 수행.
		
		// 자원반납 수행
		fcFrom.close();
		fcTo.close();
		
		System.out.println("파일 복사 성공");
	} // main
	
} // end class
