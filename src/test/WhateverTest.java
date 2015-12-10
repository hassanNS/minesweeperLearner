package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WhateverTest {

	public static void main(String[] args)
	{
		/**
		 * 250,000 games
		  	1-	23
			2-	20
			3-	26
			4-	36
			5-	32
			6-	24
			7-	29
			8-	28
			9-	34
			10-	44
			
			1,000,000 games  1378 Lost:998622
			1-	123
			2-	128
			3-	144
			4-	133
			5-	139
			6-	153
			7-	129
			8-	144
			9-	132
			10-	152
		 * 
		 * 
		 */
		
		int maxScore = 1000000;  //number of games
		int[] score = new int[50]; 
		
		for(int i=0; i<score.length; i++)
			score[i] = 0; 
		
		Scanner input;
		try {
			input = new Scanner(new File("gameWon"));
		} catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
			return; 
		} 
		
		String build = ""; 
		
		while(input.hasNext())
		{
			
			String  in = input.nextLine() + " ";  
			System.out.println("added " +in);
			build += in; 
			
			if(in.startsWith("0"))
				break; 
		}
		
		input.close();
		
		System.out.println("Splitting: " + build);
		String[] valuesStr = build.split(" "); 
		
		int[] values = new int[valuesStr.length];
		
		System.out.println("Adding..");
		for(int i=0; i<values.length-1; i++)
		{
			try
			{
			values[i] = Integer.parseInt(valuesStr[i].replace("\\D", "")); 
			
			score[(values[i]*score.length)/maxScore]++; 
			System.out.println("found " + values[i] );
			}
			catch(Exception e)
			{
				System.out.println("Happened at " + i);
				e.printStackTrace();
			}
		
		}
		
		
		for(int i=0; i<score.length; i++)
		{
			System.out.println((i+1)+"-\t"+score[i]);
		}
		
	}

}
