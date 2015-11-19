package models;

import java.util.Arrays;
 /**
  * Pattern is a sequence of characters that surrounds a tile (excluding the tile). The pattern uses 
  * it's string to uniquely identify matches 
  * 
  * 
  * 
  * TODO
  *  needs more work (refine and optimize)  - KLD
  *
  */
public class Pattern {
	
	// Stores the pattern in an array format.
	private String pattern;
	
	/**
	 * number of mines in pattern
	 */
	int numOfMines;
	
	/*
	 * sum of only integers in pattern  
	 */
	int tileSum;
	
	/**
	 * contains values of mine and empty occurrence 
	 */
	public int[] score; 
	
	/**
	 * 0 for mine and 1 for empty 
	 */
	int type; 
	
	int x, y;
	public int index = tileSum;
	public Pattern next;
	
	public Pattern(String pattern) 
	{
		this.pattern = pattern;
		calculateSum();
	}
	
	public Pattern(String p, int x, int y)
	{
		this.pattern = p;
		calculateSum();
		this.x = x;
		this.y = y;
	}
	
	public Pattern(String p, int x, int y, int type)
	{
		this.pattern = p;
		calculateSum();
		this.x = x;
		this.y = y;
		
		this.type = type; 
		score = new int[2];
		score[type]++; 
	}

	// This function returns a score based on how
	// well two patterns are similar.
	public int match (Pattern pat)
	{
		char[] thisPatArray = getPatternArray();
		char [] patArray = pat.getPatternArray();
		int PATLENGTH = patArray.length;
		
		if (thisPatArray.length != patArray.length)
		{
			System.out.println("Patterns not same length!");
			return -0;
		}
		
		// We will run 4 checks forward
		// Then 4 checks backwards
		int [] matchWeights = {0, 0, 0, 0, 0, 0, 0, 0};
		
		for (int i=0; i < thisPatArray.length; i++)
		{
			// These decisions checks from left to right, from each
			// corner of the pattern. 0, 2, 4, and 6
			if (thisPatArray[i] == patArray[i])
				matchWeights[0]++;
			if (thisPatArray[i] == patArray[(i + 2) % PATLENGTH])
				matchWeights[1]++;
			if (thisPatArray[i] == patArray[(i + 4) % PATLENGTH])
				matchWeights[2]++;
			if (thisPatArray[i] == patArray[(i + 6) % PATLENGTH])
				matchWeights[3]++;
			
			int iBackwards = ((-1 * i) + PATLENGTH) % PATLENGTH;
			// These decisions does the same check above but backwards
			if (thisPatArray[iBackwards] == patArray[i])
				matchWeights[4]++;
			if (thisPatArray[iBackwards] == patArray[(i + 2) % PATLENGTH])
				matchWeights[5]++;
			if (thisPatArray[iBackwards] == patArray[(i + 4) % PATLENGTH])
				matchWeights[6]++;
			if (thisPatArray[iBackwards] == patArray[(i + 6) % PATLENGTH])
				matchWeights[7]++;
		}
		
		// Get the max of both comparisons
		return Arrays.stream(matchWeights).max().getAsInt();
	}

	public boolean isMatch(Pattern p)
	{
		char[] thisPatArray = getPatternArray();
		char [] patArray = p.getPatternArray();
	
		int PATLENGTH = patArray.length;

		if (thisPatArray.length != patArray.length)
		{
			System.out.println("Patterns do not match!");
			return false;
		}
		
		// We will run 4 checks forward
		// Then 4 checks backwards
		int [] matchWeights = {0, 0, 0, 0, 0, 0, 0, 0};
		
		for (int i=0; i < thisPatArray.length; i++)
		{
			// These decisions checks from left to right, from each
			// corner of the pattern. 0, 2, 4, and 6
			if (thisPatArray[i] == patArray[i])
				matchWeights[0]++;
			if (thisPatArray[i] == patArray[(i + 2) % PATLENGTH])
				matchWeights[1]++;
			if (thisPatArray[i] == patArray[(i + 4) % PATLENGTH])
				matchWeights[2]++;
			if (thisPatArray[i] == patArray[(i + 6) % PATLENGTH])
				matchWeights[3]++;
			
			int iBackwards = ((-1 * i) + PATLENGTH) % PATLENGTH;
			// These decisions does the same check above but backwards
			if (thisPatArray[iBackwards] == patArray[i])
				matchWeights[4]++;
			if (thisPatArray[iBackwards] == patArray[(i + 2) % PATLENGTH])
				matchWeights[5]++;
			if (thisPatArray[iBackwards] == patArray[(i + 4) % PATLENGTH])
				matchWeights[6]++;
			if (thisPatArray[iBackwards] == patArray[(i + 6) % PATLENGTH])
				matchWeights[7]++;
		}
		
		if (Arrays.stream(matchWeights).max().getAsInt() == PATLENGTH)
			return true;
		
		return false;
	}
	
	public char[] getPatternArray()
	{
		return pattern.toCharArray();
	}
	
	public int getSum()
	{
		return this.tileSum;
	}
	
	public int getMines()
	{
		return this.numOfMines;
	}
	
	/**
	 * Appends the added pattern at the end of the linkedlist only if it's unique.
	 * 	Otherwise, increment the parallel pattern by the type of the new pattern.  
	 * @param p new pattern to add
	 */
	public void append(Pattern p)
	{
		//catch duplicates 
		if(isMatch(p))
		{
			score[p.type]++; 
			return; 
		}
			
		//adds linkedlist-style
		if (this.next == null)
			this.next = p;
		else
			this.next.append(p);
	}
	/**
	 * Returns a string presentation of the pattern
	 */
	public String toString()
	{
		String [] k = pattern.split("");
		
		return (k[0]+k[1]+k[2]+"\n"+k[7]+" "+k[3]+"\n"+k[6]+k[5]+k[4]); 
		
		/*
		return String.format("%c %c %c\n%c   %c\n%c %c %c", 
						k[0],k[1],k[2],k[7],k[3],k[6],k[5],k[4], 
						this.x, this.y, this.tileSum, this.numOfMines); */
	}
	
	/**
	 * Print a pattern and any other patterns linked with it
	 */
	public void printLinkedList()
	{
		System.out.println(this.toString() + " |\n V");
		if (this.next != null)
			this.next.printLinkedList();
		else
			System.out.println("null");
	}
	
	private int getCharValue(char c)
	{
		if (c == 'B' || c == '*')
			return 0;
	
		return Character.getNumericValue(c);
	}
	// Calculate the sum of all of the numbers on
	// the 3 x 3 pattern
	private void calculateSum()
	{
		char [] nums = getPatternArray();
		for (int i=0; i < nums.length; i++)
		{
			// Found a mine
			if (nums[i] == '*')
			{
				numOfMines++;
			}	
			
			tileSum += getCharValue(nums[i]); //TODO This does not skip mines and blanks! 
		}
	}
}
