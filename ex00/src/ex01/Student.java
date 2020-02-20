package ex01;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;


@AllArgsConstructor
public class Student implements Comparable<Student>{

	private @Getter String name;
	private @Getter int score;

	@Override
	public int compareTo(Student s) {
		
		System.out.println("Student::compareTo(student) invoked");
		System.out.println("\t + ±‚¡ÿ∞¥√º " + score);
		System.out.println("\t + ∫Ò±≥∞¥√º " + s.score);
		
		
		return Integer.compare(score,s.score);
	}

}
