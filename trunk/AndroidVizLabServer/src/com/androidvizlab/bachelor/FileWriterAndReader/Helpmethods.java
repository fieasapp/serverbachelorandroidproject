package com.androidvizlab.bachelor.FileWriterAndReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Jakob
 */
public class Helpmethods implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Formats a line of text to a specified number of words.
	 * Any remaining words gets removed from the end of the sentence.
	 * */
	public static String formatSentence(String sentence, int numOfWords, int offset) {
		String[] temp = sentence.split(" ");
		String[] t = removeEmptyElements(temp);
		String res = "";
		if (numOfWords <= t.length) {
			for (int i = 0; i < numOfWords; i++) {
				if (i+offset<t.length) {
					res += t[i+offset] + " ";
				}
			}
		}
		return res.trim();
	}
	
	public static String[] removeEmptyElements(String[] array){
		
		List<String> l = new ArrayList<String>();
		
		for(String s : array){
			if(s != null && s.length() > 0){
				l.add(s);
			}
		}
		
		return l.toArray(new String[l.size()]);
	}
	

	

}
