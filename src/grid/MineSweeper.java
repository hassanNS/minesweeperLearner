package grid;

import algorithm.BasicPicker;
import models.Tile;
import models.Token;

/**
 * A grid that hides MapGrid tiles and only reveals tile with clicked on a tile (which can reveal neighbor tiles recusively) 
 * 
 * @author KLD
 */
public class MineSweeper 
{
	/**
	 * map grid 
	 */
	public MapGrid map; 
	
	/**
	 * Tile Status  
	 */
	final int EXPOSED = -1; 
	/**
	 * Tile Status  
	 */
	final int HIDDEN = 0; 
	/**
	 * Tile Status  
	 */
	final int FLAGGED = 1; 
	
	/**
	 * used to determine winning condition TODO very poor 
	 */
	public int minesLeft; 
	 
	/**
	 * Tile Status  
	 */
	int[][] tileStatus; 
	
	/**
	 * Stores tokens and picks best token 
	 */
	public BasicPicker picker; 
	
	
	public MineSweeper(MapGrid m)
	{
		map = m; 
		
		minesLeft = m.mine; 
		
		tileStatus = new int[map.height][map.width];
		
		reset(); 	
	}
	
	/**
	 * hides all tiles  resting the game with the same MapGrid
	 */
	public void reset()
	{
		for(int i=0; i<map.height; i++)
			for(int j=0; j<map.width; j++)
				tileStatus[i][j] = HIDDEN; 
	}
	
	
	/**
	 * Generate a click that will result in revealing the clicked tile at x and y. If tile is empty, surrounding tiles will also be clicked. 
	 * Function will not excute if tile is already revealed. 
	 * 
	 * @param x tile x coordinate 
	 * @param y tile y coordinate 
	 * @return false if bomb is revealed, otherwise true. 
	 */
	public boolean click(int x, int y)
	{
		//test out of bounds 
		if(x < 0 || x >= map.width || y < 0 || y >= map.height)
			return true; 
		
		//already revealed
		if (tileStatus[y][x] == EXPOSED)
			return true; 
				
		//reveals clicked tile 
		tileStatus[y][x] = EXPOSED; 
				

		//return false if bomb 
		if(map.grid[y][x] <  0) //bomb
			return false; 
		
		//if empty, click surrounding 
		else if(map.grid[y][x] == 0)
		{
			//TODO make math happen
			click(x+1,y); 
			click(x+1, y+1);
			click(x+1, y-1);
			
			click(x,y+1); 

			click(x,y-1); 
			
			
			click(x-1,y); 
			click(x-1,y-1); 
			click(x-1,y+1); 
		}
		else if(map.grid[y][x] > 0) //it's a number
		{
			//tokenize surrounding 
			
			//offsets
			int[] xo = {0,1,2,2,2,1,0,0}; 
			int[] yo = {0,0,0,1,2,2,2,1};
			
			for(int i=0; i<xo.length; i++)
			{
				int lx =  x-1 + xo[i]; //local x 
				int ly =  y-1 + yo[i]; //local y
				
				if(lx < 0 || lx >= map.width || ly < 0 ||  ly >= map.height)
					continue;
				
				if(tileStatus[ly][lx] == HIDDEN)
				{
					if(picker != null)
					{
						Token token = tokenize(lx, ly); 
						if(token!=null)
							picker.addToken(token);
					}
				}
			}//end for
		}
		//up left right down
		picker.clicked(x,y); 
		return true; 
	}
	
	
	public void firstClick(int x, int y)
	{
		while(map.grid[y][x] != 0)	
				map.regenerate();	
		
		click(x,y);
	}
	
	/**
	 * Flag tile located at x,y only if tile is hidden. New tokens are added after flag. 
	 * The flagged tile will be removed from BasicPicker. 
	 * @param x coordinate 
	 * @param y coordinate
	 */
	public void flag(int x, int y)
	{
		if(tileStatus[y][x] != HIDDEN)
			return; 
		
		minesLeft--; 
		
		tileStatus[y][x] = FLAGGED; 
			
		//offsets for  3x3  TODO get these from picker 
		int[] xo = {0,1,2,2,2,1,0,0}; 
		int[] yo = {0,0,0,1,2,2,2,1};
		
		for(int i=0; i<xo.length; i++)
		{
			int lx =  x-1 + xo[i]; //local x 
			int ly =  y-1 + yo[i]; //local y
			
			//ignore out of bound coordinates 
			if(lx < 0 || lx >= map.width || ly < 0 ||  ly >= map.height)
				continue;
			
			if(tileStatus[ly][lx] == HIDDEN)
			{
				if(picker != null)
				{
					Token token = tokenize(lx, ly); 
					if(token != null)
						picker.addToken(token);
				}
			}
		}//end for	
		
		//removed clicked token  
		picker.clicked(x,y); 
		
	}
	
	
	/**
	 * Builds up a 3x3 token generated from game map 
	 * @param x coordinate
	 * @param y coordinate
	 * @return 3x3 Token 
	 */
	public Token tokenize(int x, int y)
	{
		String build = "";
		
		//offest
		int[] xo = {0,1,2,2,2,1,0,0}; 
		int[] yo = {0,0,0,1,2,2,2,1};
		
		for(int i=0; i<xo.length; i++)
		{
			
			int lx = x-1 + xo[i]; //local x 
			int ly = y-1 + yo[i]; //local y
			
			if(lx < 0 || lx >= map.width || ly < 0 || ly >= map.height)
				build += Tile.BORDER;
			else if(tileStatus[ly][lx] == HIDDEN)
				build += Tile.HIDDEN; 
			else if(tileStatus[ly][lx] == FLAGGED)
				build += Tile.MINE; 
			else if(map.grid[ly][lx] == 0)
			{
				//not a valid token
				return null; 
			}
			else //it's a number
				build += map.tileString(lx, ly); 
		}
		
		return new Token(build, x, y); 
	}
	
	
	/**
	 * Returns string representation of minesweepers grid revealing revealed parts in mapgrid while hiding unreleavled. 
	 */
	public String toString()
	{
		String build = "  "; 
		
		for(int i=0; i<map.width; i++)
			build += " " +  i + " "; 
		build += "\n"; 
		
		for(int i=0; i < map.height; i++)
		{
			build+= i+" "; 
			for(int j=0; j < map.width; j++)
			{
				if(tileStatus[i][j] == HIDDEN)
					build += "[ ]"; 
				else if(tileStatus[i][j] == FLAGGED)
					build += " F ";
				else 
					build += " " + map.tileString(j, i)+ " "; 
			}
			build += "\n"; 
		}
		
		
		return build; 
	}
}
