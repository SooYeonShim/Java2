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
		
		//-- Step.1: ���Ͻý��� ��ü ����.
		FileSystem fs = FileSystems.getDefault();
		System.out.println("- FileSystem: "+ fs);

		//-- Step.2: �� ���Ͻý��� ��ü�κ���, ���Ϻ���������Ŀ������
		//           �����ϴ� WatchService ��ü�� ��.
		WatchService ws = fs.newWatchService();
		System.out.println("- WatchService: "+ ws);

		//-- Step.3: URI �������, ������ ������ ���� Path ��ü ����.
		Path path = Paths.get(new URI("file:///C:/Temp"));
		System.out.println("- path: "+ path);
		
		//-- Step.4: �� WatchService ��, Path ��ü�� ����Ű��
		//           �ִ� Ư�� ������ �����!!! (�����϶�!!!!)
		path.register(ws, 
				// �̶�, WatchService �� ������ �̺�Ʈ ������
				// StandardWatchEventKinds static final ����� ����
				// ���� ���������� ���ÿ� ������ ������, �Ʒ��� ����, 
				// WatchEvent.Kind<T> Generic Ÿ���� �迭�� ������ ����
				new WatchEvent.Kind<?>[] {
					StandardWatchEventKinds.ENTRY_CREATE, // ����
					StandardWatchEventKinds.ENTRY_DELETE, // ����
					StandardWatchEventKinds.ENTRY_MODIFY, // ����
					StandardWatchEventKinds.OVERFLOW // �̺�Ʈ �ҽ�
		}); // register.
		
		//-- Step.5: ������ ������ ��������, �� ������ WatchService����
		//           ����Ͽ�����, ���������� ���ø� �����ϱ� ���ؼ�, ���ѷ��� ����
		while(true) { // Infinite Loop.
			// ���ѷ��� �ȿ�����, ������ ������ ������ �̺�Ʈ�� �߻��� ������,
			// ���(���ŷ): �̶� �̺�Ʈ �߻��� ��ٸ��� �޼ҵ尡 WatchService��
			// take() �޼ҵ���
			System.out.println("--------------------------------------------------------");
			System.out.println("- Polling WatchEvents ...");
			
			//-- Step.6: WatchService.take() �޼ҵ�� �̺�Ʈ �߻� ��ٸ�
			//           (*����*) �� �޼ҵ�� �⺻������ Blocking �� �� ����
			//           �� take() �� ���ϰ����δ�, WatchKey(�߻��� �̺�Ʈ
			//           ������ �����ϰ� �ִ� ��ü)�� ������!!!
			WatchKey wk = ws.take();
			System.out.println("- WatchKey: "+ wk);
			
			//-- Step.11: WatchKey �� ��ȿ���� Ȯ��
			//            ��ȿ���� �����ϴ� ������, ���������� �߰���, �ٸ�
			//            ������ �����ǰų�, ����Ǵ� ���� ���û�Ȳ�� ��ȭ��
			//            �����, ���̻� ������ �� ���� �����̰�, �̷� ��Ȳ��
			//            �߻��ϸ�, �� WatchKey�� ��ȿ���� �ʰ� ��.
			//            �� ��ȿ���� �����ϴ� �޼ҵ尡 WatchKey.isValid()��
			if(!wk.isValid()) {
				break; // ���û�Ȳ�� �Ұ����ϸ��̶� �� -> ���� ���� ��������!!
			} // if
			
			//-- Step.7: �� WatchKey(�ٽ��ѹ�, �߻��̺�Ʈ ������ ����)��
			//           pollEvents() �޼ҵ带 ����, �߻��� �� �� �̻���
			//           �̺�Ʈ ��ü(WatchEvent)���� ��
			//           �׷��� ��? ����Ʈ�� �����ٱ�?... �ѹ��� WatchService.
			//           take() �޼ҵ尡 WatchKey ��ü�� ������ ������,
			//           �߻��� �̺�Ʈ �� �ϳ��� �ƴ϶�!!!, �ѹ��� ���� �̺�Ʈ����
			//           �Ѳ����� �����ϱ� ����(��: ���ÿ�, �������� ����/���� ��)
			List<WatchEvent<?>> events = wk.pollEvents();
			System.out.println(
					// ���� �߻��� �̺�Ʈ ������ List.size()�� ��!
					"\t+ WatchEvents Count: "+ events.size());
			
			// List ��ü�� �⺻������ Iterable �ϹǷ�, ���� ����Ʈ ����
			// �� ��Ҹ� ��ȸ(traverse)�ϴµ���, enhanced for ���� ����������
			// List.forEach(Consumer<T>) �޼ҵ�� ���ٽ��� ���ؼ� traverse
			// �ϴ� ���� �� ȿ������!!!! (���ٽ��� ���������� ����϶�!!!!!!)
			// �̶�, �� ����� Ÿ���� �ٷ� �� ������ ���ø�, WatchEvent<?> ��ü��
			events.forEach(we -> {
				// we: WatchEvent<?> ��ü
				System.out.println("\t+++++++++++++++++++++++++++++++++++");
				System.out.println("\t+ WatchEvents catched: "+ we);
				System.out.println("\t+++++++++++++++++++++++++++++++++++");
				
				//-- Step.8: WatchEvent.kind() �޼ҵ� ����, �߻��� �̺�Ʈ
				//           ���������� Kind ��ü�� ������.
				Kind kind = we.kind();
				System.out.println("\t\t* Kind: "+ kind);
				
				//-- Step.9: WatchEvent.context() �޼ҵ带 ����, �߻���
				//           �̺�Ʈ�� ��� ����(�������� ����...)���� �߻��Ͽ�
				//           ������ ���� Path ��ü�� ����.
				Path eventPath = (Path) we.context();
				System.out.println("\t\t* Path: "+ eventPath);
				
				//-- Step.10: �߻��� �̺�Ʈ ���� ��ü�� Kind�� 
				//            StandardWatchEventKinds Ŭ������
				//            static final ����� ���� �����ν�,
				//            �̺�Ʈ ���� ���ľ� ����!!!
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
				
				// OVERFLOW: �ü���� ���� ���� �̺�Ʈ�� �ҽ� ���� �̺�Ʈ ����
				if(kind == StandardWatchEventKinds.OVERFLOW) {
					System.out.println(
							"\t\t\t & Kind of event: "+ 
							"OVERFLOW");
					
				} // if
				
			}); // forEach
			
			System.out.println("- WatchKey processing done.");
			
			//-- Step.12: �ѹ� ����� WatchKey(�߻� �̺�Ʈ ������ �����ϴ� ��ü)
			//            �� reset() �޼ҵ带 ���ؼ�, �缳���Ͽ���, ������ 
			//            �߻��ϴ� �̺�Ʈ ������ ���尡���ϹǷ�, 
			//			  �ٽ� WatchService.take() �޼ҵ� �������� �ʱ�ȭ��Ŵ!
			if(!wk.reset()) {	// WatchKey Reset.
				break; // WatchKey Reset ���н�, ���ѷ��� ��������
				       // ��? ���̻� ���ð� �Ұ����ϱ⶧��
			} // if
			
			System.out.println("- WatchKey reset.");
			
		} // while
		
		
		System.out.println("- Watch Service finished.");
	} // main

} // end class
