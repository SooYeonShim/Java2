package ex01;

import java.util.Arrays;

public class MatchEx {

	
	public static void main(String[] args) {
		
		
		int[] intArr = {2,4,3};
		boolean result = Arrays.stream(intArr).allMatch(a -> a%2 ==0);
		
		System.out.println("��� ¦���ΰ�? : " + result);
		
		result= Arrays.stream(intArr).anyMatch(a -> a==3);
	
		System.out.println("�ϳ��� 3�� ����� �ִ°�? : " + result);
		
		result= Arrays.stream(intArr).noneMatch(a -> a%3==0);
		
		System.out.println("3�� ����� ���°�? : " + result);
	}
}
