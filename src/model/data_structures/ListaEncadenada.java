package model.data_structures;

public class ListaEncadenada<T extends Comparable<T>> implements IListaEncadenada<T>{

	private Nodo first,last;
	
	private int tamano;

	public class Nodo{

		private T info;

		private Nodo siguiente = null;

		public Nodo(T informacion) {
			info = informacion;
		}

		public Nodo darSiguiente() {
			return siguiente;
		}

		public void cambiarSiguiente(Nodo nuevo) {
			siguiente = nuevo;
		}

		public T darInfo() {
			return info;
		}

	}

	public ListaEncadenada(){
		first = null;
		last = null;
	}

	public Nodo getFirst()
	{
		return first;
	}
	
	public Nodo getLast() {
		return last;
	}
	
	public int contarDatos() {
		return tamano;
	}

	public void insert(T dato) {
		if(first == null) {
			first = new Nodo(dato);
			last = first;
			tamano++;
		}
		else {
			Nodo actual = first;
			boolean termine = false;
			while(!termine) {
				if(actual.darSiguiente() == null) {
					actual.cambiarSiguiente(new Nodo(dato));
					last = actual.darSiguiente();
					tamano++;
					termine = true;
				}
				actual = actual.darSiguiente();
			}
		}
	}

	public T darPosicionDatos(int posicion) { 
		Nodo devolverDatos = null;
		boolean finalizar= true;
		Nodo actual = first;
		int posicionActual = 0;
		if(!existeEnLaLista()) {
			return null;
		}
		else {
			while(!finalizar && posicionActual<contarDatos()) {
				if(posicionActual == posicion) {
					devolverDatos = actual;
					finalizar = false;
				}
				posicionActual++;
				actual = actual.darSiguiente();
			}
		}
		return devolverDatos == null? null:devolverDatos.darInfo();
	}

	public boolean existeEnLaLista() {
		return first == null? true:false ;
	}

	public void agregarAlPrincipio(T dato) {
		Nodo nodo = new Nodo(dato);
		if(first==null)
		{
			first = nodo;
		}
		else
		{
			nodo.cambiarSiguiente(first);
			first = nodo;
		}
		tamano++;
	}

	public T borrar(T dato) {
		boolean acabar = false;
		if(dato.equals(first.darInfo())) {
			first = first.darSiguiente();
		}
		else {
			Nodo anterior = first;
			Nodo actual = first.darSiguiente();
			while(!acabar && actual != null) {
				if(actual.darInfo().equals(dato)) {
					anterior.cambiarSiguiente(actual.darSiguiente());
					acabar = true;
				}
				actual = actual.darSiguiente();
				anterior = anterior.darSiguiente();

			}
		}
		return dato;
	}

	public T borrarPorPosicion(int pos) {
		T quitar = darPosicionDatos(pos);
		return borrar(quitar);
	}


	public T darPrimerElemento() {
		return existeEnLaLista()?first.darInfo():null;
	}

	public T darUltimoElemento() {

		if(!existeEnLaLista()) {
			return null;
		}
		else {
			return darPosicionDatos(contarDatos()-1);
		}
	}


	public T buscar(T dato) {
		Nodo pos = first;
		if(pos.darInfo().equals(dato)) return pos.darInfo();
		while(pos.darSiguiente() != null) {
			pos = pos.darSiguiente();
			if(pos.darInfo().equals(dato)) {
				return pos.darInfo();
			}
		}
		return dato;

	}

	@Override
	public void agregarAlFinal(T dato) {
		last.cambiarSiguiente(new Nodo(dato));
		last = last.darSiguiente();
		tamano++;
	}

	@Override
	public T darElemento(int elemento) {
		if (elemento>tamano) {
			return null;
		}
		Nodo actual = first;
		int index = 0;
		while (index<elemento) {
			actual = actual.darSiguiente();
			index++;
		}
		return actual.darInfo();
	}

	@Override
	public Comparable[] elementos() {
		Comparable[] elementos = new Comparable[tamano];
		for (int i=0;i<tamano;i++) {
			elementos[i]=darElemento(i);
		}
		return elementos;
	}
}
