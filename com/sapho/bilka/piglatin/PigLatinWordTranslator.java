package com.sapho.bilka.piglatin;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class that implements pig-latin translation {@see PigLatin} for a single word .
 * 
 * @author tomas
 *
 */
class PigLatinWordTranslator {
	
	private static final String APOSTROPHE = "'";
	
	private static final String AY = "ay";
	private static final String WAY = "way";

	private static final List<String> VOWELS = Arrays.asList("a", "e", "i", "o", "u");
	
	/*
	 * End of sentence symbols matcher.
	 */
	private static Pattern sentenceEndMatcher = Pattern.compile("[.!?;:]$");
	
	/*
	 * Given end of sentence character, if present.
	 */
	private char sentenceEndChar;
	
	/*
	 * Position of apostrophe in the word, -1 if apostrophe is not present
	 */
	private int apostrophePosition;
	
	/*
	 * Original word
	 */
	private final String word;
	
	/*
	 * This is the original word in lower case stripped from possible punctuation characters.
	 */
	private String cleanedWord;
	
	
	public PigLatinWordTranslator(String word) {
		this.word = word;
		setTranslator();
	}

	
	/**
	 * Translates associated word to pig-latin.
	 * 
	 * @return pig-latin version of the given word
	 */
	public String getTranslation() {
		
		if (word.toLowerCase().endsWith(WAY)) {
			return word;
		}
		
		if (startsWithVowel(cleanedWord)) {
			return applyDescriptor(cleanedWord + WAY);
		}
		
		return applyDescriptor(cleanedWord.substring(1) + cleanedWord.substring(0,1) + AY);
	}
	
	
	private void setTranslator() {
		
		cleanedWord = word;
		
		apostrophePosition = cleanedWord.indexOf(APOSTROPHE);
		if (apostrophePosition != -1) {
			cleanedWord = cleanedWord.substring(0,apostrophePosition) + cleanedWord.substring(apostrophePosition+1);
		}
		
		if (sentenceEndMatcher.matcher(cleanedWord).find()){
			int length = cleanedWord.length();
			sentenceEndChar = cleanedWord.charAt(length-1);
			cleanedWord = cleanedWord.substring(0, word.length()-1);
		}
		
		cleanedWord = cleanedWord.toLowerCase();
	}


		
	/*
	 * Applies information about punctuation and case letters of the original word to given argument.
	 * 
	 * For example for original word "Can’t" the argument is expected to be "antcay" and the result return value will be "Antca’y".
	 * 
	 * @param pigTailWord
	 * @return modified word based on original word punctuation
	 */
	private String applyDescriptor(String pigTailWord) {
		
		StringBuilder sb = new StringBuilder(pigTailWord);
		
		if (apostrophePosition != -1) {
			sb.insert(apostrophePosition, APOSTROPHE);
		}
		
		if (sentenceEndChar != 0) {
			sb.append(sentenceEndChar);
		}
		
		char[] wordCharArray = word.toCharArray();
		for (int i = 0; i < wordCharArray.length; i++) {
			if (Character.isUpperCase(wordCharArray[i])) {
				char upperCase = Character.toUpperCase(sb.charAt(i));
				sb.setCharAt(i, upperCase);
			};
		}
		
		return sb.toString();
	}
	
	
	
	private boolean startsWithVowel(String word) {
		return VOWELS.contains(word.substring(0, 1));
	}
}


