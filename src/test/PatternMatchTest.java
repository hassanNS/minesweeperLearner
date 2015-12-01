package test;

import models.Pattern;

public class PatternMatchTest {

	public static void main(String[] args) 
	{
		String pat = "01234567";
		String[] patternTests = {
				"01234567", 
				"23456701",
				"45670123", 
				"67012345", 
				"07654321",
				"65432107",
				"43210765",
				"21076543",
		};
		
		
		
		Pattern rootPattern = new Pattern(pat, 0);
		
		System.out.println("Pattern:\n"+ rootPattern);
		for (int i=0; i < patternTests.length; i++)
		{
			Pattern patObj2 = new Pattern(patternTests[i], 0);
			
			
			System.out.println("Pattern 2\n" + patObj2);
			System.out.println("Do match score? " + rootPattern.isMatch(patObj2));
		}

	}

}
