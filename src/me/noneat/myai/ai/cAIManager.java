package me.noneat.myai.ai;

import me.noneat.myai.ai.statement.cRandomTimeStatementManager;
import me.noneat.myai.cAISettings;
import me.noneat.myai.sql.cDatabase;

import java.sql.ResultSet;


/**
 * Created by Noneatme on 13.08.2015.
 * Version: 1.0.0
 * Purpose: Manages the AI's classes and behaviour
 * License: See top folder / document root
 */
public class cAIManager
{

	// -- //
	// -- || PVars
	// -- \\
	private ResultSet QUESTION_CATEGORIES;
	private ResultSet STATEMENT_CATEGORIES;

	private boolean use_random          = false;    // Make this true if you want to use random sentences instead of the oldest sentences which have never been said.

	private cRandomTimeStatementManager randomTimeStatementManager;


	// -- //
	// -- || Constructor
	// -- \\
	public cAIManager()
	{
		this.randomTimeStatementManager = new cRandomTimeStatementManager();
		this.randomTimeStatementManager.start();

	}

	// -- //
	// -- || loadCategories
	// -- \\
	private void loadCategories()
	{
		this.QUESTION_CATEGORIES    = cAISettings.getDatabase().executeQuery("SELECT * FROM " + cDatabase.TABLE_QUESTIONS_CATEGORIES + ";");
		this.STATEMENT_CATEGORIES   = cAISettings.getDatabase().executeQuery("SELECT * FROM " + cDatabase.TABLE_STATEMENT_CATEGORIES + ";");
	}

	// -- //
	// -- || getRandomSentenceFromCategory
	// -- \\
	public ResultSet getRandomSentenceFromCategory(int category)
	{
		ResultSet sentence = null;

		try
		{
			ResultSet result = cAISettings.getDatabase().createStatement().executeQuery("SELECT sText, iResponseID FROM " + cDatabase.TABLE_QUESTIONS_RESPONSES + " WHERE iCategory = '" + category + "' ORDER BY RANDOM() LIMIT 1;");

			if(!result.isClosed())
				return result;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return sentence;
	}

	// -- //
	// -- || getRandomSentenceFromResponses
	// -- \\
	public ResultSet getRandomSentenceFromResponses(int response)
	{
		ResultSet sentence = null;

		try
		{
			ResultSet result = cAISettings.getDatabase().executeQuery("SELECT sText, iCategory, iAnswerTo FROM " + cDatabase.TABLE_QUESTIONS_RESPONSES + " WHERE iAnswerTo = '" + response + "' ORDER BY RANDOM() LIMIT 1;");
			return result;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return sentence;
	}
	// -- //
	// -- || getRandomSentenceFromCategory
	// -- \\
	public ResultSet getRandomStatementSentenceFromCategory(int category)
	{
		ResultSet sentence = null;

		try
		{
			ResultSet result = cAISettings.getDatabase().createStatement().executeQuery("SELECT sAnswer, iCategory FROM " + cDatabase.TABLE_STATEMENT_RESPONSES + " WHERE iCategory = '" + category + "' ORDER BY RANDOM() LIMIT 1;");

			if(!result.isClosed())
				return result;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return sentence;
	}

	// -- //
	// -- || getRandomSentenceFromResponses
	// -- \\
	public ResultSet getRandomStatementSentenceFromResponses(int response)
	{
		ResultSet sentence = null;

		try
		{
			ResultSet result = null;
			if(this.use_random)
				result = cAISettings.getDatabase().executeQuery("SELECT sAnswer, iCategory, iAnswerTo FROM " + cDatabase.TABLE_STATEMENT_RESPONSES + " WHERE iAnswerTo = '" + response + "' ORDER BY RANDOM() LIMIT 1;");
			else
			{
			/*
				ResultSet resultFirst = cAISettings.getDatabase().executeQuery("SELECT iID FROM " + cDatabase.TABLE_STATEMENT_RESPONSES + " WHERE iAnswerTo = '" + response + "' ORDER BY iTimestamp ASC LIMIT 1;");
				int id = resultFirst.getInt(1);

				result = cAISettings.getDatabase().executeQuery("SELECT sAnswer, iCategory, iAnswerTo FROM " + cDatabase.TABLE_STATEMENT_RESPONSES + " WHERE iID = '" + id + "';");
				cAISettings.getDatabase().executeUpdate("UPDATE " + cDatabase.TABLE_STATEMENT_RESPONSES + " SET iTimestamp = " + System.currentTimeMillis() + " WHERE iID = '" + id + "';");
				*/
				result = cAISettings.getDatabase().executeQuery("SELECT sAnswer, iCategory, iAnswerTo FROM " + cDatabase.TABLE_STATEMENT_RESPONSES + " WHERE iAnswerTo = '" + response + "' ORDER BY RANDOM() LIMIT 1;");

			}
			return result;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return sentence;
	}
	// -- //
	// -- || load
	// -- \\
	public void load()
	{
		this.loadCategories();
	}

	// -- //
	// -- || getRandomTimeStatementManager
	// -- \\
	public cRandomTimeStatementManager getRandomTimeStatementManager()
	{
		return randomTimeStatementManager;
	}

	// -- //
	// -- || DBUpdateStatementResponseTime
	// -- \\
	public void DBUpdateStatementResponseTime(int iID)
	{
		cAISettings.getDatabase().executeUpdate("UPDATE " + cDatabase.TABLE_STATEMENT_RESPONSES + " SET iTimestamp = " + System.currentTimeMillis() + " WHERE iID = " + iID + ";");
	}

	// -- //
	// -- || DBUpdateQuestionResponseTime
	// -- \\
	public void DBUpdateQuestionResponseTime(int iID)
	{
		cAISettings.getDatabase().executeUpdate("UPDATE " + cDatabase.TABLE_QUESTIONS_RESPONSES + " SET iTimestamp = " + System.currentTimeMillis() + " WHERE iID = " + iID + ";");
	}
}
