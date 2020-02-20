package sec03.exam03_buffer;

import java.nio.ByteBuffer;

public class CompactExample {
	
	
	public static void main(String[] args) {
		//---------------------------------------//
		// 1. 7바이트 크기의 ByteBuffer 다이렉트 버퍼 생성.
		//    이때 더이상, 바이트해석(ByteOrder)은 걱정하지 말자!!!
		//    JVM이 알아서, OS/JVM의 바이트해석차이를 맞춰줌(단점: 약간의 성능손해)
		//---------------------------------------//
		System.out.println("[7바이트 크기로 버퍼 생성]");
		ByteBuffer buffer = ByteBuffer.allocateDirect(7);
		
		//---------------------------------------//
		// 1-1. 버퍼에 총 5바이트를 put 함(쓰기)
		buffer.put((byte)10);
		buffer.put((byte)11);
		buffer.put((byte)12);
		buffer.put((byte)13);
		buffer.put((byte)14);
		
		//---------------------------------------//
		// 1-2. 버퍼의 데이터를 읽어내기 위한 준비 수행(flip)
		//      -  position -> 0으로 이동, limit --> position으로 이동
		buffer.flip();
		
		printState(buffer);
		
//		System.exit(0);
		
		//---------------------------------------//
		// 1-3. 버퍼에서 3바이트를 읽음(get)
		buffer.get(new byte[3]);
		
		System.out.println("\n[3바이트 읽음]");
		
		printState(buffer);
		
//		System.exit(0);
		
		//---------------------------------------//
		// 1-4. 현재 버퍼에 compact 수행!!! (*********) 
		buffer.compact();
		
		System.out.println("\n[compact() 실행후]");
		printState(buffer);
		
		//--------------------------------------//
		// 2. 상대적/절대적 메소드 사용 예제
		//--------------------------------------//
		buffer.clear();		// 버퍼 초기화

		System.out.println("\n[clear() 실행후]");
		printState(buffer);
		
		// 2-1. 상대적 메소드: position속성에 따라, 자연스럽게 버퍼에 데이터를 put
		buffer.put((byte) 20);

		System.out.println("\n[상대적 메소드 put() 실행후]");
		printState(buffer);
		
		// 2-2. 절대적 메소드 : 버퍼의 position속성에 의존하지 않고,
		//                  개발자가 직접 인덱스를 지정해서 get/put 행위
		buffer.put(5, (byte)99);

		System.out.println("\n[절대적 메소드 put() 실행후]");
		printState(buffer);
	} // main
	
	public static void printState(ByteBuffer buffer) {
		
		// 버퍼의 위치속성값 출력
		System.out.print("position:" + buffer.position() + ", ");
		System.out.print("limit:" + buffer.limit() + ", ");
		System.out.println("capacity:" + buffer.capacity());
		
		buffer.limit(7);
		
		// 버퍼에 현재 들어있는 데이터를 보여줌
		System.out.print(buffer.get(0) + ", ");
		System.out.print(buffer.get(1) + ", ");
		System.out.print(buffer.get(2) + ", ");
		System.out.print(buffer.get(3) + ", ");
		System.out.print(buffer.get(4) + ", ");
		System.out.print(buffer.get(5) + ", ");
		System.out.println(buffer.get(6));
	} // printState
	
} // end class
