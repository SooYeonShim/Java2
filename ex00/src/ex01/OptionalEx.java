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
		
		//��Ʈ�� ������������ ����ó������� ��-��� ��ü�� ����. �� ��-��� ��ü�� �޼ҵ带 �̿��Ͽ�, �پ��� ��쿡 ����
		OptionalDouble optional = list.stream().mapToInt(Integer:: intValue).average();
		
		System.out.println("��� : " + optional);
		
		double avg2 =list.parallelStream().mapToInt(Integer:: intValue).average().orElse(0.0);
				
		System.out.println("��� : " + avg2);
	}
	
	//list.stream().mapToInt(Integer :: invalue).
}
