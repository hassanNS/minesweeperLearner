package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import grid.MapGrid;
import models.Pattern;
import models.PatternHash;
import models.Tokenizer;

public class PatternTokenizerTest {

	
	/** This main method tests PatternHash capability of storing unique patterns. 
	 * 		
	 *	It starts off initlizing core variables such as grid attributes and iterations. 
	 *	Next, it starts tokenizing and regenration grid based on desired iterations. 
	 *
	 *	At the end, prints the baked PatternHash into a print stream (in this case a file) called 
	 *	"30_16_99_100" (width_height_mine_iteration)
	 * 
	 */
	public static void main(String[] args) throws FileNotFoundException
	{
		int width = 30; 
		int height = 16; 
		int minimum = 99; 
		int Iteration = 100; 
		
		//objects needed
		MapGrid map = new MapGrid(width, height, minimum);
		Tokenizer tokenizer = new Tokenizer(); 
		PatternHash list = new PatternHash(); 
		
		//print stream 
		PrintStream out = new PrintStream(new File("30_16_99_100"));
		
		//tokenize then regenerate 
		for(int itt=0; itt<Iteration; itt++)
		{
			//adding patterns to hash 
			for(int i=0; i<width; i++)
				for(int j=0; j<height; j++)
				{
					if(map.grid[j][i]==0)
						continue; 
					list.addPattern(tokenizer.tokenizePattern(i, j, map));
				}
	
			//recreating map with the same attributes 
			map.regenerate();
		
		}//end iteration 
		
		
		
		out.println(list);
		
		/*
		//Printing Patterns 
		//max mine = 8 (+empty) 
		for(int m=0; m<9; m++)
		{
			out.println("Mines = "+m);
					
			//max sum = 40 (+zero)
			for(int s=0; s<41; s++)
			{
				//skip is no pattern exist with this score to save space TODO patterns should print next to each other
				if(list.getPat(m, s)==null)
					continue; 
				
				out.println("\t Sum = "+s);
						
				//print all patterns if existed
				Pattern pointer = list.getPat(m, s);
				
				if(pointer==null) //never called 
				{
					out.println("\t\tempty");
				}
				else
				{
					do
					{ 	//print the patterns as string and attach uncomputed score at the end 
						out.println(pointer+" score:"+pointer.score[0]+":"+pointer.score[1]+"\n"); 
						pointer = pointer.next; 
					}while(pointer!=null);
				}
			}
		}//end printing  
		
		*/
		out.close(); 
		System.out.println("END");
	}

	
}