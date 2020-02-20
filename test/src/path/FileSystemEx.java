package path;

import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

public class FileSystemEx {

	
	public static void main(String[] args) throws Exception{
		FileSystem fileSystem = FileSystems.getDefault();
		
		for(FileStore store : fileSystem.getFileStores()) {
			System.out.println("����̹���: " +store.name());
			System.out.println("���� �ý���: " + store.type());
			
			System.out.println("��ü����: \t\t + "+store.getTotalSpace()+" ����Ʈ");
			System.out.println("������� ����: \t" +(store.getTotalSpace() - store.getUnallocatedSpace())+ " ����Ʈ");
			System.out.println("��밡���� ����: \t"+ store.getUsableSpace() +" ����Ʈ");
			
			System.out.println("���� ������: " +fileSystem.getSeparator());
			
			
		}
	}
}
