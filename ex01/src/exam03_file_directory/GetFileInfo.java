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
		// 1. 문자열 매개값으로, Path 객체 생성방법#1
		//----------------------------------------------
		Path path = Paths.get("C:/Temp/setup.log");
		Path path2 = Paths.get("C:/Temp/HncDownload/Update.log");
		Path path3 = Paths.get("C:", "Temp", "Kalimba.mp3");
		Path path4 = Paths.get("C:", "Temp", "Sleep Away.mp3");
		
		//----------------------------------------------
		//--- 2. URI 객체를 매개값으로, Path 객체 생성방법#2
		//----------------------------------------------
		Path path5 = Paths.get(new URI("file:///C:/Temp/RHDSetup.log"));
		Path path6 = Paths.get(new URI("file:///C:/Temp"));
		Path path7 = Paths.get(new URI("file:///C:/Temp/newFile"));
		Path path8 = Paths.get(new URI("file:///C:/Temp/newFile2"));
		
		//----------------------------------------------
		//--- 3. Files 객체의 다양한 메소드 이용
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
		
		//** 중요 ** : Files.probeContentType() 메소드는, 매개변수인 Path
		//           객체가 가리키는 파일의 내용을 검사하여, 해당 파일의 MIME Type
		//           을 리턴함!!!
		out.println("- probeContentType: "+
							Files.probeContentType(path3));
		out.println("- probeContentType: "+
							Files.probeContentType(path4));
		

		//----------------------------------------------
		//--- 4. Files 메소드 중, Path 가 포인팅 하는 파일에 대한
		//       I/O 작업 메소드 활용
		//----------------------------------------------
		// Files.readAllLines(path): Path 파일의 내용을, 행 단위로
		// 가져와(String) 리스트 객체에 담아서 리턴!!
		//----------------------------------------------
		List<String> lines = Files.readAllLines(path7);
		
		out.println("--------------------------------------");
		out.println("- Lines of a File.");
		out.println("--------------------------------------");
		
		// List.forEach(Consumer<T>)를 이용하여, 파일의 각 행에  데이터에 대한
		// 처리를 람다식으로 제공
		lines.forEach(line -> System.out.println(line)); // forEach.
		
		out.println("--------------------------------------");
		
		lines.clear(); // List 객체 Clear : GC.
		
		// Files.readAllBytes(path): Path 파일의 데이터를 byte[]로 가져옴! 
		byte[] allBytes = Files.readAllBytes(path5);
		out.println("- readAllBytes: "+ Arrays.toString(allBytes));

		// Files.readAllBytes(path): Path객체로부터, InputStream 얻기!
		InputStream is = Files.newInputStream(path5);
		out.println("- newInputStream: "+ is);		
		is.close(); // 자원반납

		// Files.readAllBytes(path): Path객체로부터, OutputStream 얻기!
		OutputStream os = Files.newOutputStream(path5);
		System.out.println("- newOutputStream: "+os);
		os.close(); // 자원반납
		
		// ***중요***: Path객체로부터, NIO Channel 얻기!!!
		SeekableByteChannel sbc = Files.newByteChannel(path5);
		System.out.println("- newByteChannel: "+sbc);
		sbc.close(); // 자원반납

		// ***중요***: Path객체로부터, STREAMS 스트림 객체 얻기!!!
		DirectoryStream<Path> ds = Files.newDirectoryStream(path6);
		System.out.println("- newDirectoryStream:"+ds);
		
		// STREAMS 객체의 최종처리 메소드인 forEach()이용하여 순회(traverse)
		ds.forEach(p -> {
			out.println("\t+ path: "+ p.getFileName());
			
		}); // forEach
		
		ds.close(); // 자원반납

		// Files.newBufferedReader(path): Path객체로부터, 
		// BufferedReader 객체 얻기!
		BufferedReader br = Files.newBufferedReader(path5);
		out.println("- newBufferedReader: "+ br);
		br.close(); // 자원반납

		// Files.newBufferedWriter(path): Path객체로부터, 
		// BufferedWriter 객체 얻기!
		BufferedWriter bw = Files.newBufferedWriter(path5);
		out.println("- newBufferedWriter: "+bw);
		bw.close(); // 자원반납
		
		// Files.write() 메소드를 이용하여, 파일에 데이터 쓰기!!!
		Files.write(path7, "Hello1\n".getBytes());
		Files.write(
				path7, 
				"Hello2\n".getBytes(), 
				StandardOpenOption.APPEND); // 파일추가모드로!!
		Files.write(
				path7, 
				"Hello3\n".getBytes(), 
				StandardOpenOption.APPEND); // 파일추가모드로!!
		
		// Files.move() 메소드를 이용하여, 파일이동!!!
		Files.move(
				path3, 
				path8, 
				StandardCopyOption.REPLACE_EXISTING); // 있으면 덮어쓰기

		// Files.move() 메소드를 이용하여, 파일이동!!!
		Files.move(path8, path3); // 기본형
		
		// Files.deleteIfExists() 메소드를 이용하여, 
		// 프로그램 종료시, Path 파일 삭제!!!
		Files.deleteIfExists(path8);

		// Files.createFile() 메소드를 이용하여, 
		// 새로운 파일 생성(이때, 크기는 0바이트) !!!
		Files.createFile(path8);

		// Files.delete() 메소드를 이용하여, Path 파일 삭제!!!
		Files.delete(path8);

		// Files.copy() 메소드를 이용하여, 파일 복사!!!
		Files.copy(path3, Files.newOutputStream(path8));
		
	} // main

} // end class
