package ru.sbt.home.task02;

import java.util.*;

/**
 * DAO из памяти
 */
public class TruckDaoMemoryImpl implements TruckDao {
	/**
	 * Формирование простого списка грузовиков
	 */
	private final List<Truck> list = new ArrayList<>(Arrays.asList(
			new Truck(1, "KAMAZ", 10),
			new Truck(2, "KAMAZ", 30),
			new Truck(3, "Daimler", 20),
			new Truck(4, "MAN", 11),
			new Truck(5, "MAN", 98),
			new Truck(6, "MAN", 100)
	));
	
	private Map<Long, Truck> listByID = new TreeMap<>();
	private Map<String, List<Truck>> listByType = new HashMap<>();
	
	// Формирование сложных листов грузовиков
	{
		for (Truck truck : list) {
			listByID.put(truck.getId(), truck);
			
			List<Truck> tmpList = listByType.get(truck.getType());
			if (tmpList == null) {
				tmpList = new ArrayList<>();
				
				listByType.put(truck.getType(), tmpList);
			}
			
			tmpList.add(truck);
		}
	}
	
	@Override
	public List<Truck> list() {
		return Collections.unmodifiableList(list);
	}
	
	@Override
	public Map<Long, Truck> listByID() {
		return listByID;
	}
	
	@Override
	public Map<String, List<Truck>> listByType() {
		return listByType;
	}
}