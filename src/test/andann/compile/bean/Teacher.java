package test.andann.compile.bean;

import test.andann.compile.GenerInteface;

@GenerInteface(suffix="suffix")
public class Teacher {
	private void teach(){
		System.out.println("teacher teach ...");
	}
	
	public void walk(){
		System.out.println("teacher walk ...");
	}
}
