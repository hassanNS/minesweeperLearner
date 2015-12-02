package test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import algorithm.BasicPicker;
import grid.MapGrid;
import grid.MineSweeper;
import models.Pattern;
import models.PatternHash;
import models.PatternListGenerator;
import models.Token;

public class AutoTest 
{

	public static void main(String[] args) throws FileNotFoundException 
	{
		int width = 8;
		int height = 8; 
		int mine = 10; 
		
		MapGrid g = new MapGrid(width, height, mine); 
		MineSweeper m = new MineSweeper(g); 
		
		System.out.println(g);
		
		PatternHash list = PatternListGenerator.untilEnough(width, height, mine, 50000);  
		
		
		System.out.println("Found pattern:" + list.count);
		
		//PrintStream out = new PrintStream(new File("Generation")); 
		//out.println(list);
		//out.close(); 
		
		System.out.println("ready!");
		
		int gameWon = 0 ; 
		int gameLost = 0 ; 
		
		for(int o=0; o<50000; o++)
		{
			
			BasicPicker picker = new BasicPicker(list);
		
			m.picker = picker; 
		
			
		
			boolean onGame = true; 
		
			int c; 
			int x; 
			int y; 
		
			//Scanner input = new Scanner(System.in); 
		
			do
			{
			
			//System.out.println(m);
			Token token = picker.pickToken();
			
			
			
			try{
			//Token token = picker.pickToken();
			
			//System.out.println("We suggest: x,y : "+ token.x + " "+ token.y + " as " + ((token.pair.type()==Pattern.TYPE_MINE)? "Mine" : "Not mine"));
			}catch (Exception e)
			{
				System.out.println("Erorr picking the asshole");
				Token pointer = picker.root;
				while(pointer!=null)
			{
				System.out.println(pointer);
				pointer = pointer.next; 
			}
			}
			
			//System.out.print("Enter c x y: ");
			//c = input.nextInt(); 
			//x = input.nextInt(); 
			//y = input.nextInt(); 
			
			
			if(token==null)
			{
				c = 0; 
				token = new Token("S", 2, 2);
			}
			else
				c = token.pair.type(); 
			x = token.x; 
			y = token.y; 
			
			if(c==Pattern.TYPE_NON_MINE)
			{
				onGame = m.click(x, y); 
			}else
			{
				try
				{
					m.flag(x, y);
				}
				catch(Exception e)
				{
					break; 
				}
			}
		
			if(m.minesLeft==0)
				break; 

			
		}while(onGame);
		
		if(m.minesLeft==0)
		{
			//System.out.println("won");
			gameWon++; 
		}
		else 
		{
			//System.out.println("lost");
			gameLost++; 
		}
		
		g.regenerate();
		System.out.println(g+"\n");
		m = new MineSweeper(g); 
	}
		//System.out.println(m);	
		//System.out.println("Game Over!");
	
		//input.close(); 
		
		
		System.out.println("Win:" + gameWon + " Lost" + gameLost); 
	}

}
