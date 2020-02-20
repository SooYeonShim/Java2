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
	
	// FileChannel을 통해서, 지정된 파일의 데이터를 모두 읽기!!
	public static void main(String[] args) throws IOException {	
		//------------------------------------------//
		// 1. Read할 파일의 경로를 Path객체로 지정	
		//------------------------------------------//
		Path path = Paths.get("C:/Temp/file.txt");
		
		//------------------------------------------//
		// 2. Path 경로가 지정한 파일에 대해, FileChannel 생성
		//    생성방법은 두가지: FileChannel.open() or 
		//					  Input/OutputStream.getChannel()
		//------------------------------------------//
		FileChannel fileChannel = FileChannel.open(
			path,						// 1st. 매개변수: 파일경로 지정 
			// 파일 읽기용으로 열기옵션 지정
			StandardOpenOption.READ		// 2nd. 매개변수: 파일열기옵션지정(열거상수로)
		);
		
		//------------------------------------------//
		// 3. Read할 파일의 데이터를 저장할 ByteBuffer 생성	
		//------------------------------------------//
		ByteBuffer byteBuffer = 
			ByteBuffer.allocate(100);	// Non-direct Buffer(capacity=100) 생성
		
		//------------------------------------------//
		// 4. Read할 파일의 형식이, text 파일이라면, 결국
		//    ByteBuffer -> String 변환이 필요하므로, Charset 객체 생성
		//------------------------------------------//
		// Charset charset = Charset.forName(특정문자셋지정);
		Charset charset = Charset.defaultCharset();		// 두가지 방법중, 기본형식
		
		String data = "";		// 최종 파일의 내용을 저장할 변수
		int byteCount;			// Read 할때마다, 읽혀진 바이트 개수저장
		
		while(true) {	// 지정된 파일의 끝까지, 루핑을 돌면서 read 작업 수행
			// 한번 FileChannel.read() 수행할 때마다, 실제 읽혀진 바이트 갯수가 반환!!!
			byteCount = fileChannel.read(byteBuffer);
			
			// 읽혀진 바이트 개수가 -1이면, 파일의 끝(End-Of-File, EOF) 도달
			// 이때 루프 종료
			if(byteCount == -1) {	// 무한루프를 빠져나갈 조건
				break;
			} // if
			
			// flip()을 통해, ByteBuffer의 데이터를 읽을 준비로, 위치속성 재설정
			// limit -> position으로 이동, position -> 0 번 인덱스로 이동
			byteBuffer.flip();
			
			// Charset 객체의 decode() 메소드로, 지정된 ByteBuffer의 데이터를
			// 문자열(String)로 역변환: ByteBuffer -> CharBuffer -> String
			data += charset.decode(byteBuffer).toString();
			// 위 코드라인에서, String data 변수에 복합대입연산자(+=)로 누적시킴!
			
			// ByteBuffer 를 처음 생성할 때의 위치속성값으로 되돌림(공장모드와 비슷?)
			// 단, 이때, 위치속성값은 재설정되지만, 버퍼안의 데이터는 별도로 삭제하지 않음
			byteBuffer.clear();	// 버퍼 초기화
		} // while
		
		// 자원반납.
		fileChannel.close();	// Channel 객체로 자원객체임을 주지!!!
		
		// 파일의 데이터를 String으로 모두 읽어낸 결과를 출력
		System.out.println("file.txt : " + data);
	} // main
	
} // end class
