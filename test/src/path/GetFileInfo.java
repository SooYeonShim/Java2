package path;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GetFileInfo {

	
	public static void main(String[] args) throws IOException, URISyntaxException{
		Path path= Paths.get("C:/app/´º¿å ´º¿å.mp3");
		Path path2= Paths.get("C:/app/jQuery/ajax.htm");
		Path path3= Paths.get("C:/app/´º¿å ´º¿å.mp3");
		Path path4= Paths.get("C:/app/´º¿å ´º¿å.mp3");
		Path path5= Paths.get("C:/app/´º¿å ´º¿å.mp3");
		Path path6= Paths.get("C:/app/´º¿å ´º¿å.mp3");
		Path path7= Paths.get("C:/app/´º¿å ´º¿å.mp3");
		Path path8= Paths.get("C:/app/´º¿å ´º¿å.mp3");
		
		System.out.println("-path:" + path);
		System.out.println("-path Class:" + path.getClass().getName());
		System.out.println("-isDirectory:" + Files.isDirectory(path));
		System.out.println("-isRegularFile:" + Files.isRegularFile(path));
		System.out.println("-getLastModifiedTime:" + Files.isRegularFile(path));
		System.out.println("-exists:" + Files.exists(path));
		System.out.println("-probeContentType:" + Files.probeContentType(path4));
		
		
	}
	
	
}
