package com.revature;

import java.util.ArrayList;
import java.util.Arrays;

public class Palindromes {

	public static void main(String[] args) {
		ArrayList<String> words = new ArrayList<String>();
		words.addAll(Arrays.asList("karan", "madam", "tom", "civic", "radar", "jimmy", "kayak", "john",  "refer", "billy", "did"));
		isDrome(words);
	}
	public static void isDrome(ArrayList<String> words){
		ReverseString rev = new ReverseString();
		ArrayList<String> dromes = new ArrayList<String>();
		for(String word : words) {
			if (word.equals(rev.reverseStr(word)))
				dromes.add(word);
		}
		System.out.println(Arrays.toString(dromes.toArray()));
	}
}