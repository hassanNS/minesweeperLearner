package test;

import java.util.Scanner;

public class WhateverTest {

	public static void main(String[] args)
	{
		int maxScore = 200000;  //number of games
		int[] score = new int[10]; 
		
		for(int i=0; i<score.length; i++)
			score[i] = 0; 
		
		Scanner input = new Scanner(System.in); 
		
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
			values[i] = Integer.parseInt(valuesStr[i]); 
			
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
