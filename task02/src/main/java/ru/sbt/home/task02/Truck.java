package ru.sbt.home.task02;

/**
 * Класс с минимальной функциональностью. Просто чтобы было.
 */
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
	public boolean equals(Object o) {
		if (this == o) {
			return false;
		}
		
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		Truck truck = (Truck) o;
		
		return id == truck.id;
	}
	
	@Override
	public int hashCode() {
		return Long.hashCode(id);
	}
	
	@Override
	public String toString() {
		return "Truck{id = " + id + "}";
	}
}