package fileChannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileChannelWriteEx {

	public static void main(String[] args) throws IOException {
	
		Path path = Paths.get("C:/app/file.txt");
		
		Files.createDirectories(path.getParent());
		
		FileChannel fileChannel = FileChannel.open(path, 		StandardOpenOption.CREATE,StandardOpenOption.WRITE);
		
		String data= "æ»≥Á«œººø‰";
		
		Charset charset = Charset.defaultCharset();
		
		ByteBuffer byteBuffer = charset.encode(data);
		System.out.println("- byteBuffer: "+ byteBuffer);
		 
		int byteCount = fileChannel.write(byteBuffer);
		System.out.println("file.txt: " +byteCount+ " bytes written");
		
		fileChannel.close();
		
		
	}
}
