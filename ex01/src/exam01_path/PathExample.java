package exam01_path;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;


public class PathExample {
	
	
	public static void main(String[] args) 
			throws Exception {
		
		//------------------------------------
		//-- 1. Path �� ������ü ����
		//------------------------------------
		//   Path �������̽��� �ֿ������, ���Ͻý��ۻ���
		//   ���(Path)�� �ڹ� ��ü�� �𵨸� �ϴ� ����
		Path path = 
			Paths.get("C:/Temp/sunnydays.mp3");
		
		System.out.println("- path: "+path);
		System.out.println("- path class: "+path.getClass().getName());

		//------------------------------------
		//-- 2. Path �� ������ü�κ��� ���� �Ӽ����� ����
		//------------------------------------
		System.out.println(
			"[���ϸ�] " + path.getFileName() );
		
		System.out.println(
			"[�θ� ���丮��]: " + 
			path.getParent().getFileName() );
		
		System.out.println(
			"��ø ��μ�: " + path.getNameCount() );
		//------------------------------------
		
		
		System.out.println();
		

		//------------------------------------
		for(int i=0; i<path.getNameCount(); i++) {
			System.out.println( path.getName(i) );
		} // for
		//------------------------------------
		
		
		System.out.println();
		

		//------------------------------------
		Iterator<Path> iterator = path.iterator();
		
		while(iterator.hasNext()) {
			Path temp = iterator.next();
			
			System.out.println(temp.getFileName());
		} // while
		//------------------------------------
		
	} // main
	
} // end class
