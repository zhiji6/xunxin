package test;

public class test2   {

	public void test() {
		test3 t= new test3();
		t.test3_();
		
	}
	
	class test3{
		public void test3_(){
			System.out.println("内部类");
		}
	}
	
	public static void main(String[] args) {
		Thread t = new Thread(){
			public void run(){
				for(int i=0;i<5;i++){
					System.out.println(i);
				}
			}
			
		};
		t.start();
	}
}

