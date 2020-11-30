package model.data_structures;

public class NodoHash <K extends Comparable<K>, V > implements Comparable<NodoHash<K,V>>
{
	
	private K key;
	
	
	private V value;
	
	public NodoHash(K pKey,V pValue)
	{
		key = pKey;
		value = pValue;
	}
	
	public K getKey( )
	{
		return key;
	}
	
	public V getValue( )
	{
		return value;
	}
	
	public void changeValue( V newValue )
	{
		value = newValue;
	}

	
	public int compareTo(NodoHash<K, V> o) 
	{
		return 0;
	}

	@SuppressWarnings("unchecked")
	public void deleteLP() 
	{
		key = (K) "EMPTY";
		value = null;
	}
	
}
