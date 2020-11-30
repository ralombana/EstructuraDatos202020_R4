package view;

public class View 
{
	    /**
	     * Metodo constructor
	     */
	    public View()
	    {
	    	
	    }
	    
		public void printMenu()
		{
			System.out.println("----------");
			System.out.println("1. Cargar Grafo");
			System.out.println("2. Estaciones críticas");
			System.out.println("3. Cantidad de Clusters parte A");
			System.out.println("4. Cantidad de Clusters parte B");
			System.out.println("5. Identificar bicicletas para matenimiento");
		}

		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}		
}
