package me.noneat.myai.ai;

import me.noneat.myai.cAISettings;
import me.noneat.myai.cMain;

import java.sql.PreparedStatement;
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
	private boolean bLearnMode      = false;


	private int iLearnStatusMode    = 0;


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
				ResultSet answerSet = null;


				if (question)
				{
					answerSet = cAISettings.aiManager.getRandomSentenceFromCategory(2);       // Question Answer
				}
				else
				{
					answerSet = cAISettings.aiManager.getRandomSentenceFromCategory(3);       // Statement Answer
				}

				if(answerSet != null)
				{
					sAnswer = answerSet.getString(1);
					this.iLastResponseID = answerSet.getInt(2);
				}
			}

			// LEARN MODE //
			if(this.getLearnMode())
			{
				if(question && this.iLearnStatusMode == 0)
				{
					if (this.iLearnStatusMode == 0)  // A Question
					{
						String sToSave = cSentenceUtils.getDatabaseReadyString(sInput, true);

						sAnswer = "How should I answer this question: " + sToSave;
						iAnswerType = 2;
						this.iLearnStatusMode = 1;

						PreparedStatement stm = cAISettings.getDatabase().createPreparedStatement("INSERT INTO ai_questions (sQuestion) VALUES (?);");
						stm.setString(1, sToSave);
						cAISettings.getDatabase().executeStatement(stm);

						// TODO: GET_LAST_INSERT_ID();
					}
				}
				else
				{
					if(this.iLearnStatusMode == 1) //
					{
						// TODO:
						String sToSave = cSentenceUtils.getDatabaseReadyString(sInput, false);

						System.out.println("Saved: " + sToSave);
						sAnswer = "Ok, Saved. Type in ABORT to cancel this question answers to implement a next question";
						iAnswerType = 1;
						this.iLearnStatusMode = 1;
					}
				}
			}
			else
			{

			}

			sAnswer = cSentenceUtils.applySentenceTypeToEnd(sAnswer, iAnswerType);
			sAnswer = cSentenceUtils.putAINameIntoString(sAnswer);


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
	public void setLearnMode(boolean learnMode)
	{
		this.bLearnMode = learnMode;

		if(this.bLearnMode)
			System.out.println("Learn Mode now active, watch your words\nThe learn mode should be used just for static questions and not for dynamic questions like 'What time is it?'\nPlease note your questions are not case-sensitive and all special characters will be removed in the database.\nUse '%s' as the first argument to get the AI's name into the answer you define.");
		else
			System.out.println("Learn Mode deactivated");
	}
	public boolean getLearnMode()
	{
		return this.bLearnMode;
	}
	public int getLearnStatusMode()
	{
		return iLearnStatusMode;
	}
	public void setLearnStatusMode(int iLearnStatusMode)
	{
		this.iLearnStatusMode = iLearnStatusMode;
	}

}
