package me.noneat.myai.sql;

import me.noneat.myai.cMain;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by Noneatme on 18.08.2015.
 */
public class cSQLScriptedQuestionManager extends cSQLAnswerFinder
{
	private int iID;
	private String sQuestion;

	public cSQLScriptedQuestionManager(String sQuestion, int iQuestionID)
	{
		this.iID        = iQuestionID;
		this.sQuestion  = sQuestion;

	//	this.start();
	}
	// -- //
	// -- || RUN
	// -- \\
	@Override
	public synchronized void run()
	{
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
		}

		if(cMain.ai.isReady())
		{
			cMain.ai.setNextAnswer(this.getAnswer());
			cMain.ai.speak();
		}
	}
}
