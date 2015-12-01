package test;

import models.Pattern;

public class PatternAsGridTest 
{

	/**
	 * Tests the Pattern.asGrid() function  
	 * 
	 */
	public static void main(String[] args)
	{

		Pattern pattern = new Pattern("01234567",1);
		
		
		String[][] patternGrid = pattern.asGrid(); 
		
		for(int i=0; i<3; i++)	
		{
				for(int j=0; j<3; j++)
				{
					System.out.print(patternGrid[i][j]);
				}
				System.out.println();
		}
		

	}

}
