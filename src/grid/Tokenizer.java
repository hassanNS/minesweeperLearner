package grid;

import models.Pattern;

/**
 * Yokenizer  create patterns from a given grid 
 * 
 * Comment 
 * This class also needs refine and optimization -KLD
 *
 */
public class Tokenizer {
	
	//TODO remove dependency 
	MapGrid mg;
	
	public Tokenizer(MapGrid m)
	{
		this.mg = m;
	}
	/**
	 * Takes a center point and returns a
	 * 3 x 3 snapshot of the board as a pattern
	 * @param x the width x coordinate
	 * @param y the height y coordinate
	 * @return a patterns of x,y in grid 
	 */
	public Pattern tokenizeTile3x3(int x, int y) 
	{
		//TODO pass grid 
		String pat="";
		
		pat+=mg.tileString(x-1,y-1);
		pat+=mg.tileString(x-1,y);
		pat+=mg.tileString(x-1,y+1);
		pat+=mg.tileString(x,y+1);
		pat+=mg.tileString(x+1,y+1);
		pat+=mg.tileString(x+1,y);
		pat+=mg.tileString(x+1,y-1);
		pat+=mg.tileString(x,y-1);
				
		
		int type = (mg.tileString(x, y).charAt(0)=='*')? 0 : 1; 
		
		//System.out.println(pat + "(" + x + "," + y+")");
		return new Pattern(pat, x, y, type);				
	}
	
	/** TODO delete: not used. 
	 * 
	 * @return An array of patterns representing 3x3 snapshots of
	 * every token in the grid.
	 */
	public Pattern[] tokenize3x3()
	{
		Pattern[] patArray = new Pattern[mg.width*mg.height];
		int patIndex = 0;
		
		for(int i=0; i<mg.width; i++)
		{
			for(int j=0; j<mg.height; j++)
			{
				patArray[patIndex] = tokenizeTile3x3(i, j);
				patIndex++;
			}
		}
		
		return patArray;
	}
	
}
