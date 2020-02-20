package test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

public class PathEx {

	public static void main(String[] args) throws IOException {
		Path from= Paths.get("C:/app/´º¿å ´º¿å.mp3");
		Path to1 = Paths.get("C:/app/´º¿å ´º¿å-1.mp3");
		Path to3 = Paths.get("C:/app/´º¿å ´º¿å-2.mp3");
		//int capacity= 100;
		long size =Files.size(from);
		FileChannel fileChannel_from = FileChannel.open(from);
		FileChannel fileChannel_to1= FileChannel.open(to1, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE));
		
		ByteBuffer ndb = ByteBuffer.allocate((int) size);
		ByteBuffer db = ByteBuffer.allocate((int) size);
		long start,  end;
		
		start = System.nanoTime();
		
		for(int i=0; i<100; i++) {
			fileChannel_from.read(ndb);
			ndb.flip();
			fileChannel_to1.read(ndb);
			ndb.clear();
		}
		end = System.nanoTime();
		
		System.out.println("½Ã°£: " +(end-start));
		start = System.nanoTime();
		
		for(int i=0; i<100; i++) {
			fileChannel_from.read(db);
			db.flip();
			fileChannel_to1.read(db);
			db.clear();
		}
		end = System.nanoTime();
		
		System.out.println("½Ã°£: " +(end-start));
	}
}
