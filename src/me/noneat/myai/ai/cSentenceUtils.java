package me.noneat.myai.ai;

import me.noneat.myai.cMain;

/**
 * Created by Noneatme on 13.08.2015.
 */
public class cSentenceUtils
{
	public static boolean hasSentenceQuestionmark(String sentence)
	{
		if(sentence.contains("?"))
		{
			return true;
		}
		return false;
	}

	public static String putAINameIntoString(String sentence)
	{
		return String.format(sentence, cMain.ai.getsName());
	}

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
}
