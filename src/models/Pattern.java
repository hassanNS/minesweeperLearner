package models;

import java.io.Serializable;
import java.util.Arrays;
 /**
  * @author KLD
  * 
  * Pattern is a sequence of characters that surrounds a tile (excluding central tile). The pattern uses
  * it's string to uniquely identify matches. Pattern also stores number of times pattern containing a mine and non-mines.
  * The score values are stored in Pattern.score 
  *
  *
  */

public class Pattern implements Serializable
{

	public static int TYPE_MINE = 0; 
	public static int TYPE_NON_MINE= 1; 
	

	/**
	 * used to remove annoying warning ( - -')
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

	/**
	 * sum of only integers in pattern
	 */
	public int sum;
	

	/**
	 * contains values of mine and empty occurrence
	 */
	public int[] score;

	/**
	 * linked list
	 */
	public Pattern next;

	
	public Pattern(String pattern, int type)
	{
	this.pattern = pattern;
	
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

	
	/** 
	 * Similar to matchPatter, match token return the number of tiles match the pattern sequence in 4 offsets and 2 directions. 
	 * 
	 * @param token token to compare 
	 * @return the max number of matched tiles 
	 */
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

	/** @author Nazmul 
	 *	Two pattern match if their equal of characters matches within 4 different points of offset and in two different direction. 
	 *
	 * Offsets are four corners and directions are clockwise and counter-clockwise. 
	 * 
	 * @param p pattern to compare 
	 * @return  true of p is a equal or a parallel to this pattern 
	 */
	public boolean isMatch(Pattern p)
	{
		char[] thisPatArray = getPatternArray();
		char[] patArray = p.getPatternArray();

		int PATLENGTH = patArray.length;

		if (thisPatArray.length != patArray.length)
		{
			//System.out.println("Patterns do not match!");
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

	//TODO too basic -KLD
	private char[] getPatternArray()
	{
		return pattern.toCharArray();
	}

	//TODO never used -KLD
	public String getPatternString()
	{
		return this.pattern;
	}

	/**
	 * Pattern is pure if one of the scores is 0 
	 * @return true if pattern if pure. Otherwise, false 
	 */
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
			score[p.type()]++;
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

	}

	/**
	 *
	 * @param a Pattern class or a token
	 * @return returns false if the pattern does not exist in the linked list
	 * and vice versa
	 * 
	 * TODO never used -KLD
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
	 * Print a pattern and any other patterns linked with it NOTE: never used -KLD
	 */
	public void printLinkedList()
	{
		System.out.println(this.toString() + " |\n V");
		if (this.next != null)
			this.next.printLinkedList();
		else
			System.out.println("null");
	}
	
	/**
	 * Build a 3x3 grid from pattern leaving central tile empty 
	 * also, appending mine:non-mine scores after the pattern 
	 * @return 3x3 grid of characters 
	 */
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
	
	
	/**
	 * strength is the ration between mine and non-mine score 
	 * 
	 * The ratio is always calculated by dividing the bigger score with the smallest (adding 1 to smaller to avoid division by zero). 
	 * 
	 * @return strength ratio 
	 */
	public int strength()
	{
		if(score[TYPE_MINE] > score[TYPE_NON_MINE])
		{
			return score[TYPE_MINE]/(score[TYPE_NON_MINE]+1);
		}
		
		return score[TYPE_NON_MINE]/(score[TYPE_MINE]+1); 
	}

	/**
	 * Pattern type is based on the highest score between mine and non-mine 
	 * @return TYPE_MINE if mine score is higher, otherwise TYPE_NON_MINE
	 */
	public int type()
	{
		if(score[TYPE_MINE] > score[TYPE_NON_MINE])
			return TYPE_MINE; 
		
		return TYPE_NON_MINE; 
	}
	
	
}
