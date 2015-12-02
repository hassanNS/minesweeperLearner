package models;

import grid.MineSweeper;

public class Token 
{
	String token; 
	
	public int sum; 
	public int mine; 
	public int border; 
	public int hidden; 
	public int empty; 
	public float certinty;
	
	public int x, y; 
	
	public Token next; 
	
	public Pattern pair; 
	
	
	public Token(String s, int x, int y)
	{
		token = s; 
		this.x = x; 
		this.y = y; 
		pair = null; 
		
		//get string stats: hidden, mine, border, sum
		for(char c : s.toCharArray())
		{
			if(c == Tile.HIDDEN) 
			{
				hidden++; 
			}
			else if(c == Tile.MINE)
			{
				mine++; 
			}
			else if(c == Tile.BORDER)
			{
				border++; 
			}
			else if(Character.isDigit(c))
			{
				int n = Character.getNumericValue(c); 
				sum+= n; 
				if(n==0)
					empty++; 
			}
		}
		
		// Calculate how certain we are based on how many tiles are exposed
		certinty = (float) (1.0 - (hidden/8));
	}
	
	public int averageSum()
	{
		return ((8*sum)/(8-hidden)); 
	}
	
	public int estemateMaxSum()
	{
		return averageSum() + (hidden+border)/2; 
	}
	
	public int estemateMaxMine()
	{
		return mine + (hidden/3 + 1); 
	}
	
	public int countNumbers()
	{
		return 8 - (mine+hidden+border+empty); 
	}
	
	public void append(Token t)
	{
		 if(next == null)
			{
				//System.out.println("added new token: \n" + t);
				this.next = t; 
			}
		else if(next.x == t.x && next.y == t.y)
		{
			
			//System.out.println("updated " + x + " " + y);
			//update my score and my token
			
			Token p; 
			if(next==null)
				p = null; 
			else 
				p = next.next; 
			
			this.next = t; 
			
			t.next = p; 
			
			//this.sum = t.sum; 
			//this.mine = t.mine; 
			//this.border = t.border; 
			//this.pair  = t.pair; 
			//this.token = t.token; 
		}
		else 
		{
			next.append(t);
		}	
	}
	
	public boolean clicked(int x, int y)
	{	
		return (this.x == x && this.y == y); 
	}
	
	public String toString()
	{
		String [] k = token.split("");

		if(pair == null)
		{
			System.out.println("dickhead pair is null");
		}
		String head; 
		String belly;
		String feet;
		try{
		 head = k[0]+k[1]+k[2]+"\t"+pair.asGrid()[0][0]+pair.asGrid()[0][1]+pair.asGrid()[0][2]; 
		 belly= k[7]+" "+ k[3]+"\t"+pair.asGrid()[1][0]+pair.asGrid()[1][1]+pair.asGrid()[1][2]; 
		 feet = k[6]+k[5]+k[4]+"\t"+pair.asGrid()[2][0]+pair.asGrid()[2][1]+pair.asGrid()[2][2]; 
		} catch(Exception e)
		{
			head = k[0]+k[1]+k[2];
			 belly= k[7]+" "+ k[3];
			 feet = k[6]+k[5]+k[4];
		}
		
	
		return (head+"\n"+belly+"\n"+feet+"\n sum:" + sum + " hidden:"+hidden + " border:" + border);
	}
	
	
}
