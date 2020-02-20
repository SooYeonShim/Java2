package path;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class getFileSystemInfo {

	public static void main(String[] args) {
		try(FileSystem fs = FileSystems.getDefault(); ){
			System.out.println("- fs: "+fs);
			
			System.out.println("\t+Separator: " +fs.getSeparator());
			System.out.println("\t+ :"+fs.getRootDirectories());
			Iterable<Path> iterable =fs.getRootDirectories();
			Iterable<FileStore> iterator=fs.getFileStores();
			
			iterable.forEach(path->{
				System.out.println("\t+ :"+fs.getFileStores());
				
				
			});
			
		}catch(Exception e) {
			
		}
	}
}
