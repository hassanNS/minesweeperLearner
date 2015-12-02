package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import models.PatternHash;
import models.PatternListGenerator;

public class PaternListGeneratorTest 
{
	public static void main(String[] args) throws FileNotFoundException 
	{
		//PatternHash list = PatternListGenerator.generateCount(8, 8, 10, 32768); 
		
		//System.out.println(list);
		//System.out.println(list.count);
		
		
		PatternHash list = PatternListGenerator.untilEnough(8, 8, 10, 5000);
		
		PrintStream out = new PrintStream(new File("the dream"));

		out.println(list);
		
		out.close(); 
		
		System.out.println("Unique patterns: " + list.count);
		
		/*for(int i=1; i<11; i++)
		{
			PatternHash list = PatternListGenerator.generateCount(8, 8, 10, i*10000); 
		
			System.out.println(""+i + "- " + list.count + " diff:  " + (list.count-prev));
			
			prev = list.count; 
		}*/
		
		//41180 
		
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
