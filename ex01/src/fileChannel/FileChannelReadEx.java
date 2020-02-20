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
		
		
		//Read할 파일 경로
		Path path = Paths.get("C:/app/file.txt");
		
		
		FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ);
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(100);
		
		
		//ByteBuffer -> String 변환이 필요하므로 Charset 객체 생성
		Charset charset = Charset.defaultCharset();
		
		String data = "";
		int byteCount;
		
		while(true) {
			
			byteCount = fileChannel.read(byteBuffer);
			if(byteCount == -1) {
				break;
			}
			//flip을 통해, ByteBuffer의 데이터를 읽을 준비로 위치속성 재설정
			//limit -> position으로 이동, position -> 0번 인덱스로 이동
			byteBuffer.flip();
			
			data += charset.decode(byteBuffer).toString();
			
			//ByteBuffer를 처음 생성할 때의 위치 속성값으로 되돌림. 이때, 위치속성값은 재설정되지만, 버퍼안의 데이터는 삭제되지 않음
			byteBuffer.clear(); //버퍼 초기화
		}
		
		fileChannel.close();//자원 반납
		
		System.out.println(data);
		
	}
}
