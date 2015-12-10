package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import models.PatternHash;
import models.PatternListGenerator;
import models.Saver_Loader;

public class PaternListGeneratorTest 
{
	/**
	 * Here we generate pattern tables
	 */
	public static void main(String[] args) throws FileNotFoundException 
	{
		
		int width = 8; 
		int height = 8; 
		int mine = 8; 
		
		int param = 1000000;
		
		//PatternHash list = PatternListGenerator.untilEnough(width, height, mine , param);
		
		
		PatternHash list = Saver_Loader.loadHashTable("1mThreshv2.ser"); 
		
		PrintStream out = new PrintStream(new File("8_8_8_u1000000.txt"));
		out.println(list);
		out.close(); 
		
		System.out.println("Unique length patterns: " + list.length);
		
		//Saver_Loader.saveHashTable(list, "1mThreshv2.ser");

		
		
		/* some previous findings
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
