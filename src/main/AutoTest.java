package main;

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
	
	/** @author KLD
	 * 
	 * 	This main will play the game 1 million times using 8x8 grid with 8 mines. 
	 * 
	 * 	Output can be controlled using boolean controls (located after game values) 
	 * 
	 * 
	 * Note: it takes a lot of time to run 1m 
	 * 
	 */

	@SuppressWarnings("resource")
	public static void main(String[] args) throws FileNotFoundException 
	{
		
		
		//game values 
		int width = 8;
		int height = 8; 
		int mine = 8; 
		int games = 5000;
		
		System.out.printf("For this version of the program, we'll be using width=%d height=%d mine=%d\n", width, height, mine);
		System.out.printf("Bot will run %d game simulations\n",games);
		
		//controls 
		boolean gridPrint = false; 
		boolean clickStatusPrint = false;
		boolean endGamesStatusPrint = true; 
		boolean pickedTokenPrint = false; 
		boolean clickCoordPrint = false;  // 
		boolean minesLeftPrint = false;  //mines left after a click or flag
		boolean singleGameStatusPrint = true;  // game number win or lose 
		
		String patternsPath = "1mThreshv2.ser"; 
		
		//load reliable pattern table  
		PatternHash list = Saver_Loader.loadHashTable(patternsPath);
		if(list==null)
		{
			System.out.println("Failed to read pattern file named " + patternsPath);
			return; 
		}
				
		System.out.println("Patterns are read and ready to run\nPress enter to start...");
		
		
		new Scanner(System.in).nextLine(); 
		
		//Vales to keep track of 
		int gameWon = 0; 
		int gameLost = 0;
		int clickSuccess = 0;
		int clickFail = 0;
		int flagSuccess = 0;
		int flagFail = 0;
		String gameNumberWins = "";  // list of games won added by game number 
		
		//if failed to win any games, this will print how close the bot  managed to progress to winning.  
		int maxMineFound = mine; 
		
		//print the map closest to winning 
		String maxMap = ""; 
		
		try
		{
		
		for(int o = 0; o < games; o++)
		{
			//create new grid 
			MapGrid grid = new MapGrid(width, height, mine); 
			
			//create new game 
			MineSweeper mineSweeper = new MineSweeper(grid); 
				
			//create new token picker 
			BasicPicker picker = new BasicPicker(list);
			
			//assign picker to game 
			mineSweeper.picker = picker; 
		
			//flag to control when game is over
			boolean onGame = true; 
		
			int c;  //control decision type:  0 click, 1 flag 
			int x;  //decision coordinate 
			int y; 	//decision coordinate 
		
			//click random place 
			Random ran = new Random();
			x = ran.nextInt(width-1);
			y  = ran.nextInt(height-1);
			
			//first click insure not losing at first click 
			 mineSweeper.firstClick(x, y);
			
			 //log 
			if(clickCoordPrint)
				System.out.println("First click at x: "+ x + " y:" + y + "\n");	
			
			//log 
			if(gridPrint)
			{
				System.out.println(mineSweeper.map+"\n");
				System.out.println(mineSweeper+"\n");
			}
			
			//start making decisions 
			do
			{
				//picks a token to based a decision on 
				Token token = picker.pickToken();
				
				//extraction input information from token such as x,y and type (mine or non-mine) 
				x = token.x; 
				y = token.y; 
				c = token.pair.type(); //type is based off score 

				//log
				if(pickedTokenPrint)
					System.out.println("Token:\n"+token);
				
				//[extra] keep track of decision's success 
				boolean success; 
				
				//decision action 
				if(c == Pattern.TYPE_NON_MINE)
				{
					//store game status after decision 
					onGame = mineSweeper.click(x, y);
					
					if (onGame) 
					{
						success = true; 
						clickSuccess++;
						
						//log 
						if(clickStatusPrint)
							System.out.printf("Success click x:%d y:%d\n", x, y);
					}
					else
					{
						success = false; 
						clickFail++;
						
						//log 
						if(clickStatusPrint)
							System.out.printf("Fail click x:%d y:%d\n", x, y);
					}
				}
				else
				{
					//flag doesn't return a value because in actual game flaging doesn't make player lose nor win 
					mineSweeper.flag(x, y);
					
					//check flag decision correctness 
					//NOTE: this is kinda cheating, but we end the game if the flag was set wrong 
					if (mineSweeper.map.tileString(x, y).charAt(0) == Tile.MINE)
					{
						success = true; 
						flagSuccess++;
						
						//log 
						if(clickStatusPrint)
							System.out.printf("Success flag x:%d y:%d\n", x, y);
						
					}
					else
					{
						success = false; 
						flagFail++;
						
						//end game 
						onGame = false; 
						
						//log
						if(clickStatusPrint)
							System.out.printf("Fail flag x:%d y:%d\n", x, y);
						
						//reverse flag function -TODO should happen in MineSweeper.flag(int,int)
						mineSweeper.minesLeft++;
					}
				}
				
				/* Expectancy: in this part the bot re-evaluates patterns based in their success or failure. 
				 * The bot doesn't tolerate failing, so it punishes failing crucially.
				 * 
				 *  (Note: pure patterns have no effect here).
				 *  Reminder that a pattern has two scores: [mine and non-mine] which correspond to [flag and click]. 
				 * 
				 *	If a non-pure pattern was successful in predicting, it would increment the decision score by 1
				 * 
				 * 	On the other hand, if a non-pure pattern fails to predict, the deduction is equal to uncertainty. 
				 * 
				 * 	Token.uncertainty is the percentage is hidden tiles. 
				 * 
				 * If the pattern is not strong (aka not expected to succeed), the deduction is halfed. 
				 * 
				 * 
				 */
				
				//storing if pattern is strong 
				int strong; 
				
				if(token.pair.strength() > 100)
					strong = 1; 
				else 
					strong = 2; 
				
				//expectancy and re-evaluation unless pattern is pure 
				if(!token.pair.isPure())
				{
					//if success, increment by 1  
					if(success)
						token.pair.score[c]++; 
					//if fails, punish 
					else 
					token.pair.score[c] -= (token.pair.score[c]*token.uncertinty())/strong;
				}
				
				//remove clicked token from picker 
				picker.clicked(x, y);
				
				//logs everywhere
				if(gridPrint)
					System.out.print(mineSweeper);
			
				if(minesLeftPrint)
					System.out.println("Mines left: "+ mineSweeper.minesLeft +"\n\n\n");

				if(mineSweeper.minesLeft < maxMineFound)
				{
					maxMineFound = mineSweeper.minesLeft; 
					maxMap = mineSweeper.toString(); 
				}
				
				//end the game if no mines are left to discover 
				if(mineSweeper.minesLeft==0)
				{
					//store game number won 
					gameNumberWins+= o + " "; 
					break;  
				}
					
		}while(onGame); //end do
		
		//after game is done, check if user won or lost 
		if(mineSweeper.minesLeft==0)
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
		
	}//end for o 
		
		
		
	}//end try & catch any unexpected error and end the run  by forcefully printing game status 
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
		}//end catch 
		
		
		//more logs 
		if(endGamesStatusPrint)
		{
			//win lose and clicks flags log 
			System.out.println("Win:" + gameWon + " Lost:" + gameLost); 
			System.out.printf("Click Suc: %d Click Fail: %d\nFlag Suc: %d Flag Fail: %d\n", clickSuccess, clickFail, flagSuccess, flagFail);
		
			//if no games are won, print the closest match to winning 
			if(gameWon==0)
			{
				System.out.println("Most mine found:"+ (mine-maxMineFound));
				System.out.println(maxMap);
				return; 
			}
			
			//if some games are won, bin the games into ten bins to see improvements 
			String[] wonGames = gameNumberWins.split(" "); 
			
			//store game in case data s lost 
			PrintStream out = new PrintStream(new File("gameWon"));
			
			//print game number line by line 
			for(int i=0; i<wonGames.length; i++)
			{
				System.out.println(wonGames[i]);
				out.println(wonGames[i]);
			}
			
			out.close();
			
			//Binning games won into 10 bins
			
			int maxScore = games;  //number of games
			
			//score bin 
			int[] score = new int[10]; 
			
			//[extra] init 
			for(int i=0; i<score.length; i++)
				score[i] = 0; 
			
			//converting string values into int 
			String[] valuesStr = wonGames;
			
			int[] values = new int[valuesStr.length];
			
			for(int i=0; i<values.length-1; i++)
			{
				try
				{
				// converting string numbers into integers after removing non-digit charachters 
				values[i] = Integer.parseInt(valuesStr[i].replace("\\D", "")); 
				
				//Incrementing bin counter 
				score[(values[i]*score.length)/maxScore]++; 
				//System.out.println("found " + values[i] );
				
				}
				catch(Exception e)
				{
					System.out.println("Happened at " + i);
					e.printStackTrace();
				}
			
			}
			
			
			//printing bin 
			for(int i=0; i<score.length; i++)
			{
				System.out.println((i+1)+"-\t"+score[i]);
			}
			
			
			
		}//end if print
	
	}

}
