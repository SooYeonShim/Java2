package ex00;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class LamdaExpressionsExample {
	public static void main(String[] args) {
		
		
		List<Student> list = Arrays.asList(
				new Student("홍길동", 93),
				new Student("신용권", 92),
				new Student("유미선", 30)
				);
		Stream<Student> stream=  list.stream();
				
				stream.forEach(s -> {
					
					String name= s.getName();
					int score = s.getScore();
					System.out.println(name + "-"+ score);
					
				});
				
				
	}
}
