package test;

import grid.MapGrid;

public class GridGenerationTest {

	public static void main(String[] args) {
	
		//set values
		int width = 30; 
		int height = 20; 
		int mines = 50; 
		
		//create grid 
		MapGrid g = new MapGrid(width, height, mines); 
		
		//print
		System.out.print(g);

	}

}
