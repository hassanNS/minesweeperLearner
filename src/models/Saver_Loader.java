package models;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * 
 * @author Nazmul
 */
public class Saver_Loader {

	public static void saveHashTable(PatternHash hashTable, String fileName)
	{
		try{
			FileOutputStream fileOut;
			ObjectOutputStream out;
			fileOut = new FileOutputStream(fileName);
			out = new ObjectOutputStream(fileOut);
		 	out.writeObject(hashTable);
		 	out.close();
		 	fileOut.close();
		} catch(Exception e){}
	}
	
	public static PatternHash loadHashTable(String fileName)
	{
		try{
			FileInputStream fileIn;
			ObjectInputStream in;
			fileIn = new FileInputStream(fileName);
			in = new ObjectInputStream(fileIn);
			PatternHash p = (PatternHash) in.readObject();
			in.close();
			return p;
		}
		catch(Exception e){
			return null;
		}		
	}
}
