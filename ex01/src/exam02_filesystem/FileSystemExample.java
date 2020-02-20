package exam02_filesystem;

import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import static java.lang.System.*;

public class FileSystemExample {

	
	public static void main(String[] args) 
			throws Exception {
		
		//-- 1. 파일시스템 구현 객체 얻기 (기본방법)
		//      (*주의*) 하지만, Path.getFileSystem()으로도
		//      파일시스템 객체를 얻을 수 있음을 기억할 것!!!
		FileSystem fileSystem = FileSystems.getDefault();
		
		// 현재 얻어낸 파일시스템으로부터, 모든 드라이브 객체를
		// 얻어냄. 여기서,드라이브란? C:, D:, E: 같은 드라이브
		// 를 생각하시면 됩니다.! --> 이를 FileStore 라는
		// 인터페이스로 드라이브를 모델링 한 것임.
		for(FileStore store : fileSystem.getFileStores()) {
			out.println("드라이버명: " + store.name());
			out.println("파일시스템: " + store.type());
			out.println(
				"전체 공간: \t\t" + 
					store.getTotalSpace() + " 바이트");
			out.println(
				"사용 중인 공간: \t" + 
				(store.getTotalSpace() - 
				 store.getUnallocatedSpace()) + " 바이트");
			out.println(
				"사용 가능한 공간: \t" + 
						store.getUsableSpace() + " 바이트");
			
			out.println("--------------");
		} // enhanced for
		

		//-------------------
		out.println(
			"파일 구분자: " + fileSystem.getSeparator());
		//-------------------
		
		out.println("------------------");
		
		//-------------------
		for(Path path : fileSystem.getRootDirectories()) {
			out.println( path.toString() );
		} // enhanced for
		//-------------------
		
	} // main
	
} // end class
