package ex00;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class IteratorVSStream {

	
	public static void main(String[] args) {
		List<String> list = Arrays.asList("홍길동", "신용권", "김자바");
		Iterator<String> iterator= list.iterator();
		while (iterator.hasNext()) {
			String name= iterator.next();
			System.out.println(name);
		}
		System.out.println();

		Stream<String> stream = list.stream();
		//스트림 타입의 내부 반복자
		stream.forEach(
				//name ->System.out.println(name)
				System.out::println //메소드 참조!
				
				);
		

		
	}
}
