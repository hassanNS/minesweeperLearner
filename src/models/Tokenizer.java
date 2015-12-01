package models;

import java.util.ArrayList;

import grid.MapGrid;

/**
 * Yokenizer  create patterns from a given grid
 *
 * Comment
 * This class also needs refine and optimization -KLD
 *
 */
public class Tokenizer {

	 
	
	/**
	 * Takes a center point and returns a
	 * 3 x 3 snapshot of the board as a pattern
	 * @param x the width x coordinate
	 * @param y the height y coordinate
	 * @return a patterns of x,y in grid
	 */
	public Pattern tokenizePattern(int x, int y, MapGrid grid)
	{
		//TODO pass grid
		String pat="";

		MapGrid mg = grid; 
		
		pat += mg.tileString(x-1,y-1);
		pat += mg .tileString(x-1,y);
		pat += mg.tileString(x-1,y+1);
		pat += mg.tileString(x,y+1);
		pat += mg.tileString(x+1,y+1);
		pat += mg.tileString(x+1,y);
		pat += mg.tileString(x+1,y-1);
		pat += mg.tileString(x,y-1);

		int type = (mg.tileString(x, y).charAt(0)=='*')? Pattern.TYPE_MINE : Pattern.TYPE_NON_MINE ;

		if (pat.contains("0"))	//.......
			return null;
		
		return new Pattern(pat, type);
	}

	
	
	/*TODO delete: not used.
	 *
	 * @return An array of patterns representing 3x3 snapshots of
	 * every token in the grid.
	 *
	public ArrayList<Pattern> tokenize3x3()
	{
		ArrayList<Pattern> patArray = new ArrayList<Pattern>();

		for(int i=0; i<mg.width; i++)
		{
			for(int j=0; j<mg.height; j++)
			{
				Pattern t = tokenizeTile3x3(i, j);
				if (t == null)
					continue;

				patArray.add(t);
			}
		}

		return patArray;
	}*/

}
