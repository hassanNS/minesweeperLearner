package test;

import grid.MapGrid;
import grid.MineSweeper;
import models.PatternHash;
import models.PatternListGenerator;

public class IterationTest {

	public static void main(String[] args) 
	{
		
		float a = 100000000000000000000000000000000000000f;
		float b =-100000000000000000000000000000000000000f;
		float c = 1.0f; 
		
		System.out.println((a+b)+c);
		System.out.println(a+(b+c));
		
		/*
		int width = 8;
		int height = 8; 
		int mine = 10; 
		
		MapGrid g = new MapGrid(width, height, mine); 
		MineSweeper m = new MineSweeper(g); 
		
		int prev =0; 
		for(int i=1000; i<10000; i+=10)
		{
			PatternHash.count = 0; 
			PatternHash list = PatternListGenerator.generate(width, height, mine, 500*i);  
			
			System.out.println(i+"- itr:"+ i*500  +"  count:" + list.count +" dif:"+(list.count-prev));
			prev = list.count; 
		}
		
*/
	}

}
