package ru.sbt.home.task11;

/**
 * Парикмахер
 * Может стричь
 * Может проверять клиентов, приглашать при необходимости
 * Может отдыхать
 */
public class Barber extends Hominid {
	private State state;
	
	public Barber(Room<Hominid> lobby, Room<Hominid> hall) {
		super(lobby, hall);
		
		state = State.FREE;
	}
	
	public boolean isBusy() {
		return state == State.BUSY;
	}
	
	private void toLobby() {
		//hall.remove(this);
		
		try {
			walk();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void toHall() {
		//hall.add(this);
	}
	
	public void haircut(Hominid customer) {
		try {
			work();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		
	}
	
	enum State {
		BUSY, FREE;
	}
}