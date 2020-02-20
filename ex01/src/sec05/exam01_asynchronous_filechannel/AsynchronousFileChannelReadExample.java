package sec05.exam01_asynchronous_filechannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsynchronousFileChannelReadExample {
	
	// �񵿱�(Async) ����� ����ä���� �̿��� ���� �б� ����
	public static void main(String[] args) throws Exception {
		//-------------------------------------------------------//
		// 1. ������Ǯ(ExecutorService) ����
		//-------------------------------------------------------//
		ExecutorService executorService = Executors.newFixedThreadPool(
			// ������ ���ļ� Ȯ���� ����, �� PC�� CPU �ھ� ������ŭ��, �ִ��
			// �����带 �����ϵ���, ������ Ǯ ����
			Runtime.getRuntime().availableProcessors() 
		); // ������ Ǯ ��ü ����.
		
		
		//-------------------------------------------------------//
		// 2. �� 10���� ������ �а�, 
		//-------------------------------------------------------//
		for(int i=0; i<10; i++) {
			Path path = Paths.get("C:/Temp/file" + i + ".txt");

			//-------------------------------------------------------//
			// 2-1. �񵿱� ���� ä�� ����
			//-------------------------------------------------------//
			AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(
				path, 
				EnumSet.of(StandardOpenOption.READ),
				executorService
			);

			//-------------------------------------------------------//
			// 2-2. ByteBuffer ���� (Non-direct Buffer)
			//-------------------------------------------------------//
			ByteBuffer byteBuffer = 
					ByteBuffer.allocate(
						(int) fileChannel.size()	// ����ä���� ����, ����ũ�⸦ ����
					); // allocate

			//-------------------------------------------------------//
			// 2-3. ������Ǯ�� Task �� WorkerThread�� ó�� �����, ȣ���ϴ�
			//      Callback ��ü�� ������ ÷�ΰ�ü�� ����� ���� Ŭ���� ����
			//-------------------------------------------------------//
			class Attachment {
				Path path;								// ���ϰ������				
				AsynchronousFileChannel fileChannel;	// �񵿱�����ä������
				ByteBuffer byteBuffer;					// NIO Buffer ����
				
				
			} // end class

			//-------------------------------------------------------//
			// 2-4. ������Ǯ�� Task �� WorkerThread�� ó�� �����, ȣ���ϴ�
			//      Callback ��ü�� ������ ÷�ΰ�ü ����.
			//
			//      ����, �۾������ Callback �޼ҵ忡 ������ �߰������� ���ٸ�,
			//      ���� ÷��Ŭ���� ���� �ʿ����, �ܼ���, Void ����Ÿ������
			//      ÷�ΰ�ü �Ű������� Ÿ���� �����ϸ� ��.
			//-------------------------------------------------------//
			Attachment attachment = new Attachment();
			
			attachment.path = path;
			attachment.fileChannel = fileChannel;
			attachment.byteBuffer = byteBuffer;

			//-------------------------------------------------------//
			// 2-5. ������Ǯ�� Task �� WorkerThread�� ó�� �����, ȣ���ϴ�
			//      Callback ��ü ����.
			//-------------------------------------------------------//
			//      CompletionHandler<R, A> : Generic Interface
			//-------------------------------------------------------//	
			// ���� �߰��� ������ ÷�ΰ�ü�� �ʿ������, �Ʒ��� ���� Void Ÿ������
			// ��üŸ�� �����ϸ� ��.
			// CompletionHandler<Integer, Void> completionHandlernew
			//-------------------------------------------------------//	
			CompletionHandler<Integer, Attachment> 
				completionHandlernew = 
					new CompletionHandler<Integer, Attachment>() {
				
				// �񵿱� Task�� WorkerThread�� ���������� ó���ÿ� ȣ���ϴ� Callback
				// �޼ҵ� ����
				@Override
				public void completed(Integer result, Attachment attachment) {
					// �񵿱� ����ä���� ����, �ش����Ϸκ��� ������ ���� �����͸�
					// �����ϰ� �ִ� ByteBuffer�κ���, ������ �����͸� �����ϱ� ����
					// ByteBuffer.flip() �޼ҵ� ���� (���۸� �б���� ��ȯ��Ŵ)
					attachment.byteBuffer.flip();
					
					// ������ ������ ���ڱ���� �ؽ� �����̹Ƿ�,�Ʒ��� ���� ��ȯ�ϱ� ����
					// Charset ��ü �ʿ�: ByteBuffer -> CharBuffer -> String
					
					// (1) Charset ��ü ����
					Charset charset = Charset.defaultCharset();

					// (2) Charset ��ü�� �̿��Ͽ�, ByteBuffer --> String ���� ����ȯ
					String data = charset.decode(attachment.byteBuffer).toString();
					
					// (3) ÷�ΰ�ü�� �ʵ���, Path ��ü�� �̿��Ͽ�, �������� �Բ� ���
					System.out.println(
						attachment.path.getFileName() + 
						" : " + 
						data + 
						" : " + 
						// ���� task�� ó���� ������ �̸��� ����ϱ� ���� �ڵ�
						// Thread.currentThread() : ���� �� �ڵ带 �����ϴ� ������ ��ü��
						// ������ ��ȯ�ϴ� �޼ҵ�, Thread.getName(): ������ �̸� ���
						Thread.currentThread().getName()); 
					
					// (4) �ش� �������� ó���� ��� �Ϸ�Ǿ����Ƿ�, �񵿱� ����ä�ΰ�ü��
					//     close �Ͽ�, �ڿ��ݳ� ����: �׷���.............
					//     ���� ���⼭ �񵿱� ����ä�� ��ü�� close �ϴ°� �������???
					try { fileChannel.close(); } 
					catch (IOException e) {;;} // try-catch
					
				} // completed

				// �񵿱� Task�� WorkerThread�� ���ܹ߻�(����) �ÿ� ȣ���ϴ� Callback
				// �޼ҵ� ����
				@Override
				public void failed(Throwable exc, Attachment attachment) {
					exc.printStackTrace();
					
					// �ش� �������� ó���� ��� �Ϸ�Ǿ����Ƿ�, 
					// �񵿱� ����ä�ΰ�ü�� close �Ͽ�, �ڿ��ݳ� ����: �׷���.............
					// ���� ���⼭ �񵿱� ����ä�� ��ü�� close �ϴ°� �������???
					try { fileChannel.close(); } 
					catch (IOException e) {;;} // try-catch
				} // failed
				
			}; // Callback ��ü�� �����ϱ� ����, �͸�����ü �����ڵ�
			

			//-------------------------------------------------------//	
			// 3. �񵿱� ����ä�ΰ� ForJoin Thread Pool, Callback ��ü,
			//    ÷�ΰ�ü�� �̿��� �񵿱� ���� READ ����
			//-------------------------------------------------------//	
			fileChannel.
				read(
					byteBuffer,				// NIO Buffer to read from file. 
					0, 						// offset to start reading from Buffer.
					attachment, 			// Attached object for Callback obj. 
					completionHandlernew	// Callback object to handle task
				); // read
			
		} // for: file0.txt ~ file9.txt �� ������ �����ϴ� ������ �����͸� ���������� �б�
		

		//-------------------------------------------------------//	
		// 4. ForkJoin Pool �ڿ��ݳ�(��� �񵿱� ó���� ���� �Ŀ�...)
		//    �׷���, �����ڰ�, �񵿱� ����ä���� READ/WRITE task��
		//    ��� ����Ǿ����� ��� �˰�, ���� ������ Ǯ�� �����Ϸ� �ϴ°ɱ��???? 
		//-------------------------------------------------------//	
		Thread.sleep(1000);		// *** �߿� ***
		executorService.shutdown();
		
	} // main
	
} // end class
