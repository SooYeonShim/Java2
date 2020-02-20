package exam03_file_directory;

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
import static java.lang.System.*;


public class WatchServiceExample {
	
	
	public static void main(String[] args) 
			throws IOException, 
					URISyntaxException, 
					InterruptedException {
		
		//-- Step.1: ���Ͻý��� ��ü ����.
		FileSystem fs = FileSystems.getDefault();
		out.println("- FileSystem: "+ fs);

		//-- Step.2: �� ���Ͻý��� ��ü�κ���, ���Ϻ���������Ŀ������
		//           �����ϴ� WatchService ��ü�� ��.
		WatchService ws = fs.newWatchService();
		out.println("- WatchService: "+ ws);

		//-- Step.3: URI �������, ������ ������ ���� Path ��ü ����.
		Path path = Paths.get(new URI("file:///C:/Temp"));
		out.println("- path: "+ path);
		
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
			out.println("--------------------------------------------------------");
			out.println("- Polling WatchEvents ...");
			
			//-- Step.6: WatchService.take() �޼ҵ�� �̺�Ʈ �߻� ��ٸ�
			//           (*����*) �� �޼ҵ�� �⺻������ Blocking �� �� ����
			//           �� take() �� ���ϰ����δ�, WatchKey(�߻��� �̺�Ʈ
			//           ������ �����ϰ� �ִ� ��ü)�� ������!!!
			WatchKey wk = ws.take();
			out.println("- WatchKey: "+ wk);
			
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
			out.println(
					// ���� �߻��� �̺�Ʈ ������ List.size()�� ��!
					"\t+ WatchEvents Count: "+ events.size());
			
			// List ��ü�� �⺻������ Iterable �ϹǷ�, ���� ����Ʈ ����
			// �� ��Ҹ� ��ȸ(traverse)�ϴµ���, enhanced for ���� ����������
			// List.forEach(Consumer<T>) �޼ҵ�� ���ٽ��� ���ؼ� traverse
			// �ϴ� ���� �� ȿ������!!!! (���ٽ��� ���������� ����϶�!!!!!!)
			// �̶�, �� ����� Ÿ���� �ٷ� �� ������ ���ø�, WatchEvent<?> ��ü��
			events.forEach(we -> {
				// we: WatchEvent<?> ��ü
				out.println("\t+++++++++++++++++++++++++++++++++++");
				out.println("\t+ WatchEvents catched: "+ we);
				out.println("\t+++++++++++++++++++++++++++++++++++");
				
				//-- Step.8: WatchEvent.kind() �޼ҵ� ����, �߻��� �̺�Ʈ
				//           ���������� Kind ��ü�� ������.
				Kind<?> kind = we.kind();
				out.println("\t\t* Kind: "+ kind);
				
				//-- Step.9: WatchEvent.context() �޼ҵ带 ����, �߻���
				//           �̺�Ʈ�� ��� ����(�������� ����...)���� �߻��Ͽ�
				//           ������ ���� Path ��ü�� ����.
				Path eventPath = (Path) we.context();
				out.println("\t\t* Path: "+ eventPath);
				
				//-- Step.10: �߻��� �̺�Ʈ ���� ��ü�� Kind�� 
				//            StandardWatchEventKinds Ŭ������
				//            static final ����� ���� �����ν�,
				//            �̺�Ʈ ���� ���ľ� ����!!!
				if(kind == StandardWatchEventKinds.ENTRY_CREATE) {
					out.println(
						"\t\t\t & Kind of event: ENTRY_CREATE");
					
				} // if
				
				if(kind == StandardWatchEventKinds.ENTRY_DELETE) {
					out.println(
						"\t\t\t & Kind of event: ENTRY_DELETE");
					
				} // if
				
				if(kind == StandardWatchEventKinds.ENTRY_MODIFY) {
					out.println(
						"\t\t\t & Kind of event: ENTRY_MODIFY");
					
				} // if
				
				// OVERFLOW: �ü���� ���� ���� �̺�Ʈ�� �ҽ� ���� �̺�Ʈ ����
				if(kind == StandardWatchEventKinds.OVERFLOW) {
					out.println(
						"\t\t\t & Kind of event: OVERFLOW");
					
				} // if
				
			}); // forEach
			
			out.println("- WatchKey processing done.");
			
			//-- Step.12: �ѹ� ����� WatchKey(�߻� �̺�Ʈ ������ �����ϴ� ��ü)
			//            �� reset() �޼ҵ带 ���ؼ�, �缳���Ͽ���, ������ 
			//            �߻��ϴ� �̺�Ʈ ������ ���尡���ϹǷ�, 
			//			  �ٽ� WatchService.take() �޼ҵ� �������� �ʱ�ȭ��Ŵ!
			if(!wk.reset()) {	// WatchKey Reset.
				break; // WatchKey Reset ���н�, ���ѷ��� ��������
				       // ��? ���̻� ���ð� �Ұ����ϱ⶧��
			} // if
			
			out.println("- WatchKey reset.");
			
		} // while
		
		
		out.println("- Watch Service finished.");
	} // main

} // end class
