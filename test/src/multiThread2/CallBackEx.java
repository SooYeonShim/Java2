//package multiThread2;
//
//import java.nio.channels.CompletionHandler;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class CallBackEx {
//	ExecutorService executorService= 
//			Executors.newFixedThreadPool(2);
//	public CallBackEx() {
//		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//		
//		CompletionHandler<Integer,Void> callback= 
//				new CompletionHandler<Integer, Void>() {
//			public void completed(
//					Integer result, Void attachment) {
//				System.out.println("completed 실행: " + result);
//			}
//
//			public void failed(Throwable exc, Void attachment) {
//				System.out.println("failed() 실행 : " +exc.toString());
//			}
//					
//			
//		};
//		
//		public void doWork(final String x, final String y) {
//			Runnable task = new Runnable() {
//				
//				public void run() {
//					try {
//						int intX = Integer.parseInt(x);
//						int intY = Integer.parseInt(y);
//						int result = intX+ intY;
//						callback.completed(result, null);
//					} catch(NumberFormatException r) {
//						callback.failed(e, null);
//					}
//				}
//			};
//			executorService.submit(task);
//		}//doWork
//		
//		public void finish() {
//			excutorService.shutdown();
//
//		}
//		
//		public static void main(String args) {
//			CallbackEx example = new CallbackEx();
//			example.doWork("3", "3");
//			example.doWork("3", "3");
//		}
//		
//	}
//}
