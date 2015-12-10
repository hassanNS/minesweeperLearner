package models;

import grid.MapGrid;

/**
 * 
 * @author KLD
 *
 *	This class contains various way to create patterns. 
 *
 *	Currently there are three ways: 
 *
 *	generate, generateCount, and  untilEnough
 *
 *
 */
public class PatternListGenerator 
{
	/**
	 * Generates patterns to feed a PatternHash. The patterns are generated from a grid. 
	 * The grid is regenerated @iteration times fed to PatternHash each iteration 
	 *
	 * @param width grid width 
	 * @param height grid height 
	 * @param mine number of mines 
	 * @param iteration number regenerations of created grid 
	 * @return a fed pattern table 
	 */
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
	
	/**
	 * Generates grid and feed it's patterns to a pattern table until the number of added patterns meet @count 
	 * @param width grid width 
	 * @param height grid height 
	 * @param mine number of mines 
	 * @param count minimum number of unique added patterns 
	 * @return a fed pattern table
	 */
	public static PatternHash generateCount(int width, int height, int mine, int count)
	{		
		//objects needed
		MapGrid map = new MapGrid(width, height, mine);
		Tokenizer tokenizer = new Tokenizer(); 
		PatternHash list = new PatternHash(); 
		
		int min_mine = mine-5; 
		//int max_mine = mine+5; 
		
		int itt =0; 
		//tokenize then regenerate 
		while(list.length < count)
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
		
	/**
	 * Generates grid and feed it's patterns to a pattern table. After a certain number (threshold) of iterations
	 * 	 passed and no new patterns are added, the function stops generating 
	 * @param width grid width 
	 * @param height grid height 
	 * @param mine number of mines 
	 * @param max_thresh max number of iteration without newly added patterns 
	 * @return a fed pattern table 
	 */
	public static PatternHash untilEnough(int width, int height, int mine, int max_thresh)
	{
		//objects needed
				MapGrid map = new MapGrid(width, height, mine);
				Tokenizer tokenizer = new Tokenizer(); 
				PatternHash list = new PatternHash(); 
				
				int min_mine = mine; 
				
				int s_prev =0;
				
				int mineRefresher = (((width*height)-mine)*85)/100; //85% of map size
				
				int prev = 0; 
				int thresh =0; 
				int itt =1; 
				
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
			
					//mine variety: mine will be different each iteration (loops back) 
					map.mine = min_mine + (itt%mineRefresher);  //max is 8x8 = 64  TODO calculate 15
					
					//recreating map with the same attributes 
					map.regenerate();
					
					
					if(itt%10000 == 0)
					{
						System.out.println(""+itt + "\tcount: " + list.length + "\tdiff: " + (list.length-s_prev));
						s_prev = list.length;
					}
					if((list.length - prev) == 0)
					{
						thresh++; 
					}
					else 
					{
						thresh = 0; 
					}
					
					prev = list.length; 
					
					
					itt++; 
				
				}//end while 
				
				
				System.out.println("iterations "+itt + "\tcount: " + list.length );
				
				return list; 
	}
	
	
}
