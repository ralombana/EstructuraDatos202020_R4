package main;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import clases.Pelicula;
import controller.Controller;
import model.data_structures.ArregloDinamico;

public class Main 
{
	public static void main(String[] args) 
	{
		Controller controler = new Controller();
		controler.run();	
	}
}
