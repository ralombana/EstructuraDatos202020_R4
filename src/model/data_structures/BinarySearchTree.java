package model.data_structures;

public class BinarySearchTree<K extends Comparable<K>, V> implements TablaSimbolosOrdenada<K, V>
{
	Nodo raiz; 
	class Nodo 
	{
		K key;
		V valor;
		Nodo hijoIzquierdo;
		Nodo hijoDerecho;
		
		public Nodo(K pLlave, V pValor)
		{
			key = pLlave;
			valor = pValor;
		}
		
		public K darLlave()
		{
			return key;
		}
		
		public V darValor()
		{
			return valor; 
		}
		
		public int darAltura()
		{
			int alturaIzquierda = 0;
			int alturaDerecha = 0;
			
			if(this.hijoIzquierdo!=null)
			{
				alturaIzquierda = this.hijoIzquierdo.darAltura();
			}
			if(this.hijoDerecho!=null)
			{
				alturaIzquierda = this.hijoDerecho.darAltura();
			}
			
			return 1 + Math.max(alturaDerecha, alturaIzquierda); 
		}
		
		public int darSize()
		{
			int size = 1;
			if(hijoIzquierdo!=null)
			{
				size += hijoIzquierdo.darSize();
			}
			if(hijoDerecho != null)
			{
				size += hijoDerecho.darSize();
			}
			return size; 
		}
		
		public ListaEncadenada<K> darKeySet()
		{
			ListaEncadenada<K> lista = new ListaEncadenada<K>();
			lista.agregarAlPrincipio(this.darLlave());
			if(hijoDerecho != null)
			{
				ListaEncadenada<K> listaDerecha = hijoDerecho.darKeySet();
				for (int i = 0; i < listaDerecha.contarDatos(); i++) 
				{
					lista.agregarAlFinal(listaDerecha.darElemento(i));
				}
			}
			
			if(hijoIzquierdo!=null)
			{
				ListaEncadenada<K> listaIzquierda = hijoDerecho.darKeySet();
				for (int i = 0; i < listaIzquierda.contarDatos(); i++) 
				{
					lista.agregarAlPrincipio(listaIzquierda.darElemento(i));
				}
			}
			return lista; 
		}
	}
	
	
	@Override
	public int size() 
	{
		int rta = 0;
		if(raiz != null)
		{
			rta = raiz.darSize();
		}
		else 
		{
			rta = 0;
		}
		return rta;
	}

	@Override
	public boolean isEmpty() {
		boolean rta = false;
		if(raiz ==null)
		{
			rta = true;
		}
		return rta;
	}

	@Override
	public V get(K key) 
	{
		Nodo nodoAct = raiz;
		if(raiz == null)
		{
			return null;
		}
		
		else
		{
			while(true)
			{
				if(nodoAct== null)
				{
					return null; 
				}
				else if(nodoAct.darLlave().compareTo(key) == 0)
				{
					return nodoAct.darValor();
				}
				else if(nodoAct.darLlave().compareTo(key) < 0)
				{
					nodoAct = nodoAct.hijoDerecho;
				}
				else if(nodoAct.darLlave().compareTo(key) > 0)
				{
					nodoAct = nodoAct.hijoIzquierdo;
				}
			}
		}
	}

	@Override
	public int getHeight(K key)
	{
		int altura = -1;
		Nodo nodoAct = raiz;
		if(raiz == null)
		{
			return altura;
		}
		
		else
		{
			altura = 0; 
			while(true)
			{
				if(nodoAct== null)
				{
					altura = -1;
					return altura; 
				}
				else if(nodoAct.darLlave().compareTo(key) == 0)
				{
					return altura;
				}
				else if(nodoAct.darLlave().compareTo(key) < 0)
				{
					nodoAct = nodoAct.hijoDerecho;
					altura++;
				}
				else if(nodoAct.darLlave().compareTo(key) > 0)
				{
					nodoAct = nodoAct.hijoIzquierdo;
				}
			}
		}
	}

	@Override
	public boolean contains(K key) 
	{
		boolean rta = false;
		Nodo nodoAct = raiz;
		if(raiz == null)
		{
			return rta;
		}
		
		else
		{
			while(true)
			{
				if(nodoAct== null)
				{
					return rta; 
				}
				else if(nodoAct.darLlave().compareTo(key) == 0)
				{
					rta = true;
					return rta;
				}
				else if(nodoAct.darLlave().compareTo(key) < 0)
				{
					nodoAct = nodoAct.hijoDerecho;
				}
				else if(nodoAct.darLlave().compareTo(key) > 0)
				{
					nodoAct = nodoAct.hijoIzquierdo;
				}
			}
		}
	}

	@Override
	public void put(K key, V val) 
	{
		Nodo agregado = new Nodo(key, val);
		if(raiz == null)
		{
			agregado = raiz;
		}
		else 
		{
			Nodo nodoAct = raiz;
			Nodo padre;
			while(true)
			{
				padre = nodoAct; 
				if(key.compareTo((K) nodoAct.darLlave())<0)
				{
					nodoAct = nodoAct.hijoIzquierdo;
					
					if(nodoAct == null)
					{
						padre.hijoIzquierdo = agregado;
						return;
					}
					
					else
					{
						nodoAct = nodoAct.hijoDerecho;
						if(nodoAct == null)
						{
							padre.hijoDerecho = agregado;
							return;
						}
					}
				}
			}
		}
	}

	@Override
	public int height() {
		if(raiz != null)
		{
			return raiz.darAltura();
		}
		else 
		{
			return 0; 
		}
	}

	@Override
	public K min() 
	{
		K rta = null;
		Nodo nodoAct = raiz;
		
		if(nodoAct == null)
		{
			rta = null;
		}
		else
		{
			while(nodoAct != null)
			{
				rta = nodoAct.darLlave();
				nodoAct = nodoAct.hijoIzquierdo;
			}
		}
		return rta;
	}

	@Override
	public K max() 
	{
		K rta = null;
		Nodo nodoAct = raiz;
		
		if(nodoAct == null)
		{
			rta = null;
		}
		else
		{
			while(nodoAct != null)
			{
				rta = nodoAct.darLlave();
				nodoAct = nodoAct.hijoDerecho;
			}
		}
		return rta;
		
	}

	@Override
	public ListaEncadenada<K> keySet() 
	{
		ListaEncadenada<K> lista = raiz.darKeySet();
		return lista;
	}

	@Override
	public ListaEncadenada<K> keysInRange(K init, K end) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListaEncadenadaSinComparable<V> valuesInRange(K init, K end) {
		// TODO Auto-generated method stub
		return null;
	}

}
