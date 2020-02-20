package ex01;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class SortingEx {

	public static void main(String[] args) {
		
		IntStream intStream = Arrays.stream(new int[] {5,3,2,1,4});
		intStream.sorted().forEach(n -> System.out.print(n + " "));
		System.out.println();
		
		List<Student> studentList = Arrays.asList(new Student("ȫ�浿", 30),
				new Student("�ſ��", 20),
				new Student("���̼�" , 25)
				
				);
		
		studentList.stream().sorted(Comparator.reverseOrder()).forEach(
				s-> System.out.println(s.getScore()+","));
				
				
	}
}
