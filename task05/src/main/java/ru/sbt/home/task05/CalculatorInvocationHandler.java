package ru.sbt.home.task05;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CalculatorInvocationHandler implements InvocationHandler {
	private final Calculator calc;
	
	public CalculatorInvocationHandler(Calculator calc) {
		this.calc = calc;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (!method.isAnnotationPresent(Cache.class)) {
			return method.invoke(calc, args);
		}
		
		CacheType ct = method.getAnnotation(Cache.class).value();
		
		Object result = ct.get(method, args);
		
		if (result == null) {
			result = method.invoke(calc, args);
			
			ct.put(method, args, result);
		}
		
		return method.getReturnType().cast(result);
	}
}