package sec06.exam03_chatting;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class ServerExample 
	extends Application {
	
	// 1. ForkJoin Pool 객체를 저장할 필드
	ExecutorService executorService;
	
	// 2. 서버소켓채널객체를 저장할 필드
	ServerSocketChannel serverSocketChannel;
	
	// 3. 모든 연결된 클라이언트 객체를 저장할 List 객체(Vector사용)
	List<Client> connections = new Vector<Client>();	
	
	
	//--------------------------------------------------
	// 4. 서버와 연결된 클라이언트를 객체로 모델링한 클래스 선언
	//--------------------------------------------------
	class Client {
		// 통신용 소켓채널객체를 저장할 필드
		SocketChannel socketChannel;
		
		
		// 생성자
		Client(SocketChannel socketChannel) {
			this.socketChannel = socketChannel;
			
			receive();		// READ 수행 : 모든 클라이언트는 생성되고나서 바로 READ수행
		} // constructor
		
		// 데이터 수신 메소드
		void receive() {
			
			// ForkJoin Pool에 작업을 위임시킬 task 생성(익명구현객체코딩 방식으로 생성)
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() { // 스레드풀의 WorkerThread가 구동시킬 메소드
					
					while(true) { // 무한루프 수행
						
						try {
							// 100바이트 크기의 Non-direct 버퍼 객체 생성
							ByteBuffer byteBuffer = ByteBuffer.allocate(100);
							
							// 클라이언트가 비정상 종료를 했을 경우 IOException 발생
							int readByteCount = socketChannel.read(byteBuffer);
							
							//클라이언트가 정상적으로 SocketChannel의 close()를 호출했을 경우
							if(readByteCount == -1) { 
								throw new IOException();	// 강제로 예외발생시킴
							} // if
							
							// 출력메시지 생성
							String message = 
								"[요청 처리: " + 
							    socketChannel.getRemoteAddress() + 
							    ": " + 
							    Thread.currentThread().getName() + 
							    "]";
							
							// JavaFX code : 이 주석이 있는 코드는 그냥 입력하세요!!
							Platform.runLater(()->displayText(message));
							
							// 읽기모드로 버퍼 준비
							byteBuffer.flip();
							
							// 받은 데이터를 String으로 역변환 수행
							// 즉, ByteBuffer -> CharBuffer -> String 역변환
							Charset charset = Charset.forName("UTF-8");
							String data = charset.decode(byteBuffer).toString();
							
							// List<Client> 객체 안에 들어있는 모든 클라이언트에게
							// 역변환하여 받은 수신 데이터를 다시 전송함!!
							for(Client client : connections) {
								client.send(data); 
							} // enhanced for
							
						} catch(Exception e) { // 예외처리: 채널통신관련
							
							try { // 예외가 발생한 클라이언트를 List<Client>에서 삭제
								connections.remove(Client.this);
								
								String message = 
									"[클라이언트 통신 안됨: " + 
									socketChannel.getRemoteAddress() + 
									": " + 
									Thread.currentThread().getName() + 
									"]";
								
								// JavaFX code : 이 주석이 있는 코드는 그냥 입력하세요!!
								Platform.runLater(()->displayText(message));
								
								// 통신용 소켓채널객체 닫기(자원반납)
								socketChannel.close();
							} catch (IOException e2) {;;}
							
							break; // 무한루프 빠져나감
						} // outer try-catch
						
					} // while
					
				} // run
				
			}; // 익명구현객체코딩방식으로, ForkJoin Pool의 JobQ에 넣을 task 생성
			
			// 스레드 풀에 task 처리 위임(JobQ에 task 제출: submit)
			executorService.submit(runnable);	// 받은 task를 대신 처리함!(비동기)
		} // run
	
		// 데이터 전송 메소드
		// 전송된 데이터는, 전송 Client를 제외한 나머지 모든 연결된 Client에 전달됨!!
		void send(String data) {
			
			// ForkJoin Pool에 처리를 위임할 task 를, 익명구현객체코딩방식으로 생성
			Runnable runnable = new Runnable() {
				
				
				@Override
				public void run() {
					
					try {
						// String -> ByteBuffer 로 순변환하여, ByteBuffer 전송
						Charset charset = Charset.forName("UTF-8");
						ByteBuffer byteBuffer = charset.encode(data);
						
						// 통신용 소켓채널의 write(ByteBuffer) 메소드로, 상대편에
						// 데이터 전송
						socketChannel.write(byteBuffer);
					} catch(Exception e) { // 각종 예외가 발생하면....
						
						try {
							// 채팅 창(윈도우)에 출력할 메시지 생성
							String message = 
								"[클라이언트 통신 안됨: " + 
								socketChannel.getRemoteAddress() + 
								": " + 
								Thread.currentThread().getName() + "]";

							// JavaFX code : 이 주석이 있는 코드는 그냥 입력하세요!!
							Platform.runLater(()->displayText(message));
							
							// 데이터 전송이 안되는 Client는 List<Client>에서 삭제
							connections.remove(Client.this);
							
							// 통신용 소켓채널객체 종료(자원반납)
							socketChannel.close();
						} catch (IOException e2) {;;} // try-catch
						
					} // try-catch
					
				} // run
				
			}; // 익명구현객체코딩방식으로 task 생성
			
			// 생성한 Runnable task를 ForkJoin Pool에 처리위임하기(제출:submit)
			executorService.submit(runnable);
		} // send
		
	} // end class
	

	//--------------------------------------------------
	// 서버를 스타트 할 때 수행.
	//--------------------------------------------------
	void startServer() {
		System.out.println("---------------------------------------------");
		System.out.println("- ServerExample::startServer() invoked.");
		System.out.println("---------------------------------------------");
		
		//---------------------------------------------//
		// ForkJoin Pool 객체 생성(스레드 풀)
		//---------------------------------------------//
		executorService = Executors.newFixedThreadPool(
			Runtime.getRuntime().availableProcessors()
	    );

		//---------------------------------------------//
		// 서버소켓채널객체 생성 및 바인딩 설정
		//---------------------------------------------//
		try {	
			serverSocketChannel = ServerSocketChannel.open();
			
			serverSocketChannel.configureBlocking(true);
			
			serverSocketChannel.bind(new InetSocketAddress(5001));
		} catch(Exception e) {
			
			if(serverSocketChannel.isOpen()) { 
				stopServer(); 		// 서버를 닫고자 할 때 수행하는 메소드.
			} // if
			
			return;		// task 바로종료 (즉, run() 메소드 바로 종료)
		} // try-catch
		
		//------------------------------------------------
		// 역시, ForkJoin Pool 처리를 위임시킬 task 생성
		Runnable runnable = new Runnable() {
			
			
			@Override
			public void run() {
				
				// JavaFX code
				Platform.runLater(()->{
					displayText("[서버 시작]");
					
					btnStartStop.setText("stop");
				});	
				
				while(true) { // 무한루프
					
					try {
						// 서버소켓채널 Listen(연결대기상태) 수행
						SocketChannel socketChannel = 
								serverSocketChannel.accept();
						
						// 클라이언트 연결이 들어와 수락되면, 
						//   1) 연결수락 메시지를 생성하여, JavaFX window 창에 출력
						String message = "[연결 수락: " + socketChannel.getRemoteAddress()  + ": " + Thread.currentThread().getName() + "]";
						Platform.runLater(()->displayText(message));
		
						//   2) 수락된 클라이언트 정보를 가지는 Client 객체 생성
						Client client = new Client(socketChannel);
						
						//   3) 생성된 Client 객체를 List<Client> 리스트에 add
						connections.add(client);
						
						// JavaFX code
						Platform.runLater(
							// 람다식
							()->displayText("[연결 개수: " + connections.size() + "]")
						); // runLater
						
					} catch (Exception e) { // try블록에서 예외가 발생하면 예외처리 수행으로
						// 서버소켓채널 객체 닫기(자원반납 수행)
						if(serverSocketChannel.isOpen()) { 
							stopServer(); 
						} // if
						
						break; // 무한루프 빠져나감
					} // try-catch
					
				} // while
				
			} // run
			
		}; // 익명구핸객체코딩방식으로, task 객체 생성.
		
		// 생성한 Runnable task를 ForkJoin Pool에 처리 위임시키기(즉, 비동기 처리)
		executorService.submit(runnable);
	} // startServer

	
	//--------------------------------------------------
	// 서버를 정지시킬 때 수행.
	//--------------------------------------------------
	void stopServer() {
		System.out.println("---------------------------------------------");
		System.out.println("- ServerExample::stopServer() invoked.");
		System.out.println("---------------------------------------------");
		
		try {
			// List<Client> 객체로부터, 외부반복자(iterator) 얻어내어,
			// List안의 각 Client 객체 요소에 대해서, 아래 코드 수행
			Iterator<Client> iterator = connections.iterator();
			
			while(iterator.hasNext()) {
				// List의 각 요소(Client) 객체 얻기
				Client client = iterator.next();
				
				// 통신용 소켓채널객체 닫기(자원반납)
				client.socketChannel.close();
				
				// List 안에서 이 요소 제거
				iterator.remove();
			} // while
			
			// 이제 서버소켓채널객체 닫기 (자원반납)
			if(serverSocketChannel!=null && serverSocketChannel.isOpen()) { 
				serverSocketChannel.close();
			} // if
			
			// ForkJoin Pool 닫기 (자원반납)
			if(executorService!=null && !executorService.isShutdown()) { 
				executorService.shutdown(); 
			} // if
			
			// JavaFX code
			Platform.runLater(()->{
				displayText("[서버 멈춤]");
				
				btnStartStop.setText("start");			
			}); // runLater
			
		} catch (Exception e) {;;} // try-catch
		
	} // stopServer
	
	
	//////////////////////////////////////////////////////
	// JavaFX 를 이용하여, 윈도우 UI 생성코드: 그냥 입력해주세요
	//////////////////////////////////////////////////////
	TextArea txtDisplay;
	Button btnStartStop;
	
	
	@Override
	public void start(Stage primaryStage) 
		throws Exception {		
		System.out.println("- ServerExample::start(primaryStage) invoked.");		
		
		BorderPane root = new BorderPane();
		root.setPrefSize(500, 300);
		
		txtDisplay = new TextArea();
		txtDisplay.setEditable(false);
		
		BorderPane.setMargin(txtDisplay, new Insets(0,0,2,0));
		
		root.setCenter(txtDisplay);
		
		btnStartStop = new Button("start");
		btnStartStop.setPrefHeight(30);
		btnStartStop.setMaxWidth(Double.MAX_VALUE);
		
		btnStartStop.setOnAction(e->{
			
			if(btnStartStop.getText().equals("start")) {
				
				startServer();	// 서버시작
				
			} else if(btnStartStop.getText().equals("stop")){
				
				stopServer();	// 서버종료
				
			} // if-else if
			
		}); // setOnAction
		
		root.setBottom(btnStartStop);
		
		Scene scene = new Scene(root);
		
		// CSS 파일 적용
		scene.getStylesheets().add(
			getClass().getResource("app.css").toString()
		);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("****** Chatting Server ******");
		
		primaryStage.setOnCloseRequest(
			event->stopServer()
		);
		
		primaryStage.show();
	} // start
	
	
	// JavaFX code
	void displayText(String text) {	
		System.out.println("- ServerExample::displayText(text) invoked.");		
		
		txtDisplay.appendText(text + "\n");
	} // displayText
	
	
	// Entry Point
	public static void main(String[] args) {
		System.out.println("- ServerExample::main(args) invoked.");		
		
		launch(args);	// JavaFX Application 구동시킴(시작점)
	} // main
	 
} // end class
