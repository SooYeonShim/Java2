package ex00;

import java.util.Arrays;
import java.util.List;

public class FilteringEx {
	public static void main(String[] args) {
		List<String> names = Arrays.asList("ȫ�浿",
				"�ſ��",
				"���ڹ�",
				"�ſ��",
				"�Ź�ö"
				);
		names.parallelStream().filter(
				n -> n.startsWith("��")
				
				).forEach(n-> System.out.println(n));
		names.
			stream().distinct().forEach(n -> System.out.println(n));
		
		

		System.out.println();
	}
}
