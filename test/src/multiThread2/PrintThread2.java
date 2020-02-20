package multiThread2;

public class PrintThread2 extends Thread{

	@Override
	public void run() {
		while(true) {
			
			System.out.println("실행 중");
			
			//이전에 수신된 시그널이 있으면 무한루프를 빠져나옴
			if(Thread.interrupted()) {
				break;
			}
		}
		System.out.println("자원 정리");
		System.out.println("실행 종료");
		
		//2nd method
//		while(!Thread.interrupted()) {
//					
//					System.out.println("실행 중");
//					
//					//이전에 수신된 시그널이 있으면 무한루프를 빠져나옴
//					if(Thread.interrupted()) {
//						break;
//					}
//				}
//				System.out.println("자원 정리");
//				System.out.println("실행 종료");
	}
	
}
