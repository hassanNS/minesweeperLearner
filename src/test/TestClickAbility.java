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

public class TestClickAbility 
{

	public static void main(String[] args) throws FileNotFoundException 
	{
		int width = 8;
		int height = 8; 
		int mine = 10; 
		
		MapGrid g = new MapGrid(width, height, mine); 
		MineSweeper m = new MineSweeper(g); 
		
		System.out.println(g);
		
		PatternHash list = PatternListGenerator.generate(width, height, mine, 10000);  
		
		System.out.println("Found pattern:" + list.count);
		
		PrintStream out = new PrintStream(new File("Generation")); 
		out.println(list);
		out.close(); 
		
		BasicPicker picker = new BasicPicker(list);
		
		m.picker = picker; 
		
		boolean onGame = true; 
		int x; 
		int y; 
		
		Scanner input = new Scanner(System.in); 
		
		do
		{
			System.out.println(m);

			
		try{
			Token token = picker.pickToken();
			
			System.out.println("We suggest: x,y : "+ token.x + " "+ token.y + " as " + ((token.pair.type()==Pattern.TYPE_MINE)? "Mine" : "Not mine"));
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
			
			System.out.print("Enter x y: ");
			x = input.nextInt(); 
			y = input.nextInt(); 
			
			onGame = m.click(x, y); 
			picker.clicked(x,y); 
			//Token token = m.tokenize(2, 5); 
			//System.out.println("Token at 2 5\n" + token);
			
			//System.out.println(list);
		
		}while(onGame);
		
		System.out.println(m);	
		System.out.println("Game Over!");
	
		input.close(); 
	}

}
