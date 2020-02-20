package fileChannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsynchronousFileChannelReadEx {

//	public static void main(String[] args) throws IOException {
//		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//		
//		
//		//for(int i=0; i<10; i++) {
//			
//		Path path =Paths.get("C:/app/file.txt");
//		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path,EnumSet.of(StandardOpenOption.READ),executorService);
//		//Files.createDirectories(path.getParent());
//		ByteBuffer byteBuffer = ByteBuffer.allocate((int)fileChannel.size());
//		//}
//		
//		
//		//AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path,
//		
//				//(OpenOption) EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE));
//		Charset charset = Charset.defaultCharset();
//		//ByteBuffer byteBuffer = charset.encode("救崇窍技夸");
//		
//		class Attachment{
//			Path path;
//			AsynchronousFileChannel fileChannel;
//		}
//		
//		Attachment attachment = new Attachment();
//		attachment.path = path;
//		attachment.fileChannel = fileChannel;
//		
//		CompletionHandler<Integer, Attachment> completionHandler = new CompletionHandler<Integer, Attachment>(){
//
//			@Override
//			public void completed(Integer result, Attachment attachment) {
//				
//				attachment.byteBuffer.flip();
//				Charset charset = Charset.defaultCharset();
//				String data = charset.decode(attachment.byteBuffer).toString();
//				System.out.println(attachment.path.getFileName()+ " + "
//						+result
//						+" bytes written : "
//						+Thread.currentThread().getName()
//						
//						);
//				try {
//					attachment.fileChannel.close();
//					
//				}catch(IOException e) {
//					;;
//				}
//				
//				
//				
//			}
//
//			@Override
//			public void failed(Throwable exc, Attachment attachment) {
//				exc.printStackTrace();
//				try {attachment.fileChannel.close();}catch(IOException e) {
//					;;
//				}
//				
//				
//			}//劳疙 备泅 按眉 内爹规侥- Callback 按眉 积己
//		};
//			fileChannel.write(byteBuffer, 0 , attachment, completionHandler);
//			
//		}
		
		
		
	
	
	
}


