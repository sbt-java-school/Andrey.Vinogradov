package ru.sbt.home.task16;

import ru.sbt.home.task16.strategy.Strategy;

/**
 * Реализация конвертации
 */
public class ConverterImpl extends AbstractConverter {
	@Override
	public <OUT> OUT convert(Object value, Class<OUT> resultClass) {
		if (value.getClass() == resultClass) {
			return (OUT) value;
		}
		
		Strategy<?, OUT> strat = getStrategy(value.getClass(), resultClass);
		
		if (strat == null) {
			return null;
		}
		
		return strat.convert(value);
	}
}