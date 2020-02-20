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
	
	// 비동기(Async) 방식의 파일채널을 이용한 파일 읽기 예제
	public static void main(String[] args) throws Exception {
		//-------------------------------------------------------//
		// 1. 스레드풀(ExecutorService) 생성
		//-------------------------------------------------------//
		ExecutorService executorService = Executors.newFixedThreadPool(
			// 진정한 병렬성 확보를 위해, 현 PC의 CPU 코어 개수만큼만, 최대로
			// 스레드를 생성하도록, 스레드 풀 생성
			Runtime.getRuntime().availableProcessors() 
		); // 스레드 풀 객체 생성.
		
		
		//-------------------------------------------------------//
		// 2. 총 10개의 파일을 읽고, 
		//-------------------------------------------------------//
		for(int i=0; i<10; i++) {
			Path path = Paths.get("C:/Temp/file" + i + ".txt");

			//-------------------------------------------------------//
			// 2-1. 비동기 파일 채널 생성
			//-------------------------------------------------------//
			AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(
				path, 
				EnumSet.of(StandardOpenOption.READ),
				executorService
			);

			//-------------------------------------------------------//
			// 2-2. ByteBuffer 생성 (Non-direct Buffer)
			//-------------------------------------------------------//
			ByteBuffer byteBuffer = 
					ByteBuffer.allocate(
						(int) fileChannel.size()	// 파일채널을 통해, 파일크기를 얻음
					); // allocate

			//-------------------------------------------------------//
			// 2-3. 스레드풀의 Task 를 WorkerThread가 처리 종료시, 호출하는
			//      Callback 객체에 전달할 첨부객체를 만들기 위한 클래스 선언
			//-------------------------------------------------------//
			class Attachment {
				Path path;								// 파일경로저장				
				AsynchronousFileChannel fileChannel;	// 비동기파일채널저장
				ByteBuffer byteBuffer;					// NIO Buffer 저장
				
				
			} // end class

			//-------------------------------------------------------//
			// 2-4. 스레드풀의 Task 를 WorkerThread가 처리 종료시, 호출하는
			//      Callback 객체에 전달할 첨부객체 생성.
			//
			//      만일, 작업종료시 Callback 메소드에 전달할 추가정보가 없다면,
			//      위의 첨부클래스 선언도 필요없고, 단순히, Void 참조타입으로
			//      첨부객체 매개변수의 타입을 선언하면 됨.
			//-------------------------------------------------------//
			Attachment attachment = new Attachment();
			
			attachment.path = path;
			attachment.fileChannel = fileChannel;
			attachment.byteBuffer = byteBuffer;

			//-------------------------------------------------------//
			// 2-5. 스레드풀의 Task 를 WorkerThread가 처리 종료시, 호출하는
			//      Callback 객체 생성.
			//-------------------------------------------------------//
			//      CompletionHandler<R, A> : Generic Interface
			//-------------------------------------------------------//	
			// 만일 추가로 전달할 첨부객체가 필요없으면, 아래와 같이 Void 타입으로
			// 구체타입 지정하면 됨.
			// CompletionHandler<Integer, Void> completionHandlernew
			//-------------------------------------------------------//	
			CompletionHandler<Integer, Attachment> 
				completionHandlernew = 
					new CompletionHandler<Integer, Attachment>() {
				
				// 비동기 Task를 WorkerThread가 성공적으로 처리시에 호출하는 Callback
				// 메소드 선언
				@Override
				public void completed(Integer result, Attachment attachment) {
					// 비동기 파일채널을 통해, 해당파일로부터 읽혀진 파일 데이터를
					// 저장하고 있는 ByteBuffer로부터, 파일의 데이터를 복원하기 위해
					// ByteBuffer.flip() 메소드 수행 (버퍼를 읽기모드로 전환시킴)
					attachment.byteBuffer.flip();
					
					// 파일의 형식이 문자기반의 텍스 파일이므로,아래와 같이 변환하기 위한
					// Charset 객체 필요: ByteBuffer -> CharBuffer -> String
					
					// (1) Charset 객체 생성
					Charset charset = Charset.defaultCharset();

					// (2) Charset 객체를 이용하여, ByteBuffer --> String 으로 역변환
					String data = charset.decode(attachment.byteBuffer).toString();
					
					// (3) 첨부객체의 필드중, Path 객체를 이용하여, 각종정보 함께 출력
					System.out.println(
						attachment.path.getFileName() + 
						" : " + 
						data + 
						" : " + 
						// 실제 task를 처리한 스레드 이름도 출력하기 위한 코드
						// Thread.currentThread() : 현재 이 코드를 수행하는 스레드 객체의
						// 참조를 반환하는 메소드, Thread.getName(): 스레드 이름 얻기
						Thread.currentThread().getName()); 
					
					// (4) 해당 스레드의 처리가 모두 완료되었으므로, 비동기 파일채널객체를
					//     close 하여, 자원반납 수행: 그런데.............
					//     과연 여기서 비동기 파일채널 객체를 close 하는게 맞을까요???
					try { fileChannel.close(); } 
					catch (IOException e) {;;} // try-catch
					
				} // completed

				// 비동기 Task를 WorkerThread가 예외발생(실패) 시에 호출하는 Callback
				// 메소드 선언
				@Override
				public void failed(Throwable exc, Attachment attachment) {
					exc.printStackTrace();
					
					// 해당 스레드의 처리가 모두 완료되었으므로, 
					// 비동기 파일채널객체를 close 하여, 자원반납 수행: 그런데.............
					// 과연 여기서 비동기 파일채널 객체를 close 하는게 맞을까요???
					try { fileChannel.close(); } 
					catch (IOException e) {;;} // try-catch
				} // failed
				
			}; // Callback 객체를 생성하기 위한, 익명구현객체 생성코드
			

			//-------------------------------------------------------//	
			// 3. 비동기 파일채널과 ForJoin Thread Pool, Callback 객체,
			//    첨부객체를 이용한 비동기 파일 READ 수행
			//-------------------------------------------------------//	
			fileChannel.
				read(
					byteBuffer,				// NIO Buffer to read from file. 
					0, 						// offset to start reading from Buffer.
					attachment, 			// Attached object for Callback obj. 
					completionHandlernew	// Callback object to handle task
				); // read
			
		} // for: file0.txt ~ file9.txt 의 기존에 존재하는 파일의 데이터를 순차적으로 읽기
		

		//-------------------------------------------------------//	
		// 4. ForkJoin Pool 자원반납(모든 비동기 처리가 끝난 후에...)
		//    그런데, 개발자가, 비동기 파일채널의 READ/WRITE task가
		//    모두 종료되었는지 어떻게 알고선, 지금 스레드 풀을 종료하려 하는걸까요???? 
		//-------------------------------------------------------//	
		Thread.sleep(1000);		// *** 중요 ***
		executorService.shutdown();
		
	} // main
	
} // end class
