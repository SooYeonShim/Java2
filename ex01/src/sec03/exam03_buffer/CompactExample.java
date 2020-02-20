package sec03.exam03_buffer;

import java.nio.ByteBuffer;

public class CompactExample {
	
	
	public static void main(String[] args) {
		//---------------------------------------//
		// 1. 7����Ʈ ũ���� ByteBuffer ���̷�Ʈ ���� ����.
		//    �̶� ���̻�, ����Ʈ�ؼ�(ByteOrder)�� �������� ����!!!
		//    JVM�� �˾Ƽ�, OS/JVM�� ����Ʈ�ؼ����̸� ������(����: �ణ�� ���ɼ���)
		//---------------------------------------//
		System.out.println("[7����Ʈ ũ��� ���� ����]");
		ByteBuffer buffer = ByteBuffer.allocateDirect(7);
		
		//---------------------------------------//
		// 1-1. ���ۿ� �� 5����Ʈ�� put ��(����)
		buffer.put((byte)10);
		buffer.put((byte)11);
		buffer.put((byte)12);
		buffer.put((byte)13);
		buffer.put((byte)14);
		
		//---------------------------------------//
		// 1-2. ������ �����͸� �о�� ���� �غ� ����(flip)
		//      -  position -> 0���� �̵�, limit --> position���� �̵�
		buffer.flip();
		
		printState(buffer);
		
//		System.exit(0);
		
		//---------------------------------------//
		// 1-3. ���ۿ��� 3����Ʈ�� ����(get)
		buffer.get(new byte[3]);
		
		System.out.println("\n[3����Ʈ ����]");
		
		printState(buffer);
		
//		System.exit(0);
		
		//---------------------------------------//
		// 1-4. ���� ���ۿ� compact ����!!! (*********) 
		buffer.compact();
		
		System.out.println("\n[compact() ������]");
		printState(buffer);
		
		//--------------------------------------//
		// 2. �����/������ �޼ҵ� ��� ����
		//--------------------------------------//
		buffer.clear();		// ���� �ʱ�ȭ

		System.out.println("\n[clear() ������]");
		printState(buffer);
		
		// 2-1. ����� �޼ҵ�: position�Ӽ��� ����, �ڿ������� ���ۿ� �����͸� put
		buffer.put((byte) 20);

		System.out.println("\n[����� �޼ҵ� put() ������]");
		printState(buffer);
		
		// 2-2. ������ �޼ҵ� : ������ position�Ӽ��� �������� �ʰ�,
		//                  �����ڰ� ���� �ε����� �����ؼ� get/put ����
		buffer.put(5, (byte)99);

		System.out.println("\n[������ �޼ҵ� put() ������]");
		printState(buffer);
	} // main
	
	public static void printState(ByteBuffer buffer) {
		
		// ������ ��ġ�Ӽ��� ���
		System.out.print("position:" + buffer.position() + ", ");
		System.out.print("limit:" + buffer.limit() + ", ");
		System.out.println("capacity:" + buffer.capacity());
		
		buffer.limit(7);
		
		// ���ۿ� ���� ����ִ� �����͸� ������
		System.out.print(buffer.get(0) + ", ");
		System.out.print(buffer.get(1) + ", ");
		System.out.print(buffer.get(2) + ", ");
		System.out.print(buffer.get(3) + ", ");
		System.out.print(buffer.get(4) + ", ");
		System.out.print(buffer.get(5) + ", ");
		System.out.println(buffer.get(6));
	} // printState
	
} // end class
