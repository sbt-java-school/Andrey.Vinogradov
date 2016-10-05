package ru.sbt.home.task11;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.random;

public abstract class Hominid implements Runnable {
	public static final long WALK_MAX_DELAY = 300;
	public static final long WORK_MAX_DELAY = 1000;
	
	protected Room<Hominid> lobby;
	protected Room<Hominid> hall;
	
	public Hominid(Room<Hominid> lobby, Room<Hominid> hall) {
		this.lobby = Objects.requireNonNull(lobby);
		this.hall = Objects.requireNonNull(hall);
	}
	
	protected void walk() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep((long) (WALK_MAX_DELAY * random()));
	}
	
	protected void work() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep((long) (WORK_MAX_DELAY * random()));
	}
}