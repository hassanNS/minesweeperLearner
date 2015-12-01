package models;

import java.io.File;
import java.io.PrintStream;

import grid.MapGrid;

public class PatternListGenerator 
{

	
	public static PatternHash generate(int width, int height, int mine, int iteration)
	{		
		//objects needed
		MapGrid map = new MapGrid(width, height, mine);
		Tokenizer tokenizer = new Tokenizer(); 
		PatternHash list = new PatternHash(); 
		
		//tokenize then regenerate 
		for(int itt=0; itt<iteration; itt++)
		{
			//adding patterns to hash 
			for(int i=0; i<width; i++)
				for(int j=0; j<height; j++)
				{
					if(map.grid[j][i]==0) // 0 cannot be tokenize cuz surrounding is exposed
						continue; 
					
					Pattern p = tokenizer.tokenizePattern(i, j, map); 
					if(p!=null)
					list.addPattern(p);
				}
	
			//recreating map with the same attributes 
			map.regenerate();
		
		}//end iteration 
		
		return list; 
	}
}
