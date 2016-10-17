package ru.sbt.home.task16;

import ru.sbt.home.task16.strategy.Strategy;

/**
 * Базовый интерфейс
 * Конвертирует Object value в значение Class<OUT>
 */
public interface Converter {
	/**
	 * Основной метод конвертации
	 *
	 * @param value       входное значение
	 * @param resultClass класс конвертированного значения
	 * @param <OUT>       параметризация класса конвертированного значения
	 * @return результат конвертации
	 */
	public <OUT> OUT convert(Object value, Class<OUT> resultClass);
	
	/**
	 * Добавление стратегии конвертации
	 *
	 * @param strategy добавляемая стратегия
	 */
	public void addStrategy(Strategy strategy);
	
	/**
	 * Удаление стратегии конвертации
	 *
	 * @param strategy удаляемая сратегия
	 */
	public void removeStrategy(Strategy strategy);
}