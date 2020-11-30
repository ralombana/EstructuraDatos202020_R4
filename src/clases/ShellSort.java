package clases;

public class ShellSort {

	public static void sortCount(Comparable[] array,int tamano, boolean mejores) {
		int salto, i;
		Comparable aux;
        boolean cambios;
  
        if (mejores) {
        	for (salto = tamano / 2; salto != 0; salto /= 2) {
                cambios = true;
                while (cambios) {                                         
                    cambios = false;
                    for (i = salto; i < tamano; i++)  
                    {
                        if (((Pelicula)array[i - salto]).compareToCount((Pelicula)array[i])<0) {       
                            aux = array[i];                 
                            array[i] = array[i - salto];
                            array[i - salto] = aux;
                            cambios = true;  
                        }
                    }
                }
            }
        }
        else {
        	for (salto = tamano / 2; salto != 0; salto /= 2) {
                cambios = true;
                while (cambios) {                                         
                    cambios = false;
                    for (i = salto; i < tamano; i++)  
                    {
                        if (((Pelicula)array[i - salto]).compareToCount((Pelicula)array[i])>0) {       
                            aux = array[i];                 
                            array[i] = array[i - salto];
                            array[i - salto] = aux;
                            cambios = true;  
                        }
                    }
                }
            }
        }
        
	}
	
	public static void sortAverage(Comparable[] array,int tamano, boolean mejores) {
		int salto, i;
		Comparable aux;
        boolean cambios;
  
        if(mejores) {
        	for (salto = tamano / 2; salto != 0; salto /= 2) {
                cambios = true;
                while (cambios) {                                   
                    cambios = false;
                    for (i = salto; i < tamano; i++)   
                    {
                        if (array[i - salto].compareTo(array[i])<0) {       
                            aux = array[i];                 
                            array[i] = array[i - salto];
                            array[i - salto] = aux;
                            cambios = true;  
                        }
                    }
                }
            }
        }
        else {
        	for (salto = tamano / 2; salto != 0; salto /= 2) {
                cambios = true;
                while (cambios) {                                   
                    cambios = false;
                    for (i = salto; i < tamano; i++)   
                    {
                        if (array[i - salto].compareTo(array[i])>0) {       
                            aux = array[i];                 
                            array[i] = array[i - salto];
                            array[i - salto] = aux;
                            cambios = true;  
                        }
                    }
                }
            }
        }
	}
	
}
