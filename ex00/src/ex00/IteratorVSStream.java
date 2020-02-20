package ex00;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class IteratorVSStream {

	
	public static void main(String[] args) {
		List<String> list = Arrays.asList("ȫ�浿", "�ſ��", "���ڹ�");
		Iterator<String> iterator= list.iterator();
		while (iterator.hasNext()) {
			String name= iterator.next();
			System.out.println(name);
		}
		System.out.println();

		Stream<String> stream = list.stream();
		//��Ʈ�� Ÿ���� ���� �ݺ���
		stream.forEach(
				//name ->System.out.println(name)
				System.out::println //�޼ҵ� ����!
				
				);
		

		
	}
}
