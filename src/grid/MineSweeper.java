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
	public MapGrid map; 
	
	final int EXPOSED = -1; 
	final int HIDDEN = 0; 
	final int FLAGGED = 1; 
	
	public int minesLeft; 
	public boolean isFirstClick;
	 
	int[][] tileStatus; 
	
	public BasicPicker picker; 
	
	
	public MineSweeper(MapGrid m)
	{
		map = m; 
		
		minesLeft = m.mine; 
		
		isFirstClick = true;
		
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
	
		if(x < 0 || x >= map.width || y < 0 || y >= map.height)
			return true; 
		
		//already revealed
		if (tileStatus[y][x] == EXPOSED)
			return true; 
				
		//reveals clicked tile 
		tileStatus[y][x] = EXPOSED; 
		
		//System.out.println("clicked: " + map.grid[y][x]);
		
		while(isFirstClick){
			if(map.grid[y][x] != 0)	
			{
				map.regenerate();
				System.out.println("regenerated");
			}
			else{
				isFirstClick = false;
				System.out.println("Clicked " + x + " " + y + " mapgrid: " + map.grid[y][x]);
				System.out.print(map + "\n");
			}
		}

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
	
	
	
	public void flag(int x, int y)
	{
		minesLeft--; 
		if(tileStatus[y][x] != HIDDEN)
			return; 
		
		
			picker.clicked(x,y); 
		
		
		tileStatus[y][x] = FLAGGED; 
			
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
	 * returns string representation of minesweepers grid revealing revealed parts in mapgrid while hiding unreleavled. 
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
