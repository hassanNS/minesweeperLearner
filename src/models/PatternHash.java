package models;

import java.io.PrintWriter;
import java.io.Serializable;
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

public class PatternHash implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * A hashtable of length 9. Each index being the number of mines.
	 * Thus, 0-8 mines.
	 * Each index in the table contains another hashtable of Patterns.
	 * Each KEY => maximum sum of number for the number of mines
	 * Each VALUE => List of corresponding pattern objects
	 */
	private int maxMine = 8;
	private int maxSum = 40;
	private Pattern [][] patHashTable;
	//int[] boundaries = {40, 40, 40, 36, 32, 24, 16, 8};

	// Initializes the Hashtable
	public PatternHash()
	{
		patHashTable = new Pattern[maxMine+1][maxSum+1];
	}

	public Pattern getPat(int mines, int sum)
	{
		if ( (mines >= 0 && sum >= 0) && (mines < 9 && sum < 41) )
		{
			return patHashTable[mines][sum];
		}
		return null;
	}

	/**
	 *
	 * @param p
	 */
	public void addPattern(Pattern p)
	{
		Pattern root = patHashTable[p.getMines()][p.getSum()];
		if (root == null)
			patHashTable[p.getMines()][p.getSum()] = p;
		else
			root.append(p);

	}

	/**
	 * Print a pattern linked list
	 * @param mineNum
	 * @param tileSum
	 */
	public void printPatternLinkedList(int mineNum, int tileSum)
	{
		patHashTable[mineNum][tileSum].printLinkedList();
	}

	/**
	 * Return a pattern linked list from the hashtable
	 * @param mineNum
	 * @param tileSum
	 * @return
	 */
	public Pattern getPatternLinkedList(int mineNum, int tileSum)
	{
		return patHashTable[mineNum][tileSum];
	}

	/**
	 * Write every pattern inside the hashtable to a csv file
	 */
	public void writeToFile()
	{
		try{
			PrintWriter writer = new PrintWriter("patternData.csv", "UTF-8");
			for(int i=0; i<maxMine+1; i++)
			{
				for(int j=0; j<maxSum+1; j++)
				{
					if(getPatternLinkedList(i, j) == null)
						continue;

					Pattern temp = getPatternLinkedList(i, j);
					do{
						writer.printf("%s,%d,%d,%f\n",
								temp.getPatternString(), temp.getMines(),
								temp.getSum(), temp.getScore());
						temp = temp.next;
					}
					while(temp != null);
				}
			}
			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println();
		}
	}
}









