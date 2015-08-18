package me.noneat.myai.ai;

import me.noneat.myai.cAISettings;

import java.sql.ResultSet;


/**
 * Created by Noneatme on 13.08.2015.
 */
public class cAIManager
{
	// -- //
	// -- || PVars
	// -- \\
	private ResultSet categories;

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
		this.categories = cAISettings.getDatabase().executeQuery("SELECT * FROM ai_categories;");

	}

	// -- //
	// -- || getRandomSentenceFromCategory
	// -- \\
	public ResultSet getRandomSentenceFromCategory(int category)
	{
		ResultSet sentence = null;

		try
		{
			ResultSet result = cAISettings.getDatabase().createStatement().executeQuery("SELECT sText, iResponseID FROM ai_responses WHERE iCategory = '" + category + "' ORDER BY RANDOM() LIMIT 1;");

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
			ResultSet result = cAISettings.getDatabase().executeQuery("SELECT sText, iCategory, iAnswerTo FROM ai_responses WHERE iAnswerTo = '" + response + "' ORDER BY RANDOM() LIMIT 1;");
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
