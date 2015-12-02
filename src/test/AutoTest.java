package test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

import algorithm.BasicPicker;
import grid.MapGrid;
import grid.MineSweeper;
import models.Pattern;
import models.PatternHash;
import models.PatternListGenerator;
import models.Saver_Loader;
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
		
		//System.out.println(g);
		
		PatternHash list = Saver_Loader.loadHashTable("HASH.ser");
				
		System.out.println("Found pattern:" + list.count);
		
		//PrintStream out = new PrintStream(new File("Generation")); 
		//out.println(list);
		//out.close(); 
		
		System.out.println("ready!");
		
		int gameWon = 0; 
		int gameLost = 0;
		int clickSuccess=0;
		int clickFail=0;
		int flagSuccess=0;
		int flagFail=0;
		
		for(int o=0; o<2; o++)
		{
			//System.out.println(m.map+"\n");			
			BasicPicker picker = new BasicPicker(list);
		
			m.picker = picker; 
		
			boolean onGame = true; 
		
			int c; 
			int x; 
			int y; 	
		
			//Scanner input = new Scanner(System.in); 
		
			//click random place 
			Random ran = new Random();
			int ran_x = ran.nextInt(width-1);
			int ran_y = ran.nextInt(height-1);
			m.isFirstClick = true;
			m.click(ran_x, ran_y);
			System.out.println("I clicked at x: "+ran_x + " y:" + ran_y + "\n" + m.map+"\n");	
			
			do
			{
			
				boolean expectancy = false;
				
				Token token = picker.pickToken();
				
				/*
				try{
				//Token token = picker.pickToken();
				
				//System.out.println("We suggest: x,y : "+ token.x + " "+ token.y + " as " + ((token.pair.type()==Pattern.TYPE_MINE)? "Mine" : "Not mine"));
				}catch (Exception e)
				{
					System.out.println("Error picking token");
					Token pointer = picker.root;
					while(pointer!=null)
					{
						System.out.println(pointer);
						pointer = pointer.next; 
					}
				}
				*/
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
				{
					c = token.pair.type(); 
				}
				
				x = token.x; 
				y = token.y; 
				
				// We are expecting
				if (token.pair != null)
				{
					if (token.pair.strength() > 100)
						expectancy = true;
				}
				else
					System.out.println("Token is null");
				
				//for both cases, if expectancy is true, and pattern type match, 
				int non_mine = token.pair.score[Pattern.TYPE_NON_MINE];
				float incr = token.certinty * non_mine;
				
				
				//decision action 
				if(c==Pattern.TYPE_NON_MINE)
				{
					onGame = m.click(x, y);

					if (expectancy == true && c == token.pair.type())
						incr = incr/2;
					
					if (onGame) 
					{
						clickSuccess++;
						System.out.printf("Good click x:%d y:%d\n", ran_x, ran_y);
						token.pair.score[Pattern.TYPE_NON_MINE] += incr;
					}
					else
					{
						System.out.printf("Bad click x:%d y:%d\n", ran_x, ran_y);
						clickFail++;
						token.pair.score[Pattern.TYPE_NON_MINE] -=  incr;
					}
					

				}else
				{
					m.flag(x, y);
					
					if (expectancy == true && c == token.pair.type())
						incr = incr/2;
					
					if (g.tileString(x, y).charAt(0) == Pattern.TYPE_MINE){
						flagSuccess++;
						System.out.printf("Good flag x:%d y:%d\n", ran_x, ran_y);
						token.pair.score[Pattern.TYPE_MINE] += incr;
					}
					else{
						flagFail++;
						System.out.printf("Bad flag x:%d y:%d\n", ran_x, ran_y);
						token.pair.score[Pattern.TYPE_MINE] -= incr;
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
			System.out.println("lost");
			gameLost++; 
		}
		
		m.map.regenerate();
	}
		//System.out.println(m);	
		//System.out.println("Game Over!");
	
		//input.close(); 
		
		
		System.out.println("Win:" + gameWon + " Lost" + gameLost); 
		System.out.printf("cS: %d cF: %d fS: %d fF: %d\n", clickSuccess, clickFail, flagSuccess, flagFail);
	}

}
