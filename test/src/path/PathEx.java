package path;

import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class PathEx {
	public static void main(String[] args) throws Exception {
		
		//-----------------------------------------
		//		Path 인터페이스의 구현객체 얻어내기
		//-----------------------------------------
		Path path =Paths.get("C:/app","뉴욕 뉴욕.mp3");
		
		
		System.out.println("[파일명] " + path.getFileName());
		System.out.println("[부모 디렉토리명] " + path.getParent().getFileName());
		System.out.println("[중첩경로 수: ] " + path.getNameCount());
		
		System.out.println();
		
		for(int i= 0; i<path.getNameCount();i++) {
			System.out.println(path.getName(i));
			
		}
		
		System.out.println();
		
		Iterator<Path> iterator = path.iterator();
		System.out.println(" - iterator: " +iterator);
		while(iterator.hasNext()) {
			Path p = iterator.next();
			System.out.println("\t+ "+ path.getFileName());
		}
		
		System.out.println(" - toString: " +path.toString());
		System.out.println(" - toUri: " +path.toUri());
		System.out.println(" - toUri path: " +path.toUri().getPath());
		System.out.println(" - toUri host: " +path.toUri().getHost());
		System.out.println(" - toUri fragment: " +path.toUri().getFragment());
		System.out.println(" - toUri query: " +path.toUri().getQuery());
		System.out.println(" - toFile: "+ path.toFile());
		Path root = path.getRoot();
		System.out.println(" - getRoot: " + root);
		Path path2 = Paths.get("C:/app/");
		System.out.println(" - nomalize: "+ path2.normalize());
		int result = path.compareTo(path);
		System.out.println(" - compareTo: " +result);
		int result2 = path2.compareTo(path);
		System.out.println(" - compareTo: " +result2);
		int result3 = path.compareTo(path2);
		System.out.println(" - compareTo: " +result3);
		
		FileSystem fs = path.getFileSystem();
		
		System.out.println();
	}
}
