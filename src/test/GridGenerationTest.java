package test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.Savepoint;
import java.util.ArrayList;

import grid.MapGrid;
import models.Pattern;
import models.PatternHash;
import models.Tokenizer;

public class GridGenerationTest {

	/*hell public static void main(String[] args) {
		//set values
		int width = 30, 
			height = 20; 
		int twidth = 30,
			theight = 20;
		
		int mines = 50; 
		// Store all the patterns in the appropriate hash table
		PatternHash hashTable = new PatternHash();	
		
		for(int i=0; i<10; i++)
		{
			// This iteration is from 50 mines to width*height # of mines
			for(int m = mines; m < (twidth*theight + (i*i)); m+=2) 
			{
				//create grid 
				MapGrid g = new MapGrid(width + (i*i), height + (i*i), m); 
				Tokenizer tk = new Tokenizer(g);
			
				ArrayList<Pattern> pats = tk.tokenize3x3();
				for (Pattern pat: pats)
				{
					hashTable.addPattern(pat);
				}
				
			}
		}
		
		// Write the hashtable to a file
		hashTable.writeToFile();
		saveHashTable(hashTable, "hashTable.ser");
	}
	
	public static void saveHashTable(PatternHash hashTable, String fileName)
	{
		try{
			FileOutputStream fileOut;
			ObjectOutputStream out;
			fileOut = new FileOutputStream(fileName);
			out = new ObjectOutputStream(fileOut);
		 	out.writeObject(hashTable);
		 	out.close();
		 	fileOut.close();
		 	System.out.printf("Serialized data is saved in hashTable.ser");
		} catch(Exception e){}
	}*/

}
