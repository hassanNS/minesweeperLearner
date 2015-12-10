package models;

import java.io.Serializable;

/**
 *
 * @author KLD
 *
 * Holds a list of patterns stored based on Pattern.mine and Pattern.sum 
 *
 */
public class PatternHash implements Serializable
{
	/**
	 * remove annoying warning
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * max number of mines 
	 */
	public static final int MAX_MINE = 8;
	
	/**
	 * max sum 
	 */
	private int maxSum = 40;
	
	/**
	 * double hash tables (first hash is mine, second is sum) 
	 */
	public Pattern [][] table;
	
	/**
	 * replaced by length TODO delete -KLD
	 */
	public static int count; 

	/**
	 * length of patterns 
	 */
	public int length; 
	
	
	/**
	 * Initializes the PatternHash by initializing table of patterns and setting length 
	 */
	public PatternHash()
	{
		length = 0; 
		//count = 0;  no longer used 
		table = new Pattern[MAX_MINE+1][maxSum+1]; //includes 0
	}

	/**
	 *  
	 * @param mine number of mine 
	 * @param sum sum 
	 * @return pattern linked list at given mine and sum 
	 */
	public Pattern getPat(int mine, int sum)
	{
		if(sum > maxSum)
			sum = maxSum; 
		
		if(mine > MAX_MINE)
			mine = MAX_MINE; 
		
		return table[mine][sum];
	}

	/**
	 * Appends unique patterns. If pattern is not unique, the score of the existing one will increment based on added score
	 * @param p pattern to add 
	 */
	public void addPattern(Pattern p)
	{
		Pattern root = table[p.mines][p.sum];
		if (root == null)
		{
			length++; 
			table[p.mines][p.sum] = p;
		}
		else
		{
			if(root.append(p)) //appends recursively adds new patterns (or increment parallel pattern score) 
				length++; 
		}

	}

	/**
	 * 
	 * Return string representation of the table with the following format:
	 * 
	 * <pre>
	 * [Format Start]
	 * Mine m
	 * 	Sum: s
	 * 		[lists all patterns in the category horizontal] 
	 * 	Sum: s+1
	 * 		[lists all patterns in the category horizontal] 
	 *	...
	 * Mine m+1 
	 * 	Sum: s
	 * 		[lists all patterns in the category horizontal] 
	 * 		...
	 * ...
	 * [Format End]
	 * </pre>
	 * 
	 *	<b>Note:</b> ...means continues
	 *			 <p>[lists all patterns in the category horizontal]: actual patterns are listed based on their toString </p>
	 */
	public String toString()
	{
		String build = ""; 
		
		for(int m=0; m<table.length; m++)
		{
			build+="Mine " + m +"\n"; 
			for(int s=0; s<table[m].length; s++)
			{
				Pattern pointer = table[m][s];
					
				int size = 3;  //TODO replaae with size
				
				String[] patternBuild = new String[size];
				for(int i=0; i<size; i++)
					patternBuild[i] = ""; 
				
				String scores = "";
				while(pointer != null)
				{
					String[][] patternGrid = pointer.asGrid(); 
					for(int i=0; i<size; i++)
					{
						//get line of chars at line i 
						String subBuild = "";  
						for(int j=0; j<size; j++)
						{
							subBuild+= patternGrid[i][j]; 
						}
						patternBuild[i]+= subBuild + "\t"; 
					}
					
					scores += pointer.score[0] + ":" + pointer.score[1]+ "\t"; 
					
					pointer = pointer.next; 
				}
				
				//print patterns for that sum 
				
				if(table[m][s] !=null)
				{
					build+= "\tSum: " + s +"\n";
					
					for(int i=0; i<patternBuild.length; i++)
					{
						build+="\t\t"+patternBuild[i]+"\n"; 
					}
					
					build+= "\t\t"+scores+"\n"; 
					build+="\n"; 
				}
				

			}//end sum
			
			
		}//end mine
		
		return build; 
	}
	
	
}









