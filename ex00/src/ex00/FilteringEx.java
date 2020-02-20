package ex00;

import java.util.Arrays;
import java.util.List;

public class FilteringEx {
	public static void main(String[] args) {
		List<String> names = Arrays.asList("È«±æµ¿",
				"½Å¿ë±Ç",
				"±èÀÚ¹Ù",
				"½Å¿ë±Ç",
				"½Å¹ÎÃ¶"
				);
		names.parallelStream().filter(
				n -> n.startsWith("½Å")
				
				).forEach(n-> System.out.println(n));
		names.
			stream().distinct().forEach(n -> System.out.println(n));
		
		

		System.out.println();
	}
}
