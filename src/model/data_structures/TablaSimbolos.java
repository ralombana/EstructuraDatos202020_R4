package model.data_structures;

import clases.Pelicula;

public interface TablaSimbolos<K, V> 
{
	public void put(K llave, V valor);
	
	public V get(K llave);
	
	public V remove(K llave);
	
	public boolean contains(K llave);
	
	public boolean isEmpty();
	
	public int size();
	
	public ListaEncadenadaSinComparable<K> keySet();
	
	public ListaEncadenadaSinComparable<V> valueSet();
	
}
