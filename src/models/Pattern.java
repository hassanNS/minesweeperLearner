package models;

import java.io.Serializable;
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

public class Pattern implements Serializable{


	public static int TYPE_MINE = 0; 
	public static int TYPE_NON_MINE= 1; 
	
	
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *  Stores the pattern in an array format.
	 */
	private String pattern;

	/**
	 * number of mines in pattern
	 */
	public int mines;

	/*
	 * sum of only integers in pattern
	 */
	public int sum;
	

	/**
	 * contains values of mine and empty occurrence
	 */
	public int[] score;

	/**
	 * 0 for mine and 1 for empty
	 */
	public int type;

	
	/**
	 * linked list
	 */
	public Pattern next;


	public Pattern(String pattern, int type)
	{
	this.pattern = pattern;
	this.type = type; 
	
	score = new int[2];
	
	score[type]++; 
		
		//calculate sum and mine 
		for(char c : pattern.toCharArray())
		{
			if(c==Tile.MINE)
			{
				mines++; 
			}
			else if(Character.isDigit(c))
			{
				sum+= Character.getNumericValue(c);
			}
		}
		//end calculate sum and mine
	
	}

	


	// This function returns a score based on how
	// well two patterns are similar.
/*	private int match (Pattern pat)
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
	}*/
	
	
	public int matchToken(Token token)
	{
		int score = 0; 
		
		for(int r = 0; r <2 ; r++)
		{
			int d = 1 - (2*r); 
			for(int init=0; init < 4; init++)
			{
				int subscore = 0; 
				for(int i=0; i<pattern.length(); i++)
				{
					int index = ((i*d)+pattern.length())%pattern.length(); 
					
					if(pattern.charAt(i) == token.token.charAt(index))
					{
						subscore+=1; 
					}
				}//end for 
				
				if(subscore > score)
				{
					score = subscore; 
				}
			}//end init
		}//end the d
		
		return score; 
	}

	public boolean isMatch(Pattern p)
	{
		char[] thisPatArray = getPatternArray();
		char[] patArray = p.getPatternArray();

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

	public String getPatternString()
	{
		return this.pattern;
	}

	public boolean isPure()
	{
		return (score[TYPE_MINE]==0 || score[TYPE_NON_MINE]==0); 
	}
	

	/**
	 * Appends the added pattern at the end of the linkedlist only if it's unique.
	 * 	Otherwise, increment the parallel pattern by the type of the new pattern.
	 * @param p new pattern to add
	 */
	public boolean append(Pattern p)
	{
		//catch duplicates
		if(isMatch(p))
		{
			score[p.type]++;
			return false;
		}
		
		if (this.next == null)
		{
			PatternHash.count++;
			this.next = p;
			return true; 
		}
		else
			return (this.next.append(p));
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
	 *
	 * @param a Pattern class or a token
	 * @return returns false if the pattern does not exist in the linked list
	 * and vice versa
	 */
	public boolean checkExists(Pattern p)
	{
		Pattern temp = this;
		if (temp.next != null)
		{
			if (isMatch(temp))
				return true;
			temp = temp.next;
		}
		
		return false;
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
	
	public String[][] asGrid()
	{
		//match b*tch
		int[] yfactor = {0,7,3};
		int[] xfactor = {-1,2,1};
		
		int size = (int)Math.sqrt(pattern.length()+1); 
		String[][] build = new String[size][size];
 
		for(int i=0; i < pattern.length()+1; i++)
		{
			int y = i/size; 
			int x = i%size; 
			
		//	System.out.println(y+""+x);
			build[y][x] = ""+pattern.charAt(y*yfactor[y]-x*xfactor[y]);
			
			if(x==y && x==size/2)
				build[y][x]= " "; //patterns has no fill, only score 
			
		}
		
		
		
		return build; 
	}
	
	
	public int strength()
	{
		if(score[TYPE_MINE] > score[TYPE_NON_MINE])
		{
			return score[TYPE_MINE]/(score[TYPE_NON_MINE]+1);
		}
		
		return score[TYPE_NON_MINE]/(score[TYPE_MINE]+1); 
	}

	
	public int type()
	{
		if(score[TYPE_MINE] > score[TYPE_NON_MINE])
			return TYPE_MINE; 
		
		return TYPE_NON_MINE; 
	}
	
	/*private int getCharValue(char c)
	{
		if (c == 'B' || c == '*')
			return 0;

		return Character.getNumericValue(c);
	}*/
	
	// Calculate the sum of all of the numbers on
	// the 3 x 3 pattern
	/*private void calculateSum()
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
	}*/
}
