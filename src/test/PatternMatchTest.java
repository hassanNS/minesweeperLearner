package test;

import models.Pattern;

public class PatternMatchTest {

	public static void main(String[] args) 
	{
		String pat = "01234567";
		String[] patternTests = {
				"01234567", 
				"67012345", 
				"45670123", 
				"23456701",
				"07654321",
				"65432107",
				"43210765"};
		
		
		Pattern patObj = new Pattern(pat);
		
		for (int i=0; i < patternTests.length; i++)
		{
			Pattern patObj2 = new Pattern(patternTests[i]);
			
			System.out.println("Sum of first one " + patObj.getSum());
			System.out.println("Sum of first two " + patObj2.getSum());
			System.out.println("Match score " + patObj.isMatch(patObj2));
		}

	}

}
