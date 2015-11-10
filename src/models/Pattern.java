package models;

import java.util.Arrays;

public class Pattern {
	
	// Stores the pattern in an array format.
	private String pattern;
	int numOfMines;
	// The sum of the highest numbers available for this pattern
	int tileSum;
	
	public int index = tileSum;
	private Pattern next;
	
	public Pattern(String pattern) 
	{
		this.pattern = pattern;
		calculateSum();
	}
	
	public int match (Pattern pat) //TODO rename to marchToekn, and create boolean matchPattern(Pattern)
	{
		char[] thisPatArray = getPatternArray();
		char [] patArray = pat.getPatternArray();
		
		// We will run 4 checks forward
		// Then 4 checks backwards
		int [] matchWeights = {0, 0, 0, 0, 0, 0, 0, 0};
		
		for (int i=0; i < thisPatArray.length; i++)
		{
			for (int j=0; j < patArray.length; j++)
			{
				// These decisions checks from left to right, from each
				// corner of the pattern. 0, 2, 4, and 6
				if (thisPatArray[i] == patArray[j])
					matchWeights[0]++;
				if (thisPatArray[i] == patArray[(j + 2) % 8])
					matchWeights[1]++;
				if (thisPatArray[i] == patArray[(j + 4) % 8])
					matchWeights[2]++;
				if (thisPatArray[i] == patArray[(j + 6) % 8])
					matchWeights[3]++;
			}
			
			for (int j=patArray.length; j > 0; j--)
			{
				// These decisions does the same check above but backwards
				if (thisPatArray[i] == patArray[(8-j)])
					matchWeights[4]++;
				if (thisPatArray[i] == patArray[Math.abs((j - 2) % 8)])
					matchWeights[5]++;
				if (thisPatArray[i] == patArray[Math.abs((j - 4) % 8)])
					matchWeights[6]++;
				if (thisPatArray[i] == patArray[Math.abs((j - 6) % 8)])
					matchWeights[7]++;
			}
		}
		
		// Get the max of both comparisons
		int max = Arrays.stream(matchWeights).max().getAsInt();
		
		for (Integer a : matchWeights)
		{
			System.out.println(a);
		}
		
		return max;
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
	
	public void addNext(Pattern p)
	{
		this.next = p; //TODO this is not how you add to linkedlist
	}
	
	public Pattern next()
	{
		return this.next;
	}
	/**
	 * Returns a string presentation of the pattern
	 */
	public String toString()
	{
	
		return ""; //TODO
	}
	
	// Calculate the sum of all of the numbers on
	// the 3 x 3 pattern
	private void calculateSum()
	{
		char [] nums = getPatternArray();
		for (int i=0; i < nums.length; i++)
		{
			tileSum += Character.getNumericValue(nums[i]); //TODO This does not skip mines and blanks! 
			if (nums[i] < 0)
			{
				numOfMines++;
			}
		}
	}
}
