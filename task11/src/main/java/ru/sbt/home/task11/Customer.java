package ru.sbt.home.task11;

/**
 * Клиент
 * Может приходить в парикмахерскую
 * Может проверять парикмахеров, будить при необходимости
 * Может сидеть
 */
public class Customer extends Hominid {
	public Customer(Room<Hominid> lobby, Room<Hominid> hall) {
		super(lobby, hall);
	}
	
	private void toLobby() {
		try {
			walk();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		lobby.enter(this);
	}
	
	private void toHall() {
		lobby.leave(this);
		
		try {
			walk();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private Barber checkBarbers() {
		hall.forEach(hominid -> {
			Barber barber = (Barber) hominid;
			
			if (barber.isBusy()) {
				return;
			}
			
			//synchronized ()
		});
		
		return (Barber) null;
	}
	
	private void awakeBarber(Barber barber) {
		synchronized (barber) {
			if (barber.isBusy()) {
				return;
			}
			
			barber.haircut(this);
		}
	}
	
	@Override
	public void run() {
		toLobby();
		
		toHall();
		
		//Barber freeBarber = seekFreeBarber();
		
		//if (freeBarber == null) {
		//	toLobby();
		//}
		
		synchronized (this) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}