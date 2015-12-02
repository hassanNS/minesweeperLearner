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
	
	public static PatternHash generateCount(int width, int height, int mine, int count)
	{		
		//objects needed
		MapGrid map = new MapGrid(width, height, mine);
		Tokenizer tokenizer = new Tokenizer(); 
		PatternHash list = new PatternHash(); 
		
		int min_mine = mine-5; 
		int max_mine = mine+5; 
		
		int itt =0; 
		//tokenize then regenerate 
		while(list.count < count)
		{
			itt++; 
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
			map.mine = min_mine + (itt%10); 
			map.regenerate();
		
		}//end count 
		
		System.out.println("Itteration " + itt);
		
		return list; 
	}
	
	
	public static PatternHash untilEnough(int width, int height, int mine, int max_thresh)
	{
		//objects needed
				MapGrid map = new MapGrid(width, height, mine);
				Tokenizer tokenizer = new Tokenizer(); 
				PatternHash list = new PatternHash(); 
				
				int min_mine = mine; 
				
				int s_prev =0;
				
				
				int prev = 0; 
				int thresh =0; 
				int itt =0; 
				
				//tokenize then regenerate 
				while(thresh < max_thresh)
				{
					//System.out.println("thresh" + thresh);
					
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
					map.mine = min_mine + (itt%35); 
					map.regenerate();
					
					
					if(itt%5000==0)
					{
						System.out.println(""+itt + "\tcount: " + list.count + "\tdiff: " + (list.count-s_prev));
						s_prev = list.count;
					}
					
					if((list.count - prev) == 0)
					{
						thresh++; 
					}
					else 
					{
						thresh = 0; 
					}
					
					prev = list.count; 
					
					
					itt++; 
				
				}//end while 
				
				//System.out.println("Itteration " + itt);
				
				System.out.println(""+itt + "\tcount: " + list.count );
				
				return list; 
	}
	
	
}
