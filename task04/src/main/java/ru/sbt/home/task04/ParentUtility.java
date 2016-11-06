package ru.sbt.home.task04;

public class ParentUtility extends GrandUtility {
	public static final long VERSION = 1L;
	
	protected int f1;
	int f4;
	
	public ParentUtility() {
	}
	
	public int getF1() {
		return f1;
	}
	
	public void setF1(int f1) {
		this.f1 = f1;
	}
	
}