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
		//               �� �����ϰ� �����Ƿ�, try-with-resources �� ��밡��
		try( FileSystem fs = FileSystems.getDefault();) { //�ڿ�����
			out.println("- fs: "+fs);

			//-----------------------------------
			// ���Ͻý��� ��α����� ����
			out.println("\t+ Separator: "+fs.getSeparator());

			//-----------------------------------
			// ���� �ý����� ROOT ������ ���
			// fs.getRootDirectories() return type: Iterable<Path>
			// ����, enhanced for�� ��밡��
			out.println("\t+ :"+fs.getRootDirectories());
			
			//-----------------------------------
			// Iterable Ÿ���� ��ü��, Iterable �ϹǷ�, enhanced for��
			// ��밡���ϳ�, �� ��Ҹ� ��ȸ(traverse)�� �� �ִ�, �� ���� �����
			// Iterable Ÿ���� �����ϴ� forEach() �޼ҵ带 ����ϴ� ����.
			// ���⼭, Iterable.forEach(Consumer<T>) �Ű������� �����Ƿ�,
			// ���ٽ��� ����� �����ϸ�, �� ��ҿ� ���ٸ� ���밡��!!!!
			Iterable<Path> iterable = fs.getRootDirectories();

			//-----------------------------------
			// Iterable.forEach(Consumer<T>) ����Ͽ� �� ��� ��ȸ!!!
			iterable.forEach(path -> {
				out.println("\t+ path: "+path);
				
			}); // forEach			

			//-----------------------------------
			// fs.getFileStores() return type ���� Iterable<FileStore>
			// �̹Ƿ�, Iterable.forEach(Consumer<T>)�� �̿��Ͽ�,
			// �� ��ҿ� ���� ���ٰ���!!!
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
			
		} catch(IOException e) { // FileSystem �� �޼ҵ�� ���ܹ߻�����!
			e.printStackTrace();
		} // try-with-resources
		
	} // main

} // end class
