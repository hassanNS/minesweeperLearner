package test;

import models.PatternHash;
import models.PatternListGenerator;
import models.Saver_Loader;

public class GenerateUntilEnough {
	
	public static void main(String[]args)
	{
		int width = 8;
		int height = 8; 
		int mine = 10; 
		
		//PatternHash list = PatternListGenerator.generate(width, height, mine, 50000);  
		// PatternHash list = PatternListGenerator.untilEnough(width, height, mine, 5000); 
		// Saver_Loader.saveHashTable(list, "HASH.ser");
		System.out.println("Loading to file");
		PatternHash list = Saver_Loader.loadHashTable("HASH.ser");
		System.out.println(list.toString());
	}
	
}
