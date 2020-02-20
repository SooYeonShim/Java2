package exam02_filesystem;

import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import static java.lang.System.*;

public class FileSystemExample {

	
	public static void main(String[] args) 
			throws Exception {
		
		//-- 1. ���Ͻý��� ���� ��ü ��� (�⺻���)
		//      (*����*) ������, Path.getFileSystem()���ε�
		//      ���Ͻý��� ��ü�� ���� �� ������ ����� ��!!!
		FileSystem fileSystem = FileSystems.getDefault();
		
		// ���� �� ���Ͻý������κ���, ��� ����̺� ��ü��
		// ��. ���⼭,����̺��? C:, D:, E: ���� ����̺�
		// �� �����Ͻø� �˴ϴ�.! --> �̸� FileStore ���
		// �������̽��� ����̺긦 �𵨸� �� ����.
		for(FileStore store : fileSystem.getFileStores()) {
			out.println("����̹���: " + store.name());
			out.println("���Ͻý���: " + store.type());
			out.println(
				"��ü ����: \t\t" + 
					store.getTotalSpace() + " ����Ʈ");
			out.println(
				"��� ���� ����: \t" + 
				(store.getTotalSpace() - 
				 store.getUnallocatedSpace()) + " ����Ʈ");
			out.println(
				"��� ������ ����: \t" + 
						store.getUsableSpace() + " ����Ʈ");
			
			out.println("--------------");
		} // enhanced for
		

		//-------------------
		out.println(
			"���� ������: " + fileSystem.getSeparator());
		//-------------------
		
		out.println("------------------");
		
		//-------------------
		for(Path path : fileSystem.getRootDirectories()) {
			out.println( path.toString() );
		} // enhanced for
		//-------------------
		
	} // main
	
} // end class
