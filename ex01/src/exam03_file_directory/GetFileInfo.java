package exam03_file_directory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import static java.lang.System.*;


public class GetFileInfo {

	
	public static void main(String[] args) 
		throws IOException, URISyntaxException {

		//----------------------------------------------
		// 1. ���ڿ� �Ű�������, Path ��ü �������#1
		//----------------------------------------------
		Path path = Paths.get("C:/Temp/setup.log");
		Path path2 = Paths.get("C:/Temp/HncDownload/Update.log");
		Path path3 = Paths.get("C:", "Temp", "Kalimba.mp3");
		Path path4 = Paths.get("C:", "Temp", "Sleep Away.mp3");
		
		//----------------------------------------------
		//--- 2. URI ��ü�� �Ű�������, Path ��ü �������#2
		//----------------------------------------------
		Path path5 = Paths.get(new URI("file:///C:/Temp/RHDSetup.log"));
		Path path6 = Paths.get(new URI("file:///C:/Temp"));
		Path path7 = Paths.get(new URI("file:///C:/Temp/newFile"));
		Path path8 = Paths.get(new URI("file:///C:/Temp/newFile2"));
		
		//----------------------------------------------
		//--- 3. Files ��ü�� �پ��� �޼ҵ� �̿�
		//----------------------------------------------
		out.println("- path:" + path);
		out.println("- Path Class:" + path.getClass().getName());		
		
		out.println("- isDirectory: "+Files.isDirectory(path));
		out.println("- isRegularFile: "+Files.isRegularFile(path));
		out.println("- getLastModifiedTime: "+
						Files.getLastModifiedTime(path));
		out.println("- size: "+Files.size(path));
		out.println("- getOwner: "+Files.getOwner(path));
		out.println("- isHidden: "+Files.isHidden(path));
		out.println("- isReadable: "+Files.isReadable(path));
		out.println("- isWritable: "+Files.isWritable(path));
		out.println("- exists: "+Files.exists(path));
		out.println("- isExecutable: "+Files.isExecutable(path));
		out.println("- isSameFile: "+Files.isSameFile(path, path2));
		out.println("- notExists: "+Files.notExists(path));
		
		//** �߿� ** : Files.probeContentType() �޼ҵ��, �Ű������� Path
		//           ��ü�� ����Ű�� ������ ������ �˻��Ͽ�, �ش� ������ MIME Type
		//           �� ������!!!
		out.println("- probeContentType: "+
							Files.probeContentType(path3));
		out.println("- probeContentType: "+
							Files.probeContentType(path4));
		

		//----------------------------------------------
		//--- 4. Files �޼ҵ� ��, Path �� ������ �ϴ� ���Ͽ� ����
		//       I/O �۾� �޼ҵ� Ȱ��
		//----------------------------------------------
		// Files.readAllLines(path): Path ������ ������, �� ������
		// ������(String) ����Ʈ ��ü�� ��Ƽ� ����!!
		//----------------------------------------------
		List<String> lines = Files.readAllLines(path7);
		
		out.println("--------------------------------------");
		out.println("- Lines of a File.");
		out.println("--------------------------------------");
		
		// List.forEach(Consumer<T>)�� �̿��Ͽ�, ������ �� �࿡  �����Ϳ� ����
		// ó���� ���ٽ����� ����
		lines.forEach(line -> System.out.println(line)); // forEach.
		
		out.println("--------------------------------------");
		
		lines.clear(); // List ��ü Clear : GC.
		
		// Files.readAllBytes(path): Path ������ �����͸� byte[]�� ������! 
		byte[] allBytes = Files.readAllBytes(path5);
		out.println("- readAllBytes: "+ Arrays.toString(allBytes));

		// Files.readAllBytes(path): Path��ü�κ���, InputStream ���!
		InputStream is = Files.newInputStream(path5);
		out.println("- newInputStream: "+ is);		
		is.close(); // �ڿ��ݳ�

		// Files.readAllBytes(path): Path��ü�κ���, OutputStream ���!
		OutputStream os = Files.newOutputStream(path5);
		System.out.println("- newOutputStream: "+os);
		os.close(); // �ڿ��ݳ�
		
		// ***�߿�***: Path��ü�κ���, NIO Channel ���!!!
		SeekableByteChannel sbc = Files.newByteChannel(path5);
		System.out.println("- newByteChannel: "+sbc);
		sbc.close(); // �ڿ��ݳ�

		// ***�߿�***: Path��ü�κ���, STREAMS ��Ʈ�� ��ü ���!!!
		DirectoryStream<Path> ds = Files.newDirectoryStream(path6);
		System.out.println("- newDirectoryStream:"+ds);
		
		// STREAMS ��ü�� ����ó�� �޼ҵ��� forEach()�̿��Ͽ� ��ȸ(traverse)
		ds.forEach(p -> {
			out.println("\t+ path: "+ p.getFileName());
			
		}); // forEach
		
		ds.close(); // �ڿ��ݳ�

		// Files.newBufferedReader(path): Path��ü�κ���, 
		// BufferedReader ��ü ���!
		BufferedReader br = Files.newBufferedReader(path5);
		out.println("- newBufferedReader: "+ br);
		br.close(); // �ڿ��ݳ�

		// Files.newBufferedWriter(path): Path��ü�κ���, 
		// BufferedWriter ��ü ���!
		BufferedWriter bw = Files.newBufferedWriter(path5);
		out.println("- newBufferedWriter: "+bw);
		bw.close(); // �ڿ��ݳ�
		
		// Files.write() �޼ҵ带 �̿��Ͽ�, ���Ͽ� ������ ����!!!
		Files.write(path7, "Hello1\n".getBytes());
		Files.write(
				path7, 
				"Hello2\n".getBytes(), 
				StandardOpenOption.APPEND); // �����߰�����!!
		Files.write(
				path7, 
				"Hello3\n".getBytes(), 
				StandardOpenOption.APPEND); // �����߰�����!!
		
		// Files.move() �޼ҵ带 �̿��Ͽ�, �����̵�!!!
		Files.move(
				path3, 
				path8, 
				StandardCopyOption.REPLACE_EXISTING); // ������ �����

		// Files.move() �޼ҵ带 �̿��Ͽ�, �����̵�!!!
		Files.move(path8, path3); // �⺻��
		
		// Files.deleteIfExists() �޼ҵ带 �̿��Ͽ�, 
		// ���α׷� �����, Path ���� ����!!!
		Files.deleteIfExists(path8);

		// Files.createFile() �޼ҵ带 �̿��Ͽ�, 
		// ���ο� ���� ����(�̶�, ũ��� 0����Ʈ) !!!
		Files.createFile(path8);

		// Files.delete() �޼ҵ带 �̿��Ͽ�, Path ���� ����!!!
		Files.delete(path8);

		// Files.copy() �޼ҵ带 �̿��Ͽ�, ���� ����!!!
		Files.copy(path3, Files.newOutputStream(path8));
		
	} // main

} // end class
