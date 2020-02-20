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
	

	// 비동기(Async) 방식의 파일채널을 이용한 파일 쓰기 예제
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
		// 2. 총 10개의 파일을 생성 및 쓰기 
		//-------------------------------------------------------//
		for(int i=0; i<10; i++) {
			//-------------------------------------------------------//
			// 2-1. 각 파일 경로에 대한 Path 객체 생성
			//-------------------------------------------------------//
			Path path = Paths.get("C:/Temp/file" + i + ".txt");
			
			// 경로상, 존재하지 않는 폴더가 있다면, 자동으로 생성해줌
			Files.createDirectories(path.getParent());

			//-------------------------------------------------------//
			//**** 2-2. 비동기 파일 채널 생성
			//-------------------------------------------------------//
			AsynchronousFileChannel fileChannel = 
				AsynchronousFileChannel.open(	// 역시, 기본생성방식인 open() 사용
					path, 	// 파일경로 지정
					
					// 파일 열기 옵션들 지정
					EnumSet.of(
						StandardOpenOption.CREATE,	// 파일 생성모드
						StandardOpenOption.WRITE	// 파일 쓰기모드
					),
					
					executorService					// 스레드 풀 지정
			); // 비동기 파일채널 객체 생성. 

			//-------------------------------------------------------//
			//**** 2-3. String 데이터를 ByteBuffer 객체로 변환
			//          : String -> ByteBuffer using Charset 객체
			//-------------------------------------------------------//
			Charset charset = Charset.defaultCharset();	// OS 문자셋 따름
			
			// String -> ByteBuffer 객체로 변환 수행(인코딩, encoding)
			ByteBuffer byteBuffer = charset.encode("안녕하세요");


			//-------------------------------------------------------//
			//**** 3. ForkJoin 스레드풀을 이용한, 비동기 파일채널 작업의 결과를
			//        통보받을 수 있도록(즉, 콜백, Callback), Callback 객체 생성
			//-------------------------------------------------------//

			//-------------------------------------------------------//
			// 3-1. 첨부 객체 생성 (첨부객체란, 스레드 풀의 WorkerThread가 READ/WRITE
			//      작업 처리 완료시, 호출하는 Callback 객체의 메소드에 추가로 전달할
			//      데이터가 필요시, 만들어 주는 객체임.
			//-------------------------------------------------------//
			class Attachment {
				
				Path path;		// 지정된 파일 경로 Path
				AsynchronousFileChannel fileChannel;	// 생성한 파일채널객체
				
				
			} // end inner class (로컬 Class 선언)

			//-------------------------------------------------------//
			// 3-2. 첨부파일 객체 생성
			//-------------------------------------------------------//
			Attachment attachment = new Attachment();
			attachment.path = path;		// 파일경로 저장
			attachment.fileChannel = fileChannel; // 비동기 파일채널객체 저장

			//-------------------------------------------------------//
			// 3-3. Callback 객체 생성
			//-------------------------------------------------------//
			// 이 Callback 객체는, 자바표준API로 제공되는 CompletionHandler
			// 라는 인터페이스를 구현한 객체임.
			//-------------------------------------------------------//
			
			// CompletionHandler<R, A> 인터페이스의 구현객체 생성
			// 여기서, R 타입 파라미터: 작업처리결과의 타입
			//        A 타입 파라미터: 첨부객체의 타입
			CompletionHandler<Integer, Attachment> completionHandler = 
				new CompletionHandler<Integer, Attachment>() {
				
				// 스레드 풀의 WorkerThread가 READ/WRITE 작업처리 성공시,
				// 호출하는 Callback Method.
				//  	- Integer result 매개변수: 처리성공시, 처리결과값 전달
				//		- Attachment attachment: 실패로직 수행시, 필요한 첨부객체전달
				@Override
				public void completed(Integer result, Attachment attachment) {
					System.out.println(
						attachment.path.getFileName() + 
						" : " + 
						result + 
						" bytes written : " + 
						Thread.currentThread().getName()
					);
					
					// READ/WRITE 작업이 완료된 싯점에, 파일채널객체 종료(close)
					// 하여, 자원반납 수행
					try { attachment.fileChannel.close(); } 
					catch (IOException e) {;;} // try-catch
				} // completed

				// 스레드 풀의 WorkerThread가 READ/WRITE 작업처리 실패시,
				// 호출하는 Callback Method.
				//  	- Throwable exc 매개변수: 처리실패시 발생한 예외객체 전달
				//		- Attachment attachment: 실패로직 수행시, 필요한 첨부객체전달
				@Override
				public void failed(Throwable exc, Attachment attachment) {
					exc.printStackTrace();
					
					// READ/WRITE 작업이 완료된 싯점에, 파일채널객체 종료(close)
					// 하여, 자원반납 수행
					try { attachment.fileChannel.close(); } 
					catch (IOException e) {;;}					
				} // failed
				
			}; // 익명구현객체 코딩 방식 - Callback 객체 생성.
			

			//-------------------------------------------------------//
			// 4. 파일채널객체의 READ/WRITE 메소드 호출 작업을, 비동기방식 답게
			//    스레드 풀의 WorkerThread에 위임(Delegation) 시킴.
			//-------------------------------------------------------//
			fileChannel.
				write(					// 파일에 쓰기작업 수행
					byteBuffer, 		// 파일에 쓸 데이터 저장 버퍼객체 전달
					0, 					// 쓰기를 수행할 버퍼의 첫번째 인덱스(offset)
					attachment, 		// Callback method호출시, 전달할 첨부객체
					completionHandler	// 작업처리완료(실패)시, 호출할 Callback객체
				);	// 스레드 풀의 Job Q에 쓰기 작업 put 하여, WorkerThread에 
					// 작업처리 위임
			
		} // for

		
		//-------------------------------------------------------//
		// 5. 스레드풀 종료 (자원반납)
		//-------------------------------------------------------//
		executorService.shutdown();
	} // main
	
} // end class
