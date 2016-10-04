package ru.sbt.home.task13.packets;

import java.io.Serializable;

/**
 * Интерфейс пакета обмена между сервером и клинетом.
 * Содержит данные пакета для передачи на клиент или сервер и механизм их обработки в контексте (выбирается в каждом пакете отдельно).
 *
 * @param <Q> тип данных пакета, наследуется от Serializable
 * @param <C> тип данных контекста обработки
 */
public interface Packet<Q extends Serializable, C> extends Serializable {
	/**
	 * Возвращает данные пакета
	 *
	 * @return данные для передачи в пакете
	 */
	public Q getQuery();
	
	/**
	 * Обработка пакета на основе переданных данных и нужном контексте.
	 * Возвращает ответный пакет, null - если ответный пакет не нужен.
	 *
	 * @param context контекст обработки данных
	 * @return ответный пакет
	 */
	public Packet process(C context);
}