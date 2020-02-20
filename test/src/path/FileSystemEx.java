package path;

import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

public class FileSystemEx {

	
	public static void main(String[] args) throws Exception{
		FileSystem fileSystem = FileSystems.getDefault();
		
		for(FileStore store : fileSystem.getFileStores()) {
			System.out.println("드라이버명: " +store.name());
			System.out.println("파일 시스템: " + store.type());
			
			System.out.println("전체공간: \t\t + "+store.getTotalSpace()+" 바이트");
			System.out.println("사용중인 공간: \t" +(store.getTotalSpace() - store.getUnallocatedSpace())+ " 바이트");
			System.out.println("사용가능한 공간: \t"+ store.getUsableSpace() +" 바이트");
			
			System.out.println("파일 구분자: " +fileSystem.getSeparator());
			
			
		}
	}
}
