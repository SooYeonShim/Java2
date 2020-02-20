package sec03.exam03_buffer;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class BufferExample {
	
	public static void main(String[] args) {
		//------------------------------------------//
		// 1. ByteBuffer 버퍼생성
		//------------------------------------------//
		//	- 버퍼생성시: position = 0, limit/capacity : 버퍼크기위치
		System.out.println("[7바이트 크기로 버퍼 생성]");
		// OS Memory에 버퍼생성 --> 다이렉트 버퍼
		ByteBuffer buffer = ByteBuffer.allocateDirect(7);
		printState(buffer);
		
		//------------------------------------------//
		// 99. Buffer 예외발생
		//------------------------------------------//
		// 총 7바이트(capacity에 도달) full로 저장
//		buffer.put(new byte[] {1,2,3,4,5,6,7});

		//------------------------------------------//
		// 예외 발생 시나리오#1
		// 1바이트를 더 쓰려고 하면?? --> BufferOverflowException 발생
//		buffer.put((byte)100);
		//------------------------------------------//

		//------------------------------------------//
		// 예외 발생 시나리오#2
		// 1바이트를 더 읽으려고 하면?? --> BufferUnderflowException 발생
//		buffer.flip();				// 읽을 준비함
//		buffer.get(new byte[7]); 	// 7바이트 모두 한번에 읽음
//		
//		buffer.get();				// 1바이트 더 읽으려고 시도!!!
		//------------------------------------------//
		
//		System.exit(0);
		
		
		

		//------------------------------------------//
		// 2. ByteBuffer 버퍼에 2바이트 데이터 쓰기(put)
		//------------------------------------------//
		//	- 각 바이트를 쓸때마다(put), position -> +1 씩 이동
		//  - limit: 이동없음, capacity: 이동없음
		buffer.put((byte)10);	// 1바이트 쓰기
		buffer.put((byte)11);	// 1바이트 쓰기
		
		System.out.println("[2바이트 저장후]");
		printState(buffer);		// 버퍼의 위치속성값 출력

		//------------------------------------------//
		// 3. ByteBuffer 버퍼에 추가로 3바이트 데이터 쓰기(put)
		//------------------------------------------//
		//	- 각 바이트를 쓸때마다(put), position -> +1 씩 이동
		//  - limit: 이동없음, capacity: 이동없음
		buffer.put((byte)12);
		buffer.put((byte)13);
		buffer.put((byte)14);
		
		System.out.println("[3바이트 저장후]");
		printState(buffer);		// 버퍼의 위치속성값 출력
		
		//------------------------------------------//
		// 4. ByteBuffer 버퍼를 읽을 준비하기(flip)
		//------------------------------------------//
		//	- limit -> position으로 이동
		//  - position -> 0 인덱스로 이동
		//  - capacity : 이동없음(항상!)
		buffer.flip();
		
		System.out.println("[flip() 실행후]");
		printState(buffer);		// 버퍼의 위치속성값 출력

		//------------------------------------------//

		// 3바이트 읽기
		//	- limit: 이동없음, position -> +3 인덱스로 이동
		buffer.get(new byte[3]);
		
		System.out.println("[3바이트 읽은후]");
		printState(buffer);		// 버퍼의 위치속성값 출력

		//------------------------------------------//
		
		// reset() 메소드 호출시 position이 되돌아갈 위치 설정(깃발꽂기)
		//	- limit: 이동없음, position: 이동없음, capacity: 이동없음
		//  오로지 현재 position 위치에 깃발(mark)만 꽂음!
//		buffer.mark();		
		System.out.println("Mark 안함!!!!");
		System.out.println("--------[현재 위치를 마크 해놓음]");
		printState(buffer);
		System.out.println("*******************");

		//------------------------------------------//
		
		// 2바이트 추가로 읽기
		//	- limit: 이동없음, position -> +2 인덱스로 이동
		buffer.get(new byte[2]);	
		
		System.out.println("[2바이트 읽은후]");
		printState(buffer);		// 버퍼의 위치속성값 출력

		//------------------------------------------//
		
		// 버퍼 초기화?(reset): position -> mark로 이동!
		// 이때 되돌아갈 깃발(mark)가 없이 reset() 호출하면 예외발생
		// InvalidMarkException
		//	- limit: 이동없음, position -> mark 인덱스로 이동
		//  - capacity : 이동없음
		buffer.reset();
		
		System.out.println("--------[position을 마크 위치로 옮김]");
		printState(buffer);		// 버퍼의 위치속성값 출력

		//------------------------------------------//
		
		// 버퍼 되감기(rewind): 다시 데이터를 읽기
		//	- limit: 이동없음, position -> 0번 인덱스로 이동
		//  - capacity : 이동없음
		buffer.rewind();
		
		System.out.println("[rewind() 실행후]");
		printState(buffer);		// 버퍼의 위치속성값 출력

		//------------------------------------------//
		
		// 이게(clear) 진짜 버퍼 초기화! -> 3개 위치속성이 원래대로 이동!
		//	- limit -> capacity로 이동, 
		//	- position -> 0번 인덱스로 이동
		//  - capacity : 이동없음
		buffer.clear();			
		
		System.out.println("[clear() 실행후]");
		printState(buffer);		// 버퍼의 위치속성값 출력
	} // main
	
	
	/***********************
	 * 매개변수에 전달된 버퍼객체의 위치속성값(position, limit, capacity)
	 * 출력 메소드 
	 */
	public static void printState(Buffer buffer) {
		System.out.print("\t+ position:" + buffer.position() + ", ");
		System.out.print("\t+ limit:" + buffer.limit() + ", ");
		System.out.println("\t+ capacity:" + buffer.capacity());
	} // printState
	
} // end class
