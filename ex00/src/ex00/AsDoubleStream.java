package ex00;

import java.util.Arrays;
import java.util.stream.IntStream;

public class AsDoubleStream {
	
	public static void main(String[] args) {
		
		int[] intArray = {1,2,3}
				;
		
		
		IntStream intStream = Arrays.stream(intArray);
		intStream.asDoubleStream().forEach(d-> System.out.println(d));
		intStream.boxed().forEach(obj -> System.out.println(obj.intValue()));
	}
	
	
}
