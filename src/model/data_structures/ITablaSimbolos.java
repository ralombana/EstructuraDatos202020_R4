package model.data_structures;

import java.util.List;

public interface ITablaSimbolos <K,V>{


	/**
	 * 	Agregar una dupla (K, V) a la tabla. 
	 * Si la llave K existe, se reemplaza su valor V asociado. 
	 * V no puede ser null. En este caso una llave K solo tiene asociado un valor V.
	 * @param key llave
	 * @param valor
	 */
	public void put(K key, V value);


	/**
	 * 	Obtener el valor V asociado a la llave K. 
	 * Se obtiene null solo si la llave K no existe. 
	 * Se usa el comparador sobre las llaves para saber si existe.
	 * @param llave
	 * @return valor buscado
	 */
	public V get(K key);

	/**
	 * Borrar la dupla asociada a la llave K. 
	 * Se obtiene el valor V asociado a la llave K. 
	 * Se obtiene null solo si la llave K no existe.
	 */
	public V remove(K key);

	/**
	 * 	Retorna true en el caso que la llave K se encuentre 
	 * almacenada en la Tabla, o false en el caso contrario
	 * @param key
	 * @return true si se hay algo false en lo contrario
	 */
	public boolean contains (K key);

	/**
	 * Retorna true si la Tabla NO tiene datos, o false en caso contrario.
	 * @return true si vacio, false en contrario
	 */
	public boolean isEmpty();

	/**
	 * 	Retorna el numero de duplas en la Tabla de Simbolos
	 * @return Retorna el numero de duplas en la Tabla de mbolos
	 */
	public int size();

	/**
	 * 	Retorna todas las llaves almacenadas en la Tabla.
	 * @return Retorna todas las llaves almacenadas en la Tabla.
	 */
	public List<K> keySet();

	/**
	 * 	Retorna todos los valores almacenados en la Tabla.
	 * @return	
	 * Retorna todos los valores almacenados en la Tabla.
	 */
	public List<V> valueSet();

}
