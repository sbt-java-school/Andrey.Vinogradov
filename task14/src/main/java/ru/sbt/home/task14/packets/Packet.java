package ru.sbt.home.task14.packets;

import ru.sbt.home.task14.AbstractSocketHandler;

import java.io.Serializable;

public interface Packet<Q extends Serializable, C extends AbstractSocketHandler> extends Serializable {
	public Q getQuery();
	
	public Packet process(C context);
}