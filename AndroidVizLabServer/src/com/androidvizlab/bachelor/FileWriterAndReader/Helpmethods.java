package com.androidvizlab.bachelor.FileWriterAndReader;

import static com.androidvizlab.bachelor.FileWriterAndReader.Helpmethods.formatSentence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Jakob
 */
public class Helpmethods implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Formats a line of text to a specified number of words. Any remaining
	 * words gets removed from the end of the sentence.
	 * */
	public static String formatSentence(String sentence, int numOfWords,
			int offset) {
		String[] temp = sentence.split(" ");
		String[] t = removeEmptyElements(temp);
		String res = "";
		if (numOfWords <= t.length) {
			for (int i = 0; i < numOfWords; i++) {
				if (i + offset < t.length) {
					res += t[i + offset] + " ";
				}
			}
		}
		return res.trim();
	}

	public static String[] removeEmptyElements(String[] array) {

		List<String> l = new ArrayList<String>();

		for (String s : array) {
			if (s != null && s.length() > 0) {
				l.add(s);
			}
		}

		return l.toArray(new String[l.size()]);
	}

	public static String removeChars(String s) {
		String ret = s.replaceAll(",", "");
		int slength = ret.length();
		if (ret.substring(slength - 1, slength).equals(".")) {
			ret = ret.substring(0, slength - 1);
		}

		return ret;
	}
	
	/**
	 * @param word
	 *            A string that gets compared to the string of every index of
	 *            the list.
	 * @return An ArrayList containing all the indices of the input string.
	 */
	public static ArrayList<Integer> findIndices(String word, ArrayList<String> list) {
		ArrayList<Integer> al = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			if (formatSentence(list.get(i), 1, 0)
					.equals(word)) {
				al.add(i);
			}
		}
		return al;
	}
	


}
