package models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author Naz
 * 
 * Class acting as double hashtable to store minesweeper
 * 3 x 3 patterns
 *
 */

public class PatternHash {
	/**
	 * A hashtable of length 9. Each index being the number of mines.
	 * Thus, 0-8 mines. 
	 * Each index in the table contains another hashtable of Patterns.
	 * Each KEY => maximum sum of number for the number of mines
	 * Each VALUE => List of corresponding pattern objects
	 */
	private Pattern [][] patHashTable = new Pattern[9][41];
	
	// int[] boundaries = {40, 40, 40, 36, 32, 24, 16, 8};
	
	// Initializes the Hashtable
	public PatternHash()
	{
		
	}
	
	public Pattern getPat(int mines, int sum)
	{
		if ( (mines >= 0 && sum >= 0) && (mines < 9 && sum < 41) )
		{
			return patHashTable[mines][sum];
		}
		return null;
	}
}









