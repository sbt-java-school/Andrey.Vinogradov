package ru.sbt.home.task02;

public class Truck {
	private long id;
	private String type;
	private int capacity;
	
	public Truck(long id, String type, int capacity) {
		this.id = id;
		this.type = type;
		this.capacity = capacity;
	}
	
	public long getId() {
		return id;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public String getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return "Truck{id = " + id + "}";
	}
}