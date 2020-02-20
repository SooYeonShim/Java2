package sec05.exam01_asynchronous_filechannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsynchronousFileChannelWriteExample {
	

	// �񵿱�(Async) ����� ����ä���� �̿��� ���� ���� ����
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
		// 2. �� 10���� ������ ���� �� ���� 
		//-------------------------------------------------------//
		for(int i=0; i<10; i++) {
			//-------------------------------------------------------//
			// 2-1. �� ���� ��ο� ���� Path ��ü ����
			//-------------------------------------------------------//
			Path path = Paths.get("C:/Temp/file" + i + ".txt");
			
			// ��λ�, �������� �ʴ� ������ �ִٸ�, �ڵ����� ��������
			Files.createDirectories(path.getParent());

			//-------------------------------------------------------//
			//**** 2-2. �񵿱� ���� ä�� ����
			//-------------------------------------------------------//
			AsynchronousFileChannel fileChannel = 
				AsynchronousFileChannel.open(	// ����, �⺻��������� open() ���
					path, 	// ���ϰ�� ����
					
					// ���� ���� �ɼǵ� ����
					EnumSet.of(
						StandardOpenOption.CREATE,	// ���� �������
						StandardOpenOption.WRITE	// ���� ������
					),
					
					executorService					// ������ Ǯ ����
			); // �񵿱� ����ä�� ��ü ����. 

			//-------------------------------------------------------//
			//**** 2-3. String �����͸� ByteBuffer ��ü�� ��ȯ
			//          : String -> ByteBuffer using Charset ��ü
			//-------------------------------------------------------//
			Charset charset = Charset.defaultCharset();	// OS ���ڼ� ����
			
			// String -> ByteBuffer ��ü�� ��ȯ ����(���ڵ�, encoding)
			ByteBuffer byteBuffer = charset.encode("�ȳ��ϼ���");


			//-------------------------------------------------------//
			//**** 3. ForkJoin ������Ǯ�� �̿���, �񵿱� ����ä�� �۾��� �����
			//        �뺸���� �� �ֵ���(��, �ݹ�, Callback), Callback ��ü ����
			//-------------------------------------------------------//

			//-------------------------------------------------------//
			// 3-1. ÷�� ��ü ���� (÷�ΰ�ü��, ������ Ǯ�� WorkerThread�� READ/WRITE
			//      �۾� ó�� �Ϸ��, ȣ���ϴ� Callback ��ü�� �޼ҵ忡 �߰��� ������
			//      �����Ͱ� �ʿ��, ����� �ִ� ��ü��.
			//-------------------------------------------------------//
			class Attachment {
				
				Path path;		// ������ ���� ��� Path
				AsynchronousFileChannel fileChannel;	// ������ ����ä�ΰ�ü
				
				
			} // end inner class (���� Class ����)

			//-------------------------------------------------------//
			// 3-2. ÷������ ��ü ����
			//-------------------------------------------------------//
			Attachment attachment = new Attachment();
			attachment.path = path;		// ���ϰ�� ����
			attachment.fileChannel = fileChannel; // �񵿱� ����ä�ΰ�ü ����

			//-------------------------------------------------------//
			// 3-3. Callback ��ü ����
			//-------------------------------------------------------//
			// �� Callback ��ü��, �ڹ�ǥ��API�� �����Ǵ� CompletionHandler
			// ��� �������̽��� ������ ��ü��.
			//-------------------------------------------------------//
			
			// CompletionHandler<R, A> �������̽��� ������ü ����
			// ���⼭, R Ÿ�� �Ķ����: �۾�ó������� Ÿ��
			//        A Ÿ�� �Ķ����: ÷�ΰ�ü�� Ÿ��
			CompletionHandler<Integer, Attachment> completionHandler = 
				new CompletionHandler<Integer, Attachment>() {
				
				// ������ Ǯ�� WorkerThread�� READ/WRITE �۾�ó�� ������,
				// ȣ���ϴ� Callback Method.
				//  	- Integer result �Ű�����: ó��������, ó������� ����
				//		- Attachment attachment: ���з��� �����, �ʿ��� ÷�ΰ�ü����
				@Override
				public void completed(Integer result, Attachment attachment) {
					System.out.println(
						attachment.path.getFileName() + 
						" : " + 
						result + 
						" bytes written : " + 
						Thread.currentThread().getName()
					);
					
					// READ/WRITE �۾��� �Ϸ�� ������, ����ä�ΰ�ü ����(close)
					// �Ͽ�, �ڿ��ݳ� ����
					try { attachment.fileChannel.close(); } 
					catch (IOException e) {;;} // try-catch
				} // completed

				// ������ Ǯ�� WorkerThread�� READ/WRITE �۾�ó�� ���н�,
				// ȣ���ϴ� Callback Method.
				//  	- Throwable exc �Ű�����: ó�����н� �߻��� ���ܰ�ü ����
				//		- Attachment attachment: ���з��� �����, �ʿ��� ÷�ΰ�ü����
				@Override
				public void failed(Throwable exc, Attachment attachment) {
					exc.printStackTrace();
					
					// READ/WRITE �۾��� �Ϸ�� ������, ����ä�ΰ�ü ����(close)
					// �Ͽ�, �ڿ��ݳ� ����
					try { attachment.fileChannel.close(); } 
					catch (IOException e) {;;}					
				} // failed
				
			}; // �͸�����ü �ڵ� ��� - Callback ��ü ����.
			

			//-------------------------------------------------------//
			// 4. ����ä�ΰ�ü�� READ/WRITE �޼ҵ� ȣ�� �۾���, �񵿱��� ���
			//    ������ Ǯ�� WorkerThread�� ����(Delegation) ��Ŵ.
			//-------------------------------------------------------//
			fileChannel.
				write(					// ���Ͽ� �����۾� ����
					byteBuffer, 		// ���Ͽ� �� ������ ���� ���۰�ü ����
					0, 					// ���⸦ ������ ������ ù��° �ε���(offset)
					attachment, 		// Callback methodȣ���, ������ ÷�ΰ�ü
					completionHandler	// �۾�ó���Ϸ�(����)��, ȣ���� Callback��ü
				);	// ������ Ǯ�� Job Q�� ���� �۾� put �Ͽ�, WorkerThread�� 
					// �۾�ó�� ����
			
		} // for

		
		//-------------------------------------------------------//
		// 5. ������Ǯ ���� (�ڿ��ݳ�)
		//-------------------------------------------------------//
		executorService.shutdown();
	} // main
	
} // end class
