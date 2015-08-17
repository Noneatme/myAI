package me.noneat.myai.ai;

import me.noneat.myai.cMain;

/**
 * Created by Noneatme on 13.08.2015.
 */
// -- //
// -- || Class: SentenceUtils
// -- \\
public class cSentenceUtils
{
	// -- //
	// -- || hasSentenceQuestionmark
	// -- \\
	public static boolean hasSentenceQuestionmark(String sentence)
	{
		if(sentence.contains("?"))
		{
			return true;
		}
		return false;
	}

	// -- //
	// -- || putAINameIntoString
	// -- \\
	public static String putAINameIntoString(String sentence)
	{
		return sentence.replaceAll("%s", cMain.ai.getAIName());
	}

	// -- //
	// -- || isSentence
	// -- \\
	public static boolean isSentence(String string)
	{
		if(string.contains(" "))
		{
			return true;
		}
		return false;
	}

	/*
		Answer Types:
		1 - Normal answer - Dot.
		2 - Question Answer - Questionmark
		3 - Loud answer - Exclamation mark
		4 - Very loud answer - 3 Exclamation mark
		5 - No mark
	 */
	// -- //
	// -- || applySentenceTypeToEnd
	// -- \\
	public static String applySentenceTypeToEnd(String sentence, int type)
	{
		String sString = "";

		switch(type)
		{
			case 1:
				sString = sentence + ".";
				break;
			case 2:
				sString = sentence + "?";
				break;
			case 3:
				sString = sentence + "!";
				break;
			case 4:
				sString = sentence + "!!!";
				break;
			case 5:
				sString = sentence;
				break;
			default:
				break;
		}

		return sString;
	}

	// -- //
	// -- || getDatabaseReadyString
	// -- \\
	public static String getDatabaseReadyString(String sString, boolean ignoreNone)
	{
		if(ignoreNone)
		{
			// TODO: APPLY CASE SENSITIVE OPTION FOR OUTPUT INTO LEARN MODE
			sString = sString.toLowerCase();

			return sString.replaceAll("[-+.^:,?]", "");
		}
		return sString;
	}

	// -- //
	// -- || splitSentenceIntoWords
	// -- \\
	public static String[] splitSentenceIntoWords(String sSentence)
	{
		String[] words = sSentence.split("\\s+");
		for(int i = 0; i < words.length; i++)
			words[i] = words[i].replaceAll("[^\\w]", "");
		return words;
	}
}
