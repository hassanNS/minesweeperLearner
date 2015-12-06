package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import models.PatternHash;
import models.PatternListGenerator;
import models.Saver_Loader;

public class PaternListGeneratorTest 
{
	public static void main(String[] args) throws FileNotFoundException 
	{
		//PatternHash list = PatternListGenerator.generateCount(8, 8, 10, 32768); 
		
		//System.out.println(list);
		//System.out.println(list.count);
		
		int w = 8; 
		int h = 8; 
		int m = 10; 
		
		int param = 5000000;
		
		PatternHash list = PatternListGenerator.untilEnough(w, h, m, param);
		
		PrintStream out = new PrintStream(new File("U5M"));
		out.println(list);
		out.close(); 
		
		//Saver_Loader.saveHashTable(list, "1mThresh.ser");

		System.out.println("Unique length patterns: " + list.length);
		
		
		
		/*
		 *  optimal
		 * 295000	count: 48300	diff: 43
			Unique patterns: 48302
			
			thresh 1000: 
			
			248455	count: 48140
			Unique patterns: 48140
			
			
			357333	count: 48998
			Found pattern:48998
			
			thresh = 5000 
			754833	count: 50142
			
			
			
		 */
		
	
	}
}
