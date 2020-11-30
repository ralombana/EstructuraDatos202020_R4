package model.data_structures;

public class StackGenerico<T> 
{
	ListaEncadenadaSinComparable<T> pila;
	public StackGenerico()
	{
		pila = new ListaEncadenadaSinComparable<T>(); 
	}
	
	public void push(T element)
	{
		pila.agregarAlPrincipio(element);
	}
	
	public T pop()
	{
		T rta = pila.darPrimerElemento(); 
		pila.borrar(pila.darPrimerElemento());
		return rta;
	}
	
	public boolean isEmpty()
	{
		boolean rta = false;
		if(pila.contarDatos()==0)
		{
			rta = true;
		}
		return rta; 
	}
	
	public T top()
	{
		T rta = pila.darPrimerElemento(); 
		return rta;
	}
	
	public int size() 
	{
		return pila.contarDatos();
	}
}
