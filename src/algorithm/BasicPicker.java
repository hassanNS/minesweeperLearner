package algorithm;

import models.Pattern;
import models.PatternHash;
import models.Token;
/**
 * BasicPicker holds a PatternHash and a linkedlist of tokens. It has two jobs: 
 * 
 * First, to add tokens and pair each token with best pattern 
 * Second, picks mostnj reliable token. 
 * 
 * 
 * @author KLD
 *
 */
public class BasicPicker 
{
	/**
	 * Pattern List 
	 */
	public PatternHash list; 
	
	/**
	 * Token List 
	 */
	public Token root; 
	
	/**
	 * sets passed PatternHash
	 * @param ph PatternHash
	 */
	public BasicPicker(PatternHash ph)
	{
		list = ph; 
	}

	/**
	 * 
	 * Finds best pattern pair for the token and then add to list (or update token if exist).
	 *
	 * @param token new/updated token
	 */
	public void addToken(Token token)
	{
		int hightestScore = -1; 
		
		token.pair = list.getPat(token.mine,token.averageSum()); 
		
		int mineStart = 0; 
		int sumStart = 0; 
		
		//sets initial pair 
		while(token.pair == null)
		{
			token.pair = list.getPat(token.mine + mineStart,token.averageSum()+ sumStart++); 
			
			if(token.averageSum()+ sumStart==41)
			{
				sumStart=0;
				mineStart++; 
			}
		}
		
		/*
		 * Picked the best pair requires assumption because token has hidden tiles. 
		 * 
		 * So we try to pick a range of mines and sums to search for best pair based on calculations
		 * provided by Token.estemateMaxMine() and Token.estemateMaxSum() 
		 * 
		 */
		for(int mine = token.mine; mine < token.estemateMaxMine(); mine++)
		{
			//ignore after maxMine
			if (mine > 8)
				continue;
			
			for(int sum = token.averageSum(); sum < token.estemateMaxSum(); sum++)
			{
				//ignore after maxSum 
				if (sum > 40)
					continue;
				
				//Magic happens 
				Pattern pointer = list.getPat(mine, sum); 
				
				if(pointer==null)
					continue; 
				
				//compares the while list of patterns at pointer with the current pait to find the best 
				while(pointer.next != null)
				{
					pointer = pointer.next; 
					
					if(pointer.matchToken(token) > hightestScore)
					{
						token.pair = pointer; 
						hightestScore = pointer.matchToken(token); 
					}
						
					else if(pointer.matchToken(token) ==  hightestScore)
					{
						if(pointer.strength() > token.pair.strength())
						{	
							token.pair = pointer; 
						}
					}
					
				}//end while
			}//end sum 
		}//end mine
		
		
	//add new token to list 
		if(root == null)
			root = token;
		else //append() adds or updates token 
			root.append(token); 
		
	}//end add
	
	
	/**
	 * removed clicked token from the list 
	 * @param x coordinate 
	 * @param y coordinate
	 */
	public void clicked(int x, int y)
	{
		//linked list stuff 
		
		if(root == null)
			return;

		if(root.isClicked(x, y))
		{
			//System.out.println("removed root at "+x +","+y);
			root = root.next; 
			return; 
		}
		
		Token  p = root; 
		
		while(p.next != null)
		{
			if(p.next.isClicked(x, y))
			{
				p.next = p.next.next; 
				return; 
			}
		p = p.next; 
		}//end while 
	}
	
	/**
	 * return token with most exposed non-zero number tiles and strongest pair 
	 * @return
	 */
	public Token pickToken()
	{
		if(root == null)
		{
			//PSYCH
			return null; 
		}
		
		Token picked = root; 
		Token pointer = root.next; 
		
		
		
		while(pointer != null)
		{
			//hidden tiles is unreliable because of borders,  
			if( picked.countNumbers() < pointer.countNumbers())
			{
				picked = pointer; 	
			}
			else if (picked.countNumbers() == pointer.countNumbers())
			{
				if(picked.pair.strength() < pointer.pair.strength())
					picked = pointer; 
			}
			pointer = pointer.next; 
		}
		
		return picked; 
	}
	
	
	
}
