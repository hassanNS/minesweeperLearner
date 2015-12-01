package test;

import models.PatternHash;
import models.PatternListGenerator;

public class PaternListGeneratorTest {

	public static void main(String[] args) 
	{
		
		PatternHash list = PatternListGenerator.generate(30, 60, 99, 25); 
		
		System.out.println(list);

	}

}
