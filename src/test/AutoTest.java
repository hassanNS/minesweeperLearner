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
import models.Saver_Loader;
import models.Tile;
import models.Token;

public class AutoTest 
{
	
	/**
	 *  size\mine	8			9			10			11			12			
	 *  8x8			18:24982						1:24999
	 *  			15413:16570						14001:15063
	 *  			40663:8412						36130:9936
	 *  
	 *  
	 *  
	 *  int width = 8;
	 *	int height = 8; 
	 *	int mine = 8; 
	 *	int games = 250000;
	 *  Win:296 Lost:249704
	 *	Click Suc: 154024 Click Fail: 171333
	 *	Flag Suc: 387848 Flag Fail: 78371
	 */

	public static void main(String[] args) throws FileNotFoundException 
	{
		int width = 8;
		int height = 8; 
		int mine = 8; 
		int games = 1000000;
		
		//controls 
		boolean gridPrint = false; 
		boolean clickStatusPrint = false;
		boolean endGamesStatusPrint = true; 
		boolean pickedTokenPrint = false; 
		boolean clickCoordPrint = false;  // 
		boolean minesLeftPrint = false;  //mines left after a click or flag
		boolean singleGameStatusPrint = true;  // game number win or lose 
		
		//MapGrid g = new MapGrid(width, height, mine); 
		//MineSweeper m = new MineSweeper(g); 
		
		//System.out.println(g);
		
		PatternHash list = Saver_Loader.loadHashTable("1mThresh.ser");
				if(list==null)
				{
					System.out.println("Failed to read pattern");
					return; 
				}
				
		//System.out.println("Found pattern:" + list.length);
		
		//PrintStream out = new PrintStream(new File("Generation")); 
		//out.println(list);
		//out.close(); 
		
		int gameWon = 0; 
		int gameLost = 0;
		int clickSuccess=0;
		int clickFail=0;
		int flagSuccess=0;
		int flagFail=0;
		
		int maxMineFound = mine; 
		String maxMap = ""; 
		String gameNumberWins = ""; 
		
		try
		{
		
		for(int o=0; o<games; o++)
		{
			MapGrid g = new MapGrid(width, height, mine); 
			MineSweeper m = new MineSweeper(g); 
					
			BasicPicker picker = new BasicPicker(list);
			m.picker = picker; 
		
			boolean onGame = true; 
		
			int c; 
			int x; 
			int y; 	
		
			//click random place 
			Random ran = new Random();
			int ran_x = ran.nextInt(width-1);
			int ran_y = ran.nextInt(height-1);
			
			 m.firstClick(ran_x, ran_y);
			
			if(clickCoordPrint)
				System.out.println("first click at x: "+ ran_x + " y:" + ran_y + "\n");	
			
			if(gridPrint)
			{
				System.out.println(m.map+"\n");
				System.out.println(m+"\n");
			}
			
			do
			{
				Token token = picker.pickToken();
				
				x = token.x; 
				y = token.y; 
				c = token.pair.type(); 

				//for both cases, if expectancy is true, and pattern type match, 
				//int non_mine = token.pair.score[Pattern.TYPE_NON_MINE];
				//float incr = token.certinty * non_mine;
				if(pickedTokenPrint)
					System.out.println("Token:\n"+token);
				
				//float special = 0f; 
				boolean success; 
				
				//decision action 
				if(c==Pattern.TYPE_NON_MINE)
				{
					onGame = m.click(x, y);
					//if (expectancy == true && c == token.pair.type())
						//incr = incr/2;
					
					if (onGame) 
					{
						success = true; 
						clickSuccess++;
						if(clickStatusPrint)
							System.out.printf("Success click x:%d y:%d\n", x, y);
					}
					else
					{
						success = false; 
						if(clickStatusPrint)
							System.out.printf("Fail click x:%d y:%d\n", x, y);
						clickFail++;
					}
				}
				else
				{
					m.flag(x, y);
					
					if (g.tileString(x, y).charAt(0) == Tile.MINE)
					{
						success = true; 
						flagSuccess++;
						if(clickStatusPrint)
							System.out.printf("Success flag x:%d y:%d\n", x, y);
						
					}
					else
					{
						success = false; 
						flagFail++;
						if(clickStatusPrint)
							System.out.printf("Fail flag x:%d y:%d\n", x, y);
						m.minesLeft++; 
						break;
					}
				}
				
				int expected; 
				
				if(token.pair.strength() > 100)
					expected = 1; 
				else 
					expected = 2; 
				
				
				if(!token.pair.isPure())
				{
					if(success)
						token.pair.score[c]++; 
					else
					token.pair.score[c] -= (token.pair.score[c]*token.uncertinty())/expected;
				}
				
				picker.clicked(x, y);
				
				if(gridPrint)
					System.out.print(m);
			
				if(minesLeftPrint)
					System.out.println("Mines left: "+ m.minesLeft +"\n\n\n");

				if(m.minesLeft < maxMineFound)
				{
					maxMineFound = m.minesLeft; 
					maxMap = m.toString(); 
				}
				
				if(m.minesLeft==0)
				{
					gameNumberWins+= o + " "; 
					break;  
				}
					
		}while(onGame);
		
		
		if(m.minesLeft==0)
		{
			if(singleGameStatusPrint)
				System.out.println("GAME #"+(o+1)+" Won!");
			gameWon++; 
		}
		else 
		{
			if(singleGameStatusPrint)
				System.out.println("GAME #"+(o+1)+" lost");
			gameLost++; 
		}
		
		m.map.regenerate();
	}//end do 
		
		
		
	}//end try 
		catch(Exception e)
		{
			System.out.println("Win:" + gameWon + " Lost:" + gameLost); 
			System.out.printf("Click Suc: %d Click Fail: %d\nFlag Suc: %d Flag Fail: %d\n", clickSuccess, clickFail, flagSuccess, flagFail);
		
			if(gameWon==0)
			{
				System.out.println("Most mine found:"+ (mine-maxMineFound));
				System.out.println(maxMap);
			}
			
			e.printStackTrace();
		}
		if(endGamesStatusPrint)
		{
			System.out.println("Win:" + gameWon + " Lost:" + gameLost); 
			System.out.printf("Click Suc: %d Click Fail: %d\nFlag Suc: %d Flag Fail: %d\n", clickSuccess, clickFail, flagSuccess, flagFail);
		
			if(gameWon==0)
			{
				System.out.println("Most mine found:"+ (mine-maxMineFound));
				System.out.println(maxMap);
			}
			
			String[] wonGames = gameNumberWins.split(" "); 
			
			PrintStream out = new PrintStream(new File("gameWon"));
			
			for(int i=0; i<wonGames.length; i++)
			{
				System.out.println(wonGames[i]);
				out.println(wonGames[i]);
			}
			
			out.close();
			
			int maxScore = games;  //number of games
			int[] score = new int[10]; 
			
			for(int i=0; i<score.length; i++)
				score[i] = 0; 
			
			//Scanner input = new Scanner(System.in); 
			
			//String build = ""; 
			
			/*while(input.hasNext())
			{
				
				String  in = input.nextLine() + " ";  
				System.out.println("added " +in);
				build += in; 
				
				if(in.startsWith("0"))
					break; 
			}*/
			
			//input.close();
			
			//System.out.println("Splitting: " + build);
			String[] valuesStr = wonGames;//build.split(" "); 
			
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
			
			
			
			
			
		}//end if print
	
	}

}
