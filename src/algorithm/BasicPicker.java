package algorithm;

import models.Pattern;
import models.PatternHash;
import models.Token;

public class BasicPicker 
{
	public PatternHash list; 
	public Token root; 
	
	
	public BasicPicker(PatternHash ph)
	{
		list = ph; 
	}

	
	public void addToken(Token token)
	{
		//find token's best pattern pair
		int hightestScore = -1; 
		
		token.pair = list.getPat(token.mine,token.averageSum()); 
		
		
		int dick = 1; 
		while(token.pair == null)
		{
			token.pair = list.getPat(token.mine,token.averageSum()+ dick++); 
		}
		
		if(token.pair == null)
			System.out.println("pussy ass token.pair is null");
		
		
		System.out.println("Paired Token: \n" + token);
		
		//System.out.println("Search range: mine:"+token.mine + "-"+token.estemateMaxMine());
		//System.out.println("\tSum:"+token.averageSum() + "-"+token.estemateMaxSum());
		
		for(int mine = token.mine; mine < token.estemateMaxMine(); mine++)
		{
			for(int sum = token.averageSum(); sum < token.estemateMaxSum(); sum++)
			{
				Pattern pointer = list.getPat(mine, sum); //  
				
				if(pointer==null)
					continue; 
				
			
				while(pointer.next != null)
				{
					pointer = pointer.next; 
					
					if(pointer.matchToken(token) >= hightestScore)
					{
						if(token.pair == null)
							System.out.println("bich as token.pair is null");
						
						
						if(pointer.strength() >  token.pair.strength())
						{
							token.pair = pointer; 
							hightestScore = pointer.matchToken(token); 
						}
					}
				}//end while
			}//end sum 
		}//end mine
		
		
		
		if(root == null)
			root = token;
		else
			root.append(token); 
		
		if(token.pair == null)
			System.out.println("bich token.pair is a missing");
		else
			System.out.println("token.pair is a found: \n" + token.pair + "\n\n" + token);
	
	}//end add
	
	public void clicked(int x, int y)
	{
		if(root.clicked(x, y))
		{
			root = root.next; 
			return; 
		}
		
		Token  p = root; 
		
		while(p != null)
		{
			if(p.next !=null)
				if(p.next.clicked(x, y))
				{
					if(p.next.next==null)
						p.next = null; 
					else
						p.next = p.next.next; 
					
					return; 
				}
		p = p.next; 
		}
			System.out.println("no token was removed");
	}
	
	
	public Token pickToken()
	{
		if(root == null)
		{
			//PSYCH
			return new Token("",-1,-1); 
		}
		
		Token picked = root; 
		Token pointer = root.next; 
		
		
		while(pointer != null)
		{
			if(picked.hidden >= pointer.hidden)
			{
				System.out.println(picked);
				if(picked.pair==null)
					System.out.println("fking picked.pair is null");
				if(pointer.pair==null)
					System.out.println("fking pointer.pair is null");
				
				if(picked.pair.strength() < pointer.pair.strength())
					picked = pointer; 
			}
			pointer = pointer.next; 
		}
		
		
		return picked; 
	}
	
	
	
}
