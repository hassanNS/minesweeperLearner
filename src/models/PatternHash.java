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
	public static final int MAX_MINE = 8;
	private int maxSum = 40;
	public Pattern [][] table;
	public static int count; 
	//int[] boundaries = {40, 40, 40, 36, 32, 24, 16, 8};
	public int length; 
	
	
	// Initializes the Hashtable
	public PatternHash()
	{
		length = 0; 
		count = 0; 
		table = new Pattern[MAX_MINE+1][maxSum+1]; //includes 0
	}

	public Pattern getPat(int mine, int sum)
	{
		//System.out.println("mine:" + mine+" sum"+sum);
			return table[mine][sum];
	}

	/**
	 *
	 * @param p
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
			if(root.append(p))
				length++; 
		}

	}

	/**
	 * Print a pattern linked list
	 * @param mineNum
	 * @param tileSum
	 */
	public void printPatternLinkedList(int mineNum, int tileSum)
	{
		table[mineNum][tileSum].printLinkedList();
	}

	/**
	 * Return a pattern linked list from the hashtable
	 * @param mineNum
	 * @param tileSum
	 * @return
	 */
	public Pattern getPatternLinkedList(int mineNum, int tileSum)
	{
		return table[mineNum][tileSum];
	}

	/**
	 * Write every pattern inside the hashtable to a csv file TODO delete or move! -KLD
	 */
	public void writeToFile()
	{
		try{
			PrintWriter writer = new PrintWriter("patternData.csv", "UTF-8");
			for(int i=0; i<MAX_MINE+1; i++)
			{
				for(int j=0; j<maxSum+1; j++)
				{
					if(getPatternLinkedList(i, j) == null)
						continue;

					Pattern temp = getPatternLinkedList(i, j);
					do{
						writer.printf("%s,%d,%d\n",
								temp.getPatternString(), temp.mines,
								temp.sum);
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


	@Override
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









