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
	
	// 1. ForkJoin Pool ��ü�� ������ �ʵ�
	ExecutorService executorService;
	
	// 2. ��������ä�ΰ�ü�� ������ �ʵ�
	ServerSocketChannel serverSocketChannel;
	
	// 3. ��� ����� Ŭ���̾�Ʈ ��ü�� ������ List ��ü(Vector���)
	List<Client> connections = new Vector<Client>();	
	
	
	//--------------------------------------------------
	// 4. ������ ����� Ŭ���̾�Ʈ�� ��ü�� �𵨸��� Ŭ���� ����
	//--------------------------------------------------
	class Client {
		// ��ſ� ����ä�ΰ�ü�� ������ �ʵ�
		SocketChannel socketChannel;
		
		
		// ������
		Client(SocketChannel socketChannel) {
			this.socketChannel = socketChannel;
			
			receive();		// READ ���� : ��� Ŭ���̾�Ʈ�� �����ǰ��� �ٷ� READ����
		} // constructor
		
		// ������ ���� �޼ҵ�
		void receive() {
			
			// ForkJoin Pool�� �۾��� ���ӽ�ų task ����(�͸�����ü�ڵ� ������� ����)
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() { // ������Ǯ�� WorkerThread�� ������ų �޼ҵ�
					
					while(true) { // ���ѷ��� ����
						
						try {
							// 100����Ʈ ũ���� Non-direct ���� ��ü ����
							ByteBuffer byteBuffer = ByteBuffer.allocate(100);
							
							// Ŭ���̾�Ʈ�� ������ ���Ḧ ���� ��� IOException �߻�
							int readByteCount = socketChannel.read(byteBuffer);
							
							//Ŭ���̾�Ʈ�� ���������� SocketChannel�� close()�� ȣ������ ���
							if(readByteCount == -1) { 
								throw new IOException();	// ������ ���ܹ߻���Ŵ
							} // if
							
							// ��¸޽��� ����
							String message = 
								"[��û ó��: " + 
							    socketChannel.getRemoteAddress() + 
							    ": " + 
							    Thread.currentThread().getName() + 
							    "]";
							
							// JavaFX code : �� �ּ��� �ִ� �ڵ�� �׳� �Է��ϼ���!!
							Platform.runLater(()->displayText(message));
							
							// �б���� ���� �غ�
							byteBuffer.flip();
							
							// ���� �����͸� String���� ����ȯ ����
							// ��, ByteBuffer -> CharBuffer -> String ����ȯ
							Charset charset = Charset.forName("UTF-8");
							String data = charset.decode(byteBuffer).toString();
							
							// List<Client> ��ü �ȿ� ����ִ� ��� Ŭ���̾�Ʈ����
							// ����ȯ�Ͽ� ���� ���� �����͸� �ٽ� ������!!
							for(Client client : connections) {
								client.send(data); 
							} // enhanced for
							
						} catch(Exception e) { // ����ó��: ä����Ű���
							
							try { // ���ܰ� �߻��� Ŭ���̾�Ʈ�� List<Client>���� ����
								connections.remove(Client.this);
								
								String message = 
									"[Ŭ���̾�Ʈ ��� �ȵ�: " + 
									socketChannel.getRemoteAddress() + 
									": " + 
									Thread.currentThread().getName() + 
									"]";
								
								// JavaFX code : �� �ּ��� �ִ� �ڵ�� �׳� �Է��ϼ���!!
								Platform.runLater(()->displayText(message));
								
								// ��ſ� ����ä�ΰ�ü �ݱ�(�ڿ��ݳ�)
								socketChannel.close();
							} catch (IOException e2) {;;}
							
							break; // ���ѷ��� ��������
						} // outer try-catch
						
					} // while
					
				} // run
				
			}; // �͸�����ü�ڵ��������, ForkJoin Pool�� JobQ�� ���� task ����
			
			// ������ Ǯ�� task ó�� ����(JobQ�� task ����: submit)
			executorService.submit(runnable);	// ���� task�� ��� ó����!(�񵿱�)
		} // run
	
		// ������ ���� �޼ҵ�
		// ���۵� �����ʹ�, ���� Client�� ������ ������ ��� ����� Client�� ���޵�!!
		void send(String data) {
			
			// ForkJoin Pool�� ó���� ������ task ��, �͸�����ü�ڵ�������� ����
			Runnable runnable = new Runnable() {
				
				
				@Override
				public void run() {
					
					try {
						// String -> ByteBuffer �� ����ȯ�Ͽ�, ByteBuffer ����
						Charset charset = Charset.forName("UTF-8");
						ByteBuffer byteBuffer = charset.encode(data);
						
						// ��ſ� ����ä���� write(ByteBuffer) �޼ҵ��, �����
						// ������ ����
						socketChannel.write(byteBuffer);
					} catch(Exception e) { // ���� ���ܰ� �߻��ϸ�....
						
						try {
							// ä�� â(������)�� ����� �޽��� ����
							String message = 
								"[Ŭ���̾�Ʈ ��� �ȵ�: " + 
								socketChannel.getRemoteAddress() + 
								": " + 
								Thread.currentThread().getName() + "]";

							// JavaFX code : �� �ּ��� �ִ� �ڵ�� �׳� �Է��ϼ���!!
							Platform.runLater(()->displayText(message));
							
							// ������ ������ �ȵǴ� Client�� List<Client>���� ����
							connections.remove(Client.this);
							
							// ��ſ� ����ä�ΰ�ü ����(�ڿ��ݳ�)
							socketChannel.close();
						} catch (IOException e2) {;;} // try-catch
						
					} // try-catch
					
				} // run
				
			}; // �͸�����ü�ڵ�������� task ����
			
			// ������ Runnable task�� ForkJoin Pool�� ó�������ϱ�(����:submit)
			executorService.submit(runnable);
		} // send
		
	} // end class
	

	//--------------------------------------------------
	// ������ ��ŸƮ �� �� ����.
	//--------------------------------------------------
	void startServer() {
		System.out.println("---------------------------------------------");
		System.out.println("- ServerExample::startServer() invoked.");
		System.out.println("---------------------------------------------");
		
		//---------------------------------------------//
		// ForkJoin Pool ��ü ����(������ Ǯ)
		//---------------------------------------------//
		executorService = Executors.newFixedThreadPool(
			Runtime.getRuntime().availableProcessors()
	    );

		//---------------------------------------------//
		// ��������ä�ΰ�ü ���� �� ���ε� ����
		//---------------------------------------------//
		try {	
			serverSocketChannel = ServerSocketChannel.open();
			
			serverSocketChannel.configureBlocking(true);
			
			serverSocketChannel.bind(new InetSocketAddress(5001));
		} catch(Exception e) {
			
			if(serverSocketChannel.isOpen()) { 
				stopServer(); 		// ������ �ݰ��� �� �� �����ϴ� �޼ҵ�.
			} // if
			
			return;		// task �ٷ����� (��, run() �޼ҵ� �ٷ� ����)
		} // try-catch
		
		//------------------------------------------------
		// ����, ForkJoin Pool ó���� ���ӽ�ų task ����
		Runnable runnable = new Runnable() {
			
			
			@Override
			public void run() {
				
				// JavaFX code
				Platform.runLater(()->{
					displayText("[���� ����]");
					
					btnStartStop.setText("stop");
				});	
				
				while(true) { // ���ѷ���
					
					try {
						// ��������ä�� Listen(���������) ����
						SocketChannel socketChannel = 
								serverSocketChannel.accept();
						
						// Ŭ���̾�Ʈ ������ ���� �����Ǹ�, 
						//   1) ������� �޽����� �����Ͽ�, JavaFX window â�� ���
						String message = "[���� ����: " + socketChannel.getRemoteAddress()  + ": " + Thread.currentThread().getName() + "]";
						Platform.runLater(()->displayText(message));
		
						//   2) ������ Ŭ���̾�Ʈ ������ ������ Client ��ü ����
						Client client = new Client(socketChannel);
						
						//   3) ������ Client ��ü�� List<Client> ����Ʈ�� add
						connections.add(client);
						
						// JavaFX code
						Platform.runLater(
							// ���ٽ�
							()->displayText("[���� ����: " + connections.size() + "]")
						); // runLater
						
					} catch (Exception e) { // try��Ͽ��� ���ܰ� �߻��ϸ� ����ó�� ��������
						// ��������ä�� ��ü �ݱ�(�ڿ��ݳ� ����)
						if(serverSocketChannel.isOpen()) { 
							stopServer(); 
						} // if
						
						break; // ���ѷ��� ��������
					} // try-catch
					
				} // while
				
			} // run
			
		}; // �͸��ڰ�ü�ڵ��������, task ��ü ����.
		
		// ������ Runnable task�� ForkJoin Pool�� ó�� ���ӽ�Ű��(��, �񵿱� ó��)
		executorService.submit(runnable);
	} // startServer

	
	//--------------------------------------------------
	// ������ ������ų �� ����.
	//--------------------------------------------------
	void stopServer() {
		System.out.println("---------------------------------------------");
		System.out.println("- ServerExample::stopServer() invoked.");
		System.out.println("---------------------------------------------");
		
		try {
			// List<Client> ��ü�κ���, �ܺιݺ���(iterator) ����,
			// List���� �� Client ��ü ��ҿ� ���ؼ�, �Ʒ� �ڵ� ����
			Iterator<Client> iterator = connections.iterator();
			
			while(iterator.hasNext()) {
				// List�� �� ���(Client) ��ü ���
				Client client = iterator.next();
				
				// ��ſ� ����ä�ΰ�ü �ݱ�(�ڿ��ݳ�)
				client.socketChannel.close();
				
				// List �ȿ��� �� ��� ����
				iterator.remove();
			} // while
			
			// ���� ��������ä�ΰ�ü �ݱ� (�ڿ��ݳ�)
			if(serverSocketChannel!=null && serverSocketChannel.isOpen()) { 
				serverSocketChannel.close();
			} // if
			
			// ForkJoin Pool �ݱ� (�ڿ��ݳ�)
			if(executorService!=null && !executorService.isShutdown()) { 
				executorService.shutdown(); 
			} // if
			
			// JavaFX code
			Platform.runLater(()->{
				displayText("[���� ����]");
				
				btnStartStop.setText("start");			
			}); // runLater
			
		} catch (Exception e) {;;} // try-catch
		
	} // stopServer
	
	
	//////////////////////////////////////////////////////
	// JavaFX �� �̿��Ͽ�, ������ UI �����ڵ�: �׳� �Է����ּ���
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
				
				startServer();	// ��������
				
			} else if(btnStartStop.getText().equals("stop")){
				
				stopServer();	// ��������
				
			} // if-else if
			
		}); // setOnAction
		
		root.setBottom(btnStartStop);
		
		Scene scene = new Scene(root);
		
		// CSS ���� ����
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
		
		launch(args);	// JavaFX Application ������Ŵ(������)
	} // main
	 
} // end class
