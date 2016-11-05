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
	 * Constructs new Truck having 0 max mass
	 */
	public Truck() {
		this(0);
	}
	
	/**
	 * Constructs new Truck with given max mass
	 *
	 * @param maxMass max mass
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
	
	/**
	 * Gets current max mass
	 *
	 * @return current truck max mass
	 */
	public int getMaxMass() {
		return maxMass;
	}
	
	/**
	 * Sets new truck max mass. Does not check if current mass larger then given new max mass
	 *
	 * @param w new max mass
	 */
	public void setMaxMass(int w) {
		maxMass = w;
	}
	
	/**
	 * Returns current truck mass
	 *
	 * @return current mass
	 */
	public int getMass() {
		return mass;
	}
	
	/**
	 * Returns current truck goods count
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