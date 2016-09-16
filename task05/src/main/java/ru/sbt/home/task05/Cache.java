package ru.sbt.home.task05;

import java.lang.annotation.*;

@Target(ElementType.METHOD) // method only
@Retention(RetentionPolicy.RUNTIME) // runtime visible
@Inherited // наследуется при оверрайде
@interface Cache {
	/**
	 * Тип кэша хранения, по умолчанию - DEFAULT
	 *
	 * @return тип кэша
	 */
	CacheType value() default CacheType.DEFAULT;
}