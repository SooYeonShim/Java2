package ex01;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class OptionalEx {

	public static void main() {
		List<Integer> list = new ArrayList<>();
		
		double avg = list.parallelStream().
				mapToInt(Integer:: intValue).
				average().getAsDouble();
		System.out.println(avg);
		
		//스트림 파이프라인의 최종처리결과를 값-기반 객체로 받음. 이 값-기반 객체의 메소드를 이용하여, 다양한 경우에 대응
		OptionalDouble optional = list.stream().mapToInt(Integer:: intValue).average();
		
		System.out.println("평균 : " + optional);
		
		double avg2 =list.parallelStream().mapToInt(Integer:: intValue).average().orElse(0.0);
				
		System.out.println("평균 : " + avg2);
	}
	
	//list.stream().mapToInt(Integer :: invalue).
}
