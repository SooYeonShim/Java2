package path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;


public class WatchServiceExample {
	
	
	public static void main(String[] args) 
			throws IOException, 
					URISyntaxException, 
					InterruptedException {
		
		//-- Step.1: 파일시스템 객체 얻어내기.
		FileSystem fs = FileSystems.getDefault();
		System.out.println("- FileSystem: "+ fs);

		//-- Step.2: 얻어낸 파일시스템 객체로부터, 파일변경통지메커니즘을
		//           제공하는 WatchService 객체를 얻어냄.
		WatchService ws = fs.newWatchService();
		System.out.println("- WatchService: "+ ws);

		//-- Step.3: URI 방식으로, 감시할 폴더에 대한 Path 객체 생성.
		Path path = Paths.get(new URI("file:///C:/Temp"));
		System.out.println("- path: "+ path);
		
		//-- Step.4: 얻어낸 WatchService 에, Path 객체가 가리키고
		//           있는 특정 폴더를 등록함!!! (감시하라!!!!)
		path.register(ws, 
				// 이때, WatchService 가 감시할 이벤트 유형을
				// StandardWatchEventKinds static final 상수로 지정
				// 여러 감시유형을 동시에 지정할 때에는, 아래와 같이, 
				// WatchEvent.Kind<T> Generic 타입의 배열로 여러개 지정
				new WatchEvent.Kind<?>[] {
					StandardWatchEventKinds.ENTRY_CREATE, // 생성
					StandardWatchEventKinds.ENTRY_DELETE, // 삭제
					StandardWatchEventKinds.ENTRY_MODIFY, // 수정
					StandardWatchEventKinds.OVERFLOW // 이벤트 소실
		}); // register.
		
		//-- Step.5: 감시할 폴더도 정해졌고, 이 폴더를 WatchService에도
		//           등록하였으니, 본격적으로 감시를 수행하기 위해서, 무한루프 생성
		while(true) { // Infinite Loop.
			// 무한루프 안에서는, 위에서 지정된 유형의 이벤트가 발생할 때가지,
			// 대기(블록킹): 이때 이벤트 발생을 기다리는 메소드가 WatchService의
			// take() 메소드임
			System.out.println("--------------------------------------------------------");
			System.out.println("- Polling WatchEvents ...");
			
			//-- Step.6: WatchService.take() 메소드로 이벤트 발생 기다림
			//           (*주의*) 이 메소드는 기본적으로 Blocking 될 수 있음
			//           이 take() 의 리턴값으로는, WatchKey(발생한 이벤트
			//           정보를 저장하고 있는 객체)를 돌려줌!!!
			WatchKey wk = ws.take();
			System.out.println("- WatchKey: "+ wk);
			
			//-- Step.11: WatchKey 가 유효한지 확인
			//            유효성을 검증하는 이유는, 감시폴더가 중간에, 다른
			//            이유로 삭제되거나, 변경되는 등의 감시상황에 변화가
			//            생기면, 더이상 감시할 수 없기 때문이고, 이런 상황이
			//            발생하면, 얻어낸 WatchKey는 유효하지 않게 됨.
			//            이 유효성을 검증하는 메소드가 WatchKey.isValid()임
			if(!wk.isValid()) {
				break; // 감시상황이 불가능하면이란 뜻 -> 무한 루프 빠져나감!!
			} // if
			
			//-- Step.7: 얻어낸 WatchKey(다시한번, 발생이벤트 정보를 저장)의
			//           pollEvents() 메소드를 통해, 발생된 한 개 이상의
			//           이벤트 객체(WatchEvent)들을 얻어냄
			//           그런데 왜? 리스트로 돌려줄까?... 한번의 WatchService.
			//           take() 메소드가 WatchKey 객체를 돌려줄 때에는,
			//           발생한 이벤트 당 하나가 아니라!!!, 한번에 여러 이벤트들을
			//           한꺼번에 전달하기 때문(예: 동시에, 여러파일 삭제/변경 등)
			List<WatchEvent<?>> events = wk.pollEvents();
			System.out.println(
					// 실제 발생한 이벤트 개수를 List.size()로 얻어냄!
					"\t+ WatchEvents Count: "+ events.size());
			
			// List 객체는 기본적으로 Iterable 하므로, 역시 리스트 안의
			// 각 요소를 순회(traverse)하는데에, enhanced for 문도 가능하지만
			// List.forEach(Consumer<T>) 메소드와 람다식을 통해서 traverse
			// 하는 것이 더 효율적임!!!! (람다식을 적극적으로 사용하라!!!!!!)
			// 이때, 각 요소의 타입은 바로 위 구문을 보시면, WatchEvent<?> 객체임
			events.forEach(we -> {
				// we: WatchEvent<?> 객체
				System.out.println("\t+++++++++++++++++++++++++++++++++++");
				System.out.println("\t+ WatchEvents catched: "+ we);
				System.out.println("\t+++++++++++++++++++++++++++++++++++");
				
				//-- Step.8: WatchEvent.kind() 메소드 통해, 발생한 이벤트
				//           유형정보를 Kind 객체로 돌려줌.
				Kind kind = we.kind();
				System.out.println("\t\t* Kind: "+ kind);
				
				//-- Step.9: WatchEvent.context() 메소드를 통해, 발생한
				//           이벤트가 어느 폴더(감시폴더 이하...)에서 발생하였
				//           는지를 역시 Path 객체로 리턴.
				Path eventPath = (Path) we.context();
				System.out.println("\t\t* Path: "+ eventPath);
				
				//-- Step.10: 발생한 이벤트 종류 객체인 Kind와 
				//            StandardWatchEventKinds 클래스의
				//            static final 상수와 비교해 봄으로써,
				//            이벤트 종류 비교파악 가능!!!
				if(kind == StandardWatchEventKinds.ENTRY_CREATE) {
					System.out.println(
						"\t\t\t & Kind of event: "+ 
						"ENTRY_CREATE");
					
				} // if
				
				if(kind == StandardWatchEventKinds.ENTRY_DELETE) {
					System.out.println(
						"\t\t\t & Kind of event: "+ 
						"ENTRY_DELETE");
					
				} // if
				
				if(kind == StandardWatchEventKinds.ENTRY_MODIFY) {
					System.out.println(
						"\t\t\t & Kind of event: "+ 
						"ENTRY_MODIFY");
					
				} // if
				
				// OVERFLOW: 운영체제에 의한 변경 이벤트의 소실 등의 이벤트 종류
				if(kind == StandardWatchEventKinds.OVERFLOW) {
					System.out.println(
							"\t\t\t & Kind of event: "+ 
							"OVERFLOW");
					
				} // if
				
			}); // forEach
			
			System.out.println("- WatchKey processing done.");
			
			//-- Step.12: 한번 사용한 WatchKey(발생 이벤트 정보를 저장하는 객체)
			//            는 reset() 메소드를 통해서, 재설정하여야, 새로이 
			//            발생하는 이벤트 정보를 저장가능하므로, 
			//			  다시 WatchService.take() 메소드 수행전에 초기화시킴!
			if(!wk.reset()) {	// WatchKey Reset.
				break; // WatchKey Reset 실패시, 무한루프 빠져나감
				       // 왜? 더이상 감시가 불가능하기때문
			} // if
			
			System.out.println("- WatchKey reset.");
			
		} // while
		
		
		System.out.println("- Watch Service finished.");
	} // main

} // end class
