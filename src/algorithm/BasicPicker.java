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
		//System.out.println("Adding token: \n" + token);
		//find token's best pattern pair
		int hightestScore = -1; 
		
		token.pair = list.getPat(token.mine,token.averageSum()); 
		
		int mineStart = 0; 
		int sumStart = 0; 
		
		while(token.pair == null)
		{
			token.pair = list.getPat(token.mine + mineStart,token.averageSum()+ sumStart++); 
			
			if(token.averageSum()+ sumStart==41)
			{
				sumStart=0;
				mineStart++; 
			}
				
		}
		
		if(token.pair == null)
			System.out.println("pussy ass token.pair is null");
		
		
		//System.out.println("Paired Token: \n" + token);
		
		//System.out.println("Search range: mine:"+token.mine + "-"+token.estemateMaxMine());
		//System.out.println("\tSum:"+token.averageSum() + "-"+token.estemateMaxSum());
		
		for(int mine = token.mine; mine < token.estemateMaxMine(); mine++)
		{
			if (mine > 8)
				continue;
			
			for(int sum = token.averageSum(); sum < token.estemateMaxSum(); sum++)
			{
				if (sum > 40)
					continue;
				
				Pattern pointer = list.getPat(mine, sum); //  
				
				if(pointer==null)
					continue; 
				
			
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
							//hightestScore = pointer.matchToken(token); 
						}
					}
					
				}//end while
			}//end sum 
		}//end mine
		
		
		
		if(root == null)
			root = token;
		else
			root.append(token); 
		
		//if(token.pair == null)
		//	System.out.println("bich token.pair is a missing");
		//else
		//	System.out.println("token.pair is a found: \n" + token.pair + "\n\n" + token);
	
	}//end add
	
	public void clicked(int x, int y)
	{
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
				//System.out.println("removed token at "+x +","+y);
				p.next = p.next.next; 
				return; 
			}
		p = p.next; 
		}
			//System.out.println("no token was removed");
	}
	
	
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
			//System.out.println("picker:\n" + pointer);
			
			if( picked.countNumbers() < pointer.countNumbers())
				{

				picked = pointer; 
				///System.out.println(picked);
				//if(picked.pair==null)
				//	System.out.println("fking picked.pair is null");
				//if(pointer.pair==null)
				//	System.out.println("fking pointer.pair is null");
				
				//if(picked.pair.strength() < pointer.pair.strength())
					//picked = pointer; 
			}
			else if (picked.countNumbers() == pointer.countNumbers())
			{
				if(picked.pair.strength() < pointer.pair.strength())
					picked = pointer; 
			}
			
			pointer = pointer.next; 
		}
		
		//System.out.println("picked:" + (picked.hidden+picked.border));
		
		return picked; 
	}
	
	
	
}
