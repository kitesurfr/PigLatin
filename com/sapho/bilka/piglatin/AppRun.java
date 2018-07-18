package com.sapho.bilka.piglatin;

public class AppRun {
	
	private static final String APP_CMD_LINE_DESCRIPTION = "Translates text to pig-latin. "
														 + "For custom text translation provide text as a first parameter, "
					                                     + "e.g. java -com.sapho.bilka.piglatin.AppRun \"my custom text\"\n"
														 + "NOTE: only ' character is recognized as an apostrophe character.\n";
	
	private static final String DEFAULT_TEXT = "Hello apple, stairways can't end. This-thing becomes Eachbay McCloud.";
	
	public static void main(String[] args)  {
		
		String text = DEFAULT_TEXT;
		
		if (args == null || args.length == 0 || args[0].length() == 0 ) {
			System.out.println(APP_CMD_LINE_DESCRIPTION);
		} else {
			text = args[0];
		}
		
		System.out.println(text);
		System.out.println();
		System.out.println(new PigLatin().translate(text));
		return;
	}

}
