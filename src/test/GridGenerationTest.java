package test;

import grid.MapGrid;
import grid.Tokenizer;
import models.Pattern;
import models.PatternHash;

public class GridGenerationTest {

	public static void main(String[] args) {
	
		//set values
		int width = 30; 
		int height = 20; 
		int mines = 50; 
		
		//create grid 
		MapGrid g = new MapGrid(width, height, mines); 
		Tokenizer tk = new Tokenizer(g);
		//print
		System.out.print(g);

		Pattern[] pats = tk.tokenize3x3();
		
		// Store all the patterns in the appropriate hash table
		PatternHash hashTable = new PatternHash();
		for (Pattern pat: pats)
		{
			hashTable.addPattern(pat);
		}
		
		hashTable.printPatternLinkedList(1, 4);
		
		
	}

}
