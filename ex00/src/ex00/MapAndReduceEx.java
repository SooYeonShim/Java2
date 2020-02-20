package ex00;

import java.util.Arrays;
import java.util.List;

public class MapAndReduceEx {

	public static void main(String[] args) {
		List<Student> studentList = Arrays.asList(
				new Student("ȫ�浿", 93),
				new Student("�ſ��", 92),
				new Student("���̼�", 30)
				);
		
		double avg = studentList.
				stream().
				mapToInt(Student :: getScore).
				average().
				getAsDouble();
		
		System.out.println("��� ����: " + avg);
	}
}
