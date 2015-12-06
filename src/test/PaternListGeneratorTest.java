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
		
		
		PatternHash list = PatternListGenerator.untilEnough(8, 8, 10, 1000000);
		
		PrintStream out = new PrintStream(new File("5DEC619-U1m"));
		out.println(list);
		out.close(); 
		
		
		//Saver_Loader.saveHashTable(list, "1mThresh.ser");

		System.out.println("Unique count patterns: " + list.count);
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
