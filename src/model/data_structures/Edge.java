package model.data_structures;

public class Edge<K extends Comparable<K>, V extends Comparable<V>> implements Comparable
{
	/**
	 * Peso de ese arco, en este caso el promedio de horas de los ciclistas que han pasado por ese camino
	 */
	Viaje viaje;

	
	/**
	 * Vertice del que sale el arco
	 */
	Vertex<K, V> source;
	
	/**
	 * Vertice al que llega el arco
	 */
	Vertex<K, V> destination;
	
	/**
	 * Crea un vertice segun el origen y el destino que le den 
	 * @param origen vertice del que sale
	 * @param destino vertice al que entra
	 */
	public Edge(Vertex origen, Vertex destino, Viaje viaje)
	{
		source = origen;
		destination = destino;
		this.viaje = viaje; 
	}
	
	/**
	 * Retorna vertice del source
	 * @return source
	 */
	public Vertex<K, V> getSource()
	{
		return source;
	}

	/**
	 * Retorna vertice del destino
	 * @return destination
	 */
	public Vertex<K, V> getDest()
	{
		return destination;
	}
	
	/**
	 * El peso del arco actual
	 * @return weight 
	 */
	public Viaje getViaje()
	{
		return viaje;
	}
	
	/**
	 * Cambia el peso por el valor que entra por parametro
	 * @param newWeight nuevo valor del peso que entra por parametro
	 */
	public void setViaje(Viaje viaje)
	{
		this.viaje = viaje; 
	}

	public void revert()
	{
		Vertex<K, V> temp = source;
		source = destination;
		destination = temp; 
	}
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(((Edge) o).getViaje().getTime() > this.getViaje().getTime())
			return 1;
		else if(((Edge) o).getViaje().getTime()  < this.getViaje().getTime())
			return -1;
		else
			return 0;
	}
	
}
