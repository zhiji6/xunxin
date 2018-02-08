package test;

public class teststatic2 extends teststatic1 {

	@Override
	public void bbb1() {
		// TODO Auto-generated method stub
		super.bbb1();
	}
	public static void aaa1(){
		System.out.println("aaa2");
	}
	
	
	
	public static void main(String[] args) {
		teststatic1 t=new teststatic2();
		t.bbb1();
		aaa1();
	}
}
