package model.data_structures;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class TablaHashLinearProbing2 <K, V> implements ITablaSimbolos <K, V>{
	

	K[] listaLlaves;
	V[] listaValores;
	int M;
	int N;
	
	public TablaHashLinearProbing2 (int pCapacidadInicial) {
		M = pCapacidadInicial;
		listaValores = (V[]) new Object [pCapacidadInicial];
		listaLlaves = (K[]) new Object[pCapacidadInicial];
	}
	
	private int hash (K key) {return ((key.hashCode() & 0x7fffffff) % M);}
			
	@Override
	public void put(K key, V value) {
		if (N >= M/2) reHash();  // double M (see text)
		   int i;
		   for (i = hash(key); listaLlaves[i] != null; i = (i + 1) % M)
		if (listaLlaves[i].equals(key)) { listaValores[i] = value; return; } listaLlaves[i] = key;
		listaValores[i] = value;
		N++;
	}

	private void reHash() {
		TablaHashLinearProbing2<K, V> t;
	      t = new TablaHashLinearProbing2<K, V>(M*2);
	      for (int i = 0; i < M; i++)
	if (listaLlaves[i] != null) t.put(listaLlaves[i], listaValores[i]);
	      listaLlaves = t.listaLlaves; listaValores = t.listaValores; M = t.M;
		System.out.println("Se ha realizado un rehash de la tabla con manejo de colisiones linear probing");
	}

	@Override
	public V get(K key) {
		int hashKey = hash(key);
		int cantidadDeDatosPasados = 0;
		while(cantidadDeDatosPasados<M) {
			if(listaLlaves[hashKey]==null) 
			{
				return null;
			}
			if(listaLlaves[hashKey].equals(key)) {
				return listaValores[hashKey];
			}
			else {
				hashKey = (hashKey+1)%M;
				cantidadDeDatosPasados ++;
			}
		}
		return null;
	}

	@Override
	public V remove(K key) {
		int hashKey = hash(key);
		int cantidadDeDatosPasados = 0;
		while(cantidadDeDatosPasados<M) {
			if(listaLlaves[hashKey].equals(key)) {
				V borrado = listaValores[hashKey];
				listaValores[hashKey] = null;
				listaLlaves[hashKey] = null;
				return borrado;
			}else {
				hashKey = (hashKey+1)%M;
				cantidadDeDatosPasados ++;
			}
		}
		return null;
	}

	@Override
	public boolean contains(K key) {
		int hashKey = hash(key);
		int cantidadDeDatosPasados = 0;
		while(cantidadDeDatosPasados<M) {
			if(listaLlaves[hashKey] != null && listaLlaves[hashKey].equals(key)) {
				return true;
			}else {
				hashKey = (hashKey+1)%M;
				cantidadDeDatosPasados ++;
			}
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		for (int i = 0; i<M; i++) {
			if(listaValores[i]!=null) return false;
		}
		return true;
	}

	@Override
	public int size() {
		return N;
	}

	@Override
	public List<K> keySet() {
		ArrayList<K> listaRta  = new ArrayList<K>();
		for (K k : listaLlaves) {
			if(k!=null)listaRta.add(k);
		}
		return listaRta;
	}
	

	@Override
	public List<V> valueSet() 
	{
		ArrayList<V> listaRta  = new ArrayList<V>();
		for (V v : listaValores) {
			if(v!=null)listaRta.add(v);
		}
		return listaRta;
	}
	
	public V darPrimerElemento() {
		for(int i =0 ; i < N; i++)
		{
			if(listaValores[i] != null) return listaValores[i];
		}
		return null;
	}
	
	public V darUltimoElemento() {
		for(int i =M-1 ; i >0; i--)
		{
			if(listaValores[i] != null)
				{return listaValores[i];}
		}
		return null;
	}
	
	public int[] pruebaInexistentes()
	{
		int[] container = new int[200];
		boolean seLLeno = false;
		int j = 0; 
		
		for(int i =0; i<M && j <200; i ++)
		{
			if(listaValores[i]==null)
			{
				container[j] = i;
				j++;
			}
			i++;
		}
		return container;
	
	}
	public String[] pruebaExistentes()
	{
		String[] container = new String[800];
		boolean seLLeno = false;
		int j = 0; 
		
		for(int i =0; i<M && j <800; i ++)
		{
			if(listaValores[i]!=null)
			{
				container[j] = (String)listaLlaves[i];
				j++;
			}
			i++;
		}
		return container;
	}

	

}
