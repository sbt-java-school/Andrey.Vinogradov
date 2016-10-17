package ru.sbt.home.task16.strategy.tointeger;

import org.apache.commons.lang3.time.DateUtils;
import ru.sbt.home.task16.strategy.AbstractStrategy;

import java.util.Calendar;
import java.util.Date;

/**
 * Конвертирует дату в Integer - количество дней с начала года
 */
public class DateToInteger extends AbstractStrategy<Date, Integer> {
	public DateToInteger(Class<Date> in, Class<Integer> out) {
		super(in, out);
	}
	
	@Override
	public Integer convert(Object value) {
		return (int) DateUtils.getFragmentInDays((Date) value, Calendar.YEAR);
	}
}