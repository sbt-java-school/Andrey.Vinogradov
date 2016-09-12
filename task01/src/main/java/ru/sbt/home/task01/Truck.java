package ru.sbt.home.task01;

public class Truck {
	/**
	 * Current goods count
	 */
	private int count;
	/**
	 * Current goods mass
	 */
	private int mass;
	
	/**
	 * Max goods mass
	 */
	private int maxMass;
	
	/**
	 * Constructs new Truck with 0 max mass
	 */
	public Truck() {
		this(0);
	}
	
	/**
	 * Constructs new Truck with given max mass
	 *
	 * @param maxMass new max mass. Do not check if current mass larger then given new max mass
	 */
	public Truck(int maxMass) {
		this.maxMass = maxMass;
	}
	
	/**
	 * Loads mass m to truck if possible, increasing mass and goods count
	 *
	 * @param m mass to load
	 */
	public void load(int m) {
		if (mass + m > maxMass) {
			return;
		}
		
		mass += m;
		count++;
	}
	
	/**
	 * Clears truck current mass and count
	 */
	public void clear() {
		mass = 0;
		count = 0;
	}
	
	public int getMaxMass() {
		return maxMass;
	}
	
	public void setMaxMass(int w) {
		maxMass = w;
	}
	
	/**
	 * Returns current mass
	 *
	 * @return current mass
	 */
	public int getMass() {
		return mass;
	}
	
	/**
	 * Returns current goods count
	 *
	 * @return current goods count
	 */
	public int getCount() {
		return count;
	}
	
	@Override
	public String toString() {
		return "Goods count = " + count + " mass = " + mass;
	}
}