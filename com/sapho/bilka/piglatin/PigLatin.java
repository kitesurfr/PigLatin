package com.sapho.bilka.piglatin;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;

/**
 * Orchestrates pig-latin translation for english language (see {@link https://en.wikipedia.org/wiki/Pig_Latin}}).
 * 
 * Rules {@see PigLatinWordTranslator}:
 * 
 * Words that start with a consonant have their first letter moved to the end of the word and the letters “ay” added to the end. 
 * Hello becomes Ellohay
 *
 * Words that start with a vowel have the letters “way” added to the end. 
 * apple becomes appleway
 *
 * Words that end in “way” are not modified. 
 * stairway stays as stairway
 *
 * Punctuation must remain in the same relative place from the end of the word. 
 * can’t becomes antca’y 
 * end. becomes endway.
 *
 * Hyphens are treated as two words .
 * this-thing becomes histay-hingtay
 *
 * Capitalization must remain in the same place.
 * Beach becomes Eachbay McCloud becomes CcLoudmay
 * 
 * @author tomas
 */
public class PigLatin {
	
	private static final String EMPTY_SPACE = " ";

	private static final String HYPHEN = "-";

	/**
	 * Tokenize give string and return a pig-latin translation.
	 * 
	 * @param text
	 * @return translated text in pig-latin
	 */
	public String translate(String text) {
		
		StringBuilder sb = new StringBuilder();
		
		try (Reader r = new StringReader(text)) {
		
			StreamTokenizer st = new StreamTokenizer(r);
			//do not consider "'" character as a delimiter
			st.wordChars('\'', '\'');
			while(st.nextToken() != StreamTokenizer.TT_EOF){
				
				//just add numbers untouched back to the translated text
				if (st.ttype == StreamTokenizer.TT_NUMBER) {
					sb.append(st.nval);
					continue;
				} 
					
				if (st.ttype == StreamTokenizer.TT_WORD) {
					//We do not receive the delimiter characters from StreamTokenizer 
					//(in our case it is a whitespace). So we are adding the whitespace manually.
					sb.append(EMPTY_SPACE);
					sb.append(translateWord(st.sval));
					continue;
				} else {
					//append whatever unrecognized character there is
					sb.append(((char)st.ttype));
				}
			}
			
			//remove first white-space if present
			if (sb.length() > 0) {
				if (EMPTY_SPACE.equals(sb.substring(0, 1))) {
					sb.deleteCharAt(0);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	
	
	private String translateWord(String word) {
	
		if (!word.contains(HYPHEN)) {
			return new PigLatinWordTranslator(word).getTranslation();
		} 
		
		//handle hyphen words recursively
		String[] split = word.split(HYPHEN);
		String translatedWords = "";
		for (String str : split) {
			translatedWords += translateWord(str) + HYPHEN;
		}
		
		//remove last hyphen
		return translatedWords.substring(0, translatedWords.length()-1);
	}



	
}
