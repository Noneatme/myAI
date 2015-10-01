package me.noneat.myai.sql;

import me.noneat.myai.ai.cSentenceUtils;
import me.noneat.myai.ai.questions.cAIQuestion;
import me.noneat.myai.ai.questions.cAIQuestionAskQuestion;
import me.noneat.myai.cMain;
import me.noneat.myai.web.cGoogleSearch;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by Noneatme on 18.08.2015.
 * Version: 1.0.0
 * Purpose: Manages the scriptet questions
 * License: See top folder / document root
 */
public class cSQLScriptedQuestionManager extends cSQLAnswerFinder
{
	private int iID;
	private String sQuestion;
	private String sInput;



	public cSQLScriptedQuestionManager(String sQuestion, int iQuestionID, String sInput)
	{
		this.iID        = iQuestionID;
		this.sQuestion  = sQuestion;
		this.sInput     = sInput;

	//	this.start();
	}
	// -- //
	// -- || RUN
	// -- \\
	@Override
	public void run()
	{
		Thread.currentThread().setName("Thread_cSQLScriptedQuestionManager");

		String useString        = this.sInput;
		useString               = cSentenceUtils.getDatabaseReadyString(useString, true);
		useString               = useString.replace("can you search for ", "");
		useString               = useString.replace("what is ", "");
		cAIQuestion question    = null;

		switch(this.iID)
		{
			case 18:        // What Time is it
			{
				this.setAnswer("It's " + String.format("%tR", new Date()) + ".");
				break;
			}
			case 20:        // What Date is it
			{
				this.setAnswer("It's " + ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME));
				break;
			}
			case 28:
			case 27:        // What is
			{
				try
				{
					this.setAnswer("Sure, let me see what I can find.");
					new cGoogleSearch(useString);
				}
				catch(Exception ex)
				{

				}
				break;
			}
			case 40:        // Testquestion
				question = new cAIQuestionAskQuestion(useString, this.iID);
				break;
		}

		if(question != null)
		{
			question.overrideGlobalQuestion();
			this.setAnswer(question.getFirstQuestion());
		}

		if(cMain.ai.isReady())
		{
			cMain.ai.setNextAnswer(this.getAnswer());
			cMain.ai.speak();
		}
	}
}
