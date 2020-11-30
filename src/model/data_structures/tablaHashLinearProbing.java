package model.data_structures;

import clases.Director;


public class tablaHashLinearProbing < K extends Comparable<K>, V> implements TablaSimbolos<K, V> 
{

	private double factorCarga;

	private int elementos;

	private ArregloDinamico<NodoHash<K,V>> mapa;

	private int a;

	private int b;

	private int c;

	private int d; 


	public tablaHashLinearProbing(int size) 
	{

		a  = (int) (Math.random() * (c-1)+1);
		b  = (int) (Math.random() * (c-1)+1);
		c= getPrime(2*size);
		d= getPrime(c);
		mapa = new ArregloDinamico<NodoHash<K,V>> (d);
	}


	public void put(K key, V value) 
	{
		int p = getPos(key);
		NodoHash<K,V> act = mapa.darElemento(p);
		if(act == null || act.getKey().equals("EMPTY"))
		{
			NodoHash<K,V> nuevo = new NodoHash<K,V>(key, value);
			mapa.actualizarInfo(p, nuevo);
			elementos++;
		}
		else if(act.getKey().equals(key))
			act.changeValue(value);
		else
			putRecursiveVersion(p + 1, key, value);


		verificarInvariante();
	}

	private void putRecursiveVersion(int pos, K key, V value)
	{
		if(pos >= (d - 1))
			pos = 0;
		NodoHash<K,V> act = mapa.darElemento(pos);
		if(act == null || act.getKey().equals("EMPTY"))
		{
			NodoHash<K,V> nuevo = new NodoHash<K,V>(key, value);
			mapa.actualizarInfo(pos, nuevo);
			elementos++;
		}
		else if(act.getKey().equals(key))
			act.changeValue(value);

		else
			putRecursiveVersion(pos + 1, key, value);
	}


	@Override
	public V get( K key ) 
	{
		int pos = getPos(key);
		NodoHash<K,V> act = mapa.darElemento(pos);
		if(act != null && key.equals(act.getKey()))
			return act.getValue();
		else 
			return getRecursiveVersion(pos + 1, key);
	}

	private V getRecursiveVersion(int pos, K key) 
	{
		if(pos >= (d-1))
			pos = 0;
		NodoHash<K,V> act = mapa.darElemento(pos);
		if(act != null && key.equals(act.getKey()))
			return act.getValue();
		else if(key.equals("EMPTY") || (act != null && act.getKey() != null))
			return getRecursiveVersion(pos + 1, key);
		else
			return null; 
	}


	@Override
	public V remove( K key) 
	{
		int pos = getPos(key);
		V retorno = null;
		NodoHash<K,V> act = mapa.darElemento(pos);
		if(act != null && key.equals(act.getKey()))
		{
			retorno = act.getValue();
			act.deleteLP();
			elementos=elementos-1;
		}
		else 
			return deleteRecursiveVersion(pos + 1, key);

		verificarInvariante();
		return retorno;
	}

	private V deleteRecursiveVersion(int pos, K key) 
	{
		if(pos >= (d-1))
			pos = 0;
		NodoHash<K,V> act = mapa.darElemento(pos);
		if(act != null && key.equals(act.getKey()))
		{
			V retorno = act.getValue();
			act.deleteLP();
			elementos=elementos-1;
			return retorno;
		}
		else if(key.equals("EMPTY") || (act != null && act.getKey() != null))
			return getRecursiveVersion(pos + 1, key);
		else
			return null; 
	}


	@Override
	public boolean contains( K key) 
	{
		return get(key) != null ? true:false;
	}

	/**
	 * Revisa si la tabla se encuentra vacia.
	 * @return True si esta vacia, false de lo contrario.
	 */
	public boolean isEmpty( )
	{
		return elementos > 0 ? false : true;
	}


	public int size( )
	{
		return elementos;
	}

	public ListaEncadenadaSinComparable<K> keySet() 
	{
		ListaEncadenadaSinComparable<K> result = new ListaEncadenadaSinComparable<K>();
		int i = 0;
		while(i < d)
		{
			NodoHash<K,V> temp = mapa.darElemento(i);
			if(temp != null && temp.getKey() != null && temp.getKey() != "EMPTY")
				result.agregarAlFinal(temp.getKey());	
			i++;
		}
		return result;
	}


	public ListaEncadenadaSinComparable<V> valueSet( )
	{
		ListaEncadenadaSinComparable<V> rpta = new ListaEncadenadaSinComparable<V>();
		int i = 0;
		while(i < d)
		{
			NodoHash<K,V> temp = mapa.darElemento(i);
			if(temp != null && temp.getValue() != null )
				rpta.agregarAlFinal(temp.getValue());	

			i++;
		}
		return rpta;
	}

	public int getPos(K key)
	{
		int hInicial =  ((a * key.hashCode()) + b) % c;
		int hFinal = Math.abs(hInicial) % d;
		return hFinal;
	}
	
	public static int getPrime(int x)
	{
		if (x<= 1)
			return 2;
		int numPrimo = (int) x;
		boolean joa = false;
		while(!joa)
		{
			numPrimo += 1;
			if(primo(numPrimo))
				joa = true;
		}
		return numPrimo; 
	}
	
	public static boolean primo( int x)
	{
		if(x <= 1)
			return false;
		if(x <= 3)
			return true;
		if(x % 2 ==0 )
			return false;
		for(int i = 6; i < ((int) Math.sqrt(x) + 1); i+= 7)
		{
			if(x % i == 0 || x % (i + 2) == 0)
				return false;
		}
	
		return true;
	}

	private void verificarInvariante( )
	{
		assert factorCarga < 0.75;
		assert factorCarga >= 0;
	}

	public Iterable<V> values()
	{
		Queue<V> rpta = new Queue<V>();
		int i = 0;
		while(i < d)
		{
			NodoHash<K,V> temp = mapa.darElemento(i);
			if(temp != null && temp.getValue() != null )
				rpta.enqueue(temp.getValue());	

			i++;
		}
		return rpta;
	}


}