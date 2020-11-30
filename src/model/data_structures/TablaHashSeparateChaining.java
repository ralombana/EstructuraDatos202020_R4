package model.data_structures;

import java.util.ArrayList;
import java.util.List;

public class TablaHashSeparateChaining<K extends Comparable<K>, V> implements ITablaSimbolos <K, V>{

	private Nodo[] valueList;
	private int M;

	/**
	 * Crea una tabla de has con manejo de colisiones separate Chaining
	 * @param pCapacidad Hace referencia a la cantidad de filas que va a tener la tabla.
	 */
	public TablaHashSeparateChaining(int pCapacidad) {
		M = pCapacidad;
		valueList = new Nodo[pCapacidad];
		
	}

	private int hash (K key) {return ((key.hashCode() & 0x7fffffff) % M);}

	@Override
	public void put(K key, V value) {
		// TODO Auto-generated method stub
		Integer hashKey = hash(key);
		Nodo nuevo = new Nodo(key, value);
		nuevo.setSiguiente(getNodo(hashKey));
		valueList[hashKey] = nuevo;
	}

	public Nodo getNodo(K key){return valueList[hash(key)];}
	private  Nodo getNodo (Integer hashKey){return valueList[hashKey];}
	

	
	/**
	 * Este metodo solamente va a retornar el primer elemento asociado a esa llave, (Ultimo ananido).
	 */
	@Override
	public V get(K key) {
		int valorHash = hash(key);
		Nodo aBuscar = valueList[valorHash];
		if (aBuscar==null) return null;
		while(aBuscar.darKey()!=key && aBuscar!=null) {
			aBuscar = aBuscar.darSiguiente();
		}
		return (V) aBuscar.darValor();
	}

	/**
	 * Este metodo va a eliminar TODOS los elementos asociados a esa llave
	 * Se va a retornar solamente el primer elemento agregado.
	 */
	@Override

	public V remove(K key) {
		V rta = null;
		int hashKey = hash(key);
		Nodo act = getNodo(hashKey);
		if(act == null ) return null;
		if(act.darKey().equals(key)) {
			rta = (V) act.darValor();
			valueList[hashKey] = act.darSiguiente();
		}else {
			boolean centinela = true;
			while(act.darSiguiente()!=null && centinela) {
				if(act.darSiguiente().darKey().equals(key)) {
					rta = (V) act.darSiguiente().darValor();
					centinela =false;
					act.setSiguiente(act.darSiguiente().darSiguiente());
				}
				act = act.darSiguiente();
			}
		}
		return rta;
	}

	@Override
	public boolean contains(K key) {
		Nodo act = getNodo(key);
		while(act!=null) {
			if(act.darKey().equals(key)) return true;
			act = act.darSiguiente();
		}
		return false;
	}


	@Override
	public boolean isEmpty() {
		for (Nodo nodos : valueList) {
			if(nodos!=null) return false;
		}
		return true;
	}

	@Override
	public int size() {
		int contador = 0;
		for (Nodo nodos : valueList) {
			Nodo act = nodos;
			while(act!=null) {
				++contador;
				act = act.darSiguiente();
			}
		}
		return contador;
	}

	@Override
	public List<K> keySet() {
		if(isEmpty()) return null;
		List <K> listaLlaves = new ArrayList<K>();
		for (Nodo nodo : valueList) {
			Nodo nodoAct = nodo;
			while(nodoAct!=null) {
				if(nodoAct.darKey()!=null) listaLlaves.add((K) nodoAct.darKey());
				nodoAct = nodoAct.darSiguiente();
			}
		}
		return listaLlaves;
	}

	@Override
	public List<V> valueSet() {
		if(isEmpty()) return null;
		List <V> listaValores = new ArrayList<V>();
		for (Nodo nodo : valueList) {
			Nodo nodoAct = nodo;
			while(nodoAct!=null) {
				if(nodoAct.darValor()!=null) listaValores.add((V) nodoAct.darValor());
				nodoAct = nodoAct.darSiguiente();
			}
		}
		return listaValores;

	}
	
	/**
	 * Este metodo retorna siempre el primer elemento que se encuentre en el primer nodo, esto significa el ultimo
	 * elemento agregado al primero nodo no nulo.
	 */
	public V darPrimerElemento() {
		V rta = null;
		int primerElemento = 0;
		while(valueList[primerElemento]==null) primerElemento++;
		Nodo act = valueList[primerElemento];
		return (V) act.darValor();
	}
	
	/**
	 * Este metodo retorna siempre el primer elemento que se encuentre en el ultimo nodo, esto significa el ultimo
	 * elemento agregado al ultimo nodo no nulo de la lista.
	 */
	public V darUltimoElemento() {
		V rta = null;
		int ultimoElemento = M-1;
		while(valueList[ultimoElemento]==null) ultimoElemento--;
		Nodo act = valueList[ultimoElemento];
		return (V) act.darValor();
	}




}
