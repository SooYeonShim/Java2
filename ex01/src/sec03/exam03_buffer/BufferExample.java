package sec03.exam03_buffer;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class BufferExample {
	
	public static void main(String[] args) {
		//------------------------------------------//
		// 1. ByteBuffer ���ۻ���
		//------------------------------------------//
		//	- ���ۻ�����: position = 0, limit/capacity : ����ũ����ġ
		System.out.println("[7����Ʈ ũ��� ���� ����]");
		// OS Memory�� ���ۻ��� --> ���̷�Ʈ ����
		ByteBuffer buffer = ByteBuffer.allocateDirect(7);
		printState(buffer);
		
		//------------------------------------------//
		// 99. Buffer ���ܹ߻�
		//------------------------------------------//
		// �� 7����Ʈ(capacity�� ����) full�� ����
//		buffer.put(new byte[] {1,2,3,4,5,6,7});

		//------------------------------------------//
		// ���� �߻� �ó�����#1
		// 1����Ʈ�� �� ������ �ϸ�?? --> BufferOverflowException �߻�
//		buffer.put((byte)100);
		//------------------------------------------//

		//------------------------------------------//
		// ���� �߻� �ó�����#2
		// 1����Ʈ�� �� �������� �ϸ�?? --> BufferUnderflowException �߻�
//		buffer.flip();				// ���� �غ���
//		buffer.get(new byte[7]); 	// 7����Ʈ ��� �ѹ��� ����
//		
//		buffer.get();				// 1����Ʈ �� �������� �õ�!!!
		//------------------------------------------//
		
//		System.exit(0);
		
		
		

		//------------------------------------------//
		// 2. ByteBuffer ���ۿ� 2����Ʈ ������ ����(put)
		//------------------------------------------//
		//	- �� ����Ʈ�� ��������(put), position -> +1 �� �̵�
		//  - limit: �̵�����, capacity: �̵�����
		buffer.put((byte)10);	// 1����Ʈ ����
		buffer.put((byte)11);	// 1����Ʈ ����
		
		System.out.println("[2����Ʈ ������]");
		printState(buffer);		// ������ ��ġ�Ӽ��� ���

		//------------------------------------------//
		// 3. ByteBuffer ���ۿ� �߰��� 3����Ʈ ������ ����(put)
		//------------------------------------------//
		//	- �� ����Ʈ�� ��������(put), position -> +1 �� �̵�
		//  - limit: �̵�����, capacity: �̵�����
		buffer.put((byte)12);
		buffer.put((byte)13);
		buffer.put((byte)14);
		
		System.out.println("[3����Ʈ ������]");
		printState(buffer);		// ������ ��ġ�Ӽ��� ���
		
		//------------------------------------------//
		// 4. ByteBuffer ���۸� ���� �غ��ϱ�(flip)
		//------------------------------------------//
		//	- limit -> position���� �̵�
		//  - position -> 0 �ε����� �̵�
		//  - capacity : �̵�����(�׻�!)
		buffer.flip();
		
		System.out.println("[flip() ������]");
		printState(buffer);		// ������ ��ġ�Ӽ��� ���

		//------------------------------------------//

		// 3����Ʈ �б�
		//	- limit: �̵�����, position -> +3 �ε����� �̵�
		buffer.get(new byte[3]);
		
		System.out.println("[3����Ʈ ������]");
		printState(buffer);		// ������ ��ġ�Ӽ��� ���

		//------------------------------------------//
		
		// reset() �޼ҵ� ȣ��� position�� �ǵ��ư� ��ġ ����(��߲ȱ�)
		//	- limit: �̵�����, position: �̵�����, capacity: �̵�����
		//  ������ ���� position ��ġ�� ���(mark)�� ����!
//		buffer.mark();		
		System.out.println("Mark ����!!!!");
		System.out.println("--------[���� ��ġ�� ��ũ �س���]");
		printState(buffer);
		System.out.println("*******************");

		//------------------------------------------//
		
		// 2����Ʈ �߰��� �б�
		//	- limit: �̵�����, position -> +2 �ε����� �̵�
		buffer.get(new byte[2]);	
		
		System.out.println("[2����Ʈ ������]");
		printState(buffer);		// ������ ��ġ�Ӽ��� ���

		//------------------------------------------//
		
		// ���� �ʱ�ȭ?(reset): position -> mark�� �̵�!
		// �̶� �ǵ��ư� ���(mark)�� ���� reset() ȣ���ϸ� ���ܹ߻�
		// InvalidMarkException
		//	- limit: �̵�����, position -> mark �ε����� �̵�
		//  - capacity : �̵�����
		buffer.reset();
		
		System.out.println("--------[position�� ��ũ ��ġ�� �ű�]");
		printState(buffer);		// ������ ��ġ�Ӽ��� ���

		//------------------------------------------//
		
		// ���� �ǰ���(rewind): �ٽ� �����͸� �б�
		//	- limit: �̵�����, position -> 0�� �ε����� �̵�
		//  - capacity : �̵�����
		buffer.rewind();
		
		System.out.println("[rewind() ������]");
		printState(buffer);		// ������ ��ġ�Ӽ��� ���

		//------------------------------------------//
		
		// �̰�(clear) ��¥ ���� �ʱ�ȭ! -> 3�� ��ġ�Ӽ��� ������� �̵�!
		//	- limit -> capacity�� �̵�, 
		//	- position -> 0�� �ε����� �̵�
		//  - capacity : �̵�����
		buffer.clear();			
		
		System.out.println("[clear() ������]");
		printState(buffer);		// ������ ��ġ�Ӽ��� ���
	} // main
	
	
	/***********************
	 * �Ű������� ���޵� ���۰�ü�� ��ġ�Ӽ���(position, limit, capacity)
	 * ��� �޼ҵ� 
	 */
	public static void printState(Buffer buffer) {
		System.out.print("\t+ position:" + buffer.position() + ", ");
		System.out.print("\t+ limit:" + buffer.limit() + ", ");
		System.out.println("\t+ capacity:" + buffer.capacity());
	} // printState
	
} // end class
