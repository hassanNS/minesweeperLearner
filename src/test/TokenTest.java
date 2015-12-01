package test;

import grid.MapGrid;
import grid.MineSweeper;
import models.Token;

public class TokenTest {

	public static void main(String[] args) 
	{
		MapGrid mg = new MapGrid(8, 8, 10); 
		
		MineSweeper ms = new MineSweeper(mg); 
		
		
		
		ms.click(1, 5);
		ms.click(2, 4);
		
		Token token = ms.tokenize(2, 5); 
		
		
		
		System.out.println("Map\n" + mg);
		
		
		System.out.println("Token at 2 5\n" + token);// lel, it should be all hidden 
		
		System.out.println("Avg sum:"+token.averageSum() + " MaxSum:" + token.estemateMaxMine());
		System.out.println("Est mine:"+token.estemateMaxMine());
		
		
		//test i contuned in clickability test 
		
		
	}

}
