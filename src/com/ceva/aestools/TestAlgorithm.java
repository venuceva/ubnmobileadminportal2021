package com.ceva.aestools;

public class TestAlgorithm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		TestAlgorithm.getCal("1234");
		
		
	}
	
	
	public static void getCal(String pin){
		System.out.println(pin);
		int mpin=0;
		String s=pin.substring(pin.length()-1, pin.length());
		System.out.println(s);
		mpin=Integer.parseInt(pin)*Integer.parseInt(pin.substring(pin.length()-1, pin.length()));
		System.out.println(mpin);
		s=s+""+(Integer.toString(mpin)).substring((Integer.toString(mpin)).length()-2, (Integer.toString(mpin)).length()-1);
		System.out.println(s);
		mpin=Integer.parseInt((Integer.toString(mpin)))*Integer.parseInt((Integer.toString(mpin)).substring((Integer.toString(mpin)).length()-2, (Integer.toString(mpin)).length()-1));
		System.out.println(mpin);
		
		s=s+""+(Integer.toString(mpin)).substring((Integer.toString(mpin)).length()-3, (Integer.toString(mpin)).length()-2);
		System.out.println(s);
		mpin=Integer.parseInt((Integer.toString(mpin)))*Integer.parseInt((Integer.toString(mpin)).substring((Integer.toString(mpin)).length()-3, (Integer.toString(mpin)).length()-2));
		System.out.println(mpin);
		
		s=s+""+(Integer.toString(mpin)).substring((Integer.toString(mpin)).length()-4, (Integer.toString(mpin)).length()-3);
		System.out.println(s);
		mpin=Integer.parseInt((Integer.toString(mpin)))*Integer.parseInt((Integer.toString(mpin)).substring((Integer.toString(mpin)).length()-4, (Integer.toString(mpin)).length()-3));
		System.out.println(mpin);
		
		System.out.println(mpin);
	}

}
