package exam02_filesystem;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import static java.lang.System.*;


public class GetFileSystemInfo {

	
	public static void main(String[] args) {
//		FileSystem fs = FileSystems.getDefault();

		//-----------------------------------
		//-- FileSystem: abstract class and Closeable interface
		//               를 구현하고 잇으므로, try-with-resources 문 사용가능
		try( FileSystem fs = FileSystems.getDefault();) { //자원생성
			out.println("- fs: "+fs);

			//-----------------------------------
			// 파일시스템 경로구분자 얻어내기
			out.println("\t+ Separator: "+fs.getSeparator());

			//-----------------------------------
			// 파일 시스템의 ROOT 폴더명 얻기
			// fs.getRootDirectories() return type: Iterable<Path>
			// 따라서, enhanced for문 사용가능
			out.println("\t+ :"+fs.getRootDirectories());
			
			//-----------------------------------
			// Iterable 타입의 객체는, Iterable 하므로, enhanced for문
			// 사용가능하나, 각 요소를 순회(traverse)할 수 있는, 더 낳은 방법은
			// Iterable 타입이 제공하는 forEach() 메소드를 사용하는 것임.
			// 여기서, Iterable.forEach(Consumer<T>) 매개변수를 가지므로,
			// 람다식을 만들어 제공하면, 각 요소에 람다를 적용가능!!!!
			Iterable<Path> iterable = fs.getRootDirectories();

			//-----------------------------------
			// Iterable.forEach(Consumer<T>) 사용하여 각 요소 순회!!!
			iterable.forEach(path -> {
				out.println("\t+ path: "+path);
				
			}); // forEach			

			//-----------------------------------
			// fs.getFileStores() return type 역시 Iterable<FileStore>
			// 이므로, Iterable.forEach(Consumer<T>)를 이용하여,
			// 각 요소에 쉽게 접근가능!!!
			Iterable<FileStore> iterator = fs.getFileStores();
			
			iterator.forEach(store -> {
//				System.out.print("\t+ FileStore: "+ store);
				
				try {
					out.println("\t+ name: "+store.name() );
					out.println("\t+ type: "+store.type() );
					out.println("\t+ TotalSpace: "+
							store.getTotalSpace() );
					out.println("\t+ UnallocatedSpace: "+ 
							store.getUnallocatedSpace() );
					out.println("\t+ UsableSpace: "+ 
							store.getUsableSpace() );									
				} catch(IOException e) {
					e.printStackTrace();
				} // try-catch
				
			}); // forEach
			
		} catch(IOException e) { // FileSystem 의 메소드는 예외발생가능!
			e.printStackTrace();
		} // try-with-resources
		
	} // main

} // end class
