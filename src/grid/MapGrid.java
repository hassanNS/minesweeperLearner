package grid;
import java.util.Random;

/**
 * Map grid produces a Width x Height integer grid with n Bomb randomly placed.
 *  Placing a bomb would replace tile at x,y with a bomb adding +1 to surrounding tiles.
 * 
 * @author KLD
 *
 */
public class MapGrid 
{
	final int BOMB = -10; 
	
	//0 empty 
	int[][] grid; 
	
	int width; 
	int height; 
	
	int maxMine; 
	
	public MapGrid(int w, int h, int m)
	{
		width = w; 
		height = h; 
		maxMine = m; 
		
		regenerate(); 
		
	}//end constructor 
	
	/**
	 * initliizeze grid and place random bombs
	 */
	public void regenerate()
	{
		//generate empty grid 
		
		grid = new int[height][width]; 
		
		for(int i=0; i<height; i++)
			for(int j=0; j<width; j++)
				grid[i][j] = 0; 
		
		
		//randomly place bombs (bombs are negative 
		Random r = new Random(); 
		int rx, ry; 
		
		for(int i=0; i<maxMine; i++)
		{
			rx = r.nextInt(width);
			ry = r.nextInt(height); 
			
			while(grid[ry][rx] < 0)
			{
				rx = r.nextInt(width);
				ry = r.nextInt(height); 
			}
	
				//place bomb 
			grid[ry][rx] = BOMB; 
			
			int ox = rx-1;
			int oy = ry-1; 
			
			for(int j=0; j<9; j++)
			{
				int tx = ox + j%3; 
				int ty = oy + j/3; 
			
				if(tx >= 0 && tx < width && ty >= 0 && ty < height)
					grid[ty][tx]++; 
				
			}
		}
				
	}
	
	/**
	 * Returns string charachter representation of tile at given x and y
	 * @param x x position
	 * @param y y position 
	 * @return tile[y][x] as string char: "*"->bomb, " "->empty, "%n"->number
	 */
	public String tileString(int x, int y)
	{
		if(grid[y][x] == 0)
			return "0"; 
		else if(grid[y][x] > 0)
			return grid[y][x]+""; 
		else
			return "*";
	}
	
	/**
	 * Returns a string presentation of the grid 
	 */
	public String toString()
	{
		String build = ""; 
		
		for(int i=0; i<height; i++)
		{
			for(int j=0; j<width; j++)
			{
				build += " " + tileString(j,i) + " ";
					
			}//end j
			build += "\n"; 
		}//end i
		
		
		
		return build; 
	}
	
	
}//end class 
