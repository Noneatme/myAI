package me.noneat.myai.ai;

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
	// -- //
	// -- || Constructor
	// -- \\
	public cAIManager()
	{

	}

	// -- //
	// -- || loadCategories
	// -- \\
	private void loadCategories()
	{
		this.QUESTION_CATEGORIES = cAISettings.getDatabase().executeQuery("SELECT * FROM " + cDatabase.TABLE_QUESTIONS_CATEGORIES + ";");
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
			ResultSet result = cAISettings.getDatabase().executeQuery("SELECT sAnswer, iCategory, iAnswerTo FROM " + cDatabase.TABLE_STATEMENT_RESPONSES + " WHERE iAnswerTo = '" + response + "' ORDER BY RANDOM() LIMIT 1;");

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
}
