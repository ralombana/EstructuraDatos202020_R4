package model.data_structures;

public class QueueGenerica <T>
{
	ListaEncadenadaSinComparable<T> cola;
	
	public QueueGenerica()
	{
		cola = new ListaEncadenadaSinComparable<T>(); 
	}
	
	public void enqueue(T elemento)
	{
		cola.agregarAlFinal(elemento);
	}
	
	public T dequeue()
	{
		return cola.darUltimoElemento(); 
	}
	
	public boolean isEmpty()
	{
		boolean rta = false;
		if(cola.contarDatos()==0)
		{
			rta = true;
		}
		return rta; 
	}
	
	public T peek()
	{
		return cola.darPrimerElemento(); 
	}
	
	public int size() 
	{
		return cola.contarDatos();
	}
}
