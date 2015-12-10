package generate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import models.PatternHash;
import models.PatternListGenerator;
import models.Saver_Loader;

public class Generator 
{
	/**
	 * Generate patterns and store them into a file 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException
	{
		//map values 
		int width = 8; 
		int height = 8; 
		int mine = 8; 
		
		//based on algorithm used. Current: max_threshold 
		int param = 1000000;
		
		//control flags
		boolean printToFile = true; 
		boolean storeToFile = true; 
		
		//paths 
		String asTextOutput = String.format("W%dH%dM%dP%d.txt", width, height, mine, param);
		String asObjOutput = String.format("W%dH%dM%dP%.ser", width, height, mine, param);
		
		//generate patterns 
		PatternHash list = PatternListGenerator.untilEnough(width, height, mine , param);
		
		if(printToFile)
		{
			PrintStream out = new PrintStream(new File(asTextOutput));
			out.println(list);
			out.close(); 
			System.out.println("printed results at:"+asTextOutput);
		}
		
		if(storeToFile)
		{
			Saver_Loader.saveHashTable(list, asObjOutput);
			System.out.println("stored obj at:"+asObjOutput);
		}
		


	}

}
