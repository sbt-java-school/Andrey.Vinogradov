package ru.sbt.home.task02;

import java.util.List;
import java.util.Map;

/**
 * Базовый dao интерфейс
 */
public interface TruckDao {
	List<Truck> list();
	
	Map<Long, Truck> listByID();
	
	Map<String, List<Truck>> listByType();
}