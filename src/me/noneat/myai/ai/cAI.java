package me.noneat.myai.ai;

import me.noneat.myai.cAISettings;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Noneatme on 12.08.2015.
 */

// -- //
// -- || Class AI
// -- \\
public class cAI extends Thread
{
	// -- //
	// -- || PVars
	// -- \\
	public String VERSION = "0.0.1";

	private String sDateCreated     = null;
	private String sName            = null;

	private String sOwner           = null;
	private String sNextAnswer      = "";
	private String sInput           = "";
	private boolean bHasToThing     = false;
	private boolean bReady          = false;

	private int iLastResponseID     = 0;



	// -- //
	// -- || Constructor
	// -- \\
	public cAI()
	{

	}
	// -- //
	// -- || getSentenceCategory
	// -- \\

	public int getSentenceCategory(String sSentence)
	{
		boolean question = cSentenceUtils.hasSentenceQuestionmark(sSentence);
		int contex = 2;
		if(question)
		{
			contex = 2;
		}
		else
		{
			contex = 3;
		}
		return 2; // Unclear Expression
	}

	// -- //
	// -- || Think
	// -- \\
	public boolean think() throws SQLException
	{
		try
		{
			if (!this.isReady())
				return false;

			String sAnswer = "";
			String sText = this.sInput;
			boolean bAnswerFound = false;
			int iAnswerType = 1;
			boolean question = cSentenceUtils.hasSentenceQuestionmark(sText);
			int iCategory = this.getSentenceCategory(sText);

		/*
			Answer Types:
			1 - Normal answer - Dot.
			2 - Question Answer - Questionmark
			3 - Loud answer - Exclamation mark
			4 - Very loud answer - 3 Exclamation mark
			5 - No mark
		 */

			if (!bAnswerFound)
			{
				ResultSet answerSet;
				if (question)
				{
					answerSet = cAISettings.aiManager.getRandomSentenceFromCategory(2);       // Question Answer
				}
				else
				{
					answerSet = cAISettings.aiManager.getRandomSentenceFromCategory(3);       // Statement Answer
				}

				sAnswer = answerSet.getString(1);
				this.iLastResponseID = answerSet.getInt(2);

				sAnswer = cSentenceUtils.applySentenceTypeToEnd(sAnswer, iAnswerType);
				sAnswer = cSentenceUtils.putAINameIntoString(sAnswer);
			}

			this.setNextAnswer(sAnswer);
			this.speak();
			this.bHasToThing = false;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		return false;
	}

	// -- //
	// -- || setInput
	// -- \\
	public void setInput(String sInput)
	{
		this.sInput         = sInput;
		this.bHasToThing    = true;
	}

	// -- //
	// -- || setNextAnswer
	// -- \\
	public void setNextAnswer(String sAnswer)
	{
		this.sNextAnswer = sAnswer;
	}
	// -- //
	// -- || Run
	// -- \\
	public void speak()
	{
		System.out.println(this.sNextAnswer);
	}

	// -- //
	// -- || Run
	// -- \\
	public synchronized void run()
	{
		try
		{
			System.out.println("AI Started. Awaiting Input");
			this.bReady = true;
			while (true)
			{
				if(this.bHasToThing)
				{

					this.think();
				}
				sleep(1000);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	// -- //
	// -- || GETTER AND SETTER
	// -- \\
	public String getsName()
	{
		return sName;
	}

	public void setsName(String sName)
	{
		this.sName = sName;
	}

	public String getsOwner()
	{
		return sOwner;
	}

	public void setsOwner(String sOwner)
	{
		this.sOwner = sOwner;
	}

	public String getsDateCreated()
	{
		return sDateCreated;
	}

	public void setsDateCreated(String sDateCreated)
	{
		this.sDateCreated = sDateCreated;
	}
	public boolean isReady()
	{
		return this.bReady;
	}
	public void setReady(boolean bBool)
	{
		this.bReady = bBool;
	}
	public int getiLastResponseID()
	{
		return iLastResponseID;
	}

	public void setiLastResponseID(int iLastResponseID)
	{
		this.iLastResponseID = iLastResponseID;
	}
}
