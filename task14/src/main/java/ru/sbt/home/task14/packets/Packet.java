package ru.sbt.home.task14.packets;

import java.io.Serializable;

public interface Packet<Q extends Serializable, C> extends Serializable {
	public Q getQuery();
	
	public Packet process(C context);
}