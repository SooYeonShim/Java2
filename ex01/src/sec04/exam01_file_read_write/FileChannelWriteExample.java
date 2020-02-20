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

// 파일에 쓰기 예제
public class FileChannelWriteExample {
	
	
	public static void main(String[] args) throws IOException {
		// 1. Path 객체로 쓰기작업할 파일 경로 지정
		Path path = Paths.get("C:/Temp/file.txt");
		
		// 2. Path 객체가 지정한 경로상, 존재하지 않는 모든 폴더를 자동 생성
		Files.createDirectories(path.getParent());
		
		// 3. FileChannel 객체 생성(기본방식)
		FileChannel fileChannel = 
			FileChannel.open(
					path,			// 쓰기작업할 파일경로지정
					
					// 파일열기옵션은 1개 이상 지정가능(가변인자(...), 즉 배열)
					StandardOpenOption.CREATE,  // 열기옵션1: 생성모드
					StandardOpenOption.WRITE	// 열기옵션2: 쓰기모드
			); // open
		
		// 4. Path 객체의 경로파일에 저장할 데이터(문자열) 지정
		String data = "안녕하세요";
		
		// 5. String -> ByteBuffer 로 변환에 필요한, Charset 객체 생성(기본방식)
		Charset charset = Charset.defaultCharset();		// 첫번째 방식(MS949)
//		Charset charset = Charset.forName("UTF-8");		// 두번째 방식
		
		// 6. String -> ByteBuffer 로 변환 using Charset.encode() method
		ByteBuffer byteBuffer = charset.encode(data);
		byteBuffer.flip();
		System.out.println("- byteBuffer: "+ byteBuffer);
		
		// 7. FileChannel.write(ByteBuffer) 메소드로 파일에 쓰기 수행.
		//    실제 파일에 쓰여진 바이트수 반환(byteCount)
		int byteCount = fileChannel.write(byteBuffer);  
		
		System.out.println("file.txt : " + byteCount + " bytes written");
		
		// 8. FileChannel 작업이 모두 끝났으면, 반드시 자원반납 수행
		fileChannel.close();		// 채널 닫기.
		
	} // main
	
} // end class
