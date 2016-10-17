package ru.sbt.home.task16;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.collections4.map.MultiKeyMap;
import ru.sbt.home.task16.strategy.Strategy;

/**
 * Вспомогательный класс
 * Реализовано добавление и удаление стратегии
 * Стратегии хранятся в мапе по 2м ключам - входной и возвращаемый класс
 */
public abstract class AbstractConverter implements Converter {
	private final MultiKeyMap<Class, Strategy> strats = MultiKeyMap.multiKeyMap(new HashedMap());
	
	/**
	 * Вспомогательный метод получения стратегии
	 *
	 * @param in  класс входящего значения
	 * @param out класс возвращаемого значения
	 * @return
	 */
	protected Strategy getStrategy(Class in, Class out) {
		return strats.get(in, out);
	}
	
	@Override
	public void addStrategy(Strategy strategy) {
		strats.put(strategy.getInClass(), strategy.getOutClass(), strategy);
	}
	
	@Override
	public void removeStrategy(Strategy strategy) {
		strats.remove(strategy.getInClass(), strategy.getOutClass());
	}
}