package model.data_structures;

import java.util.ArrayList;

public class Vertex<K extends Comparable<K>, T extends Comparable<T>>
{
	/**
	 * El id de este vertice
	 */
	K id;
	
	/**
	 * El valor contenido en el vertice
	 */
	private T valor;
	
	/**
	 * ID que dice a cual componente fuertemente conectada pertenece ese vertice
	 */
	int idCC;
	
	/**
	 * Tabla que guarda los arcos
	 * Sus llaves son los id del destino diferente al actual
	 * Sus valores son de tipo Edge
	 */
	TablaHashLinearProbing2<K, Edge<K, T>> tablaArcos = new TablaHashLinearProbing2<K, Edge<K, T>>(500); ;
	
	/**
	 * Tabla que guarda los vertices, sus valores son de tipo Vertex
	 */
	TablaHashLinearProbing2<K, Vertex<K, T>> tablaVertices = new TablaHashLinearProbing2<K, Vertex<K, T>>(500); ;
	
	/**
	 * Booleano que muestra si el Vertex actual está o no está marcado
	 */
	boolean marcado; 
	
	
	/**
	 * Constructor del Vertex
	 * @param pId id 
	 * @param pValor los datos que se quieran guardar 
	 */
	public Vertex(K pId, T pValor)
	{
		id = pId;
		valor = pValor;
		marcado = false;
	}
	
	/**
	 * Retorna el id de este vertex
	 * @return id
	 */
	public K getId()
	{
		return id;
	}
	
	/**
	 * El valor del vertex
	 * @return valor
	 */
	public T getInfo()
	{
		return getValor();
	}
	
	/**
	 * Retorna el estado de la marca
	 * @return marcado, puede ser true o false
	 */
	public boolean getMark()
	{
		return marcado;
	}
	
	/**
	 * Se agrega un nuevo arco a la tablaArcos
	 * Para hacer las llaves de los arcos el metodo toma como llave el id del vertex
	 * distinto del actual. 
	 * EJEMPLOS: 
	 * -Si el arco sale de este vertex, entonces toma el vertex al que llega
	 * -Si el arco llega a este vertex, entonces toma el vertex del que sale ese arco
	 * Siempre va a tomar el vertex distinto la actual, para esto usa el if 
	 * @param nuevoArco el arco que se desea agregar
	 * Actualiza siempre la lista de vertices
	 */
	public void addEdge(Edge<K, T> e)
	{
		if(e.getDest()== this)
		{
			tablaArcos.put(e.getSource().getId(), e);
			tablaVertices.put(e.getSource().getId(), e.getSource());
		}
		else
		{
			tablaArcos.put(e.getDest().getId(), e);	
			tablaVertices.put(e.getDest().getId(), e.getDest());
		}
	}
	
	/**
	 * Este vertice queda marcado, marcado se vuelve verdadero
	 */
	public void mark()
	{
		marcado = true;
	}
	
	/**
	 * Este vertice queda desmarcado, marcado se vuelve false
	 */
	public void unMark()
	{
		marcado = false;
	}
	
	/**
	 * Retorna cuantos arcos están saliendo del vertex actual
	 * Para esto pide la lista encadenada de los valores de tablaArcos, 
	 * esa lista retorna Edges, a los cuales se les pide dar el vertex del que salen
	 * Si salen del vertex actual aumenta el contador
	 */
	public int outDegrees()
	{
		ListaEncadenadaSinComparable<T> arcos = (ListaEncadenadaSinComparable<T>) tablaArcos.valueSet(); 
		int numSalientes = 0; 
		
		for (int i = 0; i < arcos.contarDatos(); i++) 
		{
			Edge<K,T> act = (Edge<K, T>) arcos.darElemento(i); 
			
			if(act.getSource() == this)
			{
				numSalientes++;
			}
		}
		return numSalientes;
	}
	
	/**
	 * Retorna cuantos arcos están llegando al vertex actual
	 * Para esto pide la lista encadenada de los valores de tablaArcos, 
	 * esa lista retorna Edges, a los cuales se les pide dar el vertex al que llegan
	 * Si llegan al verex actual aumenta el contador
	 */
	public int inDegrees()
	{
		ListaEncadenadaSinComparable<T> arcos = (ListaEncadenadaSinComparable<T>) tablaArcos.valueSet(); 
		int numSalientes = 0; 
		
		for (int i = 0; i < arcos.contarDatos(); i++) 
		{
			Edge<K,T> act = (Edge<K, T>) arcos.darElemento(i); 
			
			if(act.getDest() == this)
			{
				numSalientes++;
			}
		}
		return numSalientes;
	}
	
	public ListaEncadenadaSinComparable<Edge<K, T>> listaInDegrees()
	{
		ListaEncadenadaSinComparable<Edge<K, T>> rta = new ListaEncadenadaSinComparable<Edge<K, T>>();
		ArrayList<Edge<K, T>> arcos = (ArrayList<Edge<K, T>>) this.edges().valueSet(); 
		if(arcos.size() == 1)
		{
			rta.agregarAlFinal(arcos.get(0));
			return rta; 
		}
		else
		{
			for (int i = 0; i < arcos.size(); i++) 
			{
				Edge<K,T> act = (Edge<K, T>) arcos.get(i); 
				if(act.getDest() == this)
				{
					rta.agregarAlPrincipio((Edge<K, T>) act);;
				}
			}
			return rta;
		}
	}
	
	/**
	 * Retorna el arco entre el vertex actual y el vertex por parametro
	 * @param vertex, vertex conectado al actual
	 * @return null si no hay arco
	 * @return Edge, el arco entre las dos estaciones
	 */
	public Edge<K, T> getEdge(K llave)
	{
		Edge<K, T> rta =  tablaArcos.get(llave);
		return rta; 
	}
	
	public boolean hasEdge(K vertex)
	{
		return tablaArcos.contains(vertex);
	}
	/**
	 * Retorna la tabla de hash que guarda los arcos
	 * @return tablaArcos, tabla con los arcos adyacentes
	 */
	public TablaHashLinearProbing2<K, Edge<K, T>> edges()
	{
		return tablaArcos;
	}
	
	/**
	 * Retorna la tabla de hash que guarda los vertices adyacentes
	 * @return tablaVertices, tabla con los vertices adyacentes
	 */
	public TablaHashLinearProbing2<K, Vertex<K, T>> vertices()
	{
		return tablaVertices; 
	}
	
	public Iterable edgess()
	{
		return tablaArcos.valueSet();
	}

	public T getValor() {
		return valor;
	}

	public void setValor(T valor) {
		this.valor = valor;
	}
	
	public void revertAllEdges()
	{
		ArrayList<Edge<K, T>> listaEdges = (ArrayList<Edge<K, T>>) edges().valueSet(); 

		if(listaEdges.size()>0)
		{
			if(listaEdges.size() == 1)
			{
				listaEdges.get(0).revert();
			}
			else
			{
				for (int i = 0; i < listaEdges.size(); i++) 
				{
					listaEdges.get(i).revert();
				}
			}

		}
	}
	
}
