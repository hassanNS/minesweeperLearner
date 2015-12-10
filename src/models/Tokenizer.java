package models;

import grid.MapGrid;

/**
 * Tokenizer create patterns from a given grid
 *
 * Comment
 * This class also needs refine and optimization -KLD
 *
 */
public class Tokenizer 
{

	/**
	 * Takes a center point and returns a
	 * 3x3 snapshot of the board as a pattern not including central point at x,y 
	 * @param x the width x coordinate
	 * @param y the height y coordinate
	 * @return null of pattern contains empty tile, otherwise a patterns of x,y in grid
	 * 
	 * TODO rename to 3x3 tokenizer 
	 */
	public Pattern tokenizePattern(int x, int y, MapGrid grid)
	{
		String pat="";

		MapGrid mg = grid; 
		
		//TODO need loop and math -KLD 
		pat += mg.tileString(x-1,y-1);
		pat += mg .tileString(x-1,y);
		pat += mg.tileString(x-1,y+1);
		pat += mg.tileString(x,y+1);
		pat += mg.tileString(x+1,y+1);
		pat += mg.tileString(x+1,y);
		pat += mg.tileString(x+1,y-1);
		pat += mg.tileString(x,y-1);

		int type = (mg.tileString(x, y).charAt(0)=='*')? Pattern.TYPE_MINE : Pattern.TYPE_NON_MINE ;

		if (pat.contains("0"))	//patterns shouldn't have 0. Because 0 means center is always not a mine. 
			return null;
		
		return new Pattern(pat, type);
	}
}
