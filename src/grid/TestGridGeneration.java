package grid;

import models.Pattern;

public class TestGridGeneration {

	public static void main(String[] args) 
	{
		//MapGrid g = new MapGrid(30, 20, 50); 
		
		
		//System.out.print(g);

		String pat = "01211111";
		String pat2 = "11012111";
		
		Pattern patObj = new Pattern(pat);
		Pattern patObj2 = new Pattern(pat2);
		
		System.out.println("Sum of first one " + patObj.getSum());
		System.out.println("Sum of first two " + patObj2.getSum());
		System.out.println("Match score " + patObj.match(patObj2));
	}

}
