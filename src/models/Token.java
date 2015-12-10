package models;


/**
 * 
 *  Token is a presentation of a tokenized area in a grid with a sequel of characters that present the surrounding tile in a grid. 
 *  
 *  
 * 	Each Token is paired to a Pattern based on estimated hidden attributes. The pair helps the token predict whats in it. 
 * 
 * 
 * 
 * @author KLD
 *
 */
public class Token 
{
	/**
	 * sequel of charachters around the tile 
	 */
	String token; 
	
	/**
	 * sum of exposed numbers 
	 */
	public int sum; 
	/**
	 * number of mines 
	 */
	public int mine; 
	/**
	 * number of borders 
	 */
	public int border; 
	
	/**
	 * number of hidden tiles 
	 */
	public int hidden; 
	
	/**
	 * number of empty (zero) tiles 
	 */
	public int empty; 
	
	/**
	 * coordinates 
	 */
	public int x, y; 
	
	/**
	 * next token 
	 */
	public Token next; 
	
	/**
	 * paired pattern
	 */
	public Pattern pair; 
	
	public int numberCounter; 
	
	/**
	 * Initializes token and calculates hidden, mines, border, sum, and empty.
	 * @param s
	 * @param x
	 * @param y
	 */
	public Token(String s, int x, int y)
	{
		token = s; 
		this.x = x; 
		this.y = y; 
		pair = null; 
		
		numberCounter = 1; 
		
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
				else 
					numberCounter++; 
			}
		}
		
	}
	
	/**
	 * @return average sum of exposed numbers 
	 */
	public int averageSum()
	{
		return ((8*sum)/(countNumbers())); 
	}
	
	/**
	 * Estimated sum of all tiles based on average and hidden/border tiles  
	 * @return estimated sum 
	 */
	public int estemateMaxSum()
	{
		return averageSum() + (hidden+border)/2; 
	}
	 
	/**
	 * Estimated number of mine of all tiles based on mines and hidden tiles  
	 * @return estimated mines  
	 */
	public int estemateMaxMine()
	{
		return mine + (hidden/3 + 1); 
	}
	
	/**
	 * @return number of exposed non-zero numbers 
	 */
	public int countNumbers()
	{
		return numberCounter; 
		//return 8 - (mine+hidden+border+empty); 
	}
	
	
	/**
	 * @return percentage of hidden tiles 
	 */
	public float uncertinty()
	{
		return  (((float)hidden)/8);
	}
	
	
	/**
	 * Adds or updates token based on coordinates. If added token is new, it'll get appended. Otherwise,
	 * it'll replace old token with the same x,y coordinates
	 * @param t token to add 
	 */
	public void append(Token t)
	{
		 if(next == null)
			{
				this.next = t; 
			}
		else if(next.x == t.x && next.y == t.y)
		{
			Token p; 
			if(next==null)
				p = null; 
			else 
				p = next.next; 
			
			this.next = t; 
			t.next = p; 
		}
		else 
		{
			next.append(t);
		}	
	}
	
	/**
	 * @param x coordinates to check
	 * @param y coordinates to check
	 * @return true if both coordinates match 
	 */
	public boolean isClicked(int x, int y)
	{	
		return (this.x == x && this.y == y); 
	}
	
	/**
	 * @return a string  of 3x3 grid presentation of token along with pair as well as token's stats attached at the bottom
	 */
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
