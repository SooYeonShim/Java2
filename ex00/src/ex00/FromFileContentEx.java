package ex00;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FromFileContentEx {
	public static void main(String[] args) throws Exception{
		Path path= Paths.get("D:/test.txt");
		Stream<String> stream;
		
		stream =Files.lines(path,Charset.defaultCharset());
		
		stream.forEach(System.out :: println);
		stream.close();
		System.out.println();
		
		File file = path.toFile();
		FileReader fileReader = new FileReader(file);
		BufferedReader br = new BufferedReader(fileReader);
		stream= br.lines();
		
		stream.close();
	}
}
