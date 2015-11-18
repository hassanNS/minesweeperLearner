package grid;

import java.util.ArrayList;

import models.Pattern;

public class Tokenizer {
	
	MapGrid mg;
	final String empty = "00000000";
	
	public Tokenizer(MapGrid m)
	{
		this.mg = m;
	}
	/**
	 * Takes a center point and returns a
	 * 3 x 3 snapshot of the board as a pattern
	 * @param x the width x coordinate
	 * @param y the height y coordinate
	 * @return
	 */
	public Pattern tokenizeTile3x3(int x, int y)
	{
		String pat="";
		
		pat+=mg.tileString(x-1,y-1);
		pat+=mg.tileString(x-1,y);
		pat+=mg.tileString(x-1,y+1);
		pat+=mg.tileString(x,y+1);
		pat+=mg.tileString(x+1,y+1);
		pat+=mg.tileString(x+1,y);
		pat+=mg.tileString(x+1,y-1);
		pat+=mg.tileString(x,y-1);
		
		if (pat.equals(empty))
			return null;
		//System.out.println(pat + "(" + x + "," + y+")");
		return new Pattern(pat, x, y);				
	}
	
	/**
	 * 
	 * @return An array of patterns representing 3x3 snapshots of
	 * every token in the grid.
	 */
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
	}
	
}
