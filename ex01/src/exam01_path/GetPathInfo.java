package exam01_path;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import static java.lang.System.*
;
public class GetPathInfo {
	
	
	public static void main(String[] args) {
		//-----------------------------------------
		// 1. Path 인터페이스의 구현객체 얻어내기#2
		//-----------------------------------------
		// Paths.get() 메소드는 여러개로 오버로딩 되어있음
		// 예: Paths.get("경로명");
		//     Paths.get("이름1", "이름2", .... );
		//     Paths.get(new URI("file:///C:/temp/sunnydays.mp3"));
		Path path = Paths.get("C:/Temp", "sunnydays.mp3");
		
		System.out.println("- path: "+path);
		//-----------------------------------------

		//-----------------------------------------
		System.out.println(
			"\t+ getFileName: "+path.getFileName());
		
		System.out.println(
			"\t+ getParent: "+
			path.getParent().getFileName());
		
		System.out.println(
			"\t+ getNameCount: "+path.getNameCount());
		//-----------------------------------------

		//-----------------------------------------
		for(int i=0; i<path.getNameCount(); i++) {
			System.out.println(
				"\t\t* getName: "+path.getName(i));
			
		} // for

		//-----------------------------------------
		Iterator<Path> iterator = path.iterator();
		System.out.println("- iterator: "+ iterator);
		
		while(iterator.hasNext()) {
			Path p = iterator.next();
			System.out.println(
				"\t+ path: "+p.getFileName());
			
		} // while
		//-----------------------------------------

		//-----------------------------------------
		out.println("- toString: "+path.toString());		
		out.println("- toUri: "+ path.toUri());
		out.println(
			"\t+ toUri.getPath: "+ path.toUri().getPath());
		out.println(
			"\t+ toUri.getHost: "+path.toUri().getHost());
		out.println(
			"\t+ toUri.getFragment: "+
					path.toUri().getFragment());
		out.println(
			"\t+ toUri.getQuery: "+path.toUri().getQuery());
		//-----------------------------------------
		
		//-----------------------------------------
		out.println("- toFile: "+ path.toFile());
		
		Path root = path.getRoot();
		out.println("- getRoot: "+ root);
		
		Path path2 = Paths.get("C:/Temp/HncDownload/../");
		out.println("- normalize: "+path2.normalize());
		
		// 1. 두 파일경로가 동일하면, 0을 리턴
		// 2. 두 파일경로중에, 기준경로가 비교경로보다, 상위경로이면
		//    음수를 리턴
		// 3. 두 파일경로중에, 기준경로가 비교경로보다, 하위경로이면
		//    양수를 리턴
		int result = path.compareTo(path);
		out.println("- compareTo: "+result);
		
		int result2 = path2.compareTo(path);
		out.println("- compareTo: "+result2);
		
		int result3 = path.compareTo(path2);
		out.println("- compareTo: "+result3);
		//-----------------------------------------
		
		//-----------------------------------------
		FileSystem fs = path.getFileSystem();
		System.out.println("- getFileSystem: "+fs);
		//-----------------------------------------
		
	} // main

} // end class
