package com.revature;

public class ScopeDriver {
	
	public static int x = 0;
	public static String myStr = "hello";
	public static int myOuterInt = 9;
	
	public static void main(String[] args) {
		System.out.println(x);
		System.out.println(changeX(x));
		System.out.println(x);		
		
		System.out.println(myStr);
		System.out.println(changeString(myStr));
		System.out.println(myStr);
	}
	
	
	//Java is PASS-BY-VALUE. Only the value of x is passed in, not the reference to the variable itself.
	public static int changeX(int x) {
		return ++x;
	}
	
	public static String changeString(String value) {
		return value + ", world!";
	}
	
//	public static int getMyInnerInt() {
//		return myInnerInt; Can't see inside the inner class
//	}
	
	class SomeClass {
		private int myInnerInt = 6;
		
		public int getMyOuterInt() {
			return myOuterInt; 
		}
	}

}
