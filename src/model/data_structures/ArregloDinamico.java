package model.data_structures;

/**
 * 2019-01-23
 * Estructura de Datos Arreglo Dinamico de Strings.
 * El arreglo al llenarse (llegar a su maxima capacidad) debe aumentar su capacidad.
 * @author Fernando De la Rosa
 *
 */
public class ArregloDinamico<T extends Comparable<T>> implements IListaEncadenada<T>
{
	/**
	 * Capacidad maxima del arreglo
	 */
	private int tamanoMax;
	/**
	 * Numero de elementos presentes en el arreglo (de forma compacta desde la posicion 0)
	 */
	private int tamanoAct;
	/**
	 * Arreglo de elementos de tamaNo maximo
	 */
	private T elementos[];

	/**
	 * Construir un arreglo con la capacidad maxima inicial.
	 * @param max Capacidad maxima inicial
	 */
	public ArregloDinamico( int max )
	{
		elementos = (T[]) new Comparable[max];
		tamanoMax = max;
		tamanoAct = 0;
	}

	public void agregarAlFinal( T dato )
	{
		if ( tamanoAct == tamanoMax )
		{  // caso de arreglo lleno (aumentar tamaNo)
			tamanoMax = 2 * tamanoMax;
			T copia[] = elementos;
			elementos = (T[]) new Comparable[tamanoMax];
			for ( int i = 0; i < tamanoAct; i++)
			{
				elementos[i] = copia[i];
			} 
			System.out.println("Arreglo lleno: " + tamanoAct + " - Arreglo duplicado: " + tamanoMax);
		}	
		elementos[tamanoAct] = dato;
		tamanoAct++;
	}
	
	public Comparable[] darElementos() {
		return elementos;
	}
	public void InsertarElemento(T elemento, int pos)
	{
		T copia[] = elementos;
		elementos[pos] = elemento;
		for (int i = pos+1; i < tamanoAct; i++)
		{
			elementos[i]= copia[i-1]; 
		}
	}

	public T eliminarElPrimero()
	{
		T rta = elementos[0];
		for (int i = 0; i < elementos.length; i++) 
		{
			elementos[i] = elementos[i+1];
		}
		return rta; 
	}
	
	public T eliminarElUltimo()
	{
		T rta = elementos[tamanoAct];
		elementos[tamanoAct] = null;
		return rta; 
	}

	public T darPrimerElemento()
	{
		if (!estaVacio()) {
			return elementos[0];
		}
		return null;
	}
	
	public T darUltimoElemento()
	{
		if (!estaVacio()) {
			return elementos[tamanoAct];
		}
		return null;
	}
	
	public int darCapacidad() 
	{
		return tamanoMax;
	}

	public int contarDatos() {
		return tamanoAct;
	}
	
	public boolean estaVacio()
	{
		if(tamanoAct==0){
			return true;
		}
		else {
			return false;
		}
	}
	
	public int estaPresente(T elemento)
	{
		int rta = -1;
		for (int i = 0; i < tamanoAct+1; i++) 
		{
			if(elementos[i].equals(elemento))
			{
				rta = i;
				break;
			}
		}
		return rta; 
	}
	
	public void intercambiar(int pos1, int pos2) 
	{
		T temporal = null;
		elementos[pos1] = temporal;
		elementos[pos1] = elementos[pos2];
		elementos[pos2] = temporal; 
	}
	
	public void actualizarInfo(int pos, T actualizado)
	{
		elementos[pos] = actualizado; 
	}
	
	public T darElemento(int i) 
	{
		T rta = null;
		if (i<=tamanoAct) {
			rta = elementos[i];
		}
		return rta; 
	}

	public T buscar(T dato) {
		T rta = null;
		for (int i = 0; i < tamanoMax; i++) 
		{
			if(elementos[i].compareTo(dato)==0)
			{
				rta = elementos[i];
				break; 
			}
		}
		return rta;
	}

	@Override
	public void insert(T dato) {
		
		if ( tamanoAct == tamanoMax )
		{  // caso de arreglo lleno (aumentar tamaNo)
			tamanoMax = 2 * tamanoMax;
			T copia[] = elementos;
			elementos = (T[]) new Comparable[tamanoMax];
			for ( int i = 0; i < tamanoAct; i++)
			{
				elementos[i] = copia[i];
			} 
			System.out.println("Arreglo lleno: " + tamanoAct + " - Arreglo duplicado: " + tamanoMax);
		}	
		
		for (int i = 0; i < tamanoAct; i++) {
			elementos[i] = elementos[i+1];
		}
		elementos[0] = dato;
		tamanoAct++;
	}

	@Override
	public T borrar(T dato) {
		T rta = null;
		for (int i = 0; i < tamanoMax; i++) 
		{
			int posAct= i;
			if(elementos[i].compareTo(dato)==0)
			{
				rta = elementos[i];
				for(int j = posAct; j< tamanoMax; j++)
				{
					elementos[j] = elementos[j+1];
				}
				break;
			}
		}
		return rta;
	}
	@Override
	public T darPosicionDatos(int pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existeEnLaLista() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Comparable[] elementos() {
		return elementos;
	}
}
