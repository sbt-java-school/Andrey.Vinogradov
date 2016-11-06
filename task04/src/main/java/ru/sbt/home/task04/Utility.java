package ru.sbt.home.task04;

public class Utility extends ParentUtility {
	public static final long VERSION = 1L;
	
	private long f2;
	private long f3;
	
	public Utility() {
		super();
	}
	
	@Override
	public int getF1() {
		return f1;
	}
	
	@Override
	public void setF1(int f1) {
		this.f1 = f1;
	}
	
	public long getF2() {
		return f2;
	}
	
	public void setF2(long f2) {
		this.f2 = f2;
	}
	
	private void method() {
	}
}